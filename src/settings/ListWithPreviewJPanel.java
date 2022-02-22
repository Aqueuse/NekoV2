package settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pet.PetAssets;
import systemTray.MySystemTray;
import toy.ToyAssets;
import static settings.SettingsFileManagement.getAssetFromSettings;

public class ListWithPreviewJPanel extends JPanel {
    String[] assetsList;
    JList<String> assetsJList;
    public ListWithPreviewJPanel(String assetsPath, String assetType) {
        assetsJList = new JList<>();
        assetsList = getAssetsList(assetsPath);
        assetsJList = repopulatedAssetsJList(assetsPath);
        assetsJList.setSelectedIndex(getAssetJListIndex(assetType, assetsList));

        JLabel assetLabel = new JLabel();
        String savedAssetPath = assetsPath + File.separatorChar + assetsList[assetsJList.getSelectedIndex()];
        assetLabel.setIcon(new ImageIcon(getAssetSubImage(new File(savedAssetPath))));

        this.add(assetsJList);
        this.add(Box.createRigidArea(new Dimension(20, 0)));
        this.add(assetLabel);

        assetsJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // This line prevents double events
                try {
                    assetsList = getAssetsList(assetsPath);
                    if (assetsJList.getSelectedIndex() == -1) assetsJList.setSelectedIndex(0);
                    SettingsFileManagement.writeSettings(assetType, assetsList[assetsJList.getSelectedIndex()]);

                    String newAssetPath = assetsPath + File.separatorChar + assetsList[assetsJList.getSelectedIndex()];
                    assetLabel.setIcon(new ImageIcon(getAssetSubImage(new File(newAssetPath))));

                    if (assetType.equals("pet")) {
                        PetAssets.petAssetsImage = getAssetFromSettings(assetType);
                        MySystemTray.trayIcon.setImage(PetAssets.preview());
                    }
                    if (assetType.equals("toy")) {
                        ToyAssets.toyAssetsImage = getAssetFromSettings(assetType);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public String[] getAssetsList(String assetsDirectory) {
        Set<String> filesSet = new HashSet<>();

        try (Stream<Path> stream = Files.walk(Paths.get(assetsDirectory), 1)) {
            filesSet = stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
        catch (IOException ioException) {
            System.out.println("directory not found");
        }

        assetsList = filesSet.toArray(new String[0]);
        return assetsList;
    }

    public JList<String> repopulatedAssetsJList(String assetsDirectory) {
        String[] assetsList = getAssetsList(assetsDirectory);
        assetsJList.setListData(assetsList);
        return assetsJList;
    }

    public BufferedImage getAssetSubImage(File assetFile) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(assetFile).getSubimage(0, 0, 32, 32);
        }
        catch (IOException ioException) {
            System.out.println("file not found");
        }
        assert bufferedImage != null;
        return bufferedImage;
    }

    public Integer getAssetJListIndex(String assetDirectory, String[] assets) {
        String setting = assetDirectory.equals("toy") ? (SettingsFileManagement.loadKeyFromSettings("toy")):(SettingsFileManagement.loadKeyFromSettings("pet"));
        for (int i = 0; i < assets.length; i++) {
            if (assets[i].equals(setting)) {
                return i;
            }
        }
        return 0;
    }
}