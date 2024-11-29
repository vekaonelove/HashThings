import java.io.IOException;
import java.util.StringTokenizer;

import hashTable.impl.HashTableImpl;
import org.json.JSONObject;
import reader.FileReader;

public class Main {
    public static void main(String[] args) {
        try {
            HashTableImpl<String, Object> hashTable = new HashTableImpl<>();

            // Reading a text file and counting word frequencies
            String textFilePath = "src/main/resources/zen.txt";
            String textContent = FileReader.readFile(textFilePath);

            // Counting word frequencies - an Entry<"word", frequency> is used inside
            HashTableImpl<String, Integer> wordFrequencyMap = new HashTableImpl<>();
            StringTokenizer tokenizer = new StringTokenizer(textContent);
            while (tokenizer.hasMoreTokens()) {
                String word = tokenizer.nextToken().toLowerCase();
                wordFrequencyMap.add(word, wordFrequencyMap.getOrDefault(word, 0).orElse(0) + 1);            }

            // Output the word frequencies
            System.out.println("Word Frequencies:");
            wordFrequencyMap.printHashTable();

            String jsonFilePath = "src/main/resources/example.json";
            JSONObject jsonContent = FileReader.readJsonFile(jsonFilePath);

            for (String key : jsonContent.keySet()) {
                Object value = jsonContent.get(key);
                hashTable.add(key, value);
            }
            System.out.println("\nHashTable Content:");
            hashTable.printHashTable();

        } catch (IOException e) {
            System.err.println("An error occurred while reading the file: " + e.getMessage());
        } catch (org.json.JSONException e) {
            System.err.println("An error occurred while processing the JSON file: " + e.getMessage());
        }
    }
}