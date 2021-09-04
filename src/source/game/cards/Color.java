package source.game.cards;

public enum Color {

    Acorns(4),
    Leaves(3),
    Hearts(2),
    Bells(1);

    public final int value;

    private Color(int value) {
        this.value = value;
    }

}
