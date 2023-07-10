package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;
import board.Location;
import board.Move;

public class Empty extends Piece {
    public Empty() {
        color = PieceColor.UNDEFINED;
    }

    @Override
    public Set<Move> getMoves(Board board, Location location) {
        return new HashSet<>();
    }

    @Override
    public String toString() {
        return " ";
    }    
}
