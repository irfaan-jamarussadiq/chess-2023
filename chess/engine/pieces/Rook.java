package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.board.Board;
import chess.engine.board.Location;
import chess.engine.board.Move;

public class Rook extends Piece {

    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        return (start.rank() == end.rank()) ^ (start.file() == end.file());
    }

    @Override
    public Set<Move> getPossibleMoves(Board board, Location location) {
        Set<Move> moves = new HashSet<>();
        moves.addAll(getStraightMoves(board, location, 1, 0));
        moves.addAll(getStraightMoves(board, location, 0, 1));
        moves.addAll(getStraightMoves(board, location, -1, 0));
        moves.addAll(getStraightMoves(board, location, 0, -1));
        return moves;
    }

    private Set<Move> getStraightMoves(Board board, Location location, int dRank, int dFile) {
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
    public String toString() {
        return color == PieceColor.WHITE ? "♜" : "♖";
    }

}