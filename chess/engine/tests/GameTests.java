package chess.engine.tests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import chess.engine.game.Game;

import static chess.engine.pieces.PieceColor.*;

public class GameTests {

    @Test
    public void testWhiteIsStartingPlayer() {
        Game game = new Game();
        assertEquals(game.getCurrentPlayer(), WHITE);
    }

    @Test
    public void testIllegalMoveDoesNotChangePlayerTurn() {
        Game game = new Game();
        game.makeMove(2, 4, 5, 4);
        assertEquals(game.getCurrentPlayer(), WHITE);
    }

    @Test
    public void testMovesAlternateThroughoutGame() {
        Game game = new Game();
        assertEquals(game.getCurrentPlayer(), WHITE);
        game.makeMove(2, 5, 4, 5);
        assertEquals(game.getCurrentPlayer(), BLACK);
        game.makeMove(7, 5, 5, 5);
        assertEquals(game.getCurrentPlayer(), WHITE);
        game.makeMove(1, 6, 4, 3);
        assertEquals(game.getCurrentPlayer(), BLACK);
        game.makeMove(8, 6, 5, 3);
        assertEquals(game.getCurrentPlayer(), WHITE);
        game.makeMove(2, 4, 3, 4);
        assertEquals(game.getCurrentPlayer(), BLACK);
        game.makeMove(8, 7, 6, 6);
        assertEquals(game.getCurrentPlayer(), WHITE);
    }

    // private void makeMove(Game game, String move) {
    //     if (move.length() == 2) {
    //         int rank = move.charAt(1) - '0';
    //         int file = move.charAt(0) - 'a' + 1;
    //         if (rank >= 1 && rank <= 8 && file >= 1 && file <= 8) {
    //             Location start = new Location(rank, file);
    //             Piece pieceToMove = board.pieceAt(start);
    //             if (pieceToMove != null) {
    //                 PieceColor enemyColor = game.getCurrentPlayer() == WHITE ? BLACK : WHITE;
    //                 Set<Location> defenders = board.getAttackers(start, enemyColor);
    //                 if (defenders.size() == 1) {
    //                     Location end = defenders.iterator().next();
    //                     board.movePiece(new Move(start, end));
    //                 }
    //             }
    //         }
    //     }
    // }

}
