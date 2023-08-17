package chess.engine.game;

public record Location(int rank, int file) {
    public boolean isWithinBoard(int boardSize) {
        return rank >= 1 && rank <= boardSize && file >= 1 && file <= boardSize;
    }
}
