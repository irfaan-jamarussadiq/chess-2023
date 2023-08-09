package chess.engine.pieces;

import java.util.LinkedHashSet;
import java.util.Set;

import chess.engine.Board;
import chess.engine.Move;

public class Rook extends Piece {
    
    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(int startRank, int startFile, int endRank, int endFile) {
        return (startRank == endRank) ^ (startFile == endFile);
    }

    @Override
    public Set<Move> getMoves(int rank, int file) {
        Set<Move> moves = new LinkedHashSet<>();
        int radius = 1;
        int boardLength = Board.BOARD_SIZE;
        while (radius <= boardLength) {
            moves.add(new Move(rank, file, rank, file + radius));
            moves.add(new Move(rank, file, rank, file - radius));
            moves.add(new Move(rank, file, rank + radius, file));
            moves.add(new Move(rank, file, rank - radius, file));    
            radius++;
        }
        moves.removeIf(move -> {
            int endRank = move.endRank();
            int endFile = move.endFile();
            return endRank < 1 || endRank > boardLength || endFile < 1 || endFile > boardLength;
        });
        return moves;
    }

}