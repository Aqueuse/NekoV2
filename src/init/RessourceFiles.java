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

    public static final File userHomeFolder = new File(System.getProperty("user.home"));
    public static final File userSettingsFolder = new File(userHomeFolder, ".neko");

    public static final File userAssetsFolder = new File(userSettingsFolder, "assets");
    public static final File userSettingsFile = new File(userSettingsFolder, "settings.txt");

    public static final String userPetsAssetsPath = new File(userAssetsFolder, "pets").getAbsolutePath();
    public static final String userToyAssetsPath = new File(userAssetsFolder, "toys").getAbsolutePath();

    public static final File sharedAssetFolder = new File(getApplicationPath(), "assets");
    public static final File sharedSettingsFile = new File(getApplicationPath(), "settings.txt");

    public static final File sharedPetsAssetsFolder = new File(sharedAssetFolder, "pets");
    public static final File sharedToysAssetsFolder = new File(sharedAssetFolder, "toys");

    public static final File[] sharedPetsAssets = new File[] {
            new File(sharedPetsAssetsFolder, "Neko.png"),
            new File(sharedPetsAssetsFolder, "Rufo.png"),
            new File(sharedPetsAssetsFolder, "NekoDark.png"),
            new File(sharedPetsAssetsFolder, "RufoDark.png")
    };

    public static final File[] sharedToysAssets = new File[] {
            new File(sharedToysAssetsFolder, "wool.png"),
            new File(sharedToysAssetsFolder, "darkWool.png"),
            new File(sharedToysAssetsFolder, "boomerang.png")
    };
}
