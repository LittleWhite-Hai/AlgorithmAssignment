import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    
   private WordNet wordnet;
   public Outcast(WordNet wordnet) {// constructor takes a WordNet object
       this.wordnet=wordnet;
   }
   public String outcast(String[] nouns) {   // given an array of WordNet nouns, return an outcast
       
       int outcast=0;
       int maxdistance=0;
       for(int i=0;i<nouns.length;i++) {
           int distance=0;
           for(int j=0;j<nouns.length;j++) {
               distance+=wordnet.distance(nouns[i], nouns[j]);
           }
           if(maxdistance<distance) {
               outcast=i;
               maxdistance=distance;
           }
       }
       return nouns[outcast];
   }
   public static void main(String[] args) {  // see test client below
      
       WordNet wordnet = new WordNet(args[0], args[1]);
       Outcast outcast = new Outcast(wordnet);
       for (int t = 2; t < args.length; t++) {
           In in = new In(args[t]);
           String[] nouns = in.readAllStrings();
           StdOut.println(args[t] + ": " + outcast.outcast(nouns));
       }
   }
}