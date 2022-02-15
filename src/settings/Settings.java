package settings;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

import neko.Neko;
import toy.Toy;

public class Settings {
    public static String loadKeyFromSettings(String key) {
        String value = "";

        try {
            File settingsFile = new File(Objects.requireNonNull(Neko.class.getProtectionDomain().getClassLoader().getResource("settings.txt")).getPath());

            Scanner scanner = new Scanner(new FileReader(settingsFile));
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(":");
                if (line[0].equals(key)) value = line[1];
            }
            scanner.close();
        }
        catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return value;
    }

        public void writeSettings(String key, String value) throws IOException {
        File settingsFile = new File(Objects.requireNonNull(Neko.class.getProtectionDomain().getClassLoader().getResource("settings.txt")).getPath());

        BufferedReader bufferedReader = new BufferedReader(new FileReader(Objects.requireNonNull(settingsFile)));
        Object[] settings = bufferedReader.lines().toArray();
        bufferedReader.close();

        for (int i=0; i<settings.length; i++) {
            if (settings[i].toString().split(":")[0].equals(key)) {
                settings[i] = key+":"+value;
            }
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Objects.requireNonNull(settingsFile), false));

        for (int i=0; i<settings.length; i++) {
            bufferedWriter.write(settings[i].toString()+System.lineSeparator());
        }
        bufferedWriter.close();
    }

    public BufferedImage getAssetFromSettings(String assetType) {
        try {
            String asset = loadKeyFromSettings(assetType);
            if (assetType.equals("toy")) return ImageIO.read(new File(Objects.requireNonNull(Toy.class.getResource("images/" + asset)).toURI()));
            else return ImageIO.read(new File(Objects.requireNonNull(Neko.class.getResource("images/" + asset)).toURI()));
        }
        catch (IOException | URISyntaxException exception) {
            System.out.println(exception);
        }
        return null;
    }
}