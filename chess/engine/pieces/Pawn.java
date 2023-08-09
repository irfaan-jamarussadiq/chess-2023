package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.Move;

public class Pawn extends Piece {
        
    public Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(int startRank, int startFile, int endRank, int endFile) {
        return startFile == endFile && endRank == startRank + color.getDirection()
            || canMoveTwoSquares(startRank, startFile, endRank, endFile);
    }

    @Override
    public boolean canCapture(int startRank, int startFile, int endRank, int endFile) {
        return Math.abs(endFile - startFile) == 1 && endRank == startRank + color.getDirection();
    }

    private boolean canMoveTwoSquares(int startRank, int startFile, int endRank, int endFile) {
        return startFile == endFile 
            && startRank == color.getPawnStartingRank() 
            && endRank == startRank + 2 * color.getDirection();
    }

    @Override
    public Set<Move> getMoves(int rank, int file) {
        Set<Move> moves = new HashSet<>();
        int dir = color.getDirection();
        moves.add(new Move(rank, file, rank + dir, file));
        moves.add(new Move(rank, file, rank + dir, file + dir));
        moves.add(new Move(rank, file, rank + dir, file - dir));
        if (rank == color.getPawnStartingRank()) {
            moves.add(new Move(rank, file, rank + 2 * dir, file));
        }
        return moves;
    }

}