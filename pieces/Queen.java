package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;

public class Queen extends Piece {
    public Queen(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, int rank, int file) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(new Rook(color).getMoves(board, rank, file));
        moves.addAll(new Bishop(color).getMoves(board, rank, file));
        return moves;
    }
}
