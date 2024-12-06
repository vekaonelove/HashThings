package dao.service;

import dao.FileRecord;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileMonitorService {

    private final Path directoryToWatch;
    private final ExecutorService executor;

    public FileMonitorService(String directoryPath) {
        this.directoryToWatch = Paths.get(directoryPath);
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void startWatching() {
        executor.submit(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                directoryToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                System.out.println("Monitoring directory: " + directoryToWatch);

                while (true) {
                    WatchKey key = watchService.take();
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                            Path createdFilePath = directoryToWatch.resolve((Path) event.context());
                            System.out.println("File created: " + createdFilePath);

                            BasicFileAttributes attrs = Files.readAttributes(createdFilePath, BasicFileAttributes.class);
                            LocalDateTime creationTime = attrs.creationTime()
                                    .toInstant()
                                    .atZone(ZoneId.systemDefault())
                                    .toLocalDateTime();

                            System.out.println("Creation time: " + creationTime);
                            saveFileMetadata(createdFilePath, creationTime);
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void saveFileMetadata(Path filePath, LocalDateTime creationTime) {
        DatabaseService databaseService = new DatabaseServiceImpl();
        FileRecord fileRecord = new FileRecord(
                filePath.getFileName().toString(),
                filePath.toAbsolutePath().toString(),
                creationTime,
                null,
                null
        );
        databaseService.create(fileRecord);
    }

    public void stopWatching() {
        executor.shutdownNow();
    }

    public static void main(String[] args) {
        String projectDirectory = "C:\\Users\\ksenveka\\IdeaProjects\\HashTable\\src\\main\\java";
        FileMonitorService monitorService = new FileMonitorService(projectDirectory);
        monitorService.startWatching();
    }
}

