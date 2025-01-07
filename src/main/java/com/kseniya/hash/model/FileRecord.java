package com.kseniya.hash.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hashfiles")
public class FileRecord {

    @Id
    private String filename;
    private String filePath;
    private LocalDateTime timestampReceived;
    private LocalDateTime timestampProcessed;
    private Duration processingTime;
}
