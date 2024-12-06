import dao.service.DatabaseService;
import dao.service.DatabaseServiceImpl;
import dao.service.FileMetadataService;
import dao.FileRecord;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Assuming you have set up DatabaseService
        DatabaseService dbService = new DatabaseServiceImpl();

        // Create a FileMetadataService instance
        FileMetadataService metadataService = new FileMetadataService(dbService);

        // Capture file metadata for a specific file
        metadataService.captureFileDetails("example.json");

        // Retrieve all captured file records
        List<FileRecord> fileRecords = metadataService.getAllFileRecords();
        fileRecords.forEach(System.out::println);
    }
}
