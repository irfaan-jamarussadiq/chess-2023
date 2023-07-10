package board;

public record Move(Location startLocation, Location endLocation) {
    public Move(int startRank, int startFile, int endRank, int endFile) {
        this(new Location(startRank, startFile), new Location(endRank, endFile));
    }
}
