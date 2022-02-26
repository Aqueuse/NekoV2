package settings;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import javax.imageio.ImageIO;

import init.RessourceFiles;

public class SettingsFileManagement {
    public static String loadKeyFromSettings(String key) {
        String value = "";
        try {
            Scanner scanner = new Scanner(new FileReader(RessourceFiles.userSettingsFile));
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split("=");
                if (line[0].equals(key)) value = line[1];
            }
            scanner.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return value;
    }

    public static void writeSettings(String key, String value) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Objects.requireNonNull(RessourceFiles.userSettingsFile)));
            Object[] settings = bufferedReader.lines().toArray();
            bufferedReader.close();

            for (int i = 0; i < settings.length; i++) {
                if (settings[i].toString().split("=")[0].equals(key)) {
                    settings[i] = key + "=" + value;
                }
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Objects.requireNonNull(RessourceFiles.userSettingsFile), false));

            for (Object setting : settings) {
                bufferedWriter.write(setting.toString() + System.lineSeparator());
            }
            bufferedWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static BufferedImage getAssetFromSettings(String assetType) throws IOException {
        File defaultPetFile = new File(RessourceFiles.userPetsAssetsPath + File.separatorChar + "Neko.png");
        File defaultToyFile = new File(RessourceFiles.userToyAssetsPath + File.separatorChar + "wool.png");

        String asset = loadKeyFromSettings(assetType);

        if (assetType.equals("pet")) {
            File assetFile = new File(RessourceFiles.userPetsAssetsPath + File.separatorChar + asset);
            if (!assetFile.exists()) {
                return ImageIO.read(defaultPetFile);
            } else return ImageIO.read(assetFile);
        } else {
            File assetFile = new File(RessourceFiles.userToyAssetsPath + File.separatorChar + asset);
            if (!assetFile.exists()) {

                return ImageIO.read(defaultToyFile);
            } else return ImageIO.read(assetFile);
        }
    }
}
