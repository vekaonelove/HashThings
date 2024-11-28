package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class FileReader {

    public static String readFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    public static JSONObject readJsonFile(String filePath) throws IOException {
        String content = readFile(filePath);
        return new JSONObject(content);
    }
}