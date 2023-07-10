package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;
import board.Location;
import board.Move;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        this.color = color;
    } 

    @Override
    public Set<Move> getMoves(Board board, Location location) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(getDiagonalMoves(board, location, 1, -1));
        moves.addAll(getDiagonalMoves(board, location, -1, 1));
        moves.addAll(getDiagonalMoves(board, location, 1, 1));
        moves.addAll(getDiagonalMoves(board, location, -1, -1));        
        return moves;
    }

    private Set<Move> getDiagonalMoves(Board board, Location location, int dRank, int dFile) {
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
        return (color == PieceColor.WHITE) ? "B" : "b";
    }
}
