package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;
import board.Location;
import board.Move;

public class Knight extends Piece {
    public Knight(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, Location location) {        
        Set<Move> moves = new HashSet<>();
        int rank = location.rank();
        int file = location.file();        
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

    @Override
    public String toString() {
        return (color == PieceColor.WHITE) ? "N" : "n";
    }
}
