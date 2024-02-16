package game;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import app.game.view.GameView;

public class TestObserver {

    GameView gameV;

    @BeforeEach

    void start() throws IOException {
        gameV = new GameView(List.of("mario", "luigi"));
    }

    @Test

    void obsTest() {
        var btnPanel = gameV.getButtonView();

        btnPanel.updateObserver(0, null, "rollDice");

    }

}
