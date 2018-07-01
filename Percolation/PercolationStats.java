import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONSTANT = 1.96;
    private final double[] x;
    private final double avgX;
    private final double s;
    // standard deviation
    public PercolationStats(int n, int trials) {
        // perform trials independent experiments on an n-by-n grid
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();
        x = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation pc = new Percolation(n);
            int row;
            int cul;
            while (!pc.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                cul = StdRandom.uniform(1, n + 1);
                pc.open(row, cul);
            }
            x[i] = (double) pc.numberOfOpenSites() / (n * n);

        }

        avgX = StdStats.mean(x); // calculate the average of x

        s = StdStats.stddev(x);

    }

    public double mean() {
        // sample mean of percolation threshold
        return avgX;
    }

    public double stddev() {
        // sample standard deviation of percolation threshold

        return s;
    }

    public double confidenceLo() {
        // low endpoint of 95% confidence interval
        return avgX - CONSTANT * s / Math.sqrt(x.length);
    }

    public double confidenceHi() {
        // high endpoint of 95% confidence interval
        return avgX + CONSTANT * s / Math.sqrt(x.length);
    }

    public static void main(String[] args) {
        // test client (described below)

        PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(ps.mean());

    }
}