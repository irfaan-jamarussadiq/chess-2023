package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.game.Location;
import chess.engine.game.Move;

public class Rook extends Piece {
    
    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMoveInDirection(Location start, Location end) {
        return (start.rank() == end.rank()) ^ (start.file() == end.file());
    }

    @Override
    public Set<Move> getPossibleMoves(Location location, int boardSize) {
        Set<Move> moves = new HashSet<>();
        int rank = location.rank();
        int file = location.file();
        int radius = 1;
        while (radius <= boardSize) {
            moves.add(new Move(rank, file, rank, file + radius));
            moves.add(new Move(rank, file, rank, file - radius));
            moves.add(new Move(rank, file, rank + radius, file));
            moves.add(new Move(rank, file, rank - radius, file));    
            radius++;
        }
        return moves;
    }

    @Override
    public String toString() {
        return color == PieceColor.WHITE ? "♖" : "♜";
    }

}