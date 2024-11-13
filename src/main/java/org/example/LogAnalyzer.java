package org.example;

import org.example.metrics.Metric;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LogAnalyzer {

    private final List<Metric> metrics;
    private int numOfLines;

    public LogAnalyzer(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public void analyzeFile(String stringPath) {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(stringPath));
            numOfLines = allLines.size();

            for (String line : allLines) {
                analyzeLogLine(line);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("File not found.");
        }
    }

    private void analyzeLogLine(String line) {

        LogLine logLine = new LogLine(line);

        for (Metric metric : this.metrics) {
            metric.processInfo(logLine);
        }
    }

    public void printResults() {

        for (Metric metric : this.metrics) {
            metric.printResults(numOfLines);
            System.out.println();
        }
    }
}
