package utils;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 15/12/31, 2015.
 * Licence MIT
 */
public class NlpPipeline {

    private static StanfordCoreNLP pipeline = null;

    public static StanfordCoreNLP getPipeline(String pro) {
        Properties prop = new Properties();
        prop.setProperty("annotators", pro);
        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }

    public static StanfordCoreNLP getPipeline(String[] pros) {
        String properties = String.join(",", pros);
        System.out.println(properties);
        Properties prop = new Properties();
        prop.setProperty("annotators", properties);

        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }

    public static StanfordCoreNLP getBasicPipeline() {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos");
        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }

    public static StanfordCoreNLP getAllPipeline() {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, sentiment");
        if (pipeline == null) {
            synchronized (StanfordCoreNLP.class) {
                pipeline = new StanfordCoreNLP(prop);
            }
        }
        return pipeline;
    }
}
