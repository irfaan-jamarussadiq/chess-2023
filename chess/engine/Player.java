package chess.engine;

import chess.engine.pieces.PieceColor;

public class Player {
    public PieceColor color;    
    int kingRank;
    int kingFile;

    public Player(PieceColor color, int kingRank, int kingFile) {
        this.color = color;
        this.kingRank = kingRank;
        this.kingFile = kingFile;
    }
}
