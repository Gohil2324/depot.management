package com.depot.management;

import java.io.FileWriter;
import java.io.IOException;

  public class Log {
    private static Log instance = null;
    private StringBuilder logData = new StringBuilder();

    private Log() {
    }

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }
        return instance;
    }

    public void logEvent(String event) {
        logData.append(event).append("\n");
    }

    public void writeToFile(String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write("--- Log of Events ---\n");
            writer.write(logData.toString());
        }
    }

    @Override
    public String toString() {
        return logData.toString();
    }
}
