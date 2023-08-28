package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.board.Board;
import chess.engine.board.Location;
import chess.engine.board.Move;

public class Pawn extends Piece {

    public Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        return start.file() == end.file() && end.rank() == start.rank() + color.getDirection()
                || canMoveTwoSquares(start.rank(), start.file(), end.rank(), end.file());
    }

    @Override
    public boolean canCaptureInDirection(Location start, Location end) {
        int pawnMoveDir = color.getDirection();
        return Math.abs(end.file() - start.file()) == 1 && end.rank() == start.rank() + pawnMoveDir;
    }

    private boolean canMoveTwoSquares(int startRank, int startFile, int endRank, int endFile) {
        return startFile == endFile
                && startRank == color.getPawnStartingRank()
                && endRank == startRank + 2 * color.getDirection();
    }

    @Override
    public Set<Move> getPossibleMoves(Board board, Location location) {
        Set<Move> candidateMoves = new HashSet<>();
        int rank = location.rank();
        int file = location.file();
        int dir = color.getDirection();

        candidateMoves.add(new Move(rank, file, rank + dir, file));

        if (rank == color.getPawnStartingRank()) {
            candidateMoves.add(new Move(rank, file, rank + 2 * dir, file));
        }

        candidateMoves.add(new Move(rank, file, rank + dir, file + dir));
        candidateMoves.add(new Move(rank, file, rank + dir, file - dir));

        Set<Move> moves = new HashSet<>();
        for (Move move : candidateMoves) {
            if (move.isWithinBoard(Board.BOARD_SIZE)) {
                moves.add(move);
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return color == PieceColor.WHITE ? "♟" : "♙";
    }

}