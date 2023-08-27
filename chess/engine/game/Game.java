package chess.engine.game;

import chess.engine.pieces.Piece;
import chess.engine.pieces.PieceColor;
import static chess.engine.pieces.PieceColor.*;
import static chess.engine.game.GameStatus.*;

import chess.engine.board.Board;
import chess.engine.board.Move;

public class Game {
    private Board board;
    private PieceColor currentPlayer;

    public Game() {
        board = new Board();
        currentPlayer = WHITE;
    }

    public void makeMove(int startRank, int startFile, int endRank, int endFile) {
        Move move = new Move(startRank, startFile, endRank, endFile);
        Piece pieceToMove = board.pieceAt(move.start());
        if (pieceToMove != null && currentPlayer == pieceToMove.color) {
            boolean isValidMove = board.movePiece(move);
            if (isValidMove) {
                currentPlayer = (currentPlayer == WHITE) ? BLACK : WHITE;
            }
        }
    }

    public PieceColor getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        if (board.isInCheckmate(currentPlayer)) {
            return currentPlayer == BLACK ? WHITE_WON : BLACK_WON;
        } else if (board.isInStalemate(currentPlayer)) {
            return DRAW;
        } else {
            return ONGOING;
        }
    }

    @Override
    public String toString() {
        String result = "Current Player: " + currentPlayer + "\n";
        result += "Game status: " + getStatus() + "\n";
        result += board.toString();
        return result;
    }
}
