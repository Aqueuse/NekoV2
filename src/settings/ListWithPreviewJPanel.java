package settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListWithPreviewJPanel extends JPanel {
    Map<String, ImageIcon> assetsMap = new HashMap<>();
    String[] assetsList;

    public ListWithPreviewJPanel(String assetsDirectory) {
        final File assetsPath = new File(
                ListWithPreviewJPanel.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                File.separatorChar + assetsDirectory + File.separatorChar + "images");

        repopulateAssetsList(assetsPath.getAbsolutePath());

        JList<String> assetsJList = new JList<>(assetsList);
        assetsJList.setSelectedIndex(0);

        JLabel assetLabel = new JLabel(assetsMap.get(assetsList[0]));
        this.add(assetsJList);
        this.add(assetLabel);
    }

    public void repopulateAssetsList(String absoluteAssetsPath) {
        Set<String> filesSet = new HashSet<>();

        try (Stream<Path> stream = Files.walk(Paths.get(absoluteAssetsPath), 1)) {
            filesSet = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
        catch (IOException ioException) {
            System.out.println("directory not found");
        }

        filesSet.forEach(file -> {
            if (file.endsWith(".png")) {
                assetsMap.put(file, getPreviewFromAssetFile(new File(absoluteAssetsPath + File.separatorChar + file)));
            }
        });
        assetsList = filesSet.toArray(new String[0]);
    }

    public ImageIcon getPreviewFromAssetFile(File assetFile) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(assetFile).getSubimage(0, 0, 32, 32);
        }
        catch (IOException ioException) {
            System.out.println("file not found");
        }
        assert bufferedImage != null;
        return new ImageIcon(bufferedImage);
    }
}

