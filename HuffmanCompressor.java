import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.util.*;
public class HuffmanCompressor {
private static LinkedList<HuffmanNode> ListtoStore;// LinkedList used to store huffmannodes
private static String [] codeHolder = new String [256];//holds the encoded strings of each letter of each huffman node
private static HuffmanNode[] copy;// stores a copy of each huffmannode
private static String store = "";// string to build the encoded file 
private static int savedSpace =0;//stores the number of saves spaces 
// I used the linked list implementation as this program requires constant addition of nodes for each character which would be faster than using the ArrayList.
// Also the LinkedList is faster for removing the nodes from the linkedlist when forming the tree than when an ArrayList is used.
public static void huffmanCoder(String filename,String filename2) throws IOException{
	   readFile(filename);
	  copyList();
	  buildTable(codeHolder,buildTrie(),"");
      encode();
      outputTable();
      outputToFile(filename2);
}
	public static void main(String[] args)throws IOException {
	String fileinput,fileoutput;
	 fileinput = args[0];
	 fileoutput = args[1];
		HuffmanCompressor.huffmanCoder(fileinput,fileoutput);
        
	}

   private static void readFile(String filename){//reads input file and stores characters in huffmannodes linkedlist
	    ListtoStore= new LinkedList<HuffmanNode>();
	  	try{
	  	InputStream in = new FileInputStream(filename);
	 	   Reader reader = new InputStreamReader(in);
	  		int c;
	         Character readCh = null;
	  		while((c = reader.read()) != -1){
	             readCh = (char)c;
	  		     if(!Character.isWhitespace(readCh)){
	                searchList(ListtoStore,readCh); //helper method to search the list for repeats.
	                store = store + (char)c;// stores the original input file string
	  		     }
	     	}
	  		reader.close();
	  	}
	  	catch(IOException exception){
	  	  System.out.println(exception);
	    }
	  
	  
   }

   private static void outputToFile(String filename) throws IOException{//outputs the encoded string to the file
	   PrintWriter out = new PrintWriter(filename);
	   out.println(translate());
	   out.close();

   }
   
private static boolean searchList(LinkedList<HuffmanNode> list,char c){
for(HuffmanNode eachNode:list){// loop to check if character is a repeat. If it is frequency in incremented
	if(eachNode.getInChar().equals(c)){
		eachNode.incrementFreq();
	    return true;
    }
}
	HuffmanNode newNode = new HuffmanNode(c);//If character is not a repeat new node is added 
	list.add(newNode);
	return false;
}
   

private static HuffmanNode buildTrie(){
	FreqComparator f1 = new FreqComparator();//object to compare huffmannodes based on frequencies 
	ListtoStore.sort(f1);//sorts the linkedlist in frequency order
	while (ListtoStore.size() > 1) {
        HuffmanNode left  = ListtoStore.remove();
        HuffmanNode right = ListtoStore.remove();// removes the last two elements in the linked list 
        HuffmanNode parent = new HuffmanNode(null, left.getFreq() + right.getFreq(), left, right);//makes new node with removed nodes 
        ListtoStore.add(parent);//adds node to the linkedlist
        ListtoStore.sort(f1);//sorts the linkedlist again
    }
	HuffmanNode root = ListtoStore.getFirst();//at the end of building the trie.Return the root node of the trie
	return root;
}
   
private static class FreqComparator implements Comparator<HuffmanNode>{//class for comparator object to compare huffmannode using frequencies
	public int compare(HuffmanNode thisOne, HuffmanNode other){
		return thisOne.getFreq()-other.getFreq();
	}

}

private static class FreqComparator2 implements Comparator<HuffmanNode>{//class for comparator object to compare huffmannode using character ASCII values
	public int compare(HuffmanNode thisOne, HuffmanNode other){
		return thisOne.getInChar()-other.getInChar();
	}

}

private static void buildTable(String[] st, HuffmanNode node,String s) {//Builds the huffmannode tree using recursion
	if (!isLeafNode(node)) {
        buildTable(st, node.left,  s + '0');
        buildTable(st, node.right, s + '1');
    }
    else {
        savedSpace = (8-s.length()) * node.getFreq() + savedSpace;
    	st[node.getInChar()] = s;//places the encoded string in an array with indexes matching character ASCII value
    }
}


private static boolean isLeafNode(HuffmanNode node){//method to determine if the node is a leadnode
return (node.getLeft() == null && node.getRight() == null);	
}

public static void encode(){//sets the encoded value to the corresponding huffmannode
	for(int r = 0;r<copy.length;r++){
		for(int s =0;s<codeHolder.length;s++){
			if(copy[r].getInChar() == (char)s)
				copy[r].setEncoded(codeHolder[s]);
		}
	}
}


public static void copyList(){//copies the linkedlist containing huffmannodes to an array
	 copy = new HuffmanNode[ListtoStore.size()];
	  int i =0;
	 for(HuffmanNode each:ListtoStore){
		 copy[i] = each;
	     i++;
	 }
	 FreqComparator2 f2 = new FreqComparator2();//comparator object to compare huffmannodes based on ASCII value of character
	 Arrays.sort(copy, f2);
}

private static void outputTable(){// outputs the table containing character frequency and encoded string
	for(HuffmanNode each: copy)
		System.out.println(each.getInChar() + "    " + each.getFreq()+ "     "+ each.getEncoded());

	}

private static String translate(){// translates the original string to the encoded string
String output ="";
	for(int i=0;i<store.length();i++){
	for(int j=0;j<copy.length;j++){
	if(copy[j].getInChar() == store.charAt(i)){//compares characters from the copy array with the characters from the input string
	   output = copy[j].getEncoded() + output;//builds the encoded string
       }
    }
}
	System.out.println("The amount of saved space is:" + "  " + savedSpace);
	return output;
}
}




