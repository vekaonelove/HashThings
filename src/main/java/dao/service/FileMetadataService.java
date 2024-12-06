package dao.service;

import dao.FileRecord;

import java.io.File;
import java.nio.file.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FileMetadataService {

    private final DatabaseService databaseService;

    public FileMetadataService(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public void captureFileDetails(String filePath) {
        try {
            File file = new File(filePath);

            LocalDateTime timestampReceived = LocalDateTime.now();
            LocalDateTime timestampProcessed = LocalDateTime.now().plusSeconds(10);
            Duration processingTime = Duration.between(timestampReceived, timestampProcessed);

            FileRecord fileRecord = new FileRecord(
                    file.getName(),
                    file.getAbsolutePath(),
                    timestampReceived,
                    timestampProcessed,
                    processingTime
            );

            databaseService.create(fileRecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void captureFilesInDirectory(String directoryPath) {
        try {
            Path path = Paths.get(directoryPath);

            WatchService watchService = FileSystems.getDefault().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            System.out.println("Watching directory for new files...");

            while (true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    return;
                }

                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();

                    if (StandardWatchEventKinds.ENTRY_CREATE == kind) {
                        Path newFilePath = (Path) event.context();
                        System.out.println("New file detected: " + newFilePath);

                        captureFileDetails(newFilePath.toString());
                    }
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<FileRecord> getAllFileRecords() {
        return databaseService.readAll();
    }
}
