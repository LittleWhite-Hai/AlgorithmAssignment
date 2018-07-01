
import edu.princeton.cs.algs4.Queue;

public class Board {

    private int hamming = 0;
    private int manhattan = 0;
    private int dimension = 0;
    private int[][] blocks = null;

    public Board(int[][] argument) {
        // construct a board from an n-by-n array of blocks,(where blocks[i][j] = block
        // in row i, column j)
        if (argument == null)
            throw new IllegalArgumentException();
        if (argument.length != argument[0].length)
            throw new IllegalArgumentException();

        dimension = argument.length;
        blocks = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                blocks[i][j] = argument[i][j];

                if (blocks[i][j] != 0 && blocks[i][j] != i * dimension + j + 1) {

                    hamming++;

                    int goalVolum = blocks[i][j] % dimension - 1;
                    int goalRow = blocks[i][j] / dimension;

                    if (goalVolum == -1) {
                        goalVolum = dimension - 1;
                        goalRow--;
                    }
                    manhattan += Math.abs(goalVolum - j) + Math.abs(goalRow - i);
                }
            }
        }

    }

    public int dimension() { // board dimension n
        return dimension;
    }

    public int hamming() { // number of blocks out of place
        return hamming;
    }

    public int manhattan() { // sum of Manhattan distances between blocks and goal
        return manhattan;
    }

    public boolean isGoal() { // is this board the goal board?

        return hamming == 0;
    }

    public Board twin() {
        // a board that is obtained by exchanging any pair of blocks
        if (dimension == 1) {
            int[][] tmp = new int[1][1];
            tmp[1][1] = 0;
            return new Board(tmp);
        }
        int[][] tmpBlock = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tmpBlock[i][j] = blocks[i][j];
            }
        }
        if (tmpBlock[0][0] != 0 && tmpBlock[0][1] != 0) {

            int t = tmpBlock[0][0];
            tmpBlock[0][0] = tmpBlock[0][1];
            tmpBlock[0][1] = t;
        } else {
            int t = tmpBlock[1][0];
            tmpBlock[1][0] = tmpBlock[1][1];
            tmpBlock[1][1] = t;
        }
        return new Board(tmpBlock);
    }

    public boolean equals(Object y) { // does this board equal y?

        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass() == this.getClass()) {
            if (((Board) y).dimension != dimension)
                return false;
            Board that = (Board) y;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (that.blocks[i][j] != this.blocks[i][j])
                        return false;
                }
            }
            return true;
        }
        return false;

    }

    public Iterable<Board> neighbors() { // all neighboring boards

        Queue<Board> queue = new Queue<>();

        int row = 0;
        int colum = 0;

        for (int r = 0; r < dimension; r++) {
            for (int c = 0; c < dimension; c++) {
                if (blocks[r][c] == 0) {
                    row = r;
                    colum = c;
                    r = dimension;
                    break;
                }
            }
        }

        int[][] up = new int[dimension][dimension];
        int[][] down = new int[dimension][dimension];
        int[][] left = new int[dimension][dimension];
        int[][] right = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                up[i][j] = blocks[i][j];
                down[i][j] = blocks[i][j];
                left[i][j] = blocks[i][j];
                right[i][j] = blocks[i][j];
            }
        }
        if (row != 0) { // can blank move up?

            up[row][colum] = up[row - 1][colum];
            up[row - 1][colum] = 0;
            Board upBoard = new Board(up);
            queue.enqueue(upBoard);

        }
        if (row != dimension - 1) {
            down[row][colum] = down[row + 1][colum];
            down[row + 1][colum] = 0;
            Board downBoard = new Board(down);
            queue.enqueue(downBoard);

        }
        if (colum != 0) {
            left[row][colum] = left[row][colum - 1];
            left[row][colum - 1] = 0;
            Board leftBoard = new Board(left);
            queue.enqueue(leftBoard);

        }
        if (colum != dimension - 1) {
            right[row][colum] = right[row][colum + 1];
            right[row][colum + 1] = 0;
            Board rightBoard = new Board(right);
            queue.enqueue(rightBoard);
        }

        return queue;

    }

    public String toString() {
        // string representation of this board (in the output format specified below)
        String s = dimension + "\r\n";
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i < dimension; i++) {
            sb.append(" ");
            for (int j = 0; j < dimension; j++) {
                if (blocks[i][j] / 10 == 0)
                    sb.append(blocks[i][j] + "  ");
                else
                    sb.append(blocks[i][j] + " ");
            }
            sb.append(" \r\n");
        }
        return sb.toString();
        // return s;
    }

    public static void main(String[] args) { // unit tests (not graded)
        // In in = new
        // In("B:/Education/MyCourseras/Algorithm/8puzzle-testing/8puzzle/puzzle3x3-02.txt");
        // //
        // int n = in.readInt();
        // int[][] blocks = new int[n][n];
        // for (int i = 0; i < n; i++)
        // for (int j = 0; j < n; j++)
        // blocks[i][j] = in.readInt();
        // Board initial = new Board(blocks);
    }

}