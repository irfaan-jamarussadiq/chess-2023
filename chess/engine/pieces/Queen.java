package chess.engine.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.engine.Move;

public class Queen extends Piece {

    public Queen(PieceColor color) {
        super(color);
    }

    @Override
    public boolean canMove(int startRank, int startFile, int endRank, int endFile) {
        int diffRank = Math.abs(startRank - endRank);
        int diffFile = Math.abs(startFile - endFile);
        return (diffRank == diffFile) || (diffRank == 0 ^ diffFile == 0);
    }

    @Override
    public Set<Move> getMoves(int rank, int file) {
        Set<Move> bishopMoves = new Bishop(color).getMoves(rank, file);
        Set<Move> rookMoves = new Rook(color).getMoves(rank, file);
        Set<Move> queenMoves = new HashSet<>();
        queenMoves.addAll(bishopMoves);
        queenMoves.addAll(rookMoves);
        return queenMoves;
   }

}