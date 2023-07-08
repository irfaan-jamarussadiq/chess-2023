package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;

public class Rook extends Piece {
    public Rook(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, int rank, int file) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(getLineOfMoves(board, rank, file, 0, 1));
        moves.addAll(getLineOfMoves(board, rank, file, 1, 0));
        moves.addAll(getLineOfMoves(board, rank, file, 0, -1));
        moves.addAll(getLineOfMoves(board, rank, file, -1, 0));
        return moves;
    }

    private Set<Move> getLineOfMoves(Board board, int rank, int file, int dRank, int dFile) {
        Set<Move> moves = new HashSet<>();
        int dx = dRank;
        int dy = dFile;
        while (validRankAndFile(rank + dx, file + dy)) {
            Move move = new Move(rank, file, rank + dx, file + dy);
            if (!validMove(board, move)) break;
            moves.add(move);
            dx += dRank;
            dy += dFile;
        }
        return moves;
    }
}
