package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

import chess.engine.*;
import chess.engine.pieces.*;
import static chess.engine.pieces.PieceColor.*;

public class BoardTests {

    @Test
    public void testBoardSetup() {
        Board board = new Board();
        assertEquals(Board.BOARD_SIZE, 8);
        for (int file = 1; file <= 8; file++) {
            assertEquals(board.pieceAt(2, file), new Pawn(WHITE));
            assertEquals(board.pieceAt(7, file), new Pawn(BLACK));
        }
        testPieceRankIsSetUp(board, WHITE);
        testPieceRankIsSetUp(board, BLACK);
    }

    private void testPieceRankIsSetUp(Board board, PieceColor color) {
        int rank = color.getPawnStartingRank() - color.getDirection();
        assertEquals(board.pieceAt(rank, 1), new Rook(color));
        assertEquals(board.pieceAt(rank, 2), new Knight(color));
        assertEquals(board.pieceAt(rank, 3), new Bishop(color));
        assertEquals(board.pieceAt(rank, 4), new Queen(color));
        assertEquals(board.pieceAt(rank, 5), new King(color));
        assertEquals(board.pieceAt(rank, 6), new Bishop(color));
        assertEquals(board.pieceAt(rank, 7), new Knight(color));
        assertEquals(board.pieceAt(rank, 8), new Rook(color));
    }

    @Test
    public void testPawnOnD2CanMoveToD4OnBoard() {
        Board board = new Board();
        assertEquals(new Pawn(WHITE), board.pieceAt(2, 4));
        assertNull(board.pieceAt(4, 4));
        board.movePiece(2, 4, 4, 4);
        assertEquals(new Pawn(WHITE), board.pieceAt(4, 4));
    }

    @Test
    public void testPawnOnD4IsBlockedByPawnOnD5() {
        Board board = new Board();
        board.movePiece(2, 4, 4, 4);
        board.movePiece(7, 4, 5, 4);
        board.movePiece(4, 4, 5, 4);
        assertEquals(new Pawn(WHITE), board.pieceAt(4, 4));
        assertEquals(new Pawn(BLACK), board.pieceAt(5, 4));
    }

    @Test
    public void testPawnOnF4CanCaptureE5() {
        Board board = new Board();
        board.movePiece(2, 6, 4, 6);
        board.movePiece(7, 5, 5, 5);
        assertTrue(board.pieceAt(4, 6).canCapture(4, 6, 5, 5));
        assertFalse(board.pieceAt(4, 6).canMove(4, 6, 5, 5));
   }

    @Test
    public void testPieceAtInvalidSquare() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(-1, 8));
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(9, 8));
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(5, 9));
        assertThrows(IllegalArgumentException.class, () -> board.pieceAt(1, -8));
        assertDoesNotThrow(() -> board.pieceAt(1, 8));
    }

}
