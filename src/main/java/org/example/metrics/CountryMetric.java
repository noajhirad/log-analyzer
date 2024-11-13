package org.example.metrics;

import org.example.LogLine;

public class CountryMetric extends Metric {

    @Override
    public void processInfo(LogLine logLine) {

        String country = logLine.getCountry();

        if (country != null) {
            countsMap.put(country, countsMap.getOrDefault(country, 0) + 1);


        } else {
            countsMap.put("Unknown", countsMap.getOrDefault("Unknown", 0) +1);
        }

        totalEntries++;
    }

    @Override
    public void printResults(int numOfLines) {

        System.out.println("Countries:");
        super.printResults(numOfLines);
    }
}
