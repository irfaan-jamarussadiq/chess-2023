package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;

import chess.engine.game.Board;
import chess.engine.game.Location;
import chess.engine.game.Move;
import chess.engine.pieces.*;
import static chess.engine.pieces.PieceColor.*;

public class BoardTests {

    @Test
    public void testBoardSetup() {
        Board board = new Board();
        assertEquals(Board.BOARD_SIZE, 8);
        for (int file = 1; file <= 8; file++) {
            assertEquals(pieceAtLocation(board, 2, file), new Pawn(WHITE));
            assertEquals(pieceAtLocation(board, 7, file), new Pawn(BLACK));
        }
        testPieceRankIsSetUp(board, WHITE);
        testPieceRankIsSetUp(board, BLACK);
    }

    private void testPieceRankIsSetUp(Board board, PieceColor color) {
        int rank = color.getPawnStartingRank() - color.getDirection();
        assertEquals(pieceAtLocation(board, rank, 1), new Rook(color));
        assertEquals(pieceAtLocation(board, rank, 2), new Knight(color));
        assertEquals(pieceAtLocation(board, rank, 3), new Bishop(color));
        assertEquals(pieceAtLocation(board, rank, 4), new Queen(color));
        assertEquals(pieceAtLocation(board, rank, 5), new King(color));
        assertEquals(pieceAtLocation(board, rank, 6), new Bishop(color));
        assertEquals(pieceAtLocation(board, rank, 7), new Knight(color));
        assertEquals(pieceAtLocation(board, rank, 8), new Rook(color));
    }

    @Test
    public void testPawnOnD2CanMoveToD4OnBoard() {
        Board board = new Board();
        assertEquals(new Pawn(WHITE), pieceAtLocation(board, 2, 4));
        assertNull(pieceAtLocation(board, 4, 4));
        moveBoardPiece(board, 2, 4, 4, 4);
        assertEquals(new Pawn(WHITE), pieceAtLocation(board, 4, 4));
    }

    @Test
    public void testPawnOnD4IsBlockedByPawnOnD5() {
        Board board = new Board();
        moveBoardPiece(board, 2, 4, 4, 4);
        moveBoardPiece(board, 7, 4, 5, 4); 
        moveBoardPiece(board, 4, 4, 5, 4);
        assertEquals(new Pawn(WHITE), pieceAtLocation(board, 4, 4));
        assertEquals(new Pawn(BLACK), pieceAtLocation(board, 5, 4));
    }

    @Test
    public void testPawnOnF4CanCaptureE5() {
        Board board = new Board();
        moveBoardPiece(board, 2, 6, 4, 6);
        moveBoardPiece(board, 7, 5, 5, 5);
        Location start = new Location(4, 6);
        Location end = new Location(5, 5);
        assertTrue(pieceAtLocation(board, 4, 6).canCaptureInDirection(start, end));
        assertFalse(pieceAtLocation(board, 4, 6).canMoveInDirection(start, end));
    }

    @Test
    public void testKnightOnG1CannotCapturePawnOnE5() {
        Board board = new Board();
        moveBoardPiece(board, 1, 7, 3, 6);
        moveBoardPiece(board, 7, 5, 5, 5);
        moveBoardPiece(board, 3, 6, 1, 7);
        Location start = new Location(1, 7);
        Location end = new Location(5, 5);
        assertFalse(pieceAtLocation(board, 1, 7).canCaptureInDirection(start, end));
    }

    private void moveBoardPiece(Board board, int sRank, int sFile, int eRank, int eFile) {
        Location start = new Location(sRank, sFile);
        Location end = new Location(eRank, eFile);
        if (board.isValidMove(new Move(start, end))) {
            board.movePiece(start, end);
        }
    }

    @Test
    public void testPieceAtInvalidSquare() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, -1, 8));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, 9, 8));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, 5, 9));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, 1, -8));
        assertDoesNotThrow(() -> pieceAtLocation(board, 1, 8));
    }

    private Piece pieceAtLocation(Board board, int rank, int file) {
        return board.pieceAt(new Location(rank, file));
    }

}
