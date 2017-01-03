package org.pitrecki.benchmark;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This small piece of code deliver a very usefull functionality to your code.
 * You will able to measure the perfomrance of your code.
 *
 * Main features:
 * - Write the result to a file
 * - Calculation of minimum, maximum and  average execution time of a section
 * of code
 *
 * @author Piotr 'pitrecki' Nowak
 * @version 1.0.0
 * Created by Pitrecki on 2017-01-03.
 */
public class Benchmark
{
    public enum Options {YES, NO}

    private static final Path PATH = Paths.get(System.getProperty("user.dir")+ "/dumpResult");

    /**
     * Displaying the run time of a given piece of code.
     *
     * @param runnable thread executor
     * @param options YES if you want save result to file,
     *                NO if you want ony print out the result
     */

    public static void measureTime(Runnable runnable, Options options) {
        long start = System.nanoTime();
        try {
            runnable.run();
        } finally {
            long end = System.nanoTime();
            double result = (end - start)/1e9;
            System.out.println("It's take(s) " + result);
            if (options.equals(Options.YES))
                dumpResultToFile(result);
        }
    }

    /**
     * The task of this method is write calculated to the file and  not overwrite it.
     *
     * @param result calculated value which will be added to the output file
     */

    private static void dumpResultToFile(double result) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH.toString(), true))){
            writer.write(String.valueOf(result));
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * This method calculates the performance statistics of selected block of your
     * code
     *
     * @param options YES if you want delete file after calculation the statistics,
     *                NO if you don't
     */

    public static void calculateStatistics(Options options) {
        List<Double> doubles = new ArrayList<>();

        try (Stream<String> stream = Files.lines(PATH)){
            doubles = stream
                    .map(Double::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        Collections.sort(doubles);
        OptionalDouble average = doubles.stream()
                .mapToDouble(value -> value)
                .average();

        System.out.println("Min: " + doubles.get(0) +"\nMax: " + doubles.get(doubles.size() - 1)
            +"\nAverage: " + average.getAsDouble());

        if (options.equals(Options.YES))
            new File(PATH.toUri()).delete();

    }
}
