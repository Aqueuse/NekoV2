package neko;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class NekoAssets {
    // settings the default pet to Neko
    public static BufferedImage assetsImage = loadPetAssets("Neko.png");

    public static BufferedImage loadPetAssets(String currentPet) {
        try {
            assetsImage = ImageIO.read(Objects.requireNonNull(Neko.class.getResource("images/" + currentPet)));
        } catch (IOException ioException) {
            System.out.println("file not found");
        }
        return assetsImage;
    }

    public static ImageIcon[] animateLeft = {
            // new ImageIcon(assetsImage.getSubimage(0, 0, 32, 32)),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "left1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "left2.GIF")))
    };
    public static ImageIcon[] animateRight = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "right1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "right2.GIF")))
    };
    public static ImageIcon[] animateTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "top1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "top2.GIF")))
    };
    public static ImageIcon[] animateBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "bottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "bottom2.GIF")))
    };
    public static ImageIcon[] animateLeftTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftTop1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftTop2.GIF")))
    };
    public static ImageIcon[] animateLeftBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftBottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftBottom2.GIF")))
    };
    public static ImageIcon[] animateRightTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightTop1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightTop2.GIF")))
    };
    public static ImageIcon[] animateRightBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightBottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightBottom2.GIF")))
    };

    public static ImageIcon[] animateScratchLeft = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchLeft1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchLeft2.GIF")))
    };
    public static ImageIcon[] animateScratchRight = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchRight1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchRight2.GIF")))
    };
    public static ImageIcon[] animateScratchTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchTop1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchTop2.GIF")))
    };
    public static ImageIcon[] animateScratchBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchBottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchBottom2.GIF")))
    };

    public static ImageIcon[] animatePrepareToSleep = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "baille.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "baille.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "baille.GIF")))
    };
    public static ImageIcon[] animateSleep = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF")))
    };

}
