package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;
import board.Location;
import board.Move;

public class Rook extends Piece {
    public Rook(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, Location location) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(getLineOfMoves(board, location, 0, 1));
        moves.addAll(getLineOfMoves(board, location, 1, 0));
        moves.addAll(getLineOfMoves(board, location, 0, -1));
        moves.addAll(getLineOfMoves(board, location, -1, 0));
        return moves;
    }

    private Set<Move> getLineOfMoves(Board board, Location location, int dRank, int dFile) {
        Set<Move> moves = new HashSet<>();
        int rank = location.rank();
        int file = location.file();
        int dx = dRank;
        int dy = dFile;
        while (validMove(board, new Move(rank, file, rank + dx, file + dy))) {
            Move move = new Move(rank, file, rank + dx, file + dy);
            if (!validMove(board, move)) break;
            moves.add(move);
            dx += dRank;
            dy += dFile;
        }
        return moves;
    }

    @Override
    public String toString() {
        return (color == PieceColor.WHITE) ? "R" : "r";
    }    
}
