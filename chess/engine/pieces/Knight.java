package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.game.Location;
import chess.engine.game.Move;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        int diffRank = Math.abs(start.rank() - end.rank());
        int diffFile = Math.abs(start.file() - end.file());
        return (diffRank == 2 && diffFile == 1) || (diffRank == 1 && diffFile == 2);
    }

    @Override
    public Set<Move> getPossibleMoves(Location location, int boardSize) {
        Set<Move> moves = new HashSet<>();
        int rank = location.rank();
        int file = location.file();
        moves.add(new Move(rank, file, rank + 1, file + 2));
        moves.add(new Move(rank, file, rank - 1, file - 2));
        moves.add(new Move(rank, file, rank + 1, file - 2));
        moves.add(new Move(rank, file, rank - 1, file + 2));
        moves.add(new Move(rank, file, rank + 2, file + 1));
        moves.add(new Move(rank, file, rank - 2, file + 1));
        moves.add(new Move(rank, file, rank + 2, file - 1));
        moves.add(new Move(rank, file, rank - 2, file - 1));
        return moves;
    }

    @Override
    public String toString() {
        return (color == PieceColor.WHITE) ? "♘" : "♞";
    }

}