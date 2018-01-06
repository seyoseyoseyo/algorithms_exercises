import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  private final WeightedQuickUnionUF unionUF;
  private boolean[][] site;
  private final int length;
  private final int vt;
  private final int vb;
  private int count;

  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException();
    }
    length = n;
    vt = n * n;
    vb = n * n + 1;
    count = 0;
    unionUF = new WeightedQuickUnionUF(n * n + 2);
    for (int i = 1; i <= n; i++) {
      int temp1 = xyTo1D(1, i);
      int temp2 = xyTo1D(n, i);
      unionUF.union(vt, temp1);
      unionUF.union(vb, temp2);
    }
    site = new boolean[n][n];
  }

  public void open(int row, int col) {
    validate(row);
    validate(col);
    if (!site[row - 1][col - 1]) {
      site[row - 1][col - 1] = true;
      count ++;
      int index = xyTo1D(row, col);
      connect(row + 1, col, index);
      connect(row - 1, col, index);
      connect(row, col+1, index);
      connect(row, col-1, index);
    }
  }

  private void connect(int i, int j, int id) {
    if (i - 1 >= 0 && j - 1 >= 0 && i - 1 < length && j - 1 < length) {
      if (site[i-1][j-1]) {
        unionUF.union(xyTo1D(i, j), id);
      }
    }
  }

  public boolean isOpen(int row, int col) {
    validate(row);
    validate(col);
    return site[row-1][col-1];
  }

  public boolean isFull(int row, int col) {
    validate(row);
    validate(col);
    if (isOpen(row, col)) {
      return unionUF.connected(vt, xyTo1D(row, col));
    }
    return false;
  }

  public int numberOfOpenSites() {
    return count;
  }

  public boolean percolates() {
    if (numberOfOpenSites() <= 0) {
      return false;
    }
    return unionUF.connected(vt, vb);
  }

  private int xyTo1D(int x, int y) {
    return (x - 1) * length + y - 1;
  }

  private void validate(int p) {
    if (p < 1 || p > length) {
      throw new IllegalArgumentException("Wrong index at " + p);
    }
  }

}
