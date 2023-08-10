package chess.engine;

import static chess.engine.pieces.PieceColor.*;
import static chess.engine.GameStatus.*;
import chess.engine.pieces.*;

import java.util.Set;

public class Game {

    private Board board;
    private Player currentPlayer;
    private GameStatus status;
    public static final Player whitePlayer = new Player(WHITE, 1, 5);
    public static final Player blackPlayer = new Player(BLACK, 8, 5);

    public Game() {
        board = new Board();
        currentPlayer = whitePlayer;
        status = ONGOING;
    }

    public void makeMove(int startRank, int startFile, int endRank, int endFile) {
        board.movePiece(startRank, startFile, endRank, endFile);
        if (isInCheck(currentPlayer)) {
            board.undoMove(startRank, startFile, endRank, endFile);
        } else if (board.pieceAt(endRank, endFile) instanceof King) {
            currentPlayer.kingRank = endRank;
            currentPlayer.kingFile = endFile;        
        }
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }

    public boolean isInCheck(Player player) {
        int kingRank = player.kingRank;
        int kingFile = player.kingFile;
        Piece king = board.pieceAt(kingRank, kingFile);
        for (Move attack : board.getAttackingMoves(player.color, kingRank, kingFile)) {
            Piece potentialEnemy = board.pieceAt(attack.endRank(), attack.endFile());
            if (potentialEnemy != null && potentialEnemy.isEnemy(king)) {
                return true;
            }
        }
        return false;
    }

    public boolean isInCheckmate(Player player) {
        return isInCheck(player) && playerHasNoMoves(player);
    }

    public boolean isInStalemate(Player player) {
        return !isInCheck(player) && playerHasNoMoves(player);
    }

    private boolean playerHasNoMoves(Player player) {
        for (int rank = 1; rank <= Board.BOARD_SIZE; rank++) {
            for (int file = 1; file <= Board.BOARD_SIZE; file++) {
                Piece piece = board.pieceAt(rank, file);
                if (piece != null && piece.color == player.color) {
                    Set<Move> moves = board.getAttackingMoves(piece.color, rank, file);
                    if (moves.size() > 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void updateStatus() {
        PieceColor playerColor = currentPlayer.color;
        boolean isCheckmate = isInCheckmate(currentPlayer);
        boolean isStalemate = isInStalemate(currentPlayer);
        if (isStalemate) {
            status = DRAW;
        } else if (playerColor == WHITE && isCheckmate) {
            status = WHITE_WON;
        } else if (playerColor == BLACK && isCheckmate) {
            status = BLACK_WON;
        }
    }

    public GameStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        String result = "";
        result += "Current Player: " + currentPlayer.color.name() + "\n";
        result += board.toString();
        return result;
    }

}
