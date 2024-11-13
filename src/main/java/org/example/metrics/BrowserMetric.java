package org.example.metrics;

import org.example.LogLine;

public class BrowserMetric extends Metric {

    @Override
    public void processInfo(LogLine logLine) {

        String browser = logLine.getBrowser();

        if (browser != null && !browser.contains("Unknown")) {
            countsMap.put(browser, countsMap.getOrDefault(browser, 0) + 1);
        } else {
            countsMap.put("Unknown", countsMap.getOrDefault("Unknown", 0) +1);
        }

        totalEntries++;
    }

    @Override
    public void printResults(int numOfLines) {

        System.out.println("Browsers:");
        super.printResults(numOfLines);
    }
}
