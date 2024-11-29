package readerTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reader.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {
    private FileReader fileReader;

    @BeforeEach
    public void setUp() {
        fileReader = new FileReader();
    }

    @Test
    public void testReadFile() throws IOException {
        String content = fileReader.readFile("src/test/resources/zen.txt");
        assertNotNull(content);
        assertEquals("This is a test file.", content.trim());
    }

    @Test
    public void testReadFile_FileNotFound() {
        assertThrows(IOException.class, () -> {
            fileReader.readFile("zen.txt");
        });
    }

    @Test
    public void testReadFile_EmptyFile() throws IOException {
        String content = fileReader.readFile("src/test/resources/emptyFile.txt");
        assertNotNull(content);
        assertEquals("", content);
    }
}