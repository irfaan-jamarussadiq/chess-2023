package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import chess.engine.game.Game;

import static chess.engine.pieces.PieceColor.*;

public class GameTests {

    @Test
    public void testWhiteIsStartingPlayer() {
        Game game = new Game();
        assertEquals(game.getCurrentPlayer(), WHITE);
    }

    @Test
    public void testIllegalMoveDoesNotChangePlayerTurn() {
        Game game = new Game();
        game.makeMove(2, 4, 5, 4);
        assertEquals(game.getCurrentPlayer(), WHITE);
    }

    @Test
    public void testMovesAlternateThroughoutGame() {
        Game game = new Game();
        assertEquals(game.getCurrentPlayer(), WHITE);
        game.makeMove(2, 5, 4, 5);
        assertEquals(game.getCurrentPlayer(), BLACK);
        game.makeMove(7, 5, 5, 5);
        assertEquals(game.getCurrentPlayer(), WHITE);
        game.makeMove(1, 6, 4, 3);
        assertEquals(game.getCurrentPlayer(), BLACK);
        game.makeMove(8, 6, 5, 3);
        assertEquals(game.getCurrentPlayer(), WHITE);
        game.makeMove(2, 4, 3, 4);
        assertEquals(game.getCurrentPlayer(), BLACK);
        game.makeMove(8, 7, 6, 6);
        assertEquals(game.getCurrentPlayer(), WHITE);
    }

}
