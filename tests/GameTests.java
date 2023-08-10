package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import static chess.engine.pieces.PieceColor.*;
import chess.engine.Game;

public class GameTests {
    
    @Test
    public void testItalianOpeningInGame() {
        Game game = new Game();
        assertEquals(game.getCurrentPlayer().color, WHITE);
        game.makeMove(2, 5, 4, 5);
        assertEquals(game.getCurrentPlayer().color, BLACK);
        game.makeMove(7, 5, 5, 5);
        assertEquals(game.getCurrentPlayer().color, WHITE);
        game.makeMove(1, 6, 4, 3);
        assertEquals(game.getCurrentPlayer().color, BLACK);
        game.makeMove(8, 6, 5, 3);
        assertEquals(game.getCurrentPlayer().color, WHITE);
        game.makeMove(2, 4, 3, 4);
        assertEquals(game.getCurrentPlayer().color, BLACK);
        game.makeMove(8, 7, 6, 6);
        assertEquals(game.getCurrentPlayer().color, WHITE);
    }

    @Test
    public void testInCheck() {
        Game game = new Game();
        game.makeMove(2, 5, 3, 5);
        game.makeMove(7, 4, 5, 4);
        assertFalse(game.isInCheck(Game.blackPlayer));
        game.makeMove(1, 6, 5, 2);
        assertFalse(game.isInCheck(Game.whitePlayer));
        assertTrue(game.isInCheck(Game.blackPlayer));
        game.makeMove(8, 5, 7, 4);
        assertTrue(game.isInCheck(Game.blackPlayer));
    }
}
