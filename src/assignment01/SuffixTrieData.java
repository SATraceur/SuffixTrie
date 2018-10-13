package assignment01;

import java.util.ArrayList;

public class SuffixTrieData {
    
    private ArrayList<Integer> startIndexes = new ArrayList<>();
    private ArrayList<SentenceData> sentenceNumbers = new ArrayList<>();
        
    SuffixTrieData(int startIndex, int sentenceNumber, boolean wholeSentence) {      // Constructor.
        this.sentenceNumbers.add(new SentenceData(sentenceNumber, wholeSentence));   // Construct SentenceData with sentence number and whole sentence flag then add to array                            
        this.startIndexes.add(startIndex);                                           // Store start index.
    }

    public void addStartIndex(int startIndex) { this.startIndexes.add(startIndex); }
    
    public void addSentenceData(int sentenceNumber, boolean wholeSentence) { 
        this.sentenceNumbers.add(new SentenceData(sentenceNumber, wholeSentence)); 
    }

    public ArrayList<Integer> getStartIndexes() { return this.startIndexes; }

    public ArrayList<SentenceData> getSentenceDataList() { return this.sentenceNumbers; }
    
    public ArrayList<Integer> getSentenceNumbers() { // Method extracts sentence numbers from data objects and returns them in a list.
        ArrayList<Integer> SN = new ArrayList<>();
        for(SentenceData SD : sentenceNumbers) { SN.add(SD.getSentenceNumber()); }
        return SN;
    }
}
