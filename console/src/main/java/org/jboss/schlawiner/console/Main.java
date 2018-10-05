package org.jboss.schlawiner.console;

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.jboss.schlawiner.engine.algorithm.OperationAlgorithm;
import org.jboss.schlawiner.engine.algorithm.Solution;
import org.jboss.schlawiner.engine.game.Dice;
import org.jboss.schlawiner.engine.game.Game;
import org.jboss.schlawiner.engine.game.Level;
import org.jboss.schlawiner.engine.game.Numbers;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Players;
import org.jboss.schlawiner.engine.game.Settings;
import org.jboss.schlawiner.engine.score.Score;

import static com.google.common.base.Strings.nullToEmpty;
import static java.util.stream.Collectors.joining;

public class Main {

    public static void main(String[] args) {
        TextIO textIO = TextIoFactory.getTextIO();
        TextTerminal<?> terminal = textIO.getTextTerminal();
        new Main(textIO, terminal).main();
    }


    private final TextIO textIO;
    private final TextTerminal<?> terminal;
    private final List<Player> players;
    private final Settings settings;

    private Main(TextIO textIO, TextTerminal<?> terminal) {
        this.textIO = textIO;
        this.terminal = terminal;
        this.players = new ArrayList<>();
        this.settings = Settings.defaults();
        this.settings.setAutoDice(true);

        terminal.println(Texts.BANNER);
    }

    private void main() {
        while (true) {
            terminal.print(Texts.MAIN);
            int option = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(3)
                .read("Please choose");

            switch (option) {
                case 1:
                    settings();
                    break;
                case 2:
                    players();
                    break;
                case 3:
                    play();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    private void settings() {
        while (true) {
            terminal.printf(Texts.SETTINGS, settings.getNumbers(), settings.getRetries(), settings.getPenalty(),
                settings.getLevel());
            int option = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(4)
                .read("Please choose");
            switch (option) {
                case 1:
                    settings.setNumbers(textIO.newIntInputReader()
                        .withMinVal(2)
                        .withMaxVal(20)
                        .withDefaultValue(8)
                        .read("Number of numbers (2..20)"));
                    break;
                case 2:
                    settings.setRetries(textIO.newIntInputReader()
                        .withMinVal(0)
                        .withMaxVal(5)
                        .withDefaultValue(3)
                        .read("Number of retries (0..5)"));
                    break;
                case 3:
                    settings.setPenalty(textIO.newIntInputReader()
                        .withMinVal(1)
                        .withMaxVal(10)
                        .withDefaultValue(5)
                        .read("Penalty after timeout (1..10)"));
                    break;
                case 4:
                    settings.setLevel(textIO.newEnumInputReader(Level.class)
                        .withDefaultValue(Level.MEDIUM)
                        .read("Level"));
                    break;
                case 0:
                    main();
                    break;
                default:
                    break;
            }
        }
    }

    private void players() {
        while (true) {
            terminal.print(Texts.PLAYERS);
            int option = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(3)
                .read("Please choose");

            switch (option) {
                case 1: {
                    String name = textIO.newStringInputReader().read("Name");
                    boolean human = textIO.newBooleanInputReader()
                        .withDefaultValue(true)
                        .read("Human");
                    players.add(new Player(name, human));
                    break;
                }
                case 2: {
                    terminal.println();
                    if (players.isEmpty()) {
                        terminal.println("No players");
                    }
                    for (Player player : players) {
                        terminal.printf("%s%n", player);
                    }
                    break;
                }
                case 3: {
                    int index = 1;
                    terminal.println();
                    for (Player player : players) {
                        terminal.printf("[%d] %s%n", index, player);
                        index++;
                    }
                    terminal.printf("[0] Back%n%n");
                    index = textIO.newIntInputReader()
                        .withMaxVal(0)
                        .withMaxVal(players.size())
                        .read("Please choose");
                    if (index != 0) {
                        index--;
                        players.remove(index);
                    }
                    break;
                }
                case 0:
                    main();
                    break;
                default:
                    break;
            }
        }
    }

    private void play() {
        boolean canceled = false;
        Game game = new Game(new Players(players), new Numbers(settings.getNumbers()), new OperationAlgorithm(),
            settings);

        terminal.print(Texts.PLAY);
        while (game.hasNext() && !canceled) {
            game.next();
            game.dice(new Dice());

            Players players = game.getPlayers();
            Player currentPlayer = players.current();
            int currentNumber = game.getNumbers().current();
            if (players.isFirst()) {
                printScoreboard(game);
            }
            if (currentPlayer.isHuman()) {
                String term = null;
                boolean validTerm = false;
                while (!validTerm && !canceled) {
                    String prompt = String.format("%s try to reach %d using %s", currentPlayer.getName(), currentNumber,
                        game.getDice());
                    try {
                        term = textIO.newStringInputReader().read(prompt);
                        if ("retry".equalsIgnoreCase(term)) {
                            if (game.retry()) {
                                terminal.printf("You have %d retries left.%n", currentPlayer.getRetries());
                            } else {
                                terminal.println("Sorry you have no retries left");
                            }
                        } else if ("skip".equalsIgnoreCase(term)) {
                            game.skip();
                            validTerm = true;
                        } else if ("exit".equalsIgnoreCase(term)) {
                            canceled = true;
                        } else {
                            int difference = game.calculate(term);
                            Solution bestSolution = game.getAlgorithm()
                                .compute(game.getDice().numbers[0], game.getDice().numbers[1],
                                    game.getDice().numbers[2], currentNumber)
                                .bestSolution();
                            int bestDifference = Math.abs(bestSolution.getValue() - currentNumber);
                            if (difference > bestDifference) {
                                terminal.printf("Your difference is %d. The best solution is %s (difference %d)%n",
                                    difference, bestSolution, bestDifference);
                            }
                            validTerm = true;
                        }
                    } catch (ArithmeticException e) {
                        terminal.printf("No valid term '%s': %s%n", term, e.getMessage());
                    }
                }
            } else {
                Solution solution = game.solve();
                terminal.printf("%s diced %s. Solution: %s%n", currentPlayer.getName(), game.getDice(), solution);
            }
        }

        if (!canceled) {
            printScoreboard(game);
            terminal.printf("Game over. ");
            List<Player> winners = game.getScoreboard().getWinners();
            if (winners.size() == 1) {
                terminal.printf("The winner is %s!%n", winners.get(0).getName());
            } else {
                terminal.printf("The winners are %s!%n", winners.stream().map(Player::getName).collect(joining(", ")));
            }
        }
        main();
    }

    private void printScoreboard(Game game) {
        // header
        terminal.println();
        terminal.printf("    ");
        for (Player player : game.getPlayers()) {
            terminal.printf("| %-20s ", player.getName());
        }
        terminal.println();
        terminal.printf("====");
        for (Player ignored : game.getPlayers()) {
            terminal.printf("+=================+====");
        }
        terminal.println();

        // body
        int numberIndex = 0;
        for (int number : game.getNumbers()) {
            terminal.printf("%3d ", number);
            for (Player player : game.getPlayers()) {
                Score score = game.getScoreboard().getScore(numberIndex, player);
                String difference = score.getDifference() == -1 ? "  " : String.format("%2d", score.getDifference());
                terminal.printf("| %15s | %s ", nullToEmpty(score.getTerm()), difference);
            }
            terminal.println();
            numberIndex++;
        }

        // footer
        terminal.printf("====");
        for (Player ignored : game.getPlayers()) {
            terminal.printf("+=================+====");
        }
        terminal.println();
        terminal.printf("    ");
        for (Player player : game.getPlayers()) {
            terminal.printf("|                 | %2d ", game.getScoreboard().getSummedScore(player));
        }
        terminal.println();
        terminal.println();
    }
}
