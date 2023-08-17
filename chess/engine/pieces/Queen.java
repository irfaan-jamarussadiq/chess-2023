package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.game.Location;
import chess.engine.game.Move;

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
    public Set<Move> getPossibleMoves(Location location, int boardSize) {
        Set<Move> bishopMoves = new Bishop(color).getPossibleMoves(location, boardSize);
        Set<Move> rookMoves = new Rook(color).getPossibleMoves(location, boardSize);
        Set<Move> queenMoves = new HashSet<>();
        queenMoves.addAll(bishopMoves);
        queenMoves.addAll(rookMoves);
        return queenMoves;
    }

    @Override
    public String toString() {
        return color == PieceColor.WHITE ? "♕" : "♛";
    }

}