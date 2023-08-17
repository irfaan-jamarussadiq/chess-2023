package tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import chess.engine.game.Board;
import chess.engine.game.Game;
import chess.engine.game.Location;
import chess.engine.game.Move;
import chess.engine.pieces.Pawn;

import static chess.engine.pieces.PieceColor.*;

public class GameTests {

    @Test
    public void testItalianOpeningInGame() {
        Game game = new Game();
        assertEquals(game.getCurrentPlayer().color, WHITE);
        game.makeMove(new Move(2, 5, 4, 5));
        assertEquals(game.getCurrentPlayer().color, BLACK);
        game.makeMove(new Move(7, 5, 5, 5));
        assertEquals(game.getCurrentPlayer().color, WHITE);
        game.makeMove(new Move(1, 6, 4, 3));
        assertEquals(game.getCurrentPlayer().color, BLACK);
        game.makeMove(new Move(8, 6, 5, 3));
        assertEquals(game.getCurrentPlayer().color, WHITE);
        game.makeMove(new Move(2, 4, 3, 4));
        assertEquals(game.getCurrentPlayer().color, BLACK);
        game.makeMove(new Move(8, 7, 6, 6));
        assertEquals(game.getCurrentPlayer().color, WHITE);
    }

    @Test
    public void testInCheck() {
        Game game = new Game();
        game.makeMove(new Move(2, 5, 3, 5));
        game.makeMove(new Move(7, 4, 5, 4));
        assertFalse(game.isInCheck(Game.blackPlayer));
        game.makeMove(new Move(1, 6, 5, 2));
        assertFalse(game.isInCheck(Game.whitePlayer));
        assertTrue(game.isInCheck(Game.blackPlayer));
        game.makeMove(new Move(8, 5, 7, 4));
        assertTrue(game.isInCheck(Game.blackPlayer));
    }

    @Test
    public void testFoolsMate() {
        Game game = new Game();
        game.makeMove(new Move(2, 5, 4, 5));
        game.makeMove(new Move(7, 7, 5, 7));
        game.makeMove(new Move(2, 4, 4, 4));
        game.makeMove(new Move(7, 6, 5, 6));
        game.makeMove(new Move(1, 4, 5, 8));
        System.out.println(game);
        assertTrue(game.isInCheckmate(Game.blackPlayer));
    }

    @Test
    public void testStrangePawnBug() {
        Game game = new Game();
        game.makeMove(new Move(2, 5, 4, 5));
        game.makeMove(new Move(7, 4, 5, 4));
        game.makeMove(new Move(4, 5, 5, 4));
        Board board = game.getBoard();
        assertTrue(board.pieceAt(new Location(5, 4)).equals(new Pawn(WHITE)));
        assertNull(board.pieceAt(new Location(2, 5)));
        assertNull(board.pieceAt(new Location(7, 4)));
    }

    @Test
    public void testCanBlockCheck() {
        Game game = new Game();
        game.makeMove(new Move(2, 5, 4, 5));
        game.makeMove(new Move(7, 4, 5, 4));
        game.makeMove(new Move(4, 5, 5, 4));
        game.makeMove(new Move(8, 4, 5, 4));
        game.makeMove(new Move(1, 2, 3, 3));
        game.makeMove(new Move(5, 4, 5, 5));
        System.out.println(game);
        assertTrue(game.isInCheck(Game.whitePlayer));
        assertFalse(game.isInCheckmate(Game.whitePlayer));
    }
}
