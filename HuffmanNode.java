import java.util.Comparator;
public class HuffmanNode{
private Character inChar;
private String encoded;// encoded string
private int freq = 1;
HuffmanNode left,right;
public String getEncoded() {//returns encoded string
	return encoded;
}
public void setEncoded(String encoded) {//sets the encoded string
	this.encoded = encoded;
}
public Character getInChar() {
	return inChar;
}
public void setInChar(Character inChar) {
	this.inChar = inChar;
}

public int getFreq() {
	return freq;
}
public void setFreq(int freq) {
	this.freq = freq;
}

public HuffmanNode getLeft() {
	return left;
}
public void setLeft(HuffmanNode left) {
	this.left = left;
}
public HuffmanNode getRight() {
	return right;
}
public void setRight(HuffmanNode right) {
	this.right = right;
}
public HuffmanNode(char charac){//constructor for huffmannode taking character is input
	this.inChar = charac;
}

public HuffmanNode(Character charac,int freq1,HuffmanNode left,HuffmanNode right){// constructor for huffmannode taking character, frequency and left and right nodes
	this.inChar = charac;
	this.freq=freq1;
	this.left=left;
	this.right = right;
}

public void incrementFreq(){
	this.freq++;
}

public String toString(){
	return this.inChar + Integer.toString(this.freq);
}

}
