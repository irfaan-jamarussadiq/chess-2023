package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;
import board.Board;
import pieces.*;
import static pieces.PieceColor.*;

// REQUIREMENTS
// The pieces are king, queen, bishop, knight, rook, and pawn.
// Pieces move on an 8x8 board.
// The pieces must be placed on the board in their starting configuration.
// There are two players, each with their own set of pieces, white and black.

public class BoardTests {

    @Test
    public void testBoardSizeIsEight() {
        assertEquals(8, Board.BOARD_SIZE);
    }

    @Test
    public void testBoardIsInStartingPosition() {
        Board board = new Board();
        Pawn whitePawn = new Pawn(WHITE);
        Pawn blackPawn = new Pawn(BLACK);        
        for (int file = 1; file <= Board.BOARD_SIZE; file++) {
            assertEquals(whitePawn, board.pieceAt(2, file));
            assertEquals(blackPawn, board.pieceAt(7, file));
        }
        testPiecesAreOnBackRow(board, 1, WHITE);
        testPiecesAreOnBackRow(board, 8, BLACK);
    }

    private void testPiecesAreOnBackRow(Board board, int rank, PieceColor color) {
        // Test pieces are on their back row.
        assertEquals(new Rook(color), board.pieceAt(rank, 1));
        assertEquals(new Knight(color), board.pieceAt(rank, 2));
        assertEquals(new Bishop(color), board.pieceAt(rank, 3));
        assertEquals(new Queen(color), board.pieceAt(rank, 4));
        assertEquals(new King(color), board.pieceAt(rank, 5));
        assertEquals(new Bishop(color), board.pieceAt(rank, 6));
        assertEquals(new Knight(color), board.pieceAt(rank, 7));
        assertEquals(new Rook(color), board.pieceAt(rank, 8));        
    }

    @Test
    public void testMovePawnToD4() {
        Board board = new Board();
        board.movePiece(2, 4, 4, 4);
        assertEquals(new Empty(), board.pieceAt(2, 4));
        assertEquals(new Pawn(WHITE), board.pieceAt(4, 4));
    }

    @Test
    public void testMoveKnightToF3() {
        Board board = new Board();
        board.movePiece(1, 7, 3, 6);
        assertEquals(new Empty(), board.pieceAt(1, 7));
        assertEquals(new Knight(WHITE), board.pieceAt(3, 6));
    }

    @Test
    public void testMoveBishopToC4() {
        Board board = new Board();        
        assertThrows(IllegalArgumentException.class, () -> board.movePiece(1, 6, 4, 3));
    }

    @Test
    public void testMoveBishopToC4AfterE4() {
        Board board = new Board();
        board.movePiece(2, 5, 4, 5);        
        board.movePiece(7, 5, 5, 5);
        assertDoesNotThrow(() -> board.movePiece(1, 6, 4, 3));
    }
    
    @Test
    public void testMoveRookToA3AfterA4() {
        Board board = new Board();
        board.movePiece(2, 1, 4, 1);
        board.movePiece(7, 5, 5, 5);
        assertDoesNotThrow(() -> board.movePiece(1, 1, 3, 1));
    }

    @Test
    public void testBongcloudOpening() {
        Board board = new Board();
        board.movePiece(2, 5, 4, 5);        
        board.movePiece(7, 5, 5, 5);
        assertDoesNotThrow(() -> board.movePiece(1, 5, 2, 5));
    }

}