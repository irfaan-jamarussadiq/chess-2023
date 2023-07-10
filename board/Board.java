package board;

import java.util.Set;
import java.util.Stack;

import pieces.*;
import static pieces.PieceColor.*;

public class Board {
    
    public static int BOARD_SIZE = 8;
    private Piece[][] board;
    private Stack<Piece> capturedPieces;

    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        setUpStartingPosition();
        capturedPieces = new Stack<>();
    }

    public static void validateLocation(Location location) {
        if(location.rank() < 1 || location.file() < 1 
        || location.rank() > Board.BOARD_SIZE 
        || location.file() > Board.BOARD_SIZE) {
            String message = String.format("Cannot find piece at rank %d and file %d", location.rank(), location.file());
            throw new IllegalArgumentException(message);            
        }
    }

    public void movePiece(int startRank, int startFile, int endRank, int endFile) {
        Location start = new Location(startRank, startFile);
        Location end = new Location(endRank, endFile);
        // Validate start and end squares to move piece.
        validateLocation(start);
        validateLocation(end);

        // Check that the current player can move the piece.
        Piece pieceToMove = board[startRank - 1][startFile - 1];
        Piece capturedPiece = board[endRank - 1][endFile - 1];
        if (pieceToMove instanceof Empty) return;

        // Check if move is possible for the piece.
        Move move = new Move(start, end);
        Set<Move> moves = pieceToMove.getMoves(this, start);
        if (!moves.contains(move)) {
            String format = "Cannot move piece at (%d, %d) to (%d, %d)";
            String message = String.format(format, startRank, startFile, endRank, endFile);
            throw new IllegalArgumentException(message);
        }

        // Move the piece.
        board[endRank - 1][endFile - 1] = pieceToMove;
        board[startRank - 1][startFile - 1] = new Empty();

        if (capturedPiece.getClass() != Empty.class) {
            capturedPieces.push(capturedPiece);
        }
    }

    public void undoMove(Move move) {
        Location start = move.startLocation();
        Location end = move.endLocation();

        Piece piece = board[end.rank() - 1][end.file() - 1];
        Piece capturedPiece = capturedPieces.isEmpty() ? new Empty() : capturedPieces.pop();
        board[start.rank() - 1][start.file() - 1] = piece;
        board[end.rank() - 1][end.file() - 1] = capturedPiece;
    }    
    
    public Piece pieceAt(int rank, int file) {
        validateLocation(new Location(rank, file));
        return board[rank - 1][file - 1];
    }
    
    @Override
    public String toString() {
        String result = "";
        for (Piece[] row : board) {
            for (Piece piece : row) {
                result += piece.toString() + " ";
            }
            result += "\n";
        }
        return result;
    }

    private void setUpStartingPosition() {
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
}
