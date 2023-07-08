package pieces;

public abstract class Piece {
    protected PieceColor color;

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || other.getClass() != getClass()) return false;

        Piece piece = (Piece) other;
        return piece.color == color;
    }
}
