package assignment01;

public class SentenceData {
    
    private int sentenceNumber;
    private boolean isWholeSentence;
    
    SentenceData(int sentenceNumber, boolean state) {
        this.sentenceNumber = sentenceNumber;
        this.isWholeSentence = state;
    }

    public int getSentenceNumber() { return this.sentenceNumber; }

    public void setSentenceNumber(int sentenceNumber) { this.sentenceNumber = sentenceNumber; }

    public boolean isIsWholeSentence() { return this.isWholeSentence; }

    public void setIsWholeSentence(boolean state) { this.isWholeSentence = state; }  
}
