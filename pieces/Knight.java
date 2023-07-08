package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;

public class Knight extends Piece {
    public Knight(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, int rank, int file) {
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(rank, file, rank + 2, file + 1));
        moves.add(new Move(rank, file, rank - 2, file - 1));
        moves.add(new Move(rank, file, rank - 2, file + 1));
        moves.add(new Move(rank, file, rank + 2, file - 1));
        moves.add(new Move(rank, file, rank - 1, file - 2));
        moves.add(new Move(rank, file, rank - 1, file + 2));
        moves.add(new Move(rank, file, rank + 1, file - 2));
        moves.add(new Move(rank, file, rank + 1, file + 2));
        moves.removeIf(move -> !validMove(board, move));
        return moves;
    }
}
