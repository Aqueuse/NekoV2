package settings;

import java.awt.*;
import javax.swing.*;

public class SettingsJFrame extends JFrame {
    public SettingsJFrame() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel settingsJpanel = new JPanel();
        settingsJpanel.setLayout(new BoxLayout(settingsJpanel, BoxLayout.PAGE_AXIS));

        JPanel petChooseLabel = singleLineLabelPanel("choose your pet", null, SwingConstants.LEFT);
        ListWithPreviewJPanel petsJPanel = new ListWithPreviewJPanel();
        AssetImportPanel petAssetImportPanel = new AssetImportPanel();

        JCheckBox twitchEnableCheckBox = new JCheckBox("enable twitch integration");

        JPanel toyChooseLabel = singleLineLabelPanel("choose his toy", null, SwingConstants.LEFT);
        ListWithPreviewJPanel toysJPanel = new ListWithPreviewJPanel();
        AssetImportPanel toyAssetImportPanel = new AssetImportPanel();

        JCheckBox betterPhysicCheckBox = new JCheckBox(" true physic for toy (consumes more CPU)");

        JPanel creditLabel = singleLineLabelPanel("credits", null, SwingConstants.CENTER);
        JPanel pyairvanderLabel = singleLineLabelPanel("Rufo assets by Pyairvander", null, SwingConstants.CENTER);
        JPanel pyairvanderLink = singleLineLabelPanel("pierre-vandermaesen.itch.io", "https://pierre-vandermaesen.itch.io/", SwingConstants.CENTER);

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsJpanel.add(petChooseLabel);
        settingsJpanel.add(petsJPanel);
        settingsJpanel.add(petAssetImportPanel);
        settingsJpanel.add(twitchEnableCheckBox);

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsJpanel.add(toyChooseLabel);
        settingsJpanel.add(toysJPanel);
        settingsJpanel.add(toyAssetImportPanel);
        settingsJpanel.add(betterPhysicCheckBox);

        settingsJpanel.add(Box.createVerticalGlue());
        settingsJpanel.add(creditLabel);
        settingsJpanel.add(pyairvanderLabel);
        settingsJpanel.add(pyairvanderLink);

        this.getContentPane().add(settingsJpanel);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
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

        switch (swingConstants) { // left
            case 2, default -> { // default is left
                jPanel.add(jLabel);
                jPanel.add(Box.createHorizontalGlue());
            }
            case 0 -> { // center
                jPanel.add(Box.createRigidArea(new Dimension(100, 0)));
                jPanel.add(jLabel);
            }
            case 4 -> { // right
                jPanel.add(Box.createHorizontalGlue());
                jPanel.add(jLabel);
            }
        }
        return jPanel;
    }
}
