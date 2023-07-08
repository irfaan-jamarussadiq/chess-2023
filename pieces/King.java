package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;

public class King extends Piece {
    public King(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, int rank, int file) {
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(rank, file, rank - 1, file - 1));
        moves.add(new Move(rank, file, rank + 1, file + 1));
        moves.add(new Move(rank, file, rank - 1, file + 1));
        moves.add(new Move(rank, file, rank + 1, file - 1));
        moves.add(new Move(rank, file, rank, file - 1));
        moves.add(new Move(rank, file, rank, file + 1));
        moves.add(new Move(rank, file, rank - 1, file));
        moves.add(new Move(rank, file, rank + 1, file));
        moves.removeIf(move -> !validMove(board, move));
        return moves;
    }
}
