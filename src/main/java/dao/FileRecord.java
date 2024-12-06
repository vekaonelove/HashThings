package dao;

import java.time.Duration;
import java.time.LocalDateTime;

public record FileRecord(
    String filename,
    String filePath,
    LocalDateTime timeStampReceived,
    LocalDateTime timeStampProcessed,
    Duration processingTime
    )
    {}
