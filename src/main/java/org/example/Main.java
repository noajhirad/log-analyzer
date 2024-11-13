package org.example;

import org.example.metrics.BrowserMetric;
import org.example.metrics.CountryMetric;
import org.example.metrics.Metric;
import org.example.metrics.OSMetric;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Metric> lst = Arrays.asList(new CountryMetric(), new OSMetric(), new BrowserMetric());
        LogAnalyzer logAnalyzer = new LogAnalyzer(lst);

        System.out.println("Analyzing logs and gathering insights... this may take a moment.");
        System.out.println();

        logAnalyzer.analyzeFile("all.log");
        logAnalyzer.printResults();
    }
}