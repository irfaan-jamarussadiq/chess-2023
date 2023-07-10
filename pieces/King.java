package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;
import board.Location;
import board.Move;

public class King extends Piece {
    public King(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, Location location) {        
        Set<Move> moves = new HashSet<>();
        int rank = location.rank();
        int file = location.file();        
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

    @Override
    public String toString() {
        return (color == PieceColor.WHITE) ? "K" : "k";
    }    
}
