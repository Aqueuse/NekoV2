package pet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import systemTray.MySystemTray;

public class PetAssets {
    public static BufferedImage petAssetsImage;

    static {
        try {
            petAssetsImage = MySystemTray.getAssetFromSettings("pet");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image preview() {
        return petAssetsImage.
                getSubimage(0, 0, 32, 32).
                getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    }

    public static Image basket() {
        return petAssetsImage.getSubimage(96, 32, 32, 32).
                getScaledInstance(16, 16, Image.SCALE_SMOOTH);
    }

    public static ImageIcon[] animateLeft() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 96, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 96, 32, 32))
        };
    }

    public static ImageIcon[] animateRight() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 96, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 96, 32, 32))
        };
    }

    public static ImageIcon[] animateTop() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 64, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 64, 32, 32))
        };
    }

    public static ImageIcon[] animateBottom() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 64, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 64, 32, 32))
        };
    }

    public static ImageIcon[] animateLeftTop() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 128, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 128, 32, 32))
        };
    }

    public static ImageIcon[] animateLeftBottom() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 160, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 160, 32, 32))
        };
    }

    public static ImageIcon[] animateRightTop() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 128, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 128, 32, 32))
        };
    }

    public static ImageIcon[] animateRightBottom() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 160, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 160, 32, 32))
        };
    }

    public static ImageIcon[] animateScratchLeft() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 224, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 224, 32, 32))
        };
    }

    public static ImageIcon[] animateScratchRight() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 224, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 224, 32, 32))
        };
    }

    public static ImageIcon[] animateScratchTop() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(32, 192, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(0, 192, 32, 32)) // inverted but is okay
        };
    }

    public static ImageIcon[] animateScratchBottom() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(96, 192, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 192, 32, 32))
        };
    }

    public static ImageIcon[] animatePrepareToSleep() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(32, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(0, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(0, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 0, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(0, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(0, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(0, 32, 32, 32))
        };
    }

    public static ImageIcon[] animateSleep() {
        return new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 32, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 32, 32, 32))
        };
    }
}
