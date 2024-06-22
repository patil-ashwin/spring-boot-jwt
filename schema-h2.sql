package com.example.fileservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class FileComparisonService {

    public void compareFiles(Path file1Path, Path file2Path) {
        try {
            // Read files into sets
            Set<String> file1Lines = new HashSet<>(Files.readAllLines(file1Path));
            Set<String> file2Lines = new HashSet<>(Files.readAllLines(file2Path));

            // Calculate differences
            Set<String> missingInFile1 = new HashSet<>(file2Lines);
            missingInFile1.removeAll(file1Lines);

            Set<String> missingInFile2 = new HashSet<>(file1Lines);
            missingInFile2.removeAll(file2Lines);

            // Log the differences
            if (!missingInFile1.isEmpty()) {
                log.info("Lines missing in file1 but present in file2: {}", missingInFile1);
            } else {
                log.info("No lines are missing in file1 compared to file2.");
            }

            if (!missingInFile2.isEmpty()) {
                log.info("Lines missing in file2 but present in file1: {}", missingInFile2);
            } else {
                log.info("No lines are missing in file2 compared to file1.");
            }

        } catch (IOException e) {
            log.error("An error occurred while reading the files", e);
        }
    }
}
