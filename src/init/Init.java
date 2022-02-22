package init;

import java.awt.*;
import java.io.File;

import pet.Pet;
import systemTray.MySystemTray;

public class Init {
    public static String petAssetsPath = new File(Init.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separatorChar + "pet/" + File.separatorChar + "images" + File.separatorChar).getAbsolutePath();
    public static String toyAssetsPath = new File(Init.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separatorChar + "toy/" + File.separatorChar + "images" + File.separatorChar).getAbsolutePath();

    public static File defaultPetFile = new File(petAssetsPath + File.separatorChar + "Neko.png");
    public static File defaultToyFile = new File(toyAssetsPath + File.separatorChar + "wool.png");

    public static Pet myNeko;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new MySystemTray();
            myNeko = new Pet();
        });
    }

}
