import hashThings.impl.HashTableImpl;

public class Main {
    public static void main(String[] args) {
        HashTableImpl<String, Integer> hashTable = new HashTableImpl<>();

        // Adding elements
        hashTable.add("one", 1);
        hashTable.add("two", 2);
        hashTable.add("three", 3);

        // Printing the hash table
        hashTable.printHashMap();

        // Getting an element
        System.out.println("Element with key 'two': " + hashTable.getElement("two"));

        // Replacing an element
        hashTable.replace("two", 2, 22);
        System.out.println("Element with key 'two' after replacement: " + hashTable.getElement("two"));

        // Removing an element
        hashTable.remove("one", 1);
        System.out.println("Hash table after removing element with key 'one':");
        hashTable.printHashMap();

        // Popping an element
        System.out.println("Popped element with key 'three': " + hashTable.pop("three"));
        System.out.println("Hash table after popping element with key 'three':");
        hashTable.printHashMap();

        // Clearing the hash table
        hashTable.clear();
        System.out.println("Hash table after clearing:");
        hashTable.printHashMap();
    }
}