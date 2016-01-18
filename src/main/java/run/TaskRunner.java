package run;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import core.Const;
import nosql.RedisClient;
import instance.AmazonReview;
import instance.AnalyzeddReview;
import nlp.EntityRecognize;
import nlp.Sentiment;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import utils.SparkUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 16/1/1, 2016.
 * Licence MIT
 */
public class TaskRunner implements Serializable {

    private static Gson gson = new Gson();
    private static Type type = new TypeToken<AmazonReview>() {}.getType();

    public static boolean runTask(String taskName, String input) {
        JavaSparkContext jsc = SparkUtil.createLocalSparkContext(taskName, Const.CORE);
        JavaRDD<String> javaRDD = jsc.textFile(input);
        javaRDD.map(new ParseFunction()).foreach(new AnalyzeVoidFunction());
        return true;
    }

    public static class ParseFunction implements Function<String, AmazonReview> {
        @Override
        public AmazonReview call(String s) throws Exception {
            return gson.fromJson(s, type);
        }
    }

    public static class AnalyzeVoidFunction implements VoidFunction<AmazonReview> {
        String review, user, summary, reviewTime, helpful;
        double overall;
        AnalyzeddReview anreview = new AnalyzeddReview();

        @Override
        public void call(AmazonReview amazonReview) throws Exception {
            review = amazonReview.getReviewText();
            user = amazonReview.getReviewerName();
            helpful = Arrays.toString(amazonReview.getHelpful());
            summary = amazonReview.getSummary();
            reviewTime = amazonReview.getReviewTime();
            overall = amazonReview.getOverall();
            int sentiment = Sentiment.getSentiment(review);
            Map<String, String> entity = EntityRecognize.getNamedEntity(review);
            anreview.setEntity(entity);
            anreview.setHelpful(helpful);
            anreview.setOverall(overall);
            anreview.setReviewerName(user);
            anreview.setReviewTime(reviewTime);
            anreview.setSentiment(sentiment);
            anreview.setSummary(summary);
            anreview.setReviewText(review);
            String doc = gson.toJson(anreview);
            RedisClient.addDocument(Const.REDIS_KEY, doc);
        }
    }

}
