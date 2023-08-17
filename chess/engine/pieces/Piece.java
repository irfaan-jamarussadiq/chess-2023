package chess.engine.pieces;

import java.util.Objects;
import java.util.Set;

import chess.engine.game.Location;
import chess.engine.game.Move;

public abstract class Piece {
    
    public PieceColor color;    

    public Piece(PieceColor color) {
        this.color = color;
    }

    public abstract Set<Move> getPossibleMoves(Location location, int boardSize);

    public abstract boolean canMoveInDirection(Location start, Location end);

    public boolean canCaptureInDirection(Location start, Location end) {
        return canMoveInDirection(start, end);
    }

    public boolean isEnemyOf(Piece piece) {
        if (piece == null) {
            return false;
        }

        return this.color != piece.color;
    }

    public boolean isFriendOf(Piece piece) {
        if (piece == null) {
            return false;
        }

        return this.color == piece.color;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null) {
            return false;
        }

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