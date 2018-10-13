package assignment01;
/*
    Recursive word search method partly obtained from, 
    https://github.com/bburns/code-lemmas/blob/master/src/main/java/lemmas/ds/Trie.java
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Trie{

    private TrieNode root = new TrieNode();

    public TrieNode insert(String str, TrieData data) {  
        char arr[] = str.toCharArray();                                         // Split string into array of characters.
        TrieNode node = root;
        
        for (int i = 0; i < str.length(); i++){                                 // For each character in the current word...
            if(node.getChild(arr[i]) == null) {                                 // If the current node does not contain the appropriate children.
                node.addChild(arr[i], new TrieNode());                          // Add the current character of string as a child.
                node.addChildKey(arr[i]);                                       // Store the keys of all children in parent node.
            } 
            node = node.getChild(arr[i]);                                       // Navigate to the child. 
        }   
        node.addData(data);                                                     // Add the word related data(frequency) to the terminal node.
        node.declareTerminal();                                                 // Set the last inserted node as the terminal node.
        return node;
    }
    
    public TrieNode getNode(String str) {
        char arr[] = str.toCharArray();         
        TrieNode node = root;
        
        for (int i = 0; i < str.length(); i++){                                 // For each character in the string...
            if(node.getChild(arr[i]) == null) { return null; }                  // If the current node does not contain the appropriate children, return null.
            else { node = node.getChild(arr[i]); }                              // Else the appropriate child exists so navigate to the child.             
        }             
        return node;
    }

    public TrieNode get(String str) {        
        TrieNode node = getNode(str);
        return (node != null && node.isTerminal() ? node : null);               // If final node was found and it's terminal, return node.
    }                                                                           // Else return null.         
    
    public List<String> getAlphabeticalListWithPrefix(String prefix) {
        List<String> words = new ArrayList<>();
        List<Character> keys = new ArrayList<>();  
        TrieNode N = getNode(prefix);                                           // Move to node at end of prefix.
        
        if(N != null) {
            if(N.isTerminal()){ words.add(prefix); }                            // If the prefix is a word, add it to list.                                               
            for (int i = N.childNumber() - 1; i >= 0; i--) {                    // For each child of the current node...
                keys.addAll(N.getChildKeys());                                  // Retreive list of keys for all children of current node.
                String updatedPrefix = prefix.concat(keys.get(i).toString());   // Append current key(letter) to current prefix and store as updatedPrefix.
                words.addAll(getAlphabeticalListWithPrefix(updatedPrefix));     // Recursivly traverse tree storing complete words in list.
            }
        }
        Collections.sort(words);
        return words;
    }
    
    /**
     * NOTE: TO BE IMPLEMENTED IN ASSIGNMENT 1 Finds the most frequently
     * occurring word represented in the trie (according to the dictionary file)
     * that begins with the provided prefix.
     *
     * @param prefix The prefix to search for
     * @return The most frequent word that starts with prefix
     */
    public String getMostFrequentWordWithPrefix(String prefix) {      
        List<String> words = getAlphabeticalListWithPrefix(prefix);             // Get list of words starting with prefix.
        int highestFrequency = Integer.MIN_VALUE;
        String word = "";
        TrieNode N;
        
        for(int i = 0; i < words.size(); i++) {                                 // For each word...
            N = getNode(words.get(i));                                          // Get terminal node of that word.
            if(N.getData().getFrequency() > highestFrequency) {                 // If frequency of that word is higher than highest frequency so far...
                highestFrequency = N.getData().getFrequency();                  // Save the frequency. 
                word = words.get(i);                                            // Save the word.
            }
        }      
        return word;
    }
    
    public static Trie readInDictionary(String fileName) {      
        Trie t = new Trie();
        String line;
         try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while((line = reader.readLine()) != null) {
                String[] ss = line.trim().split(" ");                           // Split lines by spaces to obtain word and frequency.
                int i = 1;
                while(ss[i].equals("")) { i++; }                                // Accounts for extra spacing between line number and word.
                t.insert(ss[i], new TrieData(Integer.parseInt(ss[i+1])));       // Insert word and data.
            }   
        } catch (Exception e) {
                System.err.format("Exception occurred trying to read '%s'.", fileName);
        }
         return t; 
    }
}
