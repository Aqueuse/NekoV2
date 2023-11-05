package init;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import pet.Pet;
import settings.SettingsFileManagement;
import systemTray.MySystemTray;
import twitchInteraction.TwitchListen;
import static init.RessourceFiles.*;

public class Init {
    public static Pet myNeko;
    public static TwitchListen twitchListen;
    public static MySystemTray mySystemTray;

    static void copyMissingRessources() {
        try {
            Files.createDirectory(Path.of(RessourceFiles.userSettingsFolder.getAbsolutePath()));
            Files.createDirectory(Path.of(RessourceFiles.userAssetsFolder.getAbsolutePath()));
            Files.createDirectory(Path.of(userPetsAssetsPath));
            Files.createDirectory(Path.of(userToyAssetsPath));

            Files.copy(
                    Path.of(sharedSettingsFile.getAbsolutePath()),
                    Path.of(userSettingsFolder.getAbsolutePath(), sharedSettingsFile.getName()));

            for (File sharedPetsAsset : sharedPetsAssets) {
                Files.copy(
                        Path.of(sharedPetsAsset.getAbsolutePath()),
                        Path.of(userPetsAssetsPath, sharedPetsAsset.getName()));
            }

            for (File sharedToysAsset : sharedToysAssets) {
                Files.copy(
                        Path.of(sharedToysAsset.getAbsolutePath()),
                        Path.of(userToyAssetsPath, sharedToysAsset.getName()));
            }
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            if (!userSettingsFolder.exists()) copyMissingRessources();
            twitchListen = new TwitchListen(SettingsFileManagement.loadKeyFromSettings("twitchChannelId"));
            mySystemTray = new MySystemTray();
            myNeko = new Pet();
        });
    }
}
