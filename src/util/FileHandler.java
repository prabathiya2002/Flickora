package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public List<String> readLines(String filePath) {
        ensureFileExists(filePath);
        List<String> lines = new ArrayList<String>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to read file: " + filePath, exception);
        }
        return lines;
    }

    public void writeLines(String filePath, List<String> lines) {
        ensureFileExists(filePath);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                printWriter.println(line);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to write file: " + filePath, exception);
        }
    }

    public void appendLine(String filePath, String line) {
        ensureFileExists(filePath);
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(filePath, true))) {
            printWriter.println(line);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to append file: " + filePath, exception);
        }
    }

    public boolean updateLine(String filePath, String recordId, String updatedLine) {
        List<String> lines = readLines(filePath);
        boolean updated = false;
        for (int index = 0; index < lines.size(); index++) {
            if (extractId(lines.get(index)).equalsIgnoreCase(recordId)) {
                lines.set(index, updatedLine);
                updated = true;
                break;
            }
        }
        if (updated) {
            writeLines(filePath, lines);
        }
        return updated;
    }

    public boolean deleteLine(String filePath, String recordId) {
        List<String> lines = readLines(filePath);
        boolean deleted = false;
        List<String> remaining = new ArrayList<String>();
        for (String line : lines) {
            if (extractId(line).equalsIgnoreCase(recordId)) {
                deleted = true;
                continue;
            }
            remaining.add(line);
        }
        if (deleted) {
            writeLines(filePath, remaining);
        }
        return deleted;
    }

    public String extractId(String line) {
        if (line == null || line.trim().isEmpty()) {
            return "";
        }
        String[] tokens = line.split("\\|", -1);
        return tokens.length > 0 ? tokens[0].trim() : "";
    }

    private void ensureFileExists(String filePath) {
        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException exception) {
            throw new RuntimeException("Unable to create file: " + filePath, exception);
        }
    }
}