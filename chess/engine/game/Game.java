package chess.engine.game;

import static chess.engine.game.GameStatus.*;
import static chess.engine.pieces.PieceColor.*;
import chess.engine.pieces.*;

import java.util.*;

public class Game {

    private Board board;

    private Player currentPlayer;
    public static final Player whitePlayer = new Player(WHITE, 1, 5);
    public static final Player blackPlayer = new Player(BLACK, 8, 5);

    public Game() {
        board = new Board();
        currentPlayer = whitePlayer;
    }

    public void makeMove(Move move) {
        // Check if move is out of bounds of the board or not characteristic of piece
        if (!board.isValidMove(move)) {
            return;
        }

        // Check if the player was trying to castle.
        if (isCastlingMove(move) && isInCheck(currentPlayer, board)) {
            return;
        }

        // Check if the current player is trying to move the enemy's pieces.
        Piece pieceToMove = board.pieceAt(move.start());
        if (pieceToMove.isEnemyOf(currentPlayer.king)) {
            return;
        }

        // Otherwise, move the piece on the board.
        board.movePiece(move.start(), move.end());
        if (pieceToMove instanceof King) {
            currentPlayer.setKingLocation(move.end());
        }

        // If the move is legal and does not cause check, then update the current player.
        currentPlayer = (currentPlayer == whitePlayer) ? blackPlayer : whitePlayer;
    }

    private boolean isCastlingMove(Move move) {
        Location start = move.start();
        Location end = move.end();
        Piece pieceToMove = board.pieceAt(start);
        if (pieceToMove instanceof King) {
            if (start.rank() == end.rank() && Math.abs(start.file() - end.file()) == 2) {
                int kingRank = currentPlayer.color.getPieceStartingRank();
                if (start.rank() == kingRank && (end.file() == 3 || end.file() == 7)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isInCheck(Player player, Board board) {
        Set<Move> attacks = getAttackingMoves(player.color, player.kingLocation, board);
        Piece king = new King(player.color);
        for (Move move : attacks) {
            Piece piece = board.pieceAt(move.end());
            if (king.isEnemyOf(piece) 
                && piece.canCaptureInDirection(move.start(), move.end())
                && piece.getPossibleMoves(move.start(), Board.BOARD_SIZE).contains(move)
                && moveHasClearPath(move, board)) {
                return true;
            }
        }

        return false;
    }

    public boolean isInCheck(Player player) {
        return isInCheck(player, board);
    }

    private Set<Move> getAttackingMoves(PieceColor player, Location location, Board board) {
        Set<Move> attacks = new HashSet<>();
        attacks.addAll(getCaptureMoves(new Knight(player), location, board));

        Set<Move> queenPossibleMoves = getCaptureMoves(new Queen(player), location, board);
        for (Move move : queenPossibleMoves) {
            if (moveHasClearPath(move, board)) {
                attacks.add(move);
            }
        }

        return attacks;
    }

    private Set<Move> getCaptureMoves(Piece piece, Location location, Board board) {
        int boardSize = Board.BOARD_SIZE;
        Set<Move> possibleMoves = piece.getPossibleMoves(location, boardSize);
        Set<Move> captureMoves = new HashSet<>();
        for (Move move : possibleMoves) {
            if (move.isWithinBoard(boardSize)) {
                Piece potentialEnemy = board.pieceAt(move.end());
                if (piece.isEnemyOf(potentialEnemy)) {
                    captureMoves.add(move);
                }
            }
        }

        return captureMoves;
    }

    private boolean moveHasClearPath(Move move, Board board) {
        int boardSize = Board.BOARD_SIZE;
        if (!move.isWithinBoard(boardSize)) {
            return false;
        }

        Piece pieceToMove = board.pieceAt(move.start());
        Piece pieceAttacking = board.pieceAt(move.end());

        if (pieceToMove == null) {
            return false;
        }

        if (pieceAttacking != null && !pieceAttacking.canCaptureInDirection(move.end(), move.start())) {
            return false;
        }

        if (pieceToMove instanceof Knight) {
            return true;
        }

        int dRank = (int) Math.signum(move.end().rank() - move.start().rank());
        int dFile = (int) Math.signum(move.end().file() - move.start().file());
        int pathSquareRank = move.start().rank() + dRank;
        int pathSquareFile = move.start().file() + dFile;
        Location pathSquare = new Location(pathSquareRank, pathSquareFile);
        Move moveOnPath = new Move(move.start(), pathSquare);
        while (moveOnPath.isWithinBoard(boardSize) && board.pieceAt(pathSquare) == null) {
            if (pathSquare.equals(move.end())) {
                return true;
            }
            pathSquareRank += dRank;
            pathSquareFile += dFile;
            pathSquare = new Location(pathSquareRank, pathSquareFile);
            moveOnPath = new Move(move.start(), pathSquare);
        }

        Piece end = board.pieceAt(pathSquare);
        return move.end().equals(pathSquare) && (end == null || pieceToMove.isEnemyOf(end));
    }

    public boolean isInCheckmate(Player player) {
        return isInCheck(player) && playerHasNoMoves(player);
    }

    public boolean isInStalemate(Player player) {
        return !isInCheck(player) && playerHasNoMoves(player);
    }

    private boolean playerHasNoMoves(Player player) {
        Map<Location, Piece> pieceLocations = board.getPieceLocations();
        for (Location location : pieceLocations.keySet()) {
            Piece piece = pieceLocations.get(location);
            if (piece.isFriendOf(player.king)) {
                Set<Move> moves = piece.getPossibleMoves(location, Board.BOARD_SIZE);
                for (Move move : moves) {
                    if (board.isValidMove(move) && moveHasClearPath(move, board) 
                        && !moveCausesCheck(player, move)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean moveCausesCheck(Player player, Move move) {
        Board tempBoard = board.deepCopy();
        tempBoard.movePiece(move.start(), move.end());
        return isInCheck(player, tempBoard);
    }

    public GameStatus getStatus() {
        PieceColor playerColor = currentPlayer.color;
        boolean isCheckmate = isInCheckmate(currentPlayer);
        boolean isStalemate = isInStalemate(currentPlayer);
        if (isStalemate) {
            return DRAW;
        } else if (playerColor == WHITE && isCheckmate) {
            return WHITE_WON;
        } else if (playerColor == BLACK && isCheckmate) {
            return BLACK_WON;
        } else {
            return ONGOING;
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Current Player: " + currentPlayer.color + "\n");
        result.append("Game Status: " + getStatus() + "\n");
        result.append(board);
        return result.toString();
    }

}
