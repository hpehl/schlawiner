package org.jboss.schlawiner.client.game;

import java.util.List;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;
import org.jboss.schlawiner.engine.algorithm.OperationAlgorithm;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.DiceValidator;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.jboss.schlawiner.engine.game.Settings;
import org.jboss.schlawiner.engine.score.Score;
import org.jboss.schlawiner.engine.score.Scoreboard;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.joining;

@Controller(route = "/local-game",
    selector = "content",
    component = LocalGameComponentImpl.class,
    componentInterface = LocalGameComponent.class)
public class LocalGameControllerImpl extends AbstractComponentController<Context, LocalGameComponent, HTMLElement>
    implements LocalGameController {

    private Game game;
    private String term;

    @Override
    public void start() {
        Players players = new Players(context.getPlayers());
        Settings settings = context.getSettings();
        Numbers numbers = new Numbers(settings.getNumbers());
        game = new Game(players, numbers, new OperationAlgorithm(), settings);
        component.start(game);
    }

    @Override
    public void dice() {
        if (game.hasNext()) {
            game.next();
            game.dice(new Dice());

            Players players = game.getPlayers();
            Player currentPlayer = players.current();
            Scoreboard scoreboard = game.getScoreboard();
            int currentNumber = game.getNumbers().current();
            int currentIndex = game.getNumbers().index();

            component.clear();
            component.role(game.getDice(), () -> {
                component.message(currentPlayer.getName() + " it's your turn.");
                component.highlight(currentPlayer, currentIndex);
                if (currentPlayer.isHuman()) {
                    component.countdown(context.getSettings().getTimeout(), currentNumber);
                } else {
                    Solution solution = game.solve();
                    Score score = new Score(solution.getTerm(), abs(solution.getValue() - currentNumber));
                    component.message(currentPlayer.getName() + " got " + solution + ".");
                    component.showScore(scoreboard, currentPlayer, currentIndex, score);
                }
            });
        } else {
            String message = "The winner";
            List<Player> winners = game.getScoreboard().getWinners();
            if (winners.size() == 1) {
                message += " is " + winners.get(0).getName();
            } else {
                message += "s are " + winners.stream().map(Player::getName).collect(joining(", "));
            }
            component.message("Game Over. " + message);
        }
    }

    @Override
    public void skip() {
        component.message("Skip not yet implemented");
    }

    @Override
    public void restart() {
        component.message("Restart not yet implemented");
    }

    @Override
    public void back() {
        router.route("/main");
    }

    @Override
    public void setTerm(String term, boolean updateInput) {
        this.term = term;
        boolean[] used = DiceValidator.used(game.getDice(), term);
        component.usage(used);
        if (updateInput) {
            component.showTerm(term);
        }
    }

    @Override
    public void solve() {
        try {
            DiceValidator.validate(game.getDice(), term);

            Players players = game.getPlayers();
            Player currentPlayer = players.current();
            Scoreboard scoreboard = game.getScoreboard();
            int currentNumber = game.getNumbers().current();
            int currentIndex = game.getNumbers().index();
            int difference = game.calculate(term);
            Solution bestSolution = game.getAlgorithm()
                .compute(game.getDice().numbers[0], game.getDice().numbers[1], game.getDice().numbers[2], currentNumber)
                .bestSolution();
            int bestDifference = Math.abs(bestSolution.getValue() - currentNumber);
            if (difference > bestDifference) {
                component.message("Your difference is " + difference + ". " +
                    "The best solution is " + bestSolution + ".");
            } else {
                component.message("Well done, your solution is the best.");
            }
            Score score = new Score(term, difference);
            component.showScore(scoreboard, currentPlayer, currentIndex, score);
        } catch (ArithmeticException e) {
            component.message(e.getMessage());
        }
    }
}
