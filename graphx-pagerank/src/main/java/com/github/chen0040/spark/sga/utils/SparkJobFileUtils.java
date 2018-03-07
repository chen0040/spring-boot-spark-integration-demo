package com.github.chen0040.spark.sga.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by memeanalytics on 12/8/15.
 */
public class SparkJobFileUtils {
    private static final Logger logger = LoggerFactory.getLogger(SparkJobFileUtils.class);

    public static InputStream getResourceStream(String filename) throws  IOException {
        ClassLoader classLoader = SparkJobFileUtils.class.getClassLoader();
        URL dataFile = classLoader.getResource(filename);
        return dataFile.openStream();
    }

    public static InputStreamReader getResource(String filename) throws IOException {
        return new InputStreamReader(getResourceStream(filename));
    }

    public static void lines(String filename, Consumer<Stream<String>> callback) {
        if(callback == null) {
            logger.error("There is not callback for lines");
            return;
        }

        try(BufferedReader reader = new BufferedReader(getResource(filename))){
            callback.accept(reader.lines());
        }
        catch (IOException e) {
            logger.error("Failed to read the file " + filename, e);
        }
    }

    public static List<String> lines(String filename) {

        List<String> result = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(getResource(filename))){
            result.addAll(reader.lines().collect(Collectors.toList()));
        }
        catch (IOException e) {
            logger.error("Failed to read the file " + filename, e);
        }

        return result;
    }

    public static void forEachLine(String filename, Consumer<String> callback) {
        lines(filename, lines -> lines.forEach(callback));
    }


    public static String readToEnd(String filename) {
        StringBuilder sb = new StringBuilder();
        lines(filename, stringStream -> sb.append(stringStream.collect(Collectors.joining("\n"))));
        return sb.toString();
    }
}
