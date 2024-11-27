package reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hashThings.impl.HashTableImpl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileReaderMap {

    public static HashTableImpl<String, Object> readJsonToHashTable(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashTableImpl<String, Object> hashTable = new HashTableImpl<>();
        try {
            Map<String, Object> map = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                hashTable.add(entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hashTable;
    }
}