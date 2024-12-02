package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileReader {

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static JsonNode readJsonFile(String filePath) throws IOException {
        String content = readFile(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(content);
    }
}