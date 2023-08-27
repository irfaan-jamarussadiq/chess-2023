package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.board.Board;
import chess.engine.board.Location;
import chess.engine.board.Move;

public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color);
    }

    @Override
    public Set<Move> getPossibleMoves(Board board, Location location) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(getDiagonalMoves(board, location, 1, 1));
        moves.addAll(getDiagonalMoves(board, location, -1, -1));
        moves.addAll(getDiagonalMoves(board, location, 1, -1));
        moves.addAll(getDiagonalMoves(board, location, -1, 1));
       return moves;
    }

    private Set<Move> getDiagonalMoves(Board board, Location location, int dRank, int dFile) {
        Set<Move> moves = new HashSet<>();
        int rank = location.rank() + dRank;
        int file = location.file() + dFile;
        Piece king = new King(color);
        while (rank >= 1 && file >= 1 && rank <= Board.BOARD_SIZE && file <= Board.BOARD_SIZE) {
            Piece piece = board.pieceAt(rank, file);
            if (king.isFriendOf(piece)) {
                break;
            }

            moves.add(new Move(location.rank(), location.file(), rank, file));

            if (king.isEnemyOf(piece)) {
                break;
            }

            rank += dRank;
            file += dFile;
        }

        return moves;
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        return Math.abs(start.rank() - end.rank()) == Math.abs(start.file() - end.file());
    }

    @Override
    public String toString() {
        return color == PieceColor.WHITE ? "♝" : "♗";
    }

}