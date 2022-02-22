package settings;

import java.awt.*;
import javax.swing.*;

import pet.Pet;
import systemTray.MySystemTray;
import static systemTray.MySystemTray.petAssetsPath;
import static systemTray.MySystemTray.toyAssetsPath;

public class SettingsJFrame extends JFrame {
    public static ListWithPreviewJPanel petsJPanel = new ListWithPreviewJPanel(petAssetsPath, "pet");
    public static ListWithPreviewJPanel toysJPanel = new ListWithPreviewJPanel(toyAssetsPath, "toy");

    public SettingsJFrame() {
        int FPS_MIN = 0;
        int FPS_MAX = 600;
        int FPS_INIT = Integer.parseInt(MySystemTray.loadKeyFromSettings("petDelay"));

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel settingsJpanel = new JPanel();
        settingsJpanel.setLayout(new BoxLayout(settingsJpanel, BoxLayout.PAGE_AXIS));

        JPanel petChooseLabel = singleLineLabelPanel("choose your pet", null, SwingConstants.LEFT);
        AssetImportPanel petAssetImportPanel = new AssetImportPanel(petAssetsPath, "pet");

        JCheckBox twitchEnableCheckBox = new JCheckBox("enable twitch integration");

        JPanel fpsLabel = singleLineLabelPanel("pet speed", null, SwingConstants.CENTER);
        JSlider fpsSlider = new JSlider(JSlider.HORIZONTAL, FPS_MIN, FPS_MAX, FPS_INIT);

        JPanel toyChooseLabel = singleLineLabelPanel("choose his toy", null, SwingConstants.LEFT);
        AssetImportPanel toyAssetImportPanel = new AssetImportPanel(toyAssetsPath, "toy");

        JPanel creditLabel = singleLineLabelPanel("credits", null, SwingConstants.CENTER);
        JPanel pyairvanderLabel = singleLineLabelPanel("Rufo assets by Pyairvander", null, SwingConstants.CENTER);
        JPanel pyairvanderLink = singleLineLabelPanel("pierre-vandermaesen.itch.io", "https://pierre-vandermaesen.itch.io/", SwingConstants.CENTER);

        JPanel hbellahcLabel = singleLineLabelPanel("Dynamic Neko refresh by Hbellahc", null, SwingConstants.CENTER);
        JPanel hbellahcLink = singleLineLabelPanel("https://github.com/hbellahc", "https://github.com/hbellahc", SwingConstants.CENTER);

        fpsSlider.setInverted(true);
        fpsSlider.setValue(FPS_INIT);

        fpsSlider.addChangeListener(e -> {
            JSlider source = (JSlider)e.getSource();
            if (!source.getValueIsAdjusting()) {
                Pet.timer.setDelay(source.getValue());
                MySystemTray.writeSettings("petDelay", String.valueOf(source.getValue()));
            }
        });

        if (MySystemTray.loadKeyFromSettings("twitchEnabled").equals("true")) {
            twitchEnableCheckBox.setSelected(true);
        }

        twitchEnableCheckBox.addActionListener(actionEvent -> {
            if (twitchEnableCheckBox.isSelected()) {
                MySystemTray.writeSettings("twitchEnabled", "true");
                Pet.mySystemTray = new MySystemTray();
            }
            if (!twitchEnableCheckBox.isSelected()) {
                MySystemTray.writeSettings("twitchEnabled", "false");
                Pet.mySystemTray = new MySystemTray();
            }
        });

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsJpanel.add(petChooseLabel);
        settingsJpanel.add(petsJPanel);
        settingsJpanel.add(petAssetImportPanel);
        settingsJpanel.add(twitchEnableCheckBox);
        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsJpanel.add(fpsLabel);
        settingsJpanel.add(fpsSlider);

        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 20)));
        settingsJpanel.add(toyChooseLabel);
        settingsJpanel.add(toysJPanel);
        settingsJpanel.add(toyAssetImportPanel);

        settingsJpanel.add(Box.createVerticalGlue());
        settingsJpanel.add(creditLabel);
        settingsJpanel.add(pyairvanderLabel);
        settingsJpanel.add(pyairvanderLink);
        settingsJpanel.add(hbellahcLabel);
        settingsJpanel.add(hbellahcLink);
        settingsJpanel.add(Box.createRigidArea(new Dimension(0, 10)));

        this.getContentPane().add(settingsJpanel);
        this.setSize(400,620);
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
