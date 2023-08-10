package chess.engine;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

import static chess.engine.pieces.PieceColor.*;
import chess.engine.pieces.*;

public class Board {

    public static final int BOARD_SIZE = 8;
    private Square[][] board;
    private Stack<Piece> capturedPieces;

    public Board() {
        board = new Square[BOARD_SIZE][BOARD_SIZE];
        capturedPieces = new Stack<>();

        addPlayerPieces(WHITE);

        for (int rank = 3; rank <= BOARD_SIZE - 2; rank++) {
            for (int file = 1; file <= BOARD_SIZE; file++) {
                clearSquare(rank, file);
            }
        }

        addPlayerPieces(BLACK);

    }

    private void addPlayerPieces(PieceColor player) {
        Piece pawn = new Pawn(player);
        int pawnRank = player.getPawnStartingRank();
        for (int file = 1; file <= BOARD_SIZE; file++) {
            addPiece(pawnRank, file, pawn);
        }

        int piecesStartingRank = pawnRank - player.getDirection();

        Piece rook = new Rook(player);
        Piece knight = new Knight(player);
        Piece bishop = new Bishop(player);
        Piece queen = new Queen(player);
        Piece king = new King(player);
        addPiece(piecesStartingRank, 1, rook);
        addPiece(piecesStartingRank, 2, knight);
        addPiece(piecesStartingRank, 3, bishop);
        addPiece(piecesStartingRank, 4, queen);
        addPiece(piecesStartingRank, 5, king);
        addPiece(piecesStartingRank, 6, bishop);
        addPiece(piecesStartingRank, 7, knight);
        addPiece(piecesStartingRank, 8, rook);
    }

    public void movePiece(int startRank, int startFile, int endRank, int endFile) {
        Piece pieceToMove = pieceAt(startRank, startFile);
        Piece pieceToCapture = pieceAt(endRank, endFile);
        boolean moveToEmptySquare = pieceToCapture == null
                && pieceToMove.canMove(startRank, startFile, endRank, endFile);
        boolean moveCapturesPiece = pieceToCapture != null
                && pieceToMove.canCapture(startRank, startFile, endRank, endFile);
        if (moveToEmptySquare || moveCapturesPiece) {
            if (pieceToCapture != null) {
                capturedPieces.add(pieceToCapture);
            }
            clearSquare(startRank, startFile);
            addPiece(endRank, endFile, pieceToMove);
        }
    }

    private static boolean isWithinBounds(int startRank, int startFile, int endRank, int endFile) {
        return startRank >= 1 && startRank <= BOARD_SIZE
                && startFile >= 1 && startFile <= BOARD_SIZE
                && endRank >= 1 && endRank <= BOARD_SIZE
                && endFile >= 1 && endFile <= BOARD_SIZE;
    }

    public Piece pieceAt(int rank, int file) {
        if (rank < 1 || rank > BOARD_SIZE || file < 1 || file > BOARD_SIZE) {
            String message = String.format("Cannot find piece at rank %d and file %d.", rank, file);
            throw new IllegalArgumentException(message);
        }
        return board[rank - 1][file - 1].getPiece();
    }

    private void addPiece(int rank, int file, Piece piece) {
        board[rank - 1][file - 1] = new Square(piece);
    }

    private void clearSquare(int rank, int file) {
        board[rank - 1][file - 1] = new Square();
    }

    public void undoMove(int startRank, int startFile, int endRank, int endFile) {
        Piece pieceMoved = pieceAt(endRank, endFile);
        addPiece(startRank, startFile, pieceMoved);
        if (capturedPieces.isEmpty()) {
            clearSquare(endRank, endFile);
        } else {
            addPiece(endRank, endFile, capturedPieces.pop());
        }
    }

    public Set<Move> getAttackingMoves(PieceColor player, int rank, int file) {
        Set<Move> attacks = new HashSet<>();
        Piece enemyKnight = new Knight((player == WHITE) ? BLACK : WHITE);
        // Get knight attacks
        Set<Move> knightMoves = new Knight(player).getMoves(rank, file);
        Set<Move> knightAttacks = knightMoves.stream()
                .filter(move -> enemyKnight.canCapture(move.endRank(), move.endFile(), rank, file))
                .collect(Collectors.toSet());
        attacks.addAll(knightAttacks);

        // Get queen attacks
        Piece queen = new Queen(player);
        int[][] dirs = new int[][] { { 1, 1 }, { -1, -1 }, { 1, -1 }, { -1, 1 }, { 0, 1 }, { 1, 0 }, { 0, -1 },
                { -1, 0 } };
        for (int[] dir : dirs) {
            int queenRank = rank + dir[0];
            int queenFile = file + dir[1];
            while (queenRank <= BOARD_SIZE && queenRank >= 1 && queenFile <= BOARD_SIZE && queenFile >= 1) {
                Piece piece = pieceAt(queenRank, queenFile);
                if (piece != null && piece.isFriendly(queen)) {
                    break;
                }
                attacks.add(new Move(rank, file, queenRank, queenFile));
                queenRank += dir[0];
                queenFile += dir[1];
            }
        }

        return attacks.stream()
                .filter(move -> isWithinBounds(move.startRank(), move.startFile(), move.endRank(), move.endFile()))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        String result = "";
        for (int rank = 1; rank <= BOARD_SIZE; rank++) {
            for (int file = 1; file <= BOARD_SIZE; file++) {
                Piece piece = pieceAt(BOARD_SIZE - rank + 1, file);
                if (piece == null) {
                    result += ".";
                } else if (piece instanceof Knight) {
                    result += (piece.color == WHITE) ? "N" : "n";
                } else {
                    char c = piece.getClass().getSimpleName().charAt(0);
                    result += (piece.color == WHITE) ? c : Character.toLowerCase(c);
                }
                result += " ";
            }
            result += "\n";
        }
        return result;
    }

}

class Square {

    Piece piece;

    public Square(Piece piece) {
        this.piece = piece;
    }

    public Square() {
        this(null);
    }

    public Piece getPiece() {
        return piece;
    }

}
