package chess.engine;

import java.util.Scanner;

import chess.engine.game.Game;
import chess.engine.game.GameStatus;
import chess.engine.game.Move;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        GameStatus status;
        do {
            status = game.getStatus();
            System.out.println(game);
            System.out.println("Enter a move (e.g. e2 e4): ");
            String moveInput = scanner.nextLine();
            int[] locations = parseMove(moveInput);
            if (locations != null) {
                Move move = new Move(locations[0], locations[1], locations[2], locations[3]);
                game.makeMove(move);
            }
        } while(status == GameStatus.ONGOING);
        scanner.close();
    }

    private static int[] parseMove(String move) {
        String[] squares = move.split(" ");
        if (squares.length != 2) return null;
        if (squares[0].length() != 2 || squares[1].length() != 2) return null;
        char[] start = squares[0].toCharArray();
        char[] end = squares[1].toCharArray();
        return new int[] { start[1] - '0', start[0] - 'a' + 1, end[1] - '0', end[0] - 'a' + 1};
    }
}
