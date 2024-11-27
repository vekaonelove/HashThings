package reader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hashThings.Entry;
import linkedList.impl.LinkedListImpl;


import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileReaderLinkedList {

    public static LinkedListImpl<Entry<String, Object>> readJsonToLinkedList(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        LinkedListImpl<Entry<String, Object>> linkedList = new LinkedListImpl<>();
        try {
            Map<String, Object> map = objectMapper.readValue(new File(filePath), new TypeReference<Map<String, Object>>() {});
            int index = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                linkedList.add(index++, new Entry<>(entry.getKey(), entry.getValue()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return linkedList;
    }
}