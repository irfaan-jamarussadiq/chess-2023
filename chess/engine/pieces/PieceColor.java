package chess.engine.pieces;

public enum PieceColor {
    WHITE {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public int getPawnStartingRank() {
            return 2;
        }
    }, 
    BLACK {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public int getPawnStartingRank() {
            return 7;
        }
    };

    public abstract int getDirection();
    public abstract int getPawnStartingRank();
}
