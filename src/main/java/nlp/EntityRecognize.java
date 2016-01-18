package nlp;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import utils.NlpPipeline;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 15/12/31, 2015.
 * Licence MIT
 */
public class EntityRecognize {

    final private static String[] props = {"tokenize", "ssplit", "pos", "lemma", "ner"};
    final private static StanfordCoreNLP pipeline = NlpPipeline.getAllPipeline();

    public static void main(String args[]) {
        List<String> ner = getNer("Stanford CoreNLP provides a set of natural language analysis tools. " +
                "It can give the base forms of words, their parts of speech, whether they are names of companies, people, etc..");
        System.out.println(ner);
    }

    public static List<String> getNer(String text) {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<String> ners = new LinkedList<String>();
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                if(!ne.equals("O")) ners.add(word + "|" + ne);
            }

        }
        return ners;
    }

    public static Map<String, String> getNamedEntity(String text) {
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        Map<String, String> ners = new HashMap<String, String>();
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                if (!ne.equals("O")) ners.put(word, ne);
            }

        }
        return ners;
    }
}
