package chess.engine.pieces;

import java.util.LinkedHashSet;
import java.util.Set;

import chess.engine.game.Location;
import chess.engine.game.Move;

public class Bishop extends Piece {

    public Bishop(PieceColor color) {
        super(color);
    }

    @Override
    public Set<Move> getPossibleMoves(Location location, int boardSize) {
        Set<Move> moves = new LinkedHashSet<>();
        int rank = location.rank();
        int file = location.file();
        int radius = 1;
        while (radius <= boardSize) {
            moves.add(new Move(rank, file, rank + radius, file + radius));
            moves.add(new Move(rank, file, rank - radius, file - radius));
            moves.add(new Move(rank, file, rank - radius, file + radius));    
            moves.add(new Move(rank, file, rank + radius, file - radius));
            radius++;
        }
        return moves;
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        return Math.abs(start.rank() - end.rank()) == Math.abs(start.file() - end.file());
    }

    @Override
    public String toString() {
        return color == PieceColor.WHITE ? "♗" : "♝";
    }

}