package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import chess.engine.pieces.*;
import static chess.engine.pieces.PieceColor.*;

public class PieceTests {

    @Test
    public void testBlackPawnOnE6CanCaptureOnD5() {
        Piece pawn = new Pawn(BLACK);
        assertTrue(pawn.canCapture(6, 5, 5, 4));
    }

    @Test
    public void testWhitePawnOnE6CannotCaptureOnD5() {
        Piece pawn = new Pawn(WHITE);
        assertFalse(pawn.canCapture(6, 5, 5, 4));
    }

    @Test
    public void testPawnCannotMoveLikeCapture() {
        Piece pawn = new Pawn(BLACK);
        assertFalse(pawn.canMove(6, 5, 5, 4));
    }

    @Test
    public void testBishopAtA8CanMoveOnDiagonals() {
        Piece bishop = new Bishop(WHITE);
        assertTrue(bishop.canMove(6, 3, 4, 5));
        assertTrue(bishop.canMove(6, 3, 8, 5));
        assertTrue(bishop.canMove(6, 3, 4, 1));
        assertTrue(bishop.canMove(6, 3, 1, 8));
    }

    @Test
    public void testBishopAtA8CannotMoveToD4() {
        Piece bishop = new Bishop(BLACK);
        assertFalse(bishop.canMove(8, 1, 4, 4));
    }

    @Test
    public void testWhiteKnightOnC6CanMoveOnEightSquares() {
        Piece knight = new Knight(BLACK);
        assertTrue(knight.canMove(6, 3, 5, 1));
        assertTrue(knight.canMove(6, 3, 7, 1));
        assertTrue(knight.canMove(6, 3, 8, 2));
        assertTrue(knight.canMove(6, 3, 4, 2));
        assertTrue(knight.canMove(6, 3, 8, 4));
        assertTrue(knight.canMove(6, 3, 4, 4));
        assertTrue(knight.canMove(6, 3, 5, 5));
        assertTrue(knight.canMove(6, 3, 7, 5));
    }

    @Test
    public void testBlackRookOnB5CanCaptureOnStraightLines() {
        Piece rook = new Rook(WHITE);
        for (int file = 1; file <= 8 && file != 2; file++) {
            assertTrue(rook.canMove(5, 2, 5, file));
        }

        for (int rank = 1; rank <= 8 && rank != 5; rank++) {
            assertTrue(rook.canMove(5, 2, rank, 2));
        }

        assertFalse(rook.canMove(5, 2, 5, 2));
    }

    @Test
    public void testWhiteKingOnE4CanCaptureOnD5() {
        Piece king = new King(WHITE);
        assertTrue(king.canMove(4, 5, 3, 4));
        assertTrue(king.canMove(4, 5, 3, 5));
        assertTrue(king.canMove(4, 5, 3, 6));
        assertTrue(king.canMove(4, 5, 4, 4));
        assertTrue(king.canMove(4, 5, 4, 6));
        assertTrue(king.canMove(4, 5, 5, 4));
        assertTrue(king.canMove(4, 5, 5, 5));
        assertTrue(king.canMove(4, 5, 5, 6));
    }

    @Test
    public void testBlackKingOnE4CanCaptureOnD5() {
        Piece king = new King(BLACK);
        assertTrue(king.canMove(4, 5, 3, 4));
        assertTrue(king.canMove(4, 5, 3, 5));
        assertTrue(king.canMove(4, 5, 3, 6));
        assertTrue(king.canMove(4, 5, 4, 4));
        assertTrue(king.canMove(4, 5, 4, 6));
        assertTrue(king.canMove(4, 5, 5, 4));
        assertTrue(king.canMove(4, 5, 5, 5));
        assertTrue(king.canMove(4, 5, 5, 6));
    }

    @Test
    public void testWhiteQueenCanMoveOnDiagonalsAndStraights() {
        Piece queen = new Queen(WHITE);
        for (int rank = 1; rank <= 8 && rank != 3; rank++) {
            assertTrue(queen.canMove(3, 4, rank, 4));
        }
        for (int file = 1; file <= 8 && file != 4; file++) {
            assertTrue(queen.canMove(3, 4, 3, file));
        }
        for (int radius = 0; radius <= 6; radius++) {
            if (radius != 2) {
                assertTrue(queen.canMove(3, 4, 1 + radius, 2 + radius));
            }
        }
        for (int radius = 0; radius <= 5; radius++) {
            if (radius != 2) {
                assertTrue(queen.canMove(3, 4, 1 + radius, 6 - radius));
            }
        }
    }

    @Test
    public void testIsEnemyPiece() {
        Piece knight = new Knight(WHITE);
        assertTrue(knight.isEnemy(new Knight(BLACK)));
        assertFalse(knight.isEnemy(knight));
        assertFalse(knight.isEnemy(null));
    }

    @Test
    public void testIsFriendlyPiece() {
        Piece bishop = new Bishop(WHITE);
        assertTrue(bishop.isFriendly(new Pawn(WHITE)));
        assertFalse(bishop.isFriendly(new Knight(BLACK)));
        assertFalse(bishop.isFriendly(null));
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
