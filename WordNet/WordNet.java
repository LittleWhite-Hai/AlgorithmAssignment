
import java.util.ArrayList;
import java.util.HashMap;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private final HashMap<String, ArrayList<Integer>> st = new HashMap<>();
    private final HashMap<Integer, String> ts = new HashMap<>();
    //private Digraph digraph;
    private SAP sap;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        In in = new In(synsets);
        int id = 0;
        while (in.hasNextLine()) {
            String[] string = in.readLine().split(",");
            id = Integer.parseInt(string[0]);
            String[] nouns = string[1].split(" ");
            for (int i = 0; i < nouns.length; i++) {
                if(!st.containsKey(nouns[i]))st.put(nouns[i], new ArrayList<Integer>());
                ArrayList<Integer> arraylist=st.get(nouns[i]);
                arraylist.add(id);
            }
            ts.put(id, string[1]);
        }

        Digraph digraph = new Digraph(id + 1);
        in = new In(hypernyms);
        while (in.hasNextLine()) {
            String[] string = in.readLine().split(",");
            for (int i = 1; i < string.length; i++) {
                digraph.addEdge(Integer.parseInt(string[0]), Integer.parseInt(string[i]));
            }

        }

        // detect if is a rooted DAG
        boolean[] marked = new boolean[digraph.V()];
        boolean[] onstack = new boolean[digraph.V()];
        int root[]= {-1};
        for (int i = 0; i < digraph.V(); i++) {
            if(!marked[i])dfs(marked,onstack,i,root, digraph);
        }
        sap=new SAP(digraph);
    }

    private void dfs(boolean[] marked, boolean[] onstack, int v,int[] root,Digraph digraph) {
        
        if(onstack[v])throw new IllegalArgumentException("is not a DAG");
        if (!marked[v]) {
            onstack[v]=true;
            
            marked[v]=true;
            //no ajeancent means root
            if(!digraph.adj(v).iterator().hasNext()) {
                if(root[0]==-1)root[0]=v;
                if(v!=root[0])throw new IllegalArgumentException("contains more than one roots");
            }
            for (int i : digraph.adj(v)) {
                dfs(marked, onstack, i,root,digraph);
            }
            onstack[v]=false;

        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return st.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        return st.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
      
        
        ArrayList<Integer> a= st.get(nounA);
        ArrayList<Integer> b= st.get(nounB);
        
        return sap.length(a, b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        

        ArrayList<Integer> a= st.get(nounA);
        ArrayList<Integer> b= st.get(nounB);
        
        int id = sap.ancestor(a, b);
        return ts.get(id);
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordnet = new WordNet
                ("B:\\Education\\MyCourseras\\Algorithm\\wordnet-testing\\wordnet\\synsets.txt",
                "B:\\Education\\MyCourseras\\Algorithm\\wordnet-testing\\wordnet\\hypernyms.txt");
        System.out.println(wordnet.distance("lifetime", "soccer"));
        System.out.println(wordnet.sap("lifetime", "soccer"));

    }
}