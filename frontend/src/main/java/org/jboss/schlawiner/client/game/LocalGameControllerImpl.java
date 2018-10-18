package org.jboss.schlawiner.client.game;

import java.util.List;

import com.github.nalukit.nalu.client.component.AbstractComponentController;
import com.github.nalukit.nalu.client.component.annotation.Controller;
import elemental2.dom.HTMLElement;
import org.jboss.schlawiner.client.Context;
import org.jboss.schlawiner.engine.algorithm.OperationAlgorithm;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.game.Calculation;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.DiceValidator;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.jboss.schlawiner.engine.game.Settings;

import static java.util.stream.Collectors.joining;

@Controller(route = "/local-game",
    selector = "content",
    component = LocalGameComponentImpl.class,
    componentInterface = LocalGameComponent.class)
public class LocalGameControllerImpl extends AbstractComponentController<Context, LocalGameComponent, HTMLElement>
    implements LocalGameController {

    private Game game;
    private String term;


    // ------------------------------------------------------ controller lifecycle

    @Override
    public void start() {
        Players players = new Players(context.getPlayers());
        Settings settings = context.getSettings();
        Numbers numbers = new Numbers(settings.getNumbers());
        game = new Game(players, numbers, new OperationAlgorithm(), settings);
        component.start(game, settings);
        component.reset();
        next();
    }


    // ------------------------------------------------------ interface methods

    @Override
    public void dice() {
        Player currentPlayer = game.getPlayers().current();
        int currentNumber = game.getNumbers().current();
        int currentIndex = game.getNumbers().index();

        component.reset();
        component.highlight(currentPlayer, currentNumber, currentIndex);
        component.role(currentPlayer, game.getDice(), () -> {
            if (currentPlayer.isHuman()) {
                component.countdown(context.getSettings().getTimeout());
            } else {
                Solution solution = game.solve();
                component.computer(solution, () -> {
                    component.showScore(game);
                    component.modal(currentPlayer.getName() + " got " + solution + ".", this::next);
                });
            }
        });
    }

    @Override
    public void retry() {
        if (game.retry()) {
            dice();
        }
    }

    @Override
    public void skip() {
        Solution solution = game.solve();
        component.modal("The best solution is " + solution + ". You get " +
                context.getSettings().getPenalty() + " penalty points.",
            () -> {
                game.skip();
                component.showScore(game);
                next();
            });
    }

    @Override
    public void restart() {
        router.route("/local-game");
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
    public void calculate() {
        try {
            String message;
            Calculation calculation = game.calculate(term);
            if (calculation.isBest()) {
                message = "Well done, your solution is the best.";
            } else {
                message = "Your difference is " + calculation.getDifference() + ". " +
                    "The best solution is " + calculation.getBestSolution() +
                    " (difference " + calculation.getBestDifference() + ").";
            }
            component.showScore(game);
            component.modal(message, this::next);
        } catch (ArithmeticException e) {
            component.message(e.getMessage());
        }
    }


    // ------------------------------------------------------ workflow

    private void next() {
        if (game.hasNext()) {
            game.next();
            game.dice(new Dice());

            Player currentPlayer = game.getPlayers().current();
            if (!currentPlayer.isHuman() || context.getSettings().isAutoDice()) {
                dice();
            } else if (currentPlayer.isHuman() && !context.getSettings().isAutoDice()) {
                component.humanTurn(currentPlayer);
            }

        } else {
            String message = "The winner";
            List<Player> winners = game.getScoreboard().getWinners();
            if (winners.size() == 1) {
                message += " is " + winners.get(0).getName();
            } else {
                message += "s are " + winners.stream().map(Player::getName).collect(joining(", "));
            }
            component.modal("Game Over. " + message, () -> component.gameOver());
        }
    }
}
