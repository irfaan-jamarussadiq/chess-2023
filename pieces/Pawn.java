package pieces;

import java.util.Set;
import java.util.HashSet;
import board.Board;
import static pieces.PieceColor.*;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        this.color = color;
    }

    @Override
    public Set<Move> getMoves(Board board, int rank, int file) {
        Set<Move> moves = new HashSet<>();
        int direction = (color == WHITE) ? 1 : -1;
        int firstRank = (color == WHITE) ? 2 : 7;
        int enPassantRank = (color == WHITE) ? 4 : 5;
        PieceColor otherPlayer = (color == WHITE) ? BLACK : WHITE;
        
        // Move one square
        moves.add(new Move(rank, file, rank + direction, file));

        // Move two squares on first move
        if (rank == firstRank) {
            moves.add(new Move(rank, file, rank + 2 * direction, file));        
        }

        // enPassant move
        if (rank == enPassantRank && file > 1 
            && board.pieceAt(rank, file - 1).color == otherPlayer 
            && board.pieceAt(rank, file - 1) instanceof Pawn) {
            moves.add(new Move(rank, file, rank, file - 1));
        }

        if (rank == enPassantRank && file < Board.BOARD_SIZE 
            && board.pieceAt(rank, file + 1).color == otherPlayer
            && board.pieceAt(rank, file + 1) instanceof Pawn) {
            moves.add(new Move(rank, file, rank, file - 1));
        }

        // Identify captures
        if (file > 1 
            && board.pieceAt(rank + direction, file - 1).color == otherPlayer) {
            moves.add(new Move(rank, file, rank + direction, file - 1));
        }

        if (file < Board.BOARD_SIZE 
            && board.pieceAt(rank + direction, file + 1).color == otherPlayer) {
            moves.add(new Move(rank, file, rank + direction, file + 1));
        }

        return moves;
    }
}
