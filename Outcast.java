import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {

    private WordNet wordnetRizz;

    public Outcast(WordNet wordnet) {
        if (wordnet == null) {
            throw new IllegalArgumentException();
        }
        wordnetRizz = wordnet; 

    } // constructor takes a WordNet object

    public String outcast(String[] nouns) {
        int mostUnrelated = Integer.MIN_VALUE;
        String outcast = "";
        for (String a : nouns) {

            int distance = 0;

            for (String b : nouns) {
                distance += wordnetRizz.distance(a, b);
            }

            //same logic as sap
            if (distance > mostUnrelated) {
                mostUnrelated = distance;
                outcast = a;
            }

        }

        return outcast;

    } // given an array of WordNet nouns, return an outcast

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}