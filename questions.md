## Questions

+ Coverage corner cases:

    @Override
    public String getInput() {
      try {
        return inputQ.remove();
      } catch (NoSuchElementException e) {
        System.exit(2);
        return ""; // <-- This line will never be called!
      }
    }

    private Board nextState(int row, int column) {
      BoardCoordinate moveCoordinate = new BoardCoordinate(row, column);
      Board newBoard;
        try {
          newBoard = fillSquare(moveCoordinate, nextTurn());
        } catch (InvalidMoveException e) { // <-- This will never throw an InvalidMoveException!
          newBoard = new GameBoard(rows);
        }
        return newBoard;
    }

+ Exception handling/System.exit()/bubbling
+ Tradeoffs: public methods are easy to test, private methods are cleaner
+ Decorator pattern in Java?