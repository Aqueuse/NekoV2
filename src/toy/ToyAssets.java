package toy;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;

import systemTray.MySystemTray;

public class ToyAssets {
    public static BufferedImage toyAssetsImage;

    static {
        try {
            toyAssetsImage = MySystemTray.getAssetFromSettings("toy");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon[] toySprites() {
        return new ImageIcon[]{
                new ImageIcon(toyAssetsImage.getSubimage(0, 0, 32, 32)),
                new ImageIcon(toyAssetsImage.getSubimage(32, 0, 32, 32)),
                new ImageIcon(toyAssetsImage.getSubimage(64, 0, 32, 32)),
                new ImageIcon(toyAssetsImage.getSubimage(96, 0, 32, 32)),
                new ImageIcon(toyAssetsImage.getSubimage(128, 0, 32, 32)),
                new ImageIcon(toyAssetsImage.getSubimage(160, 0, 32, 32))
        };
    }
}
