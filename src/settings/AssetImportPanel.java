package settings;

import javax.swing.*;
import java.awt.*;

public class AssetImportPanel extends JPanel {
    public AssetImportPanel() {
        JButton importButton = new JButton("import ...");
        JLabel helpImportLabel = new LinkLabel("file format", "https://ours-agile.com/projets/neko/neko.html");

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(importButton);
        this.add(Box.createRigidArea(new Dimension(50, 0)));
        this.add(helpImportLabel);
    }
}
