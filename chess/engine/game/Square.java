package chess.engine.game;

import chess.engine.pieces.Piece;

public class Square {
    private Piece piece;

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
}
