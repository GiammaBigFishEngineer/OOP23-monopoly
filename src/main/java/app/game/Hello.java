package app.game;

import app.game.view.MenuView;

/**
 * MonopolyGame Rimini-Edition.
 */
public final class Hello {
    /**
     * Constructor.
     */
    private Hello() {
        throw new IllegalStateException("Main Class");
    }

    /**
     * 
     * @param args
     */
    public static void main(final String[] args) {
        new MenuView();
    }
}
