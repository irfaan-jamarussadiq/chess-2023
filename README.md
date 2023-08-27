# Chess
Command line chess program written in Java.

## Design Decisions
### Piece class
Abstract class that Pawn, Knight, Bishop, Rook, Queen, and King classes derive from
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

### Board class
Class that allows pieces to move on the board and performs some checks on whether the moves suggested are valid according to the piece's movement properties.

#### Attributes
- `Map<Location, Piece> board`
  - Associates locations on the board (rank and file coordinates) with the pieces at those locations.
  - Using a map takes up less space than a traditional 2D array since we do not need to allocate memory for empty squares.
#### Methods
- `pieceAt(Location location)`
  - Returns the piece at the location specified.
- `movePiece(Location start, Location end)`
  - Performs a check on whether piece moving from start to end is a valid move, then moves the piece accordingly.
- `isValidMove(Move move)`
  - Determines whether a move is valid according to whether the move is within the bounds of the board and is moving according to how the piece moves or captures.
