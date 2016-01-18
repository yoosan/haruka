package nlp;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import utils.NlpPipeline;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 15/12/31, 2015.
 * Licence MIT
 */
public class Tokenize {

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);

        String text = "Easy to use a mobile. If you're taller than 4ft, be ready to tuck your legs behind you as you hang and pull." +
                "Greece held its last Summer Olympics in which year?" +
                "How many more participants were there in 1900 than in the first year?";
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                String nervalue = token.get(CoreAnnotations.NormalizedNamedEntityTagAnnotation.class);
                System.out.println(word + "\t" + pos + "\t" + ne + "\t" + lemma + "\t" + nervalue);

            }
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree);
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println(dependencies);

        }
        Map<Integer, CorefChain> map = document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
        System.out.println(map);
    }


    public static String str_tokenize(String text) {
        StringBuilder buffer = new StringBuilder();
        StanfordCoreNLP pipeline = NlpPipeline.getPipeline("tokenize, ssplit");
        Annotation document = pipeline.process(text);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase().trim();
                if (word.length() == 1 && (word.charAt(0) > 'z') || word.charAt(0) < 'A') continue;
                buffer.append(word).append(" ");
            }
        }
        return buffer.toString();
    }

    public static List<String> list_tokenize(String text) {
        List<String> result = new LinkedList<String>();
        StanfordCoreNLP pipeline = NlpPipeline.getPipeline("tokenize, ssplit");
        Annotation document = pipeline.process(text);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase().trim();
                if (word.length() == 1 && (word.charAt(0) > 'z') || word.charAt(0) < 'A') continue;
                else result.add(word);
            }
        }
        return result;
    }

    public static String delta_tokenizer(String text) {
        StringBuilder buffer = new StringBuilder();
        StanfordCoreNLP pipeline = NlpPipeline.getPipeline("tokenize, ssplit");
        Annotation document = pipeline.process(text);
        List<CoreMap> maps = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : maps) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String word = token.get(CoreAnnotations.TextAnnotation.class).toLowerCase().trim();
                if (word.length() == 1 && (word.charAt(0) > 'z') || word.charAt(0) < 'A') continue;
                if (word.contains("-"))  {
                    String[] sp = word.split("-");
                    for (String s : sp) {
                        buffer.append(s).append(" ");
                    }
                }
                buffer.append(word).append(" ");
            }
        }
        return buffer.toString();
    }
}
