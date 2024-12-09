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

            if (!file.exists() || !file.isFile()) {
                System.out.println("File not found or not valid: " + filePath);
                return;
            }

            System.out.println("Capturing metadata for file: " + filePath);

            LocalDateTime timestampReceived = LocalDateTime.now();
            LocalDateTime timestampProcessed = LocalDateTime.now().plusSeconds(10); // Simulating processing time
            Duration processingTime = Duration.between(timestampReceived, timestampProcessed);

            FileRecord fileRecord = new FileRecord(
                    file.getName(),
                    file.getAbsolutePath(),
                    timestampReceived,
                    timestampProcessed,
                    processingTime
            );

            System.out.println("FileRecord created: " + fileRecord);

            // Save the file metadata to the database
            databaseService.create(fileRecord);
            System.out.println("File metadata saved to database");

        } catch (Exception e) {
            System.out.println("Error capturing file details for: " + filePath);
            e.printStackTrace();
        }
    }

    public List<FileRecord> getAllFileRecords() {
        try {
            return databaseService.readAll();
        } catch (Exception e) {
            System.out.println("Error retrieving file records from database");
            e.printStackTrace();
            return List.of();
        }
    }
}
