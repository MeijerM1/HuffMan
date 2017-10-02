package huffman;

import java.io.Serializable;

public class HuffNode implements Comparable<HuffNode>, Serializable {

    private char myChar;
    private int myFrequency;
    private HuffNode myLeft, myRight;

    public HuffNode() {
    }

    public HuffNode(char myChar, HuffNode myLeft, HuffNode myRight) {
        this.myChar = myChar;
        this.myLeft = myLeft;
        this.myRight = myRight;
    }

    public HuffNode(HuffNode left, HuffNode right) {
        this.myLeft = left;
        this.myRight = right;
        determineFrequency();
    }

    public char getMyChar() {
        return myChar;
    }

    public void setMyChar(char myChar) {
        this.myChar = myChar;
    }

    public int getMyFrequency() {
        return myFrequency;
    }

    public void setMyFrequency(int myFrequency) {
        this.myFrequency = myFrequency;
    }

    public HuffNode getMyLeft() {
        return myLeft;
    }

    public void setMyLeft(HuffNode myLeft) {
        this.myLeft = myLeft;
    }

    public HuffNode getMyRight() {
        return myRight;
    }

    public void setMyRight(HuffNode myRight) {
        this.myRight = myRight;
    }

    public HuffNode(char myChar, int myFrequency) {
        this.myChar = myChar;
        this.myFrequency = myFrequency;
    }

    public void determineFrequency() {
        this.myFrequency = myRight.getMyFrequency() + myLeft.getMyFrequency();
    }

    @Override
    public String toString() {
        return "HuffNode{" +
                "myChar=" + myChar +
                ", myFrequency=" + myFrequency +
                ", myLeft=" + myLeft +
                ", myRight=" + myRight +
                '}';
    }

    @Override
    public int compareTo(HuffNode o) {
        if (this.myFrequency == o.getMyFrequency()) {
            if (this.myChar == '\0') {
                return 1;
            } else {
                return -1;
            }
        } else {
            return this.myFrequency - o.getMyFrequency();
        }
    }
}
