package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.board.Board;
import chess.engine.board.Location;
import chess.engine.board.Move;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return (diffRank == diffFile) || (diffRank == 0 ^ diffFile == 0);
    }

    @Override
    public Set<Move> getPossibleMoves(Board board, Location location) {
        Set<Move> queenMoves = new HashSet<>();
        queenMoves.addAll(new Bishop(color).getPossibleMoves(board, location));
        queenMoves.addAll(new Rook(color).getPossibleMoves(board, location));
        return queenMoves;
    }

    @Override
    public String toString() {
        return color == PieceColor.WHITE ? "♛" : "♕";
    }

}