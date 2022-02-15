package toy;

import neko.Neko;

import java.awt.image.BufferedImage;
import javax.swing.*;

public class ToyAssets {
    public static BufferedImage toyAssetsImage = Neko.settings.getAssetFromSettings("toy");

    public static ImageIcon[] toySprites() {
        toyAssetsImage = Neko.settings.getAssetFromSettings("toy");
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
