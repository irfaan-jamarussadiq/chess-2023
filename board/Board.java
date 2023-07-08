package board;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.PieceColor;
import pieces.Queen;
import pieces.Rook;

public class Board {
    public static int BOARD_SIZE = 8;
    private Piece[][] board;
    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        Pawn whitePawn = new Pawn(PieceColor.WHITE);
        Pawn blackPawn = new Pawn(PieceColor.BLACK);
        for (int file = 1; file <= BOARD_SIZE; file++) {
            board[1][file - 1] = whitePawn;
            board[6][file - 1] = blackPawn;
        }
        setUpPieces(1, PieceColor.WHITE);
        setUpPieces(BOARD_SIZE, PieceColor.BLACK);
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
    
    public Piece pieceAt(int rank, int file) {
        if (rank < 1 || file < 1 || rank > BOARD_SIZE || file > BOARD_SIZE) {
            String message = String.format("Cannot find piece at rank %d and file %d", rank, file);
            throw new IllegalArgumentException(message);
        }
        return board[rank - 1][file - 1];
    }
}
