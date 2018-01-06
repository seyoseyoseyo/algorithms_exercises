import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private static final double UNIT = 1.96;
  private final double mean;
  private final double stddev;
  private final double confidenceLo;
  private final double confidenceHi;


  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException();
    }
    int size = n;
    double[] results = new double[trials];
    for (int i = 0; i < trials; i++) {
      double counter = 0;
      Percolation p = new Percolation(n);
      while (!p.percolates()) {
        int int1 = StdRandom.uniform(size) + 1;
        int int2 = StdRandom.uniform(size) + 1;
        if (!p.isOpen(int1, int2)) {
          p.open(int1, int2);
          counter++;
        }
      }
      results[i] = counter / (n * n);
    }
    this.mean = StdStats.mean(results);
    this.stddev = StdStats.stddev(results);
    this.confidenceLo = mean - (UNIT * stddev) / Math.sqrt(trials);
    this.confidenceHi = mean + (UNIT * stddev) / Math.sqrt(trials);
  }

  public double mean() {
    return mean;
  }

  public double stddev() {
    return stddev;
  }

  public double confidenceLo() {
    return confidenceLo;
  }
  public double confidenceHi() {
    return confidenceHi;
  }

  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);
    int t = Integer.parseInt(args[1]);
    PercolationStats stats = new PercolationStats(n, t);
    System.out.println("mean = " + stats.mean());
    System.out.println("stddev = " + stats.stddev());
    System.out.println("Confidence Interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
  }
}
