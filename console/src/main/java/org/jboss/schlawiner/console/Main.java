package org.jboss.schlawiner.console;

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.jboss.schlawiner.engine.game.Level;
import org.jboss.schlawiner.engine.game.Player;
import org.jboss.schlawiner.engine.game.Settings;

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
            terminal.printf(Texts.SETTINGS, settings.getNumbers(), settings.getTimeout(), settings.getRetries(),
                settings.getPenalty(), settings.isAutoDice(), settings.getLevel());
            int option = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(6)
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
                    settings.setTimeout(textIO.newIntInputReader()
                        .withMinVal(0)
                        .withMaxVal(300)
                        .withDefaultValue(60)
                        .read("Timeout in seconds (0..300)"));
                    break;
                case 3:
                    settings.setRetries(textIO.newIntInputReader()
                        .withMinVal(0)
                        .withMaxVal(5)
                        .withDefaultValue(3)
                        .read("Number of retries (0..5)"));
                    break;
                case 4:
                    settings.setPenalty(textIO.newIntInputReader()
                        .withMinVal(1)
                        .withMaxVal(10)
                        .withDefaultValue(5)
                        .read("Penalty after timeout (1..10)"));
                    break;
                case 5:
                    settings.setAutoDice(textIO.newBooleanInputReader()
                        .withDefaultValue(false)
                        .read("Auto dice"));
                    break;
                case 6:
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

    }
}
