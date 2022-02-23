package init;

import java.awt.*;
import java.io.File;

import pet.Pet;
import systemTray.MySystemTray;

public class Init {
    public static Pet myNeko;

    public static String getPetAssetsPath() {
        return new File(Init.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separatorChar + "pet/" + File.separatorChar + "images" + File.separatorChar).getAbsolutePath();
    }

    public static String getToyAssetsPath() {
        return new File(Init.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separatorChar + "toy/" + File.separatorChar + "images" + File.separatorChar).getAbsolutePath();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new MySystemTray();
            myNeko = new Pet();
        });
    }

}
