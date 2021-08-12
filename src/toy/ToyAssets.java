package toy;

import javax.swing.*;
import java.util.Objects;

public class ToyAssets {
    public static ImageIcon[] toySprites = {
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool1.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool2.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool3.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool4.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool5.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool6.png"))),
    };
}
