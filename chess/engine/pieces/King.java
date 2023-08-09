package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.Move;

public class King extends Piece {

    public King(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(int startRank, int startFile, int endRank, int endFile) {
        if (startRank == endRank && startFile == endFile) return false;
        int diffRank = Math.abs(startRank - endRank);
        int diffFile = Math.abs(startFile - endFile);
        return diffRank <= 1 && diffFile <= 1;
    }

    @Override
    public Set<Move> getMoves(int rank, int file) {
        Set<Move> moves = new HashSet<>();
        moves.add(new Move(rank, file, rank + 1, file + 1));
        moves.add(new Move(rank, file, rank - 1, file - 1));
        moves.add(new Move(rank, file, rank + 1, file - 1));
        moves.add(new Move(rank, file, rank - 1, file + 1));
        moves.add(new Move(rank, file, rank, file + 1));
        moves.add(new Move(rank, file, rank, file - 1));
        moves.add(new Move(rank, file, rank + 1, file));
        moves.add(new Move(rank, file, rank - 1, file));
        return moves;
    }

}