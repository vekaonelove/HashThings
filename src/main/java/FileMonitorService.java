import dao.service.DatabaseService;
import dao.service.DatabaseServiceImpl;
import dao.service.FileMetadataService;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FileMonitorService {

    private final Path directoryToWatch;
    private final ExecutorService executor;
    private final FileMetadataService fileMetadataService;
    private volatile boolean running = true;

    public FileMonitorService(String directoryPath, FileMetadataService fileMetadataService) {
        this.directoryToWatch = Paths.get(directoryPath);
        this.executor = Executors.newSingleThreadExecutor();
        this.fileMetadataService = fileMetadataService;
    }

    public void startWatching() {
        executor.submit(() -> {
            try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
                directoryToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                System.out.println("Monitoring directory: " + directoryToWatch);

                while (running) {
                    WatchKey key = watchService.poll(1, TimeUnit.SECONDS);
                    if (key != null) {
                        for (WatchEvent<?> event : key.pollEvents()) {
                            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                                Path createdFilePath = directoryToWatch.resolve((Path) event.context());
                                processNewFile(createdFilePath);
                            }
                        }
                        key.reset();
                    }
                }
            } catch (IOException | InterruptedException e) {
                if (running) {
                    e.printStackTrace();
                } else {
                    System.out.println("File monitor stopped.");
                }
            }
        });
    }

    private void processNewFile(Path filePath) {
        try {
            for (int i = 0; i < 5; i++) {
                if (Files.exists(filePath) && Files.isReadable(filePath)) break;
                Thread.sleep(100);
            }

            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                System.out.println("File not accessible: " + filePath);
                return;
            }

            BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
            LocalDateTime creationTime = attrs.creationTime()
                    .toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            System.out.println("File detected: " + filePath);
            System.out.println("Creation time: " + creationTime);

            fileMetadataService.captureFileDetails(filePath.toAbsolutePath().toString());

        } catch (IOException | InterruptedException e) {
            System.out.println("Error processing file: " + filePath);
            e.printStackTrace();
        }
    }

    public void stopWatching() {
        running = false;
        executor.shutdownNow();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.out.println("Forced shutdown of file monitor.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String projectDirectory = "C:\\Users\\ksenveka\\IdeaProjects\\HashTable\\src\\main\\java";

        DatabaseService databaseService = new DatabaseServiceImpl();
        FileMetadataService fileMetadataService = new FileMetadataService(databaseService);

        FileMonitorService monitorService = new FileMonitorService(projectDirectory, fileMetadataService);
        Runtime.getRuntime().addShutdownHook(new Thread(monitorService::stopWatching));
        monitorService.startWatching();
    }
}
