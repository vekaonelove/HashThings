import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import hashTable.HashTableFinal;
import org.json.JSONObject;
import reader.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            // Create an instance of HashTableFinal
            HashTableFinal<String, Object> hashTable = new HashTableFinal<>();

            // Reading a text file and counting word frequencies
            String textFilePath = "src/main/resources/zen.txt";
            String textContent = FileReader.readFile(textFilePath);
            System.out.println("Text File Content:");
            System.out.println(textContent);

            // Count word frequencies
            Map<String, Integer> wordFrequencyMap = new HashMap<>();
            StringTokenizer tokenizer = new StringTokenizer(textContent);
            while (tokenizer.hasMoreTokens()) {
                String word = tokenizer.nextToken().toLowerCase();
                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
            }

            // Output the word frequencies
            System.out.println("Word Frequencies:");
            for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            // Reading a JSON file
            String jsonFilePath = "src/main/resources/example.json";
            JSONObject jsonContent = FileReader.readJsonFile(jsonFilePath);
            System.out.println("JSON File Content:");
            System.out.println(jsonContent.toString(4)); // Pretty print JSON with an indent of 4 spaces

            // Add JSON content to the hash table
            for (String key : jsonContent.keySet()) {
                Object value = jsonContent.get(key);
                hashTable.add(key, value);
            }

            // Output the content of the hash table
            System.out.println("HashTable Content:");
            hashTable.printHashMap();

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("An error occurred while processing the JSON file: " + e.getMessage());
        }
    }
}