package org.example.metrics;

import org.example.LogLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Metric {

    protected Map<String, Integer> countsMap = new HashMap<>();
    protected int totalEntries = 0;

    public abstract void processInfo(LogLine logLine);

    public void printResults(int numOfLines) {

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(countsMap.entrySet());
        entries.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        for (int i = 0; i < entries.size(); i++) {
            double percentage = (entries.get(i).getValue() * 100.0) / numOfLines;
            System.out.printf("%s - %.2f%% %n", entries.get(i).getKey(), percentage);
        }
    }
}
