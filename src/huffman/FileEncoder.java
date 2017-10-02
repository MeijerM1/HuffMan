package huffman;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

class FileEncoder {
    private static final Logger LOGGER = Logger.getLogger(FileEncoder.class.getName());

    private HuffTree tree;
    private Path newFile;
    private Path oldFile;

    FileEncoder(Path fileToEncode, Path newFileLocation) {
        tree = new HuffTree(fileToEncode);
        this.newFile = newFileLocation;
        this.oldFile = fileToEncode;

        tree.buildTree();
    }

    void encodeFile() {
        tree.getBinaryCodes();
        StringBuilder sb = new StringBuilder();

        OutputStream os = null;
        try {
            Path newFile1 = newFile.resolveSibling(newFile.getFileName() + ".bin");

            try {
                os = new BufferedOutputStream(new FileOutputStream(newFile1.toString()));
            } catch (IOException e) {
                LOGGER.severe(e.getLocalizedMessage());
            }

            try (BufferedReader reader = Files.newBufferedReader(oldFile)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    assert os != null;
                    sb.append(line);
                    os.flush();
                }
                newFile = newFile.resolveSibling(newFile.getFileName() + ".ser");

                os.write(tree.getEncodedLine(sb.toString()));

                FileOutputStream fileOut = new FileOutputStream(newFile.toString());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(tree.getTree());
                out.close();
                fileOut.close();
            } catch (Exception x) {
                LOGGER.severe(x.getLocalizedMessage());
            } finally {
                os.close();
            }
        } catch (IOException ex) {
            LOGGER.severe(ex.getLocalizedMessage());
        }
    }
}
