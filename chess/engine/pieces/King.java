package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.board.Board;
import chess.engine.board.Location;
import chess.engine.board.Move;

public class King extends Piece {

    public King(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        if (start.rank() == end.rank() && start.file() == end.file()) {
            return false;
        }

        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return diffRank <= 1 && diffFile <= 1;
    }

    @Override
    public Set<Move> getPossibleMoves(Board board, Location location) {
        Set<Move> candidateMoves = new HashSet<>();
        int rank = location.rank();
        int file = location.file();
        candidateMoves.add(new Move(rank, file, rank + 1, file + 1));
        candidateMoves.add(new Move(rank, file, rank - 1, file - 1));
        candidateMoves.add(new Move(rank, file, rank + 1, file - 1));
        candidateMoves.add(new Move(rank, file, rank - 1, file + 1));
        candidateMoves.add(new Move(rank, file, rank, file + 1));
        candidateMoves.add(new Move(rank, file, rank, file - 1));
        candidateMoves.add(new Move(rank, file, rank + 1, file));
        candidateMoves.add(new Move(rank, file, rank - 1, file));

        Set<Move> moves = new HashSet<>();
        for (Move move : candidateMoves) {
            if (move.isWithinBoard(Board.BOARD_SIZE)) {
                boolean canMove = canMoveInDirection(move.start(), move.end());
                boolean canCapture = canCaptureInDirection(move.start(), move.end());
                Piece potentialEnemy = board.pieceAt(move.end());
                if ((canMove && potentialEnemy == null) || (canCapture && potentialEnemy != null && potentialEnemy.isEnemyOf(this))) {
                    moves.add(move);
                }
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return color == PieceColor.WHITE ? "♚" : "♔";
    }

}