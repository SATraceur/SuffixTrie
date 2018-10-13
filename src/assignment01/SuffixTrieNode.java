package assignment01;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SuffixTrieNode {

    private SuffixTrieData data = null;
    private Map<Character, SuffixTrieNode> children = new TreeMap<>();
    
    public void addChild(char label, SuffixTrieNode node) { children.put(label, node); }

    public SuffixTrieNode getChild(char label) { return children.containsKey(label) ? children.get(label) : null; }
    
    public void addSuffixData(SuffixTrieData dataObject) { this.data = dataObject; }
        
    public void appendSentenceData(int sentenceNumber, boolean state) { data.addSentenceData(sentenceNumber, state); }
    
    public void appendStartIndex(int startIndex) { data.addStartIndex(startIndex); }
              
    public boolean hasData() { return this.data != null; }
    
    public Map<Character, SuffixTrieNode> getChildren() { return this.children; }
    
    public ArrayList<SentenceData> getSentenceDataList() { return this.data.getSentenceDataList(); } 
    
    public ArrayList<Integer> getStartIndexList() { return this.data.getStartIndexes(); } 

    @Override
    public String toString() {
       return ("Occurs in sentences: " + data.getSentenceNumbers() + " \nWith the start indexes: " + data.getStartIndexes() + "\n");
    }
}
