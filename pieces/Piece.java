package pieces;

import static pieces.PieceColor.*;
import java.util.Set;
import board.Board;
import board.Location;
import board.Move;

public abstract class Piece {
    protected PieceColor color;

    public abstract Set<Move> getMoves(Board board, Location location);

    protected boolean validMove(Board board, Move move) {
        try {
            Board.validateLocation(move.startLocation());
            Board.validateLocation(move.endLocation());
        } catch (IllegalArgumentException e) {
            return false;
        }
        Location end = move.endLocation();
        return board.pieceAt(end.rank(), end.file()).color != color;
    }

    public boolean isEnemy(PieceColor color) {
        if (color == UNDEFINED || this.color == UNDEFINED) {
            return false;
        } else {
            return this.color != color;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || other.getClass() != getClass()) return false;

        Piece piece = (Piece) other;
        return piece.color == color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(color);
    }

}
