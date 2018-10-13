package assignment01;

import java.util.*;
import java.util.TreeMap;

public class TrieNode {
       
    private TrieData data = null;
    private boolean terminal = false;
    private int numChildren = 0;
    private List<Character> keys = new ArrayList<>();
    private Map<Character, TrieNode> children = new TreeMap<>(); // Chose TreeMap due to it's ability to search keys alphabetically.
    
    public TrieNode getChild(char label) {
        return children.containsKey(label) ? children.get(label) : null;
    }

    public void addChild(char label, TrieNode node) {
        children.put(label, node);
        numChildren++;
    }

    public void addData(TrieData dataObject) { this.data = dataObject; }
    
    public TrieData getData() { return this.data; }

    public void declareTerminal() { this.terminal = true; }
    
    public boolean isTerminal() { return this.terminal; }
    
    public int childNumber() { return this.numChildren; }

    public void addChildKey(char key) { this.keys.add(key); }
    
    public List<Character> getChildKeys() { return this.keys; }
    
    @Override
    public String toString() {
        return ("TrieNode; isTerminal=" + terminal + ", data=" + String.valueOf(data) + ", #children=" + numChildren);
    }
}
