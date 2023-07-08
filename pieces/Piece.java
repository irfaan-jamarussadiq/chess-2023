package pieces;

import java.util.Set;
import board.Board;

public abstract class Piece {
    protected PieceColor color;

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || other.getClass() != getClass()) return false;

        Piece piece = (Piece) other;
        return piece.color == color;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(color);
    }

    protected boolean validRankAndFile(int rank, int file) {
        return rank >= 1 && file >= 1 && rank <= Board.BOARD_SIZE && file <= Board.BOARD_SIZE;
    }

    protected boolean validMove(Board board, Move move) {
        return validRankAndFile(move.startRank, move.startFile) 
            && validRankAndFile(move.endRank, move.endFile)
            && board.pieceAt(move.endRank, move.endFile).color != color;
    }    
    
    public abstract Set<Move> getMoves(Board board, int rank, int file);
}
