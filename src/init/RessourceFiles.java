package init;

import java.io.File;
import java.net.URISyntaxException;

public class RessourceFiles {
    public static String getApplicationPath() {
        try {
            return new File(Init.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getPath();
        }
        catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
        return null;
    }

    public static File userHomeFolder = new File(System.getProperty("user.home"));
    public static File userSettingsFolder = new File(userHomeFolder, ".neko");

    public static File userAssetsFolder = new File(userSettingsFolder, "assets");
    public static File userSettingsFile = new File(userSettingsFolder, "settings.txt");

    public static String userPetsAssetsPath = new File(userAssetsFolder, "pets").getAbsolutePath();
    public static String userToyAssetsPath = new File(userAssetsFolder, "toys").getAbsolutePath();

    public static File sharedAssetFolder = new File(getApplicationPath(), "assets");
    public static File sharedSettingsFile = new File(getApplicationPath(), "settings.txt");

    public static File sharedPetsAssetsFolder = new File(sharedAssetFolder, "pets");
    public static File sharedToysAssetsFolder = new File(sharedAssetFolder, "toys");

    public static File[] sharedPetsAssets = new File[] {
            new File(sharedPetsAssetsFolder, "Neko.png"),
            new File(sharedPetsAssetsFolder, "Rufo.png"),
            new File(sharedPetsAssetsFolder, "NekoDark.png"),
            new File(sharedPetsAssetsFolder, "RufoDark.png")
    };

    public static File[] sharedToysAssets = new File[] {
            new File(sharedToysAssetsFolder, "wool.png"),
            new File(sharedToysAssetsFolder, "darkWool.png"),
            new File(sharedToysAssetsFolder, "boomerang.png")
    };
}
