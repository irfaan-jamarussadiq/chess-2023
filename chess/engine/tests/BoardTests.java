package chess.engine.tests;

import static org.junit.Assert.*;
import org.junit.Test;

import chess.engine.board.Board;
import chess.engine.board.Location;
import chess.engine.board.Move;
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
        for (int rank = 3; rank <= 6; rank++) {
            for (int file = 1; file <= 8; file++) {
                assertNull(pieceAtLocation(board, rank, file));
            }
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

    private Piece pieceAtLocation(Board board, int rank, int file) {
        return board.pieceAt(new Location(rank, file));
    }

    @Test
    public void testPieceOutsideBoundsThrowsException() {
        Board board = new Board();
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, 9, 1));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, -1, 5));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, 8, 9));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, 2, -1));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, -2, -1));
        assertThrows(IllegalArgumentException.class, () -> pieceAtLocation(board, 10, 9));
    }

    @Test
    public void testPawnCanMoveTwoSquaresOnFirstMove() {
        Board board = new Board();
        Location start = new Location(2, 4);
        Location end = new Location(4, 4);
        board.movePiece(new Move(2, 4, 4, 4));
        assertNull(board.pieceAt(start));
        assert (board.pieceAt(end).equals(new Pawn(WHITE)));
    }

    @Test
    public void testPawnCanOnlyMoveOneSquareAfterFirstMove() {
        Board board = new Board();
        board.movePiece(new Move(2, 4, 4, 4));
        board.movePiece(new Move(7, 5, 5, 5));
    }

    @Test
    public void testPawnCanEnPassant() {
        Board board = new Board();
        board.movePiece(new Move(2, 4, 4, 4));
        board.movePiece(new Move(8, 2, 6, 3));
        board.movePiece(new Move(4, 4, 5, 4));
        board.movePiece(new Move(7, 5, 5, 5));
        board.movePiece(new Move(5, 4, 6, 5));
        assertEquals(new Pawn(WHITE), board.pieceAt(new Location(6, 5)));
        assertNull(board.pieceAt(new Location(5, 5)));
    }

    @Test
    public void testPawnOnE4CanCaptureOnF5() {
        Board board = new Board();
        board.movePiece(new Move(2, 5, 4, 5));
        board.movePiece(new Move(7, 6, 5, 6));
    }

    @Test
    public void testPawnCannotMoveDiagonally() {
        Board board = new Board();
        assertFalse(board.isNormalMove(new Location(2, 4), new Location(3, 5)));
        assertFalse(board.isNormalMove(new Location(2, 4), new Location(3, 3)));
    }

    @Test
    public void testPawnOnD4IsBlockedByPawnOnD5() {
        Board board = new Board();
        board.movePiece(new Move(2, 4, 4, 4));
        board.movePiece(new Move(7, 4, 5, 4));
        assertFalse(board.isNormalMove(new Location(4, 4), new Location(5, 4)));
    }

    @Test
    public void testPieceCannotCaptureFriendlyPieces() {
        Board board = new Board();
        assertFalse(board.isCaptureMove(new Location(8, 8), new Location(8, 7)));
        assertFalse(board.isCaptureMove(new Location(1, 2), new Location(2, 4)));
        assertFalse(board.isCaptureMove(new Location(1, 6), new Location(2, 5)));
    }

    @Test
    public void testPieceCannotMoveThroughPiece() {
        Board board = new Board();
        assertFalse(board.isNormalMove(new Location(1, 6), new Location(5, 2)));
        assertTrue(board.isNormalMove(new Location(1, 2), new Location(3, 3)));
        assertFalse(board.isNormalMove(new Location(1, 4), new Location(8, 4)));
    }

    @Test
    public void testCanOnlyCastleWithEmptySquares() {
        Board board = new Board();
        assertFalse(board.isShortCastlingMove(new Location(1, 5), new Location(1, 7)));
        board.movePiece(new Move(1, 7, 3, 6));
        assertFalse(board.isShortCastlingMove(new Location(1, 5), new Location(1, 7)));
        board.movePiece(new Move(7, 5, 5, 5));
        board.movePiece(new Move(2, 5, 4, 5));
        board.movePiece(new Move(7, 4, 5, 4));
        board.movePiece(new Move(1, 6, 4, 3));
        assertNull(board.pieceAt(1, 6));
        assertNull(board.pieceAt(1, 7));
        assertTrue(board.isShortCastlingMove(new Location(1, 5), new Location(1, 7)));

        boolean isValidMove = board.movePiece(new Move(1, 5, 1, 7));
        assertTrue(isValidMove);
        assertTrue(board.pieceAt(1, 7) instanceof King);
        assertTrue(board.pieceAt(1, 6) instanceof Rook);
    }

    @Test
    public void testCannotCastleIfRookHasMoved() {
        Board board = new Board();
        board.movePiece(new Move(1, 7, 3, 6));
        board.movePiece(new Move(7, 5, 5, 5));
        board.movePiece(new Move(2, 5, 4, 5));
        board.movePiece(new Move(7, 4, 5, 4));
        board.movePiece(new Move(1, 6, 4, 3));
        board.movePiece(new Move(1, 8, 1, 7));
        board.movePiece(new Move(1, 7, 1, 8));
        assertNull(board.pieceAt(1, 6));
        assertNull(board.pieceAt(1, 7));
        assertFalse(board.isShortCastlingMove(new Location(1, 5), new Location(1, 7)));
    }

    @Test
    public void testCannotCastleIfKingHasMoved() {
        Board board = new Board();
        board.movePiece(new Move(1, 7, 3, 6));
        board.movePiece(new Move(7, 5, 5, 5));
        board.movePiece(new Move(2, 5, 4, 5));
        board.movePiece(new Move(7, 4, 5, 4));
        board.movePiece(new Move(1, 6, 4, 3));
        board.movePiece(new Move(1, 5, 1, 6));
        board.movePiece(new Move(1, 6, 1, 5));
        assertNull(board.pieceAt(1, 6));
        assertNull(board.pieceAt(1, 7));
        assertFalse(board.isShortCastlingMove(new Location(1, 5), new Location(1, 7)));
    }

    @Test
    public void testIsInCheckButNotCheckmate() {
        Board board = new Board();
        board.movePiece(new Move(2, 4, 4, 4));
        board.movePiece(new Move(7, 5, 5, 5));
        board.movePiece(new Move(4, 4, 5, 5));
        board.movePiece(new Move(8, 6, 4, 2));
        assertFalse(board.isInCheckmate(WHITE));
        board.movePiece(new Move(1, 2, 3, 3));
        board.movePiece(new Move(4, 2, 3, 3));
        assertFalse(board.isInCheckmate(WHITE));
        board.movePiece(new Move(1, 3, 2, 4));
        board.movePiece(new Move(3, 3, 2, 4));
        assertFalse(board.isInCheckmate(WHITE));
    }

    @Test
    public void testIsInCheckmate() {
        Board board = new Board();
        board.movePiece(new Move(2, 5, 4, 5));
        board.movePiece(new Move(7, 5, 5, 5));
        board.movePiece(new Move(1, 6, 4, 3));
        board.movePiece(new Move(8, 6, 5, 3));
        board.movePiece(new Move(1, 4, 5, 8));
        board.movePiece(new Move(8, 7, 6, 6));
        board.movePiece(new Move(5, 8, 7, 6));
        assertFalse(board.isInCheckmate(WHITE));
        assertTrue(board.isInCheckmate(BLACK));
    }

    @Test
    public void testPieceCapturePreventsCheckmate() {
        Board board = new Board();
        board.movePiece(new Move(2, 5, 4, 5));
        board.movePiece(new Move(7, 5, 5, 5));
        board.movePiece(new Move(1, 6, 4, 3));
        board.movePiece(new Move(8, 6, 5, 3));
        board.movePiece(new Move(1, 4, 5, 8));
        board.movePiece(new Move(8, 7, 6, 8));
        board.movePiece(new Move(5, 8, 7, 6));
        assertFalse(board.isInCheckmate(WHITE));
        assertFalse(board.isInCheckmate(BLACK));
    }

    @Test
    public void testStalemate() {
        Board board = new Board();
        Move[] moves = {
            new Move(2, 5, 3, 5), new Move(7, 1, 5, 1), // e3 a5
            new Move(1, 4, 5, 8), new Move(8, 1, 6, 1), // Qh5 Ra6
            new Move(5, 8, 5, 1), new Move(7, 8, 5, 8), // Qxa5 h5
            new Move(2, 8, 4, 8), new Move(6, 1, 6, 8), // h4 Rah6
            new Move(5, 1, 7, 3), new Move(7, 6, 6, 6), // Qxc7 f6
            new Move(7, 3, 7, 4), new Move(8, 5, 7, 6), // Qxd7+ Kf7
            new Move(7, 4, 7, 2), new Move(8, 4, 3, 4), // Qxb7 Qd3
            new Move(7, 2, 8, 2), new Move(3, 4, 7, 8), // Qxb8 Qh7
            new Move(8, 2, 8, 3), new Move(7, 6, 6, 7), // Qxc8 Kg6
            new Move(8, 3, 6, 5) // Qe6
        };

        for (Move move : moves) {
            board.movePiece(move);
        }

        assertTrue(board.isInStalemate(BLACK));
    }
}
