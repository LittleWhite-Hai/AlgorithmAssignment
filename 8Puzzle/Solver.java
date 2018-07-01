

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RedBlackBST;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private Stack<Board> boardStack=new Stack<>();
    private boolean solvable = true;
    private int move=0;
    private class Node implements Comparable<Node> {

        int move = 0;
        Board board = null;
        Node predecessor = null;
        int priority;

        public Node(Board present, Node predecessor, int move) {
            this.move = move;
            this.board = present;
            this.priority = move + board.manhattan();
            this.predecessor = predecessor;
        }

        @Override
        public int compareTo(Node arg0) {
            // TODO Auto-generated method stub

            if (arg0.priority > this.priority)
                return -1;
            else if (arg0.priority < this.priority)
                return 1;
            return 0;
        }

        public Iterable<Node> neighbors() {

            Queue<Node> nodeQueue = new Queue<>();
            for (Board t : board.neighbors()) {
                if (predecessor == null || !t.equals(predecessor.board)) {
                    nodeQueue.enqueue(new Node(t, this, move + 1));
                }
            }
            return nodeQueue;

        }
    }

    public Solver(Board initial) { // find a solution to the initial board (using the A* algorithm)
        
        if (initial == null)
            throw new IllegalArgumentException();
        MinPQ<Node> minPQ = new MinPQ<>();
        MinPQ<Node> twinMinPQ = new MinPQ<>();
        RedBlackBST<Integer,Node> rbt=new RedBlackBST<>();
        RedBlackBST<Integer,Node> twinrbt=new RedBlackBST<>();
        Node minNode = new Node(initial, null, 0);
        Node twinMinNode = new Node(initial.twin(), null, 0);
        Stack<Node> stack = new Stack<>();
        stack.push(minNode);

        while (!minNode.board.isGoal()) {

            if (twinMinNode.board.isGoal()) {
                move = -1;
                solvable = false;
                return;
            }

            for (Node t : twinMinNode.neighbors()) {
                twinMinPQ.insert(t);
            }
            for (Node t : minNode.neighbors()) {
                minPQ.insert(t);
            }
            twinMinNode = twinMinPQ.delMin();
            minNode = minPQ.delMin();

            stack.push(minNode);
        }
        move=minNode.move;
        Node present = stack.pop();
        boardStack.push(present.board);
        Node predecessor = null;

        while (!stack.isEmpty()) {
            do {
                predecessor = stack.pop();
            } while (present.predecessor != predecessor);

            present = predecessor;
            boardStack.push(present.board);
        }        
    }

    public boolean isSolvable() { // is the initial board solvable?
        return solvable;
    }

    public int moves() { // min number of moves to solve initial board; -1 if unsolvable
        return move;
    }

    public Iterable<Board> solution() { // sequence of boards in a shortest solution; null if unsolvable

        if (!solvable)
            return null;
        Queue<Board> boardQueue=new Queue<>();
        for(Board tBoard:boardStack) {
            boardQueue.enqueue(tBoard);
        }
        return boardQueue;
    }

     public static void main(String[] args) { // solve a slider puzzle (given
    
     // create initial board from file
     In in = new
     In("B:/Education/MyCourseras/Algorithm/8puzzle-testing/8puzzle/puzzle30.txt");
     int n = in.readInt();
     int[][] blocks = new int[n][n];
     for (int i = 0; i < n; i++)
     for (int j = 0; j < n; j++)
     blocks[i][j] = in.readInt();
     Board initial = new Board(blocks);
    
     // solve the puzzle
     Solver solver = new Solver(initial);
    
     // print solution to standard output
     if (!solver.isSolvable())
     StdOut.println("No solution possible");
     else {
     StdOut.println("Minimum number of moves = " + solver.moves());
     for (Board board : solver.solution())
     StdOut.println(board);
     }
     }
}