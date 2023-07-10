package board;

import pieces.*;
import java.util.*;
import static board.GameStatus.*;
import static pieces.PieceColor.*;

public class Game {
    private Board board;
    private PieceColor currentPlayer;
    private Location whiteKing;
    private Location blackKing;
    private GameStatus status;
    private Stack<Move> moveHistory;

    public Game() {
        board = new Board();
        currentPlayer = WHITE;
        whiteKing = new Location(1, 5);
        blackKing = new Location(8, 5);
        status = ONGOING;
        moveHistory = new Stack<>();
    }

    public void movePiece(int startRank, int startFile, int endRank, int endFile) {
        // Move piece
        Location king = (currentPlayer == WHITE) ? whiteKing : blackKing;
        Location start = new Location(startRank, startFile);
        Location end = new Location(endRank, endFile);
        Move move = new Move(start, end);

        Piece piece = board.pieceAt(start.rank(), start.file());
        if (piece.isEnemy(currentPlayer)) {
            String format = "Current player %s cannot move enemy piece at (%d, %d)";
            String message = String.format(format, currentPlayer.name(), startRank, startFile);
            throw new IllegalArgumentException(message);
        }        

        board.movePiece(startRank, startFile, endRank, endFile);

        // Check if player is in check, checkmate, or stalemate.
        boolean inCheck = isInCheck(currentPlayer, king);
        boolean noMoves = playerHasNoMoves(currentPlayer);

        if (inCheck && noMoves) {
            status = (currentPlayer == WHITE) ? BLACK_WON : WHITE_WON;
            return;
        }

        if (!inCheck && noMoves) {
            status = DRAW;
            return;
        }

        // If player is in check after move, then the move is not valid.
        if (inCheck) {
            board.undoMove(move);
            return;
        }

        // Update current player after every move.
        currentPlayer = (currentPlayer == WHITE) ? BLACK : WHITE;

        // Update king locations
        if (piece instanceof King && currentPlayer == WHITE) {
            whiteKing = end;
        }

        if (piece instanceof King && currentPlayer == WHITE) {
            blackKing = end;
        }        

        moveHistory.push(move);
    }

    public boolean isInCheck(PieceColor player, Location king) {        
        Set<Move> moves = new HashSet<>();
        moves.addAll(new Knight(player).getMoves(board, king));
        moves.addAll(new Queen(player).getMoves(board, king));
        for (Move move : moves) {
            Location end = move.endLocation();
            if (board.pieceAt(end.rank(), end.file()).isEnemy(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean playerHasNoMoves(PieceColor player) {          
        Location king = (player == WHITE) ? whiteKing : blackKing;
        Set<Move> kingMoves = new King(player).getMoves(board, king);
        for (Move move : kingMoves) {
            Location start = move.startLocation();
            Location end = move.endLocation();
            board.movePiece(start.rank(), start.file(), end.rank(), end.file());
            boolean inCheck = isInCheck(player, end);
            board.undoMove(move);
            if (!inCheck) {
                return false;
            }
        }
        return true;
    }
    
    public PieceColor getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Stack<Move> getHistory() {
        return moveHistory;
    }
}
