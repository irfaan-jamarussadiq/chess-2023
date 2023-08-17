package chess.engine.game;

import java.util.HashMap;
import java.util.Map;

import chess.engine.pieces.*;

public class Player {
    public PieceColor color;   
    Map<Location, Piece> pieces; 
    Location kingLocation;
    King king;

    public Player(PieceColor color, int kingRank, int kingFile) {
        this.color = color;
        this.king = new King(color);
        this.pieces = new HashMap<>();
        this.kingLocation = new Location(kingRank, kingFile);
    }

    public void setKingLocation(Location kingLocation) {
        this.kingLocation = kingLocation;
    }
}
