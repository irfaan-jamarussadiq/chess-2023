package chess.engine.board;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

import static chess.engine.pieces.PieceColor.*;

import chess.engine.pieces.*;

public class Board {

    public static final int BOARD_SIZE = 8;
    private Map<Location, Piece> board;

    public Board() {
        board = new HashMap<>();
        addPlayerPieces(WHITE);
        addPlayerPieces(BLACK);
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

    public boolean movePiece(Move move) {
        if (!move.isWithinBoard(BOARD_SIZE)) {
            throw new IllegalArgumentException("Move is illegal");
        }

        Location start = move.start();
        Location end = move.end();

        if (isNormalMove(start, end)) {
            makeNormalMove(start, end);
        } else if (isCaptureMove(start, end)) {
            makeCaptureMove(start, end);
        } else if (isEnPassantMove(start, end)) {
            makeEnPassantMove(start, end);
        } else if (isLongCastlingMove(start, end)) {
            makeLongCastlingMove(start, end);
        } else if (isShortCastlingMove(start, end)) {
            makeShortCastlingMove(start, end);
        } else {
            return false;
        }

        return true;
    }

    private void makeNormalMove(Location start, Location end) {
        Piece pieceToMove = pieceAt(start);
        pieceToMove.setHasMoved();
        clearSquare(start);
        addPiece(end, pieceToMove);
    }

    public boolean isNormalMove(Location start, Location end) {
        Piece pieceToMove = pieceAt(start);
        Piece pieceAtEnd = pieceAt(end);
        Move move = new Move(start, end);
        return move.hasClearPath(this) 
                && pieceToMove != null && pieceAtEnd == null
                && pieceToMove.canMoveInDirection(start, end);
    }

    private void makeCaptureMove(Location start, Location end) {
        makeNormalMove(start, end);
    }

    public boolean isCaptureMove(Location start, Location end) {
        Piece pieceToMove = pieceAt(start);
        Piece pieceAtEnd = pieceAt(end);
        return pieceToMove != null && pieceAtEnd != null
                && pieceToMove.canCaptureInDirection(start, end)
                && pieceToMove.isEnemyOf(pieceAtEnd);
    }

    private void makeEnPassantMove(Location start, Location end) {
        makeNormalMove(start, end);
        clearSquare(new Location(start.rank(), end.file()));
    }

    public boolean isEnPassantMove(Location start, Location end) {
        // Check if move is a pawn moving one square forward diagonally.
        Piece pieceToMove = pieceAt(start);
        if (pieceToMove == null) {
            return false;
        }

        boolean canCapture = pieceToMove.canCaptureInDirection(start, end);
        // Check that pawn is on appropriate rank for enPassant.
        int playerDir = pieceToMove.color.getDirection();
        PieceColor enemyColor = (pieceToMove.color == WHITE) ? BLACK : WHITE;
        int enPassantRank = enemyColor.getPawnStartingRank() - 2 * playerDir;
        boolean onEnPassantRank = start.rank() == enPassantRank;
        // Check if pawn exists to enPassant.
        Piece capturedPawn = pieceAt(new Location(start.rank(), end.file()));
        return pieceToMove instanceof Pawn && canCapture && onEnPassantRank
                && capturedPawn instanceof Pawn;
    }

    private void makeLongCastlingMove(Location start, Location end) {
        // Move king
        makeNormalMove(start, end);
        // Move rook
        Location rookStart = new Location(start.rank(), 1);
        Location rookEnd = new Location(start.rank(), end.file() + 1);
        makeNormalMove(rookStart, rookEnd);
    }

    public boolean isLongCastlingMove(Location start, Location end) {
        // Check if king has not moved
        Piece king = pieceAt(start);
        if (king == null) {
            return false;
        }

        boolean kingHasNotMoved = king instanceof King && !king.hasMoved();
        // Check if rook has not moved
        Piece rook = pieceAt(new Location(start.rank(), 1));
        if (rook == null) {
            return false;
        }

        boolean rookHasNotMoved = rook instanceof Rook && !rook.hasMoved();
        // Check if king is not in check
        boolean kingNotInCheck = !isInCheck(king.color);
        // Check if squares between king and rook are empty
        Location firstEmpty = new Location(start.rank(), start.file() - 1);
        Location secondEmpty = new Location(start.rank(), start.file() - 2);
        Location thirdEmpty = new Location(start.rank(), start.file() - 3);
        boolean areEmpty = pieceAt(firstEmpty) == null && pieceAt(secondEmpty) == null
                && pieceAt(thirdEmpty) == null;
        // Check if empty squares are not attacked by enemy pieces
        boolean squaresNotAttacked = !squareAttacked(firstEmpty, king.color)
                && !squareAttacked(secondEmpty, king.color)
                && !squareAttacked(thirdEmpty, king.color);
        return kingHasNotMoved && rookHasNotMoved && kingNotInCheck && areEmpty && squaresNotAttacked;
    }

    private void makeShortCastlingMove(Location start, Location end) {
        // Move king
        makeNormalMove(start, end);
        // Move rook
        Location rookStart = new Location(start.rank(), 8);
        Location rookEnd = new Location(start.rank(), end.file() - 1);
        makeNormalMove(rookStart, rookEnd);
    }

    public boolean isShortCastlingMove(Location start, Location end) {
        // Check if king has not moved
        Piece king = pieceAt(start);
        if (king == null || start.file() != 5) {
            return false;
        }

        boolean kingHasNotMoved = king instanceof King && !king.hasMoved();
        // Check if rook has not moved
        Piece rook = pieceAt(new Location(start.rank(), 8));
        if (rook == null) {
            return false;
        }

        boolean rookHasNotMoved = rook instanceof Rook && !rook.hasMoved();
        // Check if king is not in check
        boolean kingNotInCheck = !isInCheck(king.color);
        // Check if squares between king and rook are empty
        Location firstEmpty = new Location(start.rank(), start.file() + 1);
        Location secondEmpty = new Location(start.rank(), start.file() + 2);
        boolean areEmpty = pieceAt(firstEmpty) == null && pieceAt(secondEmpty) == null;
        // Check if empty squares are not attacked by enemy pieces
        boolean squaresNotAttacked = !squareAttacked(firstEmpty, king.color)
                && !squareAttacked(secondEmpty, king.color);
        return kingHasNotMoved && rookHasNotMoved && kingNotInCheck && areEmpty && squaresNotAttacked;
    }

    public boolean isInCheckmate(PieceColor player) {
        if (!isInCheck(player)) {
            return false;
        }

        Location kingLocation = getKingLocation(player);
        King king = new King(player);
        Set<Move> kingMoves = king.getPossibleMoves(this, kingLocation);
        for (Move move : kingMoves) {
            if (move.isWithinBoard(BOARD_SIZE) && !squareAttacked(move.end(), player)) {
                // Move piece
                Piece startPiece = pieceAt(move.start());
                Piece endPiece = pieceAt(move.end());
                clearSquare(move.start());
                addPiece(move.end(), startPiece);
                // Determine if king is in check
                boolean isInCheck = isInCheck(player);
                // Undo move
                addPiece(move.start(), startPiece);
                addPiece(move.end(), endPiece);
                if (!isInCheck) {
                    return false;
                }
            }
        }

        // Find attacker and see if any piece can capture it.
        Set<Location> attackers = getAttackers(kingLocation, player);
        if (attackers.size() > 1) {
            return true;
        }

        PieceColor otherPlayer = (player == WHITE) ? BLACK : WHITE;
        Set<Location> defenders = getAttackers(attackers.iterator().next(), otherPlayer);
        return attackers.size() == 1 && defenders.size() == 0;
    }

    boolean squareAttacked(Location location, PieceColor player) {
        return getAttackers(location, player).size() > 0;
    }

    private Set<Location> getAttackers(Location location, PieceColor player) {
        Set<Location> attackers = new HashSet<>();
        Set<Move> queenMoves = new Queen(player).getPossibleMoves(this, location);
        for (Move move : queenMoves) {
            Piece potentialEnemy = pieceAt(move.end());
            if (potentialEnemy != null) {
                boolean isEnemy = potentialEnemy.isEnemyOf(new King(player));
                boolean canCapture = potentialEnemy.canCaptureInDirection(move.end(), move.start());
                if (isEnemy && canCapture) {
                    attackers.add(move.end());
                }
            }
        }

        Set<Move> knightMoves = new Knight(player).getPossibleMoves(this, location);
        for (Move move : knightMoves) {
            Piece potentialEnemy = pieceAt(move.end());
            if (potentialEnemy != null) {
                boolean isEnemy = potentialEnemy.isEnemyOf(new King(player));
                boolean canCapture = potentialEnemy.canCaptureInDirection(move.end(), move.start());
                if (isEnemy && canCapture) {
                    attackers.add(move.end());
                }
            }
        }
        
        return attackers;
    }

    boolean isInCheck(PieceColor player) {
        Location kingLocation = getKingLocation(player);
        return squareAttacked(kingLocation, player);
    }

    public boolean isInStalemate(PieceColor player) {
        Location kingLocation = getKingLocation(player);
        King king = new King(player);
        Set<Move> kingMoves = king.getPossibleMoves(this, kingLocation);
        for (Move move : kingMoves) {
            // Move piece
            Piece startPiece = pieceAt(move.start());
            Piece endPiece = pieceAt(move.end());
            clearSquare(move.start());
            addPiece(move.end(), startPiece);
            // Determine if king is in check
            boolean isInCheck = isInCheck(player);
            // Undo move
            addPiece(move.start(), startPiece);
            addPiece(move.end(), endPiece);
            if (!isInCheck) {
                return false;
            }
        }

        if (isInCheck(player)) {
            return false;
        }

        for (Location location : board.keySet()) {
            Piece piece = pieceAt(location);
            if (piece != null && piece.isFriendOf(king)) {
                if (piece.getPossibleMoves(this, location).size() > 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private Location getKingLocation(PieceColor player) {
        Piece king = new King(player);
        for (Location location : board.keySet()) {
            Piece piece = pieceAt(location);
            if (piece != null && piece.equals(king)) {
                return location;
            }
        }

        throw new IllegalStateException("Board should have player's king");
    }

    public Board deepCopy() {
        Board boardCopy = new Board();
        for (int rank = 1; rank <= BOARD_SIZE; rank++) {
            for (int file = 1; file <= BOARD_SIZE; file++) {
                Piece piece = pieceAt(new Location(rank, file));
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
        return board.get(location);
    }

    public Piece pieceAt(int rank, int file) {
        Location location = new Location(rank, file);
        if (!location.isWithinBoard(BOARD_SIZE)) {
            String message = String.format("Cannot find piece at rank %d and file %d.", rank, file);
            throw new IllegalArgumentException(message);
        }
        return board.get(location);
    }

    void addPiece(int rank, int file, Piece piece) {
        board.put(new Location(rank, file), piece);
    }

    void addPiece(Location location, Piece piece) {
        addPiece(location.rank(), location.file(), piece);
    }

    void clearSquare(int rank, int file) {
        board.remove(new Location(rank, file));
    }

    void clearSquare(Location location) {
        clearSquare(location.rank(), location.file());
    }

    public Map<Location, Piece> getPieceLocations() {
        return board;
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
