package huffman;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FrequencyGetter {
    private Path file;
    private int[] freq;

    public FrequencyGetter(Path file) {
        this.file = file;
        freq = new int[255];
    }

    public List<HuffNode> getNodes() {
        List<HuffNode> nodes = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                readWithArray(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        for (int i = 0; i < freq.length; i++) {
            if (freq[i] != 0) {
                HuffNode newNode = new HuffNode((char) i, freq[i]);
                System.out.println(newNode);
                nodes.add(newNode);
            }
        }
        return nodes;
    }

    private void readWithArray(String input) {
        for (int i = 0; i < input.length(); i++) {
            freq[(int) input.charAt(i)]++;
        }
    }
}
