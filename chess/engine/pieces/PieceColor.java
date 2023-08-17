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

        @Override
        public int getPieceStartingRank() {
            return 1;
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

        @Override
        public int getPieceStartingRank() {
            return 8;
        }
    };

    public abstract int getDirection();
    public abstract int getPawnStartingRank();
    public abstract int getPieceStartingRank();
}
