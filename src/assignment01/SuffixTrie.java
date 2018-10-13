package assignment01;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SuffixTrie {

    private final SuffixTrieNode root = new SuffixTrieNode();
    private int startIndex = 1;
    private String foundWord = "";
    
    public void insert(String sentence, int sentenceNumber) { // Method inserts a sentence and recursivly inserts all suffix's of the sentence along with sentence associated data.
        List<Character> list = new ArrayList<>(); 
        
        for(char c : sentence.toCharArray()) { list.add(c); }                                // Split sentence into list of characters. 
        insertHelper(list, sentenceNumber, true);                                            // Insert the list of characters into the SuffixTrie.
    }
    
    private void insertHelper(List<Character> list, int sentenceNumber, boolean firstIteration) {
        SuffixTrieNode node = root;       
        
        //System.out.println("Inserting: " + list + " with SN " + sentenceNumber + " and CI " + startIndex); // TESTING
        while(list.size() > 0) { 
            for (int i = 0; i < list.size(); i++){                                           // For each character in the current sentence...
                if(node.getChild(Character.toLowerCase(list.get(i))) == null) {              // If the current node does not contain the appropriate children...
                    node.addChild(Character.toLowerCase(list.get(i)), new SuffixTrieNode()); // Add the current character of the sentence as a child.
                } 
                node = node.getChild(Character.toLowerCase(list.get(i)));                    // Navigate to the child.
                if(node.hasData()) {                                                         // If the character already exists...
                    node.appendStartIndex(startIndex);                                       // Append char index and new SentenceData object to current lists.
                    if(firstIteration) { node.appendSentenceData(sentenceNumber, true); }    // If inserting whole sentence(not suffix), set whole sentence flag in SentenceData object.
                    else { node.appendSentenceData(sentenceNumber, false); }                 // Else, dont set the flag.                                   
                } else {                                                                     // Else, create a new SuffixTrie data object and add it to node.
                    if(firstIteration) { node.addSuffixData(new SuffixTrieData(startIndex, sentenceNumber, true)); }
                    else { node.addSuffixData(new SuffixTrieData(startIndex, sentenceNumber, false)); }                                             
                }             
            }              
            startIndex++;                                                                    // Increment start index (where first char of sentence occurs within text with respect to start of text).
            list.remove(0);                                                                  // Delete first char of sentence.
            if(!list.isEmpty()) { insertHelper(list, sentenceNumber, false); }               // Recursivly insert suffix's of sentence until all suffix's inserted i.e. empty list.
        } 
        //        .....TESTING STRUCTURE.....        
/*        for(Map.Entry<Character,SuffixTrieNode> entry : root.getChildren().entrySet()) {
            System.out.println("KEY is: " + entry.getKey() + " CHILDREN are: ");
            Map<Character,SuffixTrieNode> childs = entry.getValue().getChildren();
            for(Map.Entry<Character,SuffixTrieNode> blah : childs.entrySet()) {
                System.out.println(blah.getKey());
            }    
        }
*/
    }  

    public SuffixTrieNode getNode(String str) { // Method returns the node at the end of a provided string.
        char arr[] = str.toCharArray();         
        SuffixTrieNode node = root;
        
        for (int i = 0; i < str.length(); i++){                                          // For each character in the string...
            if(node.getChild(Character.toLowerCase(arr[i])) == null) { return null; }    // If the current node does not contain the appropriate children, return null.
            else { node = node.getChild(Character.toLowerCase(arr[i])); }                // Else the appropriate child exists so navigate to the child.             
        }           
        return node;                                                                     // Return the node at the end of the string provided.
    }
    
    public static SuffixTrie readInFromFile(String fileName) { // Method reads in a .txt file and stores it in the SuffixTrie.
        SuffixTrie S = new SuffixTrie();
        int sentenceNumber = 1;
        String line; 

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while((line = reader.readLine()) != null) {                                 // Read in file line by line.
                GUI.jTextArea1.append(line);                                            // Append line to GUI textarea so the user can see the text they're working with.
                String[] sentences = line.trim().split("[.?!]");                        // Split into array of sentences. Sentence defined to end with a '.' || '?' || '!' || CR(due to read in method).
                for (String sentence : sentences) {                                     // For each sentence...
        //            System.out.println("PASSING " + sentence + " to insert");           // TESTING
                    S.insert(sentence, sentenceNumber++);                               // Insert it then increment sentence number.   
                }                                              
            }
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", fileName);
        }
        return S;
    }
    
    public String getSentence(int sentenceNumber) { // Method returns a sentence defined by a sentence number.
        this.foundWord = "";
        getSentenceHelper(root.getChildren(), sentenceNumber, root);  
        return this.foundWord;
    }
    
    private void getSentenceHelper(Map<Character, SuffixTrieNode> children, int sentenceNumber, SuffixTrieNode n) {
        boolean done = false;      
        
        for(Map.Entry<Character,SuffixTrieNode> entry : children.entrySet()) {                      // For each child of current node...
            ArrayList<SentenceData> SDL = entry.getValue().getSentenceDataList();                   // Get list of SentenceData objects from the current TrieNode.
            for(SentenceData SD : SDL) {                                                            // For each SentenceData object...
                if(SD.getSentenceNumber() == sentenceNumber && SD.isIsWholeSentence() && !done) {   // If the sentence number matches the desired sentence number and it is a whole sentence...
                    foundWord += entry.getKey();                                                    // Append the character to a string.  
                 //   System.out.println("Word is: " + foundWord + " KEY is: " + entry.getKey() + " sentence number is: " + SD.getSentenceNumber()); // TESTING
                    n = n.getChild(entry.getKey());                                                 // Navigate to the appropriate child.
                    getSentenceHelper(n.getChildren(), sentenceNumber, n);                          // Recursivly get rest of sentence updating child map and current node.
                    done = true;                                                                    // When the deepest layer of recursion rolls out, set flag to stop further modification of foundWord
                }
            } 
        }
    }  
    
    public String getSentenceAtIndex (int charIndex) { // Returns a string starting at the user specified char index and ending at the next occuring '.' || '?' || '!'
        this.foundWord = "";
        getSentenceAtIndexHelper(root.getChildren(), charIndex, root);
        return foundWord;
    }
    
    private void getSentenceAtIndexHelper(Map<Character, SuffixTrieNode> children, int charIndex, SuffixTrieNode n) {
        for(Map.Entry<Character,SuffixTrieNode> entry : children.entrySet()) {          // For each child of current node...
            ArrayList<Integer> list = entry.getValue().getStartIndexList();             // Get list of start indexes for the current node.
         
            for(int i: list) {                                                          // For each start index in the list.
                if(i == charIndex) {                                                    // If the current node has the appropriate char index...
                    foundWord += entry.getKey();                                        // Append the character to a string.
                    n = n.getChild(entry.getKey());                                     // Navigate to the appropriate child.
                    getSentenceAtIndexHelper(n.getChildren(), charIndex, n);            // Recursivly get rest of sentence updating child map and current node.
                }       
            }   
        }
    }
     
}