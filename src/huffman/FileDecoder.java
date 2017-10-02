package huffman;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileDecoder {
    private static final Logger LOGGER = Logger.getLogger(FileDecoder.class.getName());

    private HuffTree tree;
    private Path file;
    private Path codes;
    private String[] binCodes;
    private Path outputFile;

    public FileDecoder(Path fileToDecode, Path huffManCodes) {
        binCodes = new String[255];
        tree = new HuffTree();
        file = fileToDecode;
        codes = huffManCodes;
        outputFile = Paths.get("./testfiles/decodedFile.txt");
    }

    private void readTree() {
        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            fin = new FileInputStream(codes.toString());
            ois = new ObjectInputStream(fin);
            tree.setTree((HuffNode) ois.readObject());
            binCodes = tree.getBinaryCodes();

        } catch (Exception ex) {
            LOGGER.severe(ex.getLocalizedMessage());
        } finally {
            try {
                assert fin != null;
                fin.close();
                assert ois != null;
                ois.close();
            } catch (IOException e) {
                LOGGER.severe(e.getLocalizedMessage());
            }
        }

        tree.getBinaryCodes();
    }

    public void readFile() {
        readTree();
        byte[] fileContents = null;
        StringBuilder sb = new StringBuilder();
        try {
            fileContents = Files.readAllBytes(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (byte b : fileContents) {
            String s1 = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            sb.append(s1);
            System.out.println(s1);
        }

        String decoded = decode(sb.toString());

        writeString(decoded);

        System.out.println(sb.toString());
        System.out.println(decoded);
    }

    private void writeString(String line) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(String.valueOf(outputFile)));
            writer.write(line);

        } catch (IOException e) {
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
            }
        }
    }

    private String decode(String bits) {
        // create empty string to hold decoded message
        String decodedStr = "";

        // iterate through bits
        for (int i = 0; i < bits.length(); i++) {
            if (!getChar(bits.substring(0, i + 1)).equals("")) {
                decodedStr += getChar(bits.substring(0, i + 1));
                bits = bits.substring(i + 1);
                i = 0;
            }
        }
        return decodedStr;
    }

    private String getChar(String bits) {
        // create string to hold potential character
        String character = "";
        // traverse code table to seek match
        for (int i = 0; i < binCodes.length; i++) {
            // add to string if match is found
            if (binCodes[i] != null) {
                if (binCodes[i].equals(bits)) {
                    character += (char) i;
                }
            }

        }
        return character;
    }
}
