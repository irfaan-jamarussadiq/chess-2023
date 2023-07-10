package tests;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Test;
import board.*;
import pieces.*;
import static board.GameStatus.*;
import static pieces.PieceColor.*;
import java.util.Set;

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

    @Test
    public void testFirstMoveIsWhiteTurn() {
        Game game = new Game();
        assertEquals(ONGOING, game.getStatus());
        assertEquals(WHITE, game.getCurrentPlayer());
    }

    @Test
    public void testSecondMoveIsBlackTurn() {
        Game game = new Game();
        game.movePiece(2, 5, 4, 5);
        assertEquals(ONGOING, game.getStatus());        
        assertEquals(BLACK, game.getCurrentPlayer());
    }    

    @Test
    public void testThirdMoveIsWhiteTurn() {
        Game game = new Game();
        game.movePiece(2, 5, 4, 5);        
        game.movePiece(7, 5, 5, 5);
        assertEquals(ONGOING, game.getStatus());
        assertEquals(WHITE, game.getCurrentPlayer());
    }

    @Test
    public void testKingMovesWhite() {
        Board board = new Board();
        board.movePiece(2, 5, 4, 5);
        Set<Move> moves = new King(WHITE).getMoves(board, new Location(1, 5));
        assertEquals(1, moves.size());
        assertEquals(new Move(1, 5, 2, 5), moves.iterator().next());
    }    

    @Test
    public void testKingMovesBlack() {
        Board board = new Board();
        board.movePiece(2, 5, 4, 5);
        Set<Move> moves = new King(BLACK).getMoves(board, new Location(8, 5));
        assertEquals(0, moves.size());
    }
    
    @Test
    public void testKingMovesBlack2() {
        Board board = new Board();
        board.movePiece(2, 5, 4, 5);
        board.movePiece(7, 5, 5, 5);
        Set<Move> moves = new King(BLACK).getMoves(board, new Location(8, 5));
        assertEquals(1, moves.size());
        assertEquals(new Move(8, 5, 7, 5), moves.iterator().next());
    }
    
    @Test
    public void testKingMovesBlackAndWhite() {
        Board board = new Board();
        System.out.println(board);
        board.movePiece(2, 5, 4, 5);
        board.movePiece(7, 5, 5, 5);
        board.movePiece(2, 4, 4, 4);
        board.movePiece(7, 4, 5, 4);
        Set<Move> whiteMoves = new King(WHITE).getMoves(board, new Location(1, 5));
        Set<Move> blackMoves = new King(BLACK).getMoves(board, new Location(8, 5));
        Set<Move> expectedWhite = Set.of(new Move(1, 5, 2, 5), new Move(1, 5, 2, 4));
        Set<Move> expectedBlack = Set.of(new Move(8, 5, 7, 5), new Move(8, 5, 7, 4));
        assertEquals(2, whiteMoves.size());
        assertEquals(2, blackMoves.size());
        assertEquals(expectedWhite, whiteMoves);
        assertEquals(expectedBlack, blackMoves);
    }
    
    @Test
    public void checkGameHistory() {
        Game game = new Game();
        assertEquals(0, game.getHistory().size());
        game.movePiece(2, 4, 4, 4);
        assertEquals(1, game.getHistory().size());
        assertEquals(new Move(2, 4, 4, 4), game.getHistory().peek());
        game.movePiece(7, 4, 5, 4);
        assertEquals(2, game.getHistory().size());
        assertEquals(new Move(7, 4, 5, 4), game.getHistory().peek());
        game.movePiece(1, 6, 3, 4);
        assertEquals(3, game.getHistory().size());
        assertEquals(new Move(1, 6, 3, 4), game.getHistory().peek());
        game.movePiece(7, 1, 5, 1);
        assertEquals(3, game.getHistory().size());
        assertEquals(new Move(1, 6, 3, 4), game.getHistory().peek());
    }

}