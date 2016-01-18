package utils;

import core.SparkConfig;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * Author: yoosan, SYSUDNLP Group
 * Date: 15/11/31, 2015.
 * Licence MIT
 */


public class SparkUtil implements AbstractUtil {


    public static JavaSparkContext createSparkContext(Class<?> jars, String appName) {
        return createSparkContext(jars, appName, SparkConfig.DEFAULT_CORE);
    }

    /**
     * Default method of creating JavaSparkContext
     */
    public static JavaSparkContext createSparkContext(Class<?> jars, String appName, int core) {
        String sparkUrl = SparkConfig.SPARK_URL;
        String s_core = Integer.toString(core);
        System.out.println("[INFO]SparkUrl is : " + sparkUrl);
        System.out.println("[INFO]Spark setting params as follows: ");
        System.out.println("[INFO]\t\t spark.core.max = " + s_core);
        System.out.println("[INFO]\t\t spark.executor.memory = " + SparkConfig.DEFAULT_MEMORY);
        System.out.println("[INFO]\t\t spark.default.parallelism = " + SparkConfig.DEFAULT_PARALLELISM);
        SparkConf sparkConf = new SparkConf().setMaster(sparkUrl).setAppName(appName)
                .setJars(JavaSparkContext.jarOfClass(jars))
                .set("spark.cores.max", s_core)
                .set("spark.executor.memory", SparkConfig.DEFAULT_MEMORY)
                .set("spark.default.parallelism", String.valueOf(SparkConfig.DEFAULT_PARALLELISM));
        for (String s : JavaSparkContext.jarOfClass(jars)) {
            System.out.println("[INFO]=== jar:" + s);
        }
        String sparkHome = System.getenv("SPARK_HOME");
        if (sparkHome != null) {
            System.out.println("[INFO]SparkHome: " + sparkHome);
            sparkConf.setSparkHome(sparkHome);
            System.out.println("[INFO]Set SparkHome Successfully");
        }
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        System.out.println("[INFO]Create JavaSparkContext Done!!!");
        return jsc;
    }

    public static JavaSparkContext createSparkContext(Class<?> jars, String appName, int core, int parallelism) {
        return createSparkContext(jars, appName, core, parallelism, SparkConfig.DEFAULT_MEMORY);
    }

    /**
     * For more params to construct JavaSparkContext
     *
     * @param appName     app name
     * @param core        core num
     * @param parallelism the parallelism of every machine
     * @param memory      The Java heap memory, eg 786m
     * @return JavaSparkContext
     */
    public static JavaSparkContext createSparkContext(Class<?> jars, String appName,
                                                      int core, int parallelism, String memory) {
        String sparkUrl = SparkConfig.SPARK_URL;
        String s_core = Integer.toString(core);
        String s_parallelism = Integer.toString(parallelism);
        System.out.println("[INFO] SparkUrl is : " + sparkUrl);
        System.out.println("[INFO] Spark setting params as follows: ");
        System.out.println("[INFO]\t\t spark.core.max = " + s_core);
        System.out.println("[INFO]\t\t spark.executor.memory = " + memory);
        System.out.println("[INFO]\t\t spark.default.parallelism = " + s_parallelism);
        SparkConf sparkConf = new SparkConf().setMaster(sparkUrl).setAppName(appName)
                .setJars(JavaSparkContext.jarOfClass(jars))
                .set("spark.cores.max", s_core)
                .set("spark.executor.memory", memory)
                .set("spark.default.parallelism", s_parallelism);
        for (String s : JavaSparkContext.jarOfClass(jars)) {
            System.out.println("[INFO] === jar:" + s);
        }
        String sparkHome = System.getenv("SPARK_HOME");
        if (sparkHome != null) {
            System.out.println("[INFO] SparkHome: " + sparkHome);
            sparkConf.setSparkHome(sparkHome);
            System.out.println("[INFO] Set SparkHome Successfully");
        }
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        System.out.println("[INFO] Create JavaSparkContext Done!!!");
        return jsc;
    }

    /**
     * Create local spark context
     */

    public static JavaSparkContext createLocalSparkContext(String appName, int core) {
        String sparkUrl = SparkConfig.SPARK_URL;
        String s_core = Integer.toString(core);
        System.out.println("[INFO] SparkUrl is : " + sparkUrl);
        System.out.println("[INFO] Spark setting params as follows: ");
        System.out.println("[INFO]\t\t spark.core.max = " + s_core);
        System.out.println("[INFO]\t\t spark.executor.memory = 4G");
        SparkConf sparkConf = new SparkConf().setMaster(sparkUrl).setAppName(appName)
                .set("spark.cores.max", s_core)
                .set("spark.executor.memory", "4G");
        String sparkHome = System.getenv("SPARK_HOME");
        if (sparkHome != null) {
            System.out.println("[INFO] SparkHome: " + sparkHome);
            sparkConf.setSparkHome(sparkHome);
            System.out.println("[INFO] Create JavaStreamingContext Done!!!");
        }
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);
        return jsc;
    }

    /**
     * Create SparkStreamingContext
     */
    public static JavaStreamingContext createSparkStreamingContext(Class<?> jars, String appName, int core,
                                                                   int parallelism, int duration) {
        String sparkUrl = SparkConfig.SPARK_URL;
        String s_core = Integer.toString(core);
        String s_parallelism = Integer.toString(parallelism);
        System.out.println("[INFO] SparkUrl is : " + sparkUrl);
        System.out.println("[INFO] Spark setting params as follows: ");
        System.out.println("[INFO] \t\t spark.core.max = " + s_core);
        System.out.println("[INFO] \t\t spark.executor.memory = 4G");
        System.out.println("[INFO] \t\t spark.default.parallelism = " + s_parallelism);
        SparkConf sparkConf = new SparkConf().setMaster(sparkUrl).setAppName(appName)
                .setJars(JavaSparkContext.jarOfClass(jars))
                .set("spark.cores.max", s_core)
                .set("spark.executor.memory", "4g")
                .set("spark.default.parallelism", s_parallelism);
        for (String s : JavaSparkContext.jarOfClass(jars)) {
            System.out.println("==jar:" + s);
        }
        String sparkHome = System.getenv("SPARK_HOME");
        if (sparkHome != null) {
            System.out.println("[INFO] SparkHome: " + sparkHome);
            sparkConf.setSparkHome(sparkHome);
            System.out.println("[INFO] Create JavaStreamingContext Done!!!");
        }
        return new JavaStreamingContext(sparkConf, Durations.seconds(duration));
    }


}

