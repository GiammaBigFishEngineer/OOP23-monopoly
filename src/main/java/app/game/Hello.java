package app.game;

import game.view.GameView;

public final class Hello {

    private Hello() {}; //NOPMD
    //PMD suppressed because private constructor has no unecessary semilcolon
    
    public static void main(final String[] args) {
        new GameView();
    }
}
