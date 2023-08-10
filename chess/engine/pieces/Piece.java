package chess.engine.pieces;

import java.util.Objects;
import java.util.Set;

import chess.engine.Move;

public abstract class Piece {
    
    public PieceColor color;    

    public Piece(PieceColor color) {
        this.color = color;
    }

    public abstract boolean canMove(int startRank, int startFile, int endRank, int endFile);

    public boolean canCapture(int startRank, int startFile, int endRank, int endFile) {
        return canMove(startRank, startFile, endRank, endFile);
    }

    public abstract Set<Move> getMoves(int rank, int file);

    public static boolean areEnemies(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) return false;
        return piece1.color != piece2.color;
    }

    public static boolean areFriends(Piece piece1, Piece piece2) {
        if (piece1 == null || piece2 == null) return false;
        return piece1.color == piece2.color;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null) return false;
        if (other instanceof Piece) {
            Piece otherPiece = (Piece) other;
            return otherPiece.color == color && otherPiece.getClass() == getClass();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, getClass());
    }

}