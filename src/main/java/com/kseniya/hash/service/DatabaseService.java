package com.kseniya.hash.service;

import com.kseniya.hash.model.FileRecord;
import com.kseniya.hash.model.FileRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DatabaseService {
    private final FileRecordRepository fileRecordRepository;

    public void captureFileDetails(FileRecord fileRecord) {
        fileRecordRepository.save(fileRecord);
        log.info("File metadata saved: {}", fileRecord.getFilePath());
    }
}
