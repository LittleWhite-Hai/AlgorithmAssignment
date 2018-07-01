
public class Percolation {

    private int[] grid;
    private final int n;
    private int number = 0;

    public Percolation(int n) {
        // create n-by-n grid, with all sites blocked
        if (n <= 0)
            throw new IllegalArgumentException();
        // sz=new int[n*n+1];
        grid = new int[n * n + 1];
        this.n = n;
        for (int i = 1; i <= n * n; i++) {
            // sz[i]=1;
            grid[i] = i;
        }
    }

    public void open(int row, int col) {
        // open site (row, col) if it is not open already
        if (row > n || col > n || row < 1 || col < 1)
            throw new IllegalArgumentException();
        int i = (row - 1) * n + col;

        if (grid[i] != i)
            return;

        if (i <= n) {
            grid[i] = 0;
            number++;
            zeroAround(i);
        } else {
            grid[i] = -1;
            number++;
            zeroSelf(i);
        }
    }

    private void zeroSelf(int i) {

        if (i > n) { // check upper
            if (grid[i - n] == 0) {
                grid[i] = 0;
                zeroAround(i);
            }
        }

        if (i <= n * n - n) { // check lower
            if (grid[i + n] == 0) {
                grid[i] = 0;
                zeroAround(i);
            }
        }

        if ((i - 1) % n != 0) { // check left
            if (grid[i - 1] == 0) {
                grid[i] = 0;
                zeroAround(i);
            }
        }

        if (i % n != 0) { // check right
            if (grid[i + 1] == 0) {
                grid[i] = 0;
                zeroAround(i);
                }
        }
    }

    private void zeroAround(int i) {
        // check site around the i,if the sites already open ,then let them to full
        // site.

        if (i > n) { // check upper
            if (grid[i - n] == -1) {
                grid[i - n] = 0;
                zeroAround(i - n);
            }
        }

        if (i <= n * n - n) { // check lower
            if (grid[i + n] == -1) {
                grid[i + n] = 0;
                zeroAround(i + n);
            }
        }

        if ((i - 1) % n != 0) { // check left
            if (grid[i - 1] == -1) {
                grid[i - 1] = 0;
                zeroAround(i - 1);
            }
        }

        if (i % n != 0) { // check right
            if (grid[i + 1] == -1) {
                grid[i + 1] = 0;
                zeroAround(i + 1);
            }
        }
    }

    public boolean isOpen(int row, int col) {
        // is site (row, col) open?
        if (row > n || col > n || row < 1 || col < 1)
            throw new IllegalArgumentException();
        int i = n * (row - 1) + col;

        return grid[i] != i;
    }

    public boolean isFull(int row, int col) {
        // is site (row, col) full?

        if (row > n || col > n || row < 1 || col < 1)
            throw new IllegalArgumentException();
        int i = n * (row - 1) + col;
        return grid[i] == 0;
    }

    public int numberOfOpenSites() {
        // number of open sites
        return number;
    }

    public boolean percolates() {
        // does the system percolate?
        for (int i = n * n - n + 1; i <= n * n; i++)
            if (grid[i] == 0)
                return true;

        return false;
    }

    public static void main(String[] args) {
        // test client (optional)
        Percolation pc = new Percolation(6);
        pc.isFull(6, 6);
        System.out.println(pc.isOpen(6, 6));

    }
}
