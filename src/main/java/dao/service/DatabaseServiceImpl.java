package dao.service;

import dao.ConnectionPool;
import dao.FileRecord;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {

    @Override
    public void create(FileRecord fileRecord) {
        String query = "INSERT INTO hashfiles (filename, filePath, timestampReceived, timestampProcessed, processingTime) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, fileRecord.filename());
            statement.setString(2, fileRecord.filePath());
            statement.setTimestamp(3, Timestamp.valueOf(fileRecord.timeStampReceived()));
            statement.setTimestamp(4, Timestamp.valueOf(fileRecord.timeStampProcessed()));
            statement.setString(5, fileRecord.processingTime().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public FileRecord read(int id) {
        String query = "SELECT * FROM hashfiles WHERE id = ?";
        FileRecord fileRecord = null;

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String filename = resultSet.getString("filename");
                String filePath = resultSet.getString("filePath");
                LocalDateTime timestampReceived = resultSet.getTimestamp("timestampReceived").toLocalDateTime();
                LocalDateTime timestampProcessed = resultSet.getTimestamp("timestampProcessed").toLocalDateTime();
                Duration processingTime = Duration.parse(resultSet.getString("processingTime"));

                fileRecord = new FileRecord(filename, filePath, timestampReceived, timestampProcessed, processingTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fileRecord;
    }

    @Override
    public List<FileRecord> readAll() {
        String query = "SELECT * FROM hashfiles";
        List<FileRecord> fileRecords = new ArrayList<>();

        try (Connection connection = ConnectionPool.getConnection();
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String filename = resultSet.getString("filename");
                String filePath = resultSet.getString("filePath");
                LocalDateTime timestampReceived = resultSet.getTimestamp("timestampReceived").toLocalDateTime();
                LocalDateTime timestampProcessed = resultSet.getTimestamp("timestampProcessed").toLocalDateTime();
                Duration processingTime = Duration.parse(resultSet.getString("processingTime"));

                FileRecord fileRecord = new FileRecord(filename, filePath, timestampReceived, timestampProcessed, processingTime);
                fileRecords.add(fileRecord);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return fileRecords;
    }

    @Override
    public void update(FileRecord fileRecord) {
        String query = "UPDATE hashfiles SET filename = ?, filePath = ?, timestampReceived = ?, timestampProcessed = ?, processingTime = ? WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, fileRecord.filename());
            statement.setString(2, fileRecord.filePath());
            statement.setTimestamp(3, Timestamp.valueOf(fileRecord.timeStampReceived()));
            statement.setTimestamp(4, Timestamp.valueOf(fileRecord.timeStampProcessed()));
            statement.setString(5, fileRecord.processingTime().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM hashfiles WHERE id = ?";

        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
