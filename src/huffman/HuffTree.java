package huffman;

import sun.security.ssl.Debug;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.List;

public class HuffTree {

    private HuffNode tree;
    private TreeBuilder builder;
    private String[] binCodes;

    public HuffTree(HuffNode tree) {
        this.tree = tree;
    }

    HuffTree() {
    }

    HuffTree(Path file) {
        this.builder = new TreeBuilder(file);
    }

    HuffNode getTree() {
        return tree;
    }

    void setTree(HuffNode tree) {
        this.tree = tree;
    }

    void buildTree() {
        this.tree = builder.buildTree();
    }

    String[] getBinaryCodes() {
        binCodes = new String[255];
        readNode(binCodes,tree, "");
        for(int i = 0 ; i < binCodes.length; i++) {
            if(binCodes[i] != null) {
                Debug.println(String.valueOf((char) i), binCodes[i]);
            }
        }
        return binCodes;
    }

    private void readNode(String[] codes,HuffNode node,String code) {
        if(node.getMyChar() == 0) {
            readNode(codes, node.getMyLeft(), code + '0');
            readNode(codes, node.getMyRight(), code + '1');
        }
        else {
            codes[node.getMyChar()] = code;
        }
    }

    /**
     * Encode a string using huffman tree to bytes.
     * @param line Piece of text to compress.
     * @return Byte array of encoded text.
     */
    public byte[] getEncodedLine(String line) {
        // String builder to build string of zero and one.
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i <line.length(); i++) {
            builder.append(binCodes[line.charAt(i)]);
        }

        String newLine =  builder.toString();

        // Fill up the byte with zeros if the byte is not full.
        if(newLine.length() % 8  != 0) {
            while(newLine.length() % 8  != 0) {
                newLine += "0";
            }
        }

        // Convert to string to byte array.
        byte[] bytes = new BigInteger(newLine, 2).toByteArray();

        return bytes;
    }
}
