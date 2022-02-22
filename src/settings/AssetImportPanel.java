package settings;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static systemTray.MySystemTray.petAssetsPath;
import static systemTray.MySystemTray.toyAssetsPath;

public class AssetImportPanel extends JPanel {
    public AssetImportPanel(String assetsDirectory, String assetType) {
        JButton importAssetButton = new JButton("import");
        JLabel feedbackLabel = new JLabel();
        JLabel helpImportLabel = new LinkLabel("file format", "https://ours-agile.com/projets/neko/neko.html");

        importAssetButton.addActionListener(e -> {
            JFileChooser assetJFileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Image", "png");
            assetJFileChooser.setFileFilter(filter);
            int returnVal = assetJFileChooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File assetFile = new File(assetJFileChooser.getSelectedFile().getAbsolutePath());
                String copyResult = copyAssetToAssetDirectory(assetFile, assetType);
                if (assetType.equals("pet")) SettingsJFrame.petsJPanel.repopulatedAssetsJList(assetsDirectory);
                if (assetType.equals("toy")) SettingsJFrame.toysJPanel.repopulatedAssetsJList(assetsDirectory);
                feedbackLabel.setText(copyResult);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(importAssetButton);
        this.add(Box.createRigidArea(new Dimension(50, 0)));
        this.add(feedbackLabel);
        this.add(helpImportLabel);
        this.add(Box.createRigidArea(new Dimension(100, 0)));
    }

    public String copyAssetToAssetDirectory(File originalFile, String assetType) {
        try {
            Path originalPath = Path.of(originalFile.getAbsolutePath());
            String assetFilename = originalPath.getFileName().toString();

            BufferedImage assetBufferedImage = ImageIO.read(originalFile);
            Path assetPath = null;

            if (assetType.equals("pet")) {
                assetPath = Path.of(new File(petAssetsPath+File.separatorChar+assetFilename).toURI());
            }
            if (assetType.equals("toy")) {
                assetPath = Path.of(new File(toyAssetsPath+File.separatorChar+assetFilename).toURI());
            }

            if (isAssetDimensionValid(assetBufferedImage, assetType)) {
                assert assetPath != null;
                Files.copy(originalPath, assetPath, StandardCopyOption.REPLACE_EXISTING);
                return "success";
            }
            else return "the assets dimensions are not valid";
        }
        catch (IOException ioException) {
            System.out.println(ioException);
        }
        return "not a png file";
    }

    public boolean isAssetDimensionValid(BufferedImage assetImage, String assetType) {
        Dimension assetDimension = new Dimension();

        if (assetType.equals("pet")) assetDimension  = new Dimension(128, 256);
        if (assetType.equals("toy")) assetDimension  = new Dimension(192, 32);

        return assetImage.getWidth() == assetDimension.width && assetImage.getHeight() == assetDimension.height;
    }
}
