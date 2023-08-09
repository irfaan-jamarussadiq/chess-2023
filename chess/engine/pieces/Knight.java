package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.Move;

public class Knight extends Piece {

    public Knight(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(int startRank, int startFile, int endRank, int endFile) {
        int diffRank = Math.abs(startRank - endRank);
        int diffFile = Math.abs(startFile - endFile);
        return (diffRank == 2 && diffFile == 1) || (diffRank == 1 && diffFile == 2);
    }

    @Override
    public Set<Move> getMoves(int rank, int file) {
        Set<Move> moves = new HashSet<>();
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

}