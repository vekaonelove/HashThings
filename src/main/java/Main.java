import java.io.IOException;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import hashTable.impl.HashTableImpl;
import reader.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            HashTableImpl<String, Object> hashTable = new HashTableImpl<>();

            String textFilePath = "src/main/resources/zen.txt";
            String textContent = FileReader.readFile(textFilePath);

            HashTableImpl<String, Integer> wordFrequencyMap = new HashTableImpl<>();
            StringTokenizer tokenizer = new StringTokenizer(textContent);
            while (tokenizer.hasMoreTokens()) {
                String word = tokenizer.nextToken().toLowerCase();
                wordFrequencyMap.add(word, wordFrequencyMap.getOrDefault(word, 0).orElse(0) + 1);
            }

            System.out.println("Word Frequencies:");
            wordFrequencyMap.printHashTable();

            String jsonFilePath = "src/main/resources/example.json";
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonContent = objectMapper.readTree(FileReader.readFile(jsonFilePath));

            jsonContent.fieldNames().forEachRemaining(key -> {
                JsonNode value = jsonContent.get(key);
                hashTable.add(key, value);
            });

            System.out.println("\nHashTable Content:");
            hashTable.printHashTable();

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        }
    }
}