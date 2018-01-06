import java.util.ArrayList;
import java.util.List;

public class Board {
  private final int[][] board;
  private final int dimension;
  public Board(int[][] blocks) {
    if (blocks == null) {
      throw new IllegalArgumentException();
    }
    this.dimension = blocks.length;
    board = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        board[i][j] = blocks[i][j];
      }
    }
  }
  public int dimension() {
    return dimension;
  }

  public int hamming() {
    int count = 0;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        if (!(i == dimension - 1 && j == dimension - 1)) {
          if (board[i][j] != i * dimension + j + 1) {
            count++;
          }
        }
      }
    }
    return count;
  }

  public int manhattan() {
    int count = 0;
    for (int i = 0; i < dimension(); i++) {
      for (int j = 0; j < dimension(); j++) {
        int value = board[i][j];
        if (value != 0) {
          int distance = getDistance(value, i, j);
          count = count + distance;
        }
      }
    }
    return count;
  }

  private int getDistance(int number, int i, int j) {
    number = number - 1;
    int i1 = number / dimension;
    int j1 = number % dimension;
    if (i1 == dimension - 1 && j1 == dimension - 1) {
      i1 = 0;
      j1 = 0;
    }
    return Math.abs(i1 - i) + Math.abs(j1 - j);
  }

  public boolean isGoal() {
    return hamming() == 0;
  }

  public Board twin() {
    int[][] newBoard = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        newBoard[i][j] = board[i][j];
      }
    }
    if (newBoard[0][0] == 0 || newBoard[1][1] == 0) {
      int temp = newBoard[0][1];
      newBoard[0][1] = newBoard[1][0];
      newBoard[1][0] = temp;
    } else {
      int temp = newBoard[0][0];
      newBoard[0][0] = newBoard[1][1];
      newBoard[1][1] = temp;
    }
    return new Board(newBoard);
  }

  public boolean equals(Object y) {
    if (y == null) {
      return false;
    }
    if (y.getClass() == this.getClass()) {
      Board other = (Board) y;
      if (this.dimension() != other.dimension()) {
        return false;
      }
      for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
          if (board[i][j] != other.board[i][j]) {
            return false;
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  public Iterable<Board> neighbors() {
    List<Board> boards = new ArrayList<>();
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (board[i][j] == 0) {
          if (i == 0 && j == 0) {
            boards.add(swap(i, j, i, j+1));
            boards.add(swap(i, j, i+1, j));
          } else if (i == 0 && j == dimension - 1) {
            boards.add(swap(i, j, i, j-1));
            boards.add(swap(i, j, i+1, j));
          } else if (i == dimension - 1 && j == 0) {
            boards.add(swap(i, j, i-1, j));
            boards.add(swap(i, j, i, j+1));
          } else if (i == dimension - 1 && j == dimension - 1) {
            boards.add(swap(i, j, i-1, j));
            boards.add(swap(i, j, j-1, i));
          } else if (i == 0) {
            boards.add(swap(i, j, i, j+1));
            boards.add(swap(i, j, i, j-1));
            boards.add(swap(i, j, i+1, j));
          } else if (i == dimension - 1) {
            boards.add(swap(i, j, i, j+1));
            boards.add(swap(i, j, i, j-1));
            boards.add(swap(i, j, i-1, j));
          } else if (j == 0) {
            boards.add(swap(i, j, i+1, j));
            boards.add(swap(i, j, i-1, j));
            boards.add(swap(i, j, i, j+1));
          } else if (j == dimension - 1) {
            boards.add(swap(i, j, i+1, j));
            boards.add(swap(i, j, i-1, j));
            boards.add(swap(i, j, i, j-1));
          } else {
            boards.add(swap(i, j, i+1, j));
            boards.add(swap(i, j, i-1, j));
            boards.add(swap(i, j, i, j+1));
            boards.add(swap(i, j, i, j-1));
          }
        }
      }
    }
    return boards;
  }

  private Board swap(int i1, int j1, int i2, int j2) {
    int[][] newBoard = new int[dimension][dimension];
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        newBoard[i][j] = board[i][j];
      }
    }
    int temp = newBoard[i1][j1];
    newBoard[i1][j1] = newBoard[i2][j2];
    newBoard[i2][j2] = temp;
    return new Board(newBoard);
  }


  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(dimension + "\n");
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        s.append(String.format("%2d ", board[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  public static void main(String[] args) {
    int[][] array = {{1, 0}, {2, 3}};
    Board board = new Board(array);
    System.out.println(board.toString());
    for (Board temp:board.neighbors()) {
      System.out.println(temp);
    }
  }
}
