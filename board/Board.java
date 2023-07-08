package board;

import java.util.Set;
import pieces.*;
import static pieces.PieceColor.*;

public class Board {
    
    public static int BOARD_SIZE = 8;
    private Piece[][] board;

    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        Pawn whitePawn = new Pawn(WHITE);
        Pawn blackPawn = new Pawn(BLACK);
        for (int file = 1; file <= BOARD_SIZE; file++) {
            board[1][file - 1] = whitePawn;
            board[6][file - 1] = blackPawn;
        }
        for (int rank = 3; rank <= 6; rank++) {
            for (int file = 1; file <= BOARD_SIZE; file++) {
                board[rank - 1][file - 1] = new Empty();
            }
        }
        setUpPieces(1, WHITE);
        setUpPieces(BOARD_SIZE, BLACK);
    }

    public void movePiece(int startRank, int startFile, int endRank, int endFile) {
        validateRankAndFile(startRank, startFile);
        validateRankAndFile(endRank, endFile);
        Piece pieceToMove = board[startRank - 1][startFile - 1];
        Move move = new Move(startRank, startFile, endRank, endFile);
        Set<Move> moves = pieceToMove.getMoves(this, startRank, startFile);
        if (!moves.contains(move)) {
            String message = String.format("Cannot move piece at (%d, %d) to (%d, %d)", startRank, startFile, endRank, endFile);
            throw new IllegalArgumentException(message);
        }
        board[endRank - 1][endFile - 1] = pieceToMove;
        board[startRank - 1][startFile - 1] = new Empty();
    }
    
    public Piece pieceAt(int rank, int file) {
        validateRankAndFile(rank, file);
        return board[rank - 1][file - 1];
    }    

    private void setUpPieces(int rank, PieceColor color) {
        board[rank - 1][0] = new Rook(color);
        board[rank - 1][1] = new Knight(color);
        board[rank - 1][2] = new Bishop(color);
        board[rank - 1][3] = new Queen(color);
        board[rank - 1][4] = new King(color);
        board[rank - 1][5] = new Bishop(color);        
        board[rank - 1][6] = new Knight(color);
        board[rank - 1][7] = new Rook(color);
    }

    private void validateRankAndFile(int rank, int file) {
        if (rank < 1 || file < 1 || rank > BOARD_SIZE || file > BOARD_SIZE) {
            String message = String.format("Cannot find piece at rank %d and file %d", rank, file);
            throw new IllegalArgumentException(message);
        }
    }
}
