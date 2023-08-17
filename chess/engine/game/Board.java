package chess.engine.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static chess.engine.pieces.PieceColor.*;
import chess.engine.pieces.*;

public class Board {

    public static final int BOARD_SIZE = 8;
    private Map<Location, Piece> pieces;
    private Stack<Piece> capturedPieces;

    public Board() {
        pieces = new HashMap<>();
        addPlayerPieces(WHITE);
        addPlayerPieces(BLACK);
        capturedPieces = new Stack<>();
    }

    private void addPlayerPieces(PieceColor player) {
        for (int file = 1; file <= BOARD_SIZE; file++) {
            addPiece(player.getPawnStartingRank(), file, new Pawn(player));
        }

        addPiece(player.getPieceStartingRank(), 1, new Rook(player));
        addPiece(player.getPieceStartingRank(), 2, new Knight(player));
        addPiece(player.getPieceStartingRank(), 3, new Bishop(player));
        addPiece(player.getPieceStartingRank(), 4, new Queen(player));
        addPiece(player.getPieceStartingRank(), 5, new King(player));
        addPiece(player.getPieceStartingRank(), 6, new Bishop(player));
        addPiece(player.getPieceStartingRank(), 7, new Knight(player));
        addPiece(player.getPieceStartingRank(), 8, new Rook(player));
    }

    public void movePiece(Location start, Location end) {
        Piece pieceToMove = pieceAt(start);
        Piece pieceCaptured = pieceAt(end);
        if (pieceCaptured != null) {
            capturedPieces.push(pieceCaptured);
        }
        clearSquare(start.rank(), start.file());
        addPiece(end.rank(), end.file(), pieceToMove);
    }

    public boolean isValidMove(Move move) {
        if (move.isWithinBoard(BOARD_SIZE)) {
            Piece pieceToMove = pieceAt(move.start());
            Piece pieceToCapture = pieceAt(move.end());
            if (pieceToMove != null && pieceToMove.isEnemyOf(pieceToCapture)) {
                return pieceToMove.canCaptureInDirection(move.start(), move.end());
            } else if (pieceToMove != null && pieceToCapture == null) {
                return pieceToMove.canMoveInDirection(move.start(), move.end());
            } 
        }
        return false;
    }

    public Board deepCopy() {
        Board boardCopy = new Board();
        for (int rank = 1; rank <= BOARD_SIZE; rank++) {
            for (int file = 1; file <= BOARD_SIZE; file++) {
                Piece piece = this.pieceAt(new Location(rank, file));
                boardCopy.clearSquare(rank, file);
                boardCopy.addPiece(rank, file, piece);
            }
        }
        return boardCopy;
    }

    public Piece pieceAt(Location location) {
        int rank = location.rank();
        int file = location.file();
        if (!location.isWithinBoard(BOARD_SIZE)) {
            String message = String.format("Cannot find piece at rank %d and file %d.", rank, file);
            throw new IllegalArgumentException(message);
        }
        return pieces.get(location);
    }

    private void addPiece(int rank, int file, Piece piece) {
        pieces.put(new Location(rank, file), piece);
    }

    private void clearSquare(int rank, int file) {
        pieces.remove(new Location(rank, file));
    }

    public Map<Location, Piece> getPieceLocations() {
        return pieces;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int rank = 1; rank <= BOARD_SIZE; rank++) {
            result.append((9 - rank) + " ");
            for (int file = 1; file <= BOARD_SIZE; file++) {
                Piece piece = pieceAt(new Location(BOARD_SIZE - rank + 1, file));
                if (piece == null) {
                    result.append("  ");
                } else {
                    result.append(piece.toString() + " ");
                }
            }
            result.append("\n");
        }
        result.append("   a b c d e f g h");
        return result.toString();
    }

}
