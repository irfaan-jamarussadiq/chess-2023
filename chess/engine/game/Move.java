package chess.engine.game;

public record Move(Location start, Location end) {
    public Move(int startRank, int startFile, int endRank, int endFile) {
        this(new Location(startRank, startFile), new Location(endRank, endFile));
    }

    public boolean isWithinBoard(int boardSize) {
        return start.isWithinBoard(boardSize) && end.isWithinBoard(boardSize);
    }
}
