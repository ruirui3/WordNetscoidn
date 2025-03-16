import java.util.HashSet;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private HashSet<String> numOfNoun;
    private Stack<Integer> numOfID;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        
        In in = new In(synsets);

        numOfNoun = new HashSet<String>();
        numOfID = new Stack<Integer>();

        while (in.hasNextLine()) {

            String[] synset = in.readLine().split(",");
            int id = Integer.parseInt(synset[0]);
            
            numOfID.push(id);
            
            String[] numOfNouns = synset[1].split(" ");

            for (int i = 0; i<numOfNouns.length; i++) {
                numOfNoun.add(numOfNouns[i]);
            }

        }

    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        
        return numOfNoun;

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {

        return numOfNoun.contains(word);

    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {

        

    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA
    // and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {

    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}