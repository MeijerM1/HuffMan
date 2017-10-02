package huffman;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Path fileToEncode = Paths.get("./testfiles/smalltest.txt");
        Path fileOutput = Paths.get("./testfiles/largeEncodedTest");
        Path fileToDecode = Paths.get("./testfiles/largeEncodedTest.bin");
        Path huffcodes = Paths.get("./testfiles/largeEncodedTest.ser");

        FileEncoder coder = new FileEncoder(fileToEncode, fileOutput);
        coder.encodeFile();

        FileDecoder decoder = new FileDecoder(fileToDecode, huffcodes);
        decoder.readFile();

    }
}
