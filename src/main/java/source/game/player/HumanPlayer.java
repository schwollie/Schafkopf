package source.game.player;

import source.game.cards.Card;
import source.game.cards.Color;
import source.game.core.GameManager;
import source.game.core.gametype.AceGame;
import source.game.core.gametype.Gametype;
import source.game.core.gametype.NoType;

import java.util.ArrayList;
import java.util.Scanner;

public class HumanPlayer extends Player {

    final Scanner scanner = new Scanner(System.in);

    public HumanPlayer(GameManager game, String name) {
        super(game, name);
    }

    @Override
    public void playRound(GameManager g) {
        ArrayList<Card> pcards = game.getInfo().getGametype().possibleCards(this, game);
        System.out.println(pcards);
        System.out.println("set index of card to lay: ");
        Scanner scanner = new Scanner(System.in);
        int index = scanner.nextInt();

        this.layCard(pcards.get(index));
    }

    @Override
    public Gametype selectGameType() {
        System.out.println(Card.sort(this.getCards()));
        System.out.println("choose game type by typing n or a (Notype, Acetype)");

        while (true) {
            String in = scanner.nextLine();

            if (in.equals("n")) {
                return new NoType();
            } else if (in.equals("a")) {
                System.out.println("specify color: a, l, b (acorn, leaves, bells))");
                in = scanner.nextLine();

                Gametype t = switch (in) {
                    case "a" -> new AceGame(this, Color.Acorns);
                    case "l" -> new AceGame(this, Color.Leaves);
                    case "b" -> new AceGame(this, Color.Bells);
                    default -> null;
                };

                if (Gametype.isAllowed(t)) {
                    return t;
                }
            }

            System.out.println("input is wrong try again:");
        }
    }
}
