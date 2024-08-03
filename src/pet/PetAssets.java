package pet;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import settings.SettingsFileManagement;

public class PetAssets {
    public static BufferedImage petAssetsImage;

    static ImageIcon[] animateLeftSprites;
    static ImageIcon[] animateRightSprites;
    static ImageIcon[] animateTopSprites;
    static ImageIcon[] animateBottomSprites;
    static ImageIcon[] animateLeftTopSprites;
    static ImageIcon[] animateLeftBottomSprites;
    static ImageIcon[] animateRightTopSprites;
    static ImageIcon[] animateRightBottomSprites;

    static ImageIcon[] scratchLeftSprites;
    static ImageIcon[] scratchTopSprites;
    static ImageIcon[] scratchRightSprites;
    static ImageIcon[] scratchBottomSprites;

    static ImageIcon[] prepareToSleepSprites;
    static ImageIcon[] sleepSprites;

    public static void reloadAssets() {
        animateLeftSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 96, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 96, 32, 32))
        };

        animateRightSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 96, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 96, 32, 32))
        };

        animateTopSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 64, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 64, 32, 32))
        };

        animateBottomSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 64, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 64, 32, 32))
        };

        animateLeftTopSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 128, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 128, 32, 32))
        };

        animateLeftBottomSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 160, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 160, 32, 32))
        };

        animateRightTopSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 128, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 128, 32, 32))
        };

        animateRightBottomSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 160, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 160, 32, 32))
        };;

        scratchLeftSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(0, 224, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(32, 224, 32, 32))
        };

        scratchTopSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(32, 192, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(0, 192, 32, 32)) // inverted but is okay
        };

        scratchRightSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(64, 224, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(96, 224, 32, 32))
        };

        scratchBottomSprites = new ImageIcon[]{
                new ImageIcon(petAssetsImage.getSubimage(96, 192, 32, 32)),
                new ImageIcon(petAssetsImage.getSubimage(64, 192, 32, 32))
        };

        prepareToSleepSprites = new ImageIcon[]{
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

        sleepSprites = new ImageIcon[]{
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

    static {
        try {
            petAssetsImage = SettingsFileManagement.getAssetFromSettings("pet");
        }
        catch (IOException ioException) {
            System.out.println(ioException);
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
}
