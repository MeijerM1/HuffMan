package huffman;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class TreeBuilder {
    private FrequencyGetter freqGetter;
    private List<HuffNode> nodes;
    private PriorityQueue<HuffNode> queue;

    TreeBuilder(Path input) {
        this.nodes = new ArrayList<>();
        freqGetter = new FrequencyGetter(input);
    }


    public HuffNode buildTree() {
        nodes = freqGetter.getNodes();

        queue = new PriorityQueue<>();

        queue.addAll(nodes);

        while (queue.size() > 1) {
            HuffNode left = queue.remove();
            HuffNode right  = queue.remove();
            queue.add(new HuffNode(left, right));
        }

        HuffNode node = null;

        while (queue.size() != 0) {
            node = (queue.remove());
        }

        System.out.println(node);

        try {
            write(node, "XmlTest");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }

    public static void write(Object f, String filename) throws Exception{
        XMLEncoder encoder =
                new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(filename)));
        encoder.writeObject(f);
        encoder.close();
    }

}
