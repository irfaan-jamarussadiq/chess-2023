package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;

public class Empty extends Piece {
    public Empty() {
        color = PieceColor.UNDEFINED;
    }

    @Override
    public Set<Move> getMoves(Board board, int rank, int file) {
        return new HashSet<>();
    }
}
