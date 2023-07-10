package pieces;

import java.util.Set;
import java.util.HashSet;
import board.*;

public class Queen extends Piece {
    public Queen(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, Location location) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(new Rook(color).getMoves(board, location));
        moves.addAll(new Bishop(color).getMoves(board, location));
        return moves;
    }

    @Override
    public String toString() {
        return (color == PieceColor.WHITE) ? "Q" : "q";
    }    
}
