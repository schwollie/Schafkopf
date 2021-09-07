package source.game.cards;

public enum Cardtype {

    Ace(11),
    Ten(10),
    King(4),
    Ober(3),
    Unter(2),
    Nine(0),
    Eight(0),
    Seven(0);


    public final int value;

    Cardtype(int value) {
        this.value = value;
    }

}
