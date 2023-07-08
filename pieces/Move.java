package pieces;

public class Move {
    int startRank, startFile, endRank, endFile;
    public Move(int startRank, int startFile, int endRank, int endFile) {
        this.startRank = startRank;
        this.startFile = startFile;
        this.endRank = endRank;
        this.endFile = endFile;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Move move = (Move) other;
        return startRank == move.startRank && startFile == move.startFile 
            && endRank == move.endRank && endFile == move.endFile;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(startRank, startFile, endRank, endFile);
    }
}
