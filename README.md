# Chess
Command line chess program written in Java.

## Design Decisions
### Piece class
Abstract class that Pawn, Knight, Bishop, Rook, Queen, are King classes derive from
#### Attributes
- `PieceColor color`
  - Enum, can be either `WHITE` or `BLACK`
#### Methods
- `canCaptureInDirection(Location start, Location end)`
  - Can use this method as a heuristic to check whether the piece can move from start to end based on how the piece moves.
- `getPossibleMoves(Location location, int boardSize)`
  - Generates a list of possible moves at the given location based on how the piece moves. This will not check if the moves are legal on the board.
- `isEnemyOf(Piece piece)`
  - Checks whether the current piece is not the same color as another piece. Useful when checking if one piece can capture another.
- `isFriendOf(Piece piece)`
  - Checks whether the current piece is the same color as another piece. Useful when checking if there is a friendly piece in the path of a move.
