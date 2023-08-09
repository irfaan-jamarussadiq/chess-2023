package chess.engine;

public interface MoveValidator {
    public boolean isValidPieceMove(int startRank, int startFile, int endRank, int endFile);
    public boolean isMoveCapturingFriendlyPiece(int startRank, int startFile, int endRank, int endFile);
}
