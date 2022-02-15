package settings;

import neko.Neko;
import neko.NekoAssets;

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

import static settings.Settings.*;

public class ListWithPreviewJPanel extends JPanel {
    Map<String, ImageIcon> assetsMap = new HashMap<>();
    String[] assetsList;

    public ListWithPreviewJPanel(String assetsDirectory) {
        final File assetsPath = new File(
                ListWithPreviewJPanel.class.getProtectionDomain().getCodeSource().getLocation().getPath() +
                File.separatorChar + assetsDirectory + File.separatorChar + "images"
        );

        repopulateAssetsList(assetsPath.getAbsolutePath());

        JList<String> assetsJList = new JList<>(assetsList);
        assetsJList.setSelectedIndex(getAssetJListIndex(assetsDirectory.split("/")[0], assetsList));

        JLabel assetLabel = new JLabel();
        if (assetsDirectory.equals("neko/")) assetLabel.setIcon(assetsMap.get(currentPetAsset));
        if (assetsDirectory.equals("toy/")) assetLabel.setIcon(assetsMap.get(currentToyAsset));

        this.add(assetsJList);
        this.add(assetLabel);

        assetsJList.addListSelectionListener(e -> {
            try {
                if (assetsDirectory.equals("neko/")) {
                    Neko.settings.writeSettings("pet", assetsList[assetsJList.getSelectedIndex()]);
                    assetLabel.setIcon(assetsMap.get(currentPetAsset));
                    NekoAssets.nekoAssetsImage = Neko.settings.getAssetFromSettings("pet");
                }
                if (assetsDirectory.equals("toy/")) {
                    Neko.settings.writeSettings("toy", assetsList[assetsJList.getSelectedIndex()]);
                    assetLabel.setIcon(assetsMap.get(currentToyAsset));
                    NekoAssets.nekoAssetsImage = Neko.settings.getAssetFromSettings("toy");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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

    public Integer getAssetJListIndex(String assetDirectory, String[] assets) {
        String setting = assetDirectory.equals("toy") ? (currentToyAsset):(currentPetAsset);
        for (int i = 0; i < assets.length; i++) {
            if (assets[i].equals(setting)) {
                return i;
            }
        }
        return 0;
    }
}

