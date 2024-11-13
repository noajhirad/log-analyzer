package org.example.metrics;

import org.example.LogLine;

public class OSMetric extends Metric {

    @Override
    public void processInfo(LogLine logLine) {

        String os = logLine.getOS();

        if (os != null) {
            countsMap.put(os, countsMap.getOrDefault(os, 0) + 1);
        } else {
            countsMap.put("Unknown", countsMap.getOrDefault("Unknown", 0) +1);
        }

        totalEntries++;
    }

    @Override
    public void printResults(int numOfLines) {

        System.out.println("Operating Systems:");
        super.printResults(numOfLines);
    }
}
