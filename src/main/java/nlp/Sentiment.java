package nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import utils.NlpPipeline;

import java.util.List;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 16/1/1, 2016.
 * Licence MIT
 */
public class Sentiment {

    final private static String[] props = {"tokenize", "ssplit", "parse", "sentiment"};
    final private static StanfordCoreNLP pipeline = NlpPipeline.getAllPipeline();

    public static int getSentiment(String text) {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        int score = 0;
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps){
            Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
            int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
            score += sentiment;
        }
        return score;
    }
}
