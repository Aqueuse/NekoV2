package settings;

import java.awt.*;
import javax.swing.*;

public class TwitchJPanel extends JPanel {
    public static int channelID = 0;
    public TwitchJPanel() {
        JCheckBox twitchEnableCheckBox = new JCheckBox("enable twitch integration");

        JPanel twitchChannelPanel = new JPanel();
            JLabel twitchChannelLabel = new JLabel("twitch channel ID");
            JTextField twitchChannelTextField = new JTextField(SettingsFileManagement.loadKeyFromSettings("twitchChannelId"));
            JButton saveButton = new JButton("save");

        JPanel twitchLabelsPanel = new JPanel();
            LinkLabel twitchIDLabel = new LinkLabel("get my channel ID", "https://chrome.google.com/webstore/detail/twitch-username-and-user/laonpoebfalkjijglbjbnkfndibbcoon");
            JLabel messageLabel = new JLabel();

        if (SettingsFileManagement.loadKeyFromSettings("twitchEnabled").equals("true")) {
            twitchEnableCheckBox.setSelected(true);
        }
        if (SettingsFileManagement.loadKeyFromSettings("twitchEnabled").equals("false")) {
            twitchChannelTextField.setEditable(false);
        }

        twitchChannelTextField.setColumns(10);

        twitchEnableCheckBox.addActionListener(actionEvent -> {
            if (twitchEnableCheckBox.isSelected()) {
                SettingsFileManagement.writeSettings("twitchEnabled", "true");
                twitchChannelTextField.setEditable(true);
            }
            if (!twitchEnableCheckBox.isSelected()) {
                SettingsFileManagement.writeSettings("twitchEnabled", "false");
                twitchChannelTextField.setEditable(false);
            }
        });

        saveButton.addActionListener(e -> {
            if (isChannelIdInteger(twitchChannelTextField.getText())) {
                SettingsFileManagement.writeSettings("twitchChannelId", twitchChannelTextField.getText());
                messageLabel.setText("");
            }
            else {
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("not a channel ID");
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.add(twitchEnableCheckBox);
            twitchChannelPanel.add(twitchChannelLabel);
            twitchChannelPanel.add(twitchChannelTextField);
            twitchChannelPanel.add(saveButton);
        this.add(twitchChannelPanel);
            twitchLabelsPanel.add(twitchIDLabel);
            twitchLabelsPanel.add(messageLabel);
        this.add(twitchLabelsPanel);

        this.add(Box.createRigidArea(new Dimension(20, 0)));
    }

    boolean isChannelIdInteger(String uncheckedChannelID) {
        if (uncheckedChannelID == null) {
            return false;
        }
        try {
            Integer.parseInt(uncheckedChannelID);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}

