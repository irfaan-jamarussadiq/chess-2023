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

    public boolean isEnemy(Piece potentialEnemy) {
        if (potentialEnemy == null) return false;
        return potentialEnemy.color != color;
    }

    public boolean isFriendly(Piece potentialFriend) {
        if (potentialFriend == null) return false;
        return potentialFriend.color == color;
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