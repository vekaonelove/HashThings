package com.kseniya.hash.service;

import com.kseniya.hash.model.FileRecord;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import static java.lang.Thread.*;
import static java.nio.file.Files.*;

@Service
@Slf4j
public class FileMonitorService {
    private final Path directoryToWatch;
    private final DatabaseService databaseService;
    private final PathMatcher pathMatcher;
    private boolean running = true;

    @Autowired
    public FileMonitorService(@Value("${file.monitor.directory:C:/Users/ksenveka/IdeaProjects/HashTable/src/main/java}") String directoryPath, DatabaseService databaseService, PathMatcher pathMatcher) {
        this.directoryToWatch = Paths.get(directoryPath);
        this.databaseService = databaseService;
        this.pathMatcher = pathMatcher;
    }

    public void startWatching() {
        new Thread(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                directoryToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
                log.info("Monitoring directory: {}", directoryToWatch);

                while (running) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            Path createdFilePath = directoryToWatch.resolve((Path) event.context());
                            processNewFile(createdFilePath);
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                log.error("Error while monitoring the directory", e);
            }
        }).start();
    }

    @SuppressWarnings("BusyWait")
    private void processNewFile(Path filePath) {
        try {
            while (notExists(filePath) || !isReadable(filePath)) {
                sleep(100);
            }

            LocalDateTime startTime = LocalDateTime.now();
            Map<String, Integer> hashTable = new HashMap<>();

            String content = new String(readAllBytes(filePath));
            String fileExtension = getFileExtension(filePath.toString());

            if ("txt".equalsIgnoreCase(fileExtension)) {
                StringTokenizer tokenizer = new StringTokenizer(content);
                while (tokenizer.hasMoreTokens()) {
                    String word = tokenizer.nextToken().toLowerCase();
                    hashTable.put(word, hashTable.getOrDefault(word, 0) + 1);
                }
            } else if ("json".equalsIgnoreCase(fileExtension)) {
                StringTokenizer tokenizer = new StringTokenizer(content, "{}:,\" \n");
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken().trim();
                    if (!token.isEmpty()) {
                        hashTable.put(token, hashTable.getOrDefault(token, 0) + 1);
                    }
                }
            } else {
                log.warn("Unsupported file type: {}", fileExtension);
            }

            Duration processingTime = Duration.between(startTime, LocalDateTime.now());
            log.info("Processed file: {} in {} ms", filePath, processingTime.toMillis());
            log.info("HashTable content: {}", hashTable);

            FileRecord fileRecord = new FileRecord(filePath.getFileName().toString(), filePath.toString(),
                    startTime, LocalDateTime.now(), processingTime);
            databaseService.captureFileDetails(fileRecord);

        } catch (IOException | InterruptedException e) {
            log.error("Error processing file: {}", filePath, e);
        }
    }

    @PreDestroy
    public void stopWatching() {
        running = false;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
