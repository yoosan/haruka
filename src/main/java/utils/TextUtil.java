package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import instance.AmazonReview;
import instance.AnalyzeddReview;
import nlp.EntityRecognize;
import nlp.Sentiment;
import nlp.Tokenize;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 16/1/1, 2016.
 * Licence MIT
 */
public class TextUtil {

    public static boolean amazonReviewTokenizer(String input, String output) {
        String line;
        AmazonReview entity;
        String review;
        String user;
        String helpful;
        String summary;
        double overall;
        String reviewTime;

        Gson gson = new Gson();
        // reviewname|helpul|reviewtext|summary|overall|reviewtime
        String format = "%s|%s|%s|%s|%f|%s\n";
        Type type = new TypeToken<AmazonReview>() {
        }.getType();
        LineIterator iterator = null;
        long i = 0;
        long startTime = System.currentTimeMillis();
        try {
            iterator = FileUtils.lineIterator(new File(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnalyzeddReview anreview = new AnalyzeddReview();
        try {
            assert iterator != null;
            File outFile = new File(output);
            FileWriter writer = new FileWriter(outFile);
            while (iterator.hasNext()) {
                line = iterator.nextLine();
                entity = gson.fromJson(line, type);
                user = entity.getReviewerName();
                helpful = Arrays.toString(entity.getHelpful());
                summary = entity.getSummary();
                overall = entity.getOverall();
                reviewTime = entity.getReviewTime();
                review = entity.getReviewText().trim();
                int sentiment = Sentiment.getSentiment(review);
                Map<String, String> ner = EntityRecognize.getNamedEntity(review);
                if (ner != null) anreview.setEntity(ner);
                anreview.setHelpful(helpful);
                anreview.setOverall(overall);
                anreview.setReviewerName(user);
                anreview.setReviewTime(reviewTime);
                anreview.setSentiment(sentiment);
                anreview.setSummary(summary);
                anreview.setReviewText(review);
                String doc = gson.toJson(anreview);
                System.out.println(doc);
                writer.write(String.format(format, user, helpful, review, summary, overall, reviewTime));
                i++;
                if (i != 0 && i % 1000 == 0) {
                    System.out.println("[INFO] Read " + i + " lines");
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        long mileSeconds = (System.currentTimeMillis() - startTime);
        if (!(1000 <= mileSeconds)) {
            System.out.println("[INFO] Cost time : " + mileSeconds + " ms");
        } else if (mileSeconds < 60000) {
            System.out.println("[INFO] Cost time : " + mileSeconds / 1000.0 + " Seconds");
        } else {
            System.out.println("[INFO] Cost time : " + mileSeconds / (60 * 1000.0) + " Seconds");
        }
        return true;
    }

    public static boolean textTokenizer(String input, String output) {
        String line;
        String review;
        LineIterator iterator = null;
        long i = 0;
        long startTime = System.currentTimeMillis();
        try {
            iterator = FileUtils.lineIterator(new File(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert iterator != null;
            File outFile = new File(output);
            FileWriter writer = new FileWriter(outFile);
            while (iterator.hasNext()) {
                line = iterator.nextLine();
                review = Tokenize.str_tokenize(line.toLowerCase());
                writer.write(review + "\n");
                i++;
                if (i != 0 && i % 1000 == 0) {
                    System.out.println("[INFO] Read " + i + " lines");
                }
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        long mileSeconds = (System.currentTimeMillis() - startTime);
        if (1000 < mileSeconds) {
            System.out.println("[INFO] Cost time : " + mileSeconds + " ms");
        } else if (mileSeconds < 60000) {
            System.out.println("[INFO] Cost time : " + mileSeconds / 1000.0 + " Seconds");
        } else {
            System.out.println("[INFO] Cost time : " + mileSeconds / (60 * 1000.0) + " Seconds");
        }
        return true;
    }

    public static void main(String[] args) {
        String inpath = "/Volumes/sj/aggressive_dedup.json";
        amazonReviewTokenizer(inpath, "result.txt");
    }

}
