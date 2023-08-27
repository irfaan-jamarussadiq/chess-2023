package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import chess.engine.board.Location;
import chess.engine.pieces.*;
import static chess.engine.pieces.PieceColor.*;

public class PieceTests {

    @Test
    public void testBlackPawnOnE6CanCaptureOnD5() {
        Piece pawn = new Pawn(BLACK);
        assertTrue(pieceCanCapture(pawn, 6, 5, 5, 4));
    }

    @Test
    public void testWhitePawnOnE6CannotCaptureOnD5() {
        Piece pawn = new Pawn(WHITE);
        assertFalse(pieceCanCapture(pawn, 6, 5, 5, 4));
    }

    private boolean pieceCanCapture(Piece piece, int sRank, int sFile, int eRank, int eFile) {
        Location start = new Location(sRank, sFile);
        Location end = new Location(eRank, eFile);
        return piece.canCaptureInDirection(start, end);
    }

    @Test
    public void testPawnCannotMoveLikeCapture() {
        Piece pawn = new Pawn(BLACK);
        Location start = new Location(6, 5);
        Location end = new Location(5, 4);
        assertFalse(pawn.canMoveInDirection(start, end));
    }

    @Test
    public void testBishopAtA8CanMoveOnDiagonals() {
        Piece bishop = new Bishop(WHITE);
        assertTrue(pieceCanMove(bishop, 6, 3, 4, 5));
        assertTrue(pieceCanMove(bishop, 6, 3, 8, 5));
        assertTrue(pieceCanMove(bishop, 6, 3, 4, 1));
        assertTrue(pieceCanMove(bishop, 6, 3, 1, 8));
    }

    @Test
    public void testBishopAtA8CannotMoveToD4() {
        Piece bishop = new Bishop(BLACK);
        assertFalse(bishop.canMoveInDirection(new Location(8, 1), new Location(4, 4)));
    }

    @Test
    public void testWhiteKnightOnC6CanMoveOnEightSquares() {
        Piece knight = new Knight(BLACK);
        assertTrue(pieceCanMove(knight, 6, 3, 5, 1));
        assertTrue(pieceCanMove(knight, 6, 3, 7, 1));
        assertTrue(pieceCanMove(knight, 6, 3, 8, 2));
        assertTrue(pieceCanMove(knight, 6, 3, 4, 2));
        assertTrue(pieceCanMove(knight, 6, 3, 8, 4));
        assertTrue(pieceCanMove(knight, 6, 3, 4, 4));
        assertTrue(pieceCanMove(knight, 6, 3, 5, 5));
        assertTrue(pieceCanMove(knight, 6, 3, 7, 5));
    }

    @Test
    public void testBlackRookOnB5CanCaptureOnStraightLines() {
        Piece rook = new Rook(WHITE);
        for (int file = 1; file <= 8 && file != 2; file++) {
            assertTrue(pieceCanMove(rook, 5, 2, 5, file));
        }

        for (int rank = 1; rank <= 8 && rank != 5; rank++) {
            assertTrue(pieceCanMove(rook, 5, 2, rank, 2));
        }

        assertFalse(pieceCanMove(rook, 5, 2, 5, 2));
    }

    @Test
    public void testWhiteKingOnE4CanCaptureOnD5() {
        Piece king = new King(WHITE);
        assertTrue(pieceCanMove(king, 4, 5, 3, 4));
        assertTrue(pieceCanMove(king, 4, 5, 3, 5));
        assertTrue(pieceCanMove(king, 4, 5, 3, 6));
        assertTrue(pieceCanMove(king, 4, 5, 4, 4));
        assertTrue(pieceCanMove(king, 4, 5, 4, 6));
        assertTrue(pieceCanMove(king, 4, 5, 5, 4));
        assertTrue(pieceCanMove(king, 4, 5, 5, 5));
        assertTrue(pieceCanMove(king, 4, 5, 5, 6));
    }

    @Test
    public void testBlackKingOnE4CanCaptureOnD5() {
        Piece king = new King(BLACK);
        assertTrue(pieceCanMove(king, 4, 5, 3, 5));
        assertTrue(pieceCanMove(king, 4, 5, 3, 6));
        assertTrue(pieceCanMove(king, 4, 5, 4, 4));
        assertTrue(pieceCanMove(king, 4, 5, 4, 6));
        assertTrue(pieceCanMove(king, 4, 5, 3, 4));
        assertTrue(pieceCanMove(king, 4, 5, 5, 4));
        assertTrue(pieceCanMove(king, 4, 5, 5, 5));
        assertTrue(pieceCanMove(king, 4, 5, 5, 6));
    }

    @Test
    public void testWhiteQueenCanMoveOnDiagonalsAndStraights() {
        Piece queen = new Queen(WHITE);
        for (int rank = 1; rank <= 8 && rank != 3; rank++) {
            assertTrue(pieceCanMove(queen, 3, 4, rank, 4));
        }
        for (int file = 1; file <= 8 && file != 4; file++) {
            assertTrue(pieceCanMove(queen, 3, 4, 3, file));
        }
        for (int radius = 0; radius <= 6; radius++) {
            if (radius != 2) {
                assertTrue(pieceCanMove(queen, 3, 4, 1 + radius, 2 + radius));
            }
        }
        for (int radius = 0; radius <= 5; radius++) {
            if (radius != 2) {
                assertTrue(pieceCanMove(queen, 3, 4, 1 + radius, 6 - radius));
            }
        }
    }

    private boolean pieceCanMove(Piece piece, int sRank, int sFile, int eRank, int eFile) {
        Location start = new Location(sRank, sFile);
        Location end = new Location(eRank, eFile);
        return piece.canMoveInDirection(start, end);
    }

    @Test
    public void testIsEnemyPiece() {
        Piece knight = new Knight(WHITE);
        assertTrue(knight.isEnemyOf(new Knight(BLACK)));
        assertFalse(knight.isEnemyOf(knight));
        assertFalse(knight.isEnemyOf(null));
    }

    @Test
    public void testIsFriendlyPiece() {
        Piece bishop = new Bishop(WHITE);
        assertTrue(bishop.isFriendOf(new Pawn(WHITE)));
        assertFalse(bishop.isFriendOf(new Knight(BLACK)));
        assertFalse(bishop.isFriendOf(null));
    }

    @Test
    public void testPieceEquality() {
        Piece whitePawn = new Pawn(WHITE);
        Piece whitePawn2 = new Pawn(WHITE);
        Piece blackPawn = new Pawn(BLACK);
        Piece whiteKnight = new Knight(WHITE);
        assertTrue(whitePawn.equals(whitePawn2));
        assertFalse(whitePawn.equals(blackPawn));
        assertFalse(whitePawn.equals(whiteKnight));
        assertFalse(whitePawn.equals(null));
    }
}
