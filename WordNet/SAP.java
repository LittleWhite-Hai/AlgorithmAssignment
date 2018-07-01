

import edu.princeton.cs.algs4.Digraph;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    private Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {

        if (G == null)
            throw new IllegalArgumentException();

        digraph = new Digraph(G.V());

        for (int i = 0; i < G.V(); i++) {
            for (int w : G.adj(i)) {
                digraph.addEdge(i, w);
            }
        }

    }

    private int sca(boolean[] vmark, boolean[] wmark, Queue<Integer> vqueue, int[] vdis,
            int[] wdis, int sca) {

        int t = vqueue.dequeue();

        for (int i : digraph.adj(t)) {

            if (!vmark[i]) {

                vmark[i] = true;
                vdis[i] = vdis[t] + 1;
                vqueue.enqueue(i);

                if (wmark[i]) {
                    // System.out.println(i);
                    if (sca == -1)
                        sca = i;
                    else if (vdis[i] + wdis[i] < vdis[sca] + wdis[sca]) {
                        // System.out.println(i+","+vdis[i] + wdis[i]);
                        sca = i;

                    }
                }
            }
        }

        return sca;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int ancestor(int v, int w) {

        if (v < 0 || w < 0 || v >= digraph.V() || w >= digraph.V())
            throw new IllegalArgumentException();
        if(v==w)return v;
        Queue<Integer> vqueue = new Queue<>();
        Queue<Integer> wqueue = new Queue<>();
        boolean[] vmark = new boolean[digraph.V()];
        boolean[] wmark = new boolean[digraph.V()];
        int sca = -1;
        int[] vdis = new int[digraph.V()];
        int[] wdis = new int[digraph.V()];
        vqueue.enqueue(v);
        wqueue.enqueue(w);
        vmark[v] = true;
        wmark[w] = true;
        while (!vqueue.isEmpty() || !wqueue.isEmpty()) {
            if (!vqueue.isEmpty()) {
                sca = sca(vmark, wmark, vqueue, vdis, wdis, sca);
            }

            if (!wqueue.isEmpty()) {
                sca = sca(wmark, vmark, wqueue, wdis, vdis, sca);

            }
        }
        return sca;

    }

    // a common ancestor of v and w that participates in a shortest ancestral path;
    // -1 if no such path
    public int length(int v, int w) {

        if (v < 0 || w < 0 || v >= digraph.V() || w >= digraph.V())
            throw new IllegalArgumentException();
        if(v==w)return 0;
        Queue<Integer> vqueue = new Queue<>();
        Queue<Integer> wqueue = new Queue<>();
        boolean[] vmark = new boolean[digraph.V()];
        boolean[] wmark = new boolean[digraph.V()];
        int sca = -1;
        int[] vdis = new int[digraph.V()];
        int[] wdis = new int[digraph.V()];
        vqueue.enqueue(v);
        wqueue.enqueue(w);
        vmark[v] = true;
        wmark[w] = true;
        while (!vqueue.isEmpty() || !wqueue.isEmpty()) {
            if (!vqueue.isEmpty()) {
                sca = sca(vmark, wmark, vqueue,  vdis, wdis, sca);
            }

            if (!wqueue.isEmpty()) {
                sca = sca(wmark, vmark, wqueue,  wdis, vdis, sca);

            }
        }
        if (sca == -1)
            return -1;
        return vdis[sca] + wdis[sca];

    }

    // length of shortest ancestral path between any vertex in v and any vertex in
    // w; -1 if no such path

    private void enqueue(Iterable<Integer> iterable,Queue<Integer> queue,boolean[] mark) {
        for (Integer i : iterable) {
            if (i == null)
                throw new IllegalArgumentException();
            if ( i < 0 || i >= digraph.V() )
                throw new IllegalArgumentException();
            queue.enqueue(i);

            mark[i] = true;
        }
    }
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        if (v == null || w == null)
            throw new IllegalArgumentException();

        Queue<Integer> vqueue = new Queue<>();
        Queue<Integer> wqueue = new Queue<>();
        boolean[] vmark = new boolean[digraph.V()];
        boolean[] wmark = new boolean[digraph.V()];
        int[] vdis = new int[digraph.V()];
        int[] wdis = new int[digraph.V()];
        
        enqueue(v,vqueue,vmark);
        enqueue(w,wqueue,wmark);
        
        if (vqueue.isEmpty() || wqueue.isEmpty())
            throw new IllegalArgumentException();
        
        for(int i:wqueue) {
            for(int j:vqueue) {
                if(i==j)return 0;
            }
        }
        int sca = -1;

        while (!vqueue.isEmpty() || !wqueue.isEmpty()) {
            if (!vqueue.isEmpty()) {
                sca = sca(vmark, wmark, vqueue, vdis, wdis, sca);
            }

            if (!wqueue.isEmpty()) {
                sca = sca(wmark, vmark, wqueue, wdis, vdis, sca);
            }
        }
        if (sca == -1)
            return -1;
        return vdis[sca] + wdis[sca];

    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such
    // path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

        if (v == null || w == null)
            throw new IllegalArgumentException();

        Queue<Integer> vqueue = new Queue<>();
        Queue<Integer> wqueue = new Queue<>();
        boolean[] vmark = new boolean[digraph.V()];
        boolean[] wmark = new boolean[digraph.V()];
        int sca = -1;
        int[] vdis = new int[digraph.V()];
        int[] wdis = new int[digraph.V()];
        
        enqueue(v,vqueue,vmark);
        enqueue(w,wqueue,wmark);
        if (vqueue.isEmpty() || wqueue.isEmpty())
            throw new IllegalArgumentException();
        
        for(int i:wqueue) {
            for(int j:vqueue) {
                if(i==j)return i;
            }
        }
        while (!vqueue.isEmpty() || !wqueue.isEmpty()) {
            if (!vqueue.isEmpty()) {
                sca = sca(vmark, wmark, vqueue, vdis, wdis, sca);
            }

            if (!wqueue.isEmpty()) {
                sca = sca(wmark, vmark, wqueue, wdis, vdis, sca);

            }
        }
        return sca;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("B:\\Education\\MyCourseras\\Algorithm\\wordnet-testing\\wordnet\\digraph-wordnet.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

         Stack<Integer> v = new Stack<>();
         Stack<Integer> w = new Stack<>();
        //
         v.push(44279 );
        // v.push(75648);
        // // v.push(24);
        //
         w.push(30339);
        // //w.push(37650);
        // // w.push(17);

//        int v = 5;
//        int w = 5;

        int length = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);

    }
}