package toy;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ToyAssets {
    public static BufferedImage assetsImage;
    public static void loadToyAssets(String currentToy) {
        try {
            assetsImage = ImageIO.read(Objects.requireNonNull(Toy.class.getResource("images/" + currentToy)));
        } catch (IOException ioException) {
            System.out.println("file not found");
        }
    }
    public static ImageIcon[] toySprites = {
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool1.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool2.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool3.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool4.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool5.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool6.png"))),
    };
}
