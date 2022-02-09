package settings;

import javax.swing.*;
import java.awt.*;

public class Settings extends JFrame {
    JPanel settingsJpanel = new JPanel();

    public Settings() {
        pack();
        getRootPane().add(settingsJpanel);
        
        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
