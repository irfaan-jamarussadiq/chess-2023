package chess.engine.board;

import chess.engine.pieces.Knight;

public record Move(Location start, Location end) {

    public Move(int startRank, int startFile, int endRank, int endFile) {
        this(new Location(startRank, startFile), new Location(endRank, endFile));
    }

    public boolean isWithinBoard(int boardSize) {
        return start.isWithinBoard(boardSize) && end.isWithinBoard(boardSize);
    }

    public boolean hasClearPath(Board board) {
        if (!isWithinBoard(Board.BOARD_SIZE)) {
            return false;
        }

        if (board.pieceAt(start) instanceof Knight) {
            return true;
        }

        int endRank = start.rank() + compareRankOrFiles(start.rank(), end.rank());
        int endFile = start.file() + compareRankOrFiles(start.file(), end.file());
        Move newMove = new Move(start.rank(), start.file(), endRank, endFile);
        while(newMove.isWithinBoard(Board.BOARD_SIZE) 
            && board.pieceAt(newMove.end()) == null && !newMove.equals(this)) {
            endRank = newMove.end().rank() + compareRankOrFiles(start.rank(), end.rank());
            endFile = newMove.end().file() + compareRankOrFiles(start.file(), end.file());
            newMove = new Move(start.rank(), start.file(), endRank, endFile);
        }

        return newMove.equals(this);
    }

    private int compareRankOrFiles(int startRankOrFile, int endRankOrFile) {
        if (endRankOrFile == startRankOrFile) {
            return 0;
        }

        return (endRankOrFile > startRankOrFile) ? 1 : -1;
    }
}