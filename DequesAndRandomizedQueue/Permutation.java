import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {

        RandomizedQueue<String> queue = new RandomizedQueue<>();
        In in = new In();
        while (!in.isEmpty()) {
            queue.enqueue(in.readString());
            // StdOut.println(in.readString()+" "+in.isEmpty());
        }

        for (int i = Integer.parseInt(args[0]); i > 0; i--) {
            String s = queue.dequeue();
            StdOut.println(s);
        }

    }
}
