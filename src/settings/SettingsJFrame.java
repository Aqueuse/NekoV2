package settings;

import java.awt.*;
import javax.swing.*;

import init.RessourceFiles;
import pet.Pet;

public class SettingsJFrame extends JFrame {
    public static ListWithPreviewJPanel petsListWithPreviewJPanel;
    public static ListWithPreviewJPanel toysListWithPreviewJPanel;

    public SettingsJFrame() {
        int FPS_MIN = 0;
        int FPS_MAX = 600;
        int FPS_INIT = Integer.parseInt(SettingsFileManagement.loadKeyFromSettings("petDelay"));

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel settingsJpanel = new JPanel();
        settingsJpanel.setLayout(new BoxLayout(settingsJpanel, BoxLayout.PAGE_AXIS));

        JPanel petAssetsPanel = new JPanel();
            petsListWithPreviewJPanel = new ListWithPreviewJPanel(RessourceFiles.userPetsAssetsPath, "pet");
            AssetImportPanel petAssetImportPanel = new AssetImportPanel(RessourceFiles.userPetsAssetsPath, "pet");
        JLabel nekoImportLabel = new LinkLabel("file format", "https://ours-agile.com/projets/neko/neko.html");

        JPanel fpsLabel = singleLineLabelPanel("pet speed", null, SwingConstants.CENTER);
        JSlider fpsSlider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

        TwitchJPanel twitchJPanel = new TwitchJPanel();

        JPanel toyAssetsPanel = new JPanel();
            AssetImportPanel toyAssetImportPanel = new AssetImportPanel(RessourceFiles.userToyAssetsPath, "toy");
            toysListWithPreviewJPanel = new ListWithPreviewJPanel(RessourceFiles.userToyAssetsPath, "toy");
        JLabel toyImportLabel = new LinkLabel("file format", "https://ours-agile.com/projets/neko/neko.html");

        JPanel creditLabel = singleLineLabelPanel("credits", null, SwingConstants.CENTER);
        JPanel pyairvanderLabel = singleLineLabelPanel("Rufo assets by Pyairvander", null, SwingConstants.CENTER);
        JPanel pyairvanderLink = singleLineLabelPanel("pierre-vandermaesen.itch.io", "https://pierre-vandermaesen.itch.io/", SwingConstants.CENTER);

        fpsSlider.setValue(FPS_INIT);

        fpsSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                Pet.timer.setDelay(600-source.getValue());
                SettingsFileManagement.writeSettings("petDelay", String.valueOf(600-source.getValue()));
            }
        });

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 10)));

            petAssetsPanel.add(petAssetImportPanel);
            petAssetsPanel.add(petsListWithPreviewJPanel);
        settingsJpanel.add(petAssetsPanel);
        settingsJpanel.add(nekoImportLabel);
        settingsJpanel.add(twitchJPanel);

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 20)));

        settingsJpanel.add(fpsLabel);
        settingsJpanel.add(fpsSlider);

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 20)));

            toyAssetsPanel.add(toyAssetImportPanel);
            toyAssetsPanel.add(toysListWithPreviewJPanel);
        settingsJpanel.add(toyAssetsPanel);
        settingsJpanel.add(toyImportLabel);

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsJpanel.add(creditLabel);
        settingsJpanel.add(pyairvanderLabel);
        settingsJpanel.add(pyairvanderLink);
        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.getContentPane().add(settingsJpanel);
        this.setSize(400,800);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(false);
    }

    public JPanel singleLineLabelPanel(String text, String link, int swingConstants) {
        JPanel jPanel = new JPanel();
        JLabel jLabel;
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.X_AXIS));

        if (link == null) {
            jLabel = new JLabel(text);
        }
        else {
            jLabel = new LinkLabel(text, link);
        }

        switch (swingConstants) {
            case 2 -> { // left
                jPanel.add(Box.createRigidArea(new Dimension(20, 0)));
                jPanel.add(jLabel);
                jPanel.add(Box.createHorizontalGlue());
            }
            case 0 -> { // center
                jPanel.add(Box.createRigidArea(new Dimension(50, 0)));
                jPanel.add(jLabel);
            }
            case 4 -> { // right
                jPanel.add(Box.createHorizontalGlue());
                jPanel.add(jLabel);
                jPanel.add(Box.createRigidArea(new Dimension(20, 0)));
            }
        }
        return jPanel;
    }
}
