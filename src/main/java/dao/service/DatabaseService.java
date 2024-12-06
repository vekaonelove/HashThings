package dao.service;
import dao.FileRecord;

import java.util.List;

public interface DatabaseService {
    void create(FileRecord fileRecord);
    void update(FileRecord fileRecord);
    void delete(int id);

    FileRecord read(int id);
    List<FileRecord> readAll();
}
