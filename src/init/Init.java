package init;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;

import pet.Pet;
import settings.SettingsFileManagement;
import systemTray.MySystemTray;
import twitchInteraction.TwitchListen;

public class Init {
    public static Pet myNeko;
    public static TwitchListen twitchListen;

    public static String applicationLocation = getApplicationPath();
    public static File settingsFile = new File(applicationLocation, "settings.txt");
    static File assetsDirectory = new File(applicationLocation, "assets");

    public static String petsAssetsPath = new File(assetsDirectory, "pets").getAbsolutePath();
    public static String toysAssetsPath = new File(assetsDirectory, "toys").getAbsolutePath();

    public static int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }
    public static int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }

    public static String getApplicationPath() {
        try {
            return new File(Init.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
        }
        catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            twitchListen = new TwitchListen(SettingsFileManagement.loadKeyFromSettings("twitchChannelId"));
            new MySystemTray();
            myNeko = new Pet();
        });
    }

}
