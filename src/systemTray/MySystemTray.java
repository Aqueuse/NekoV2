package systemTray;

import pet.PetAssets;
import settings.SettingsJFrame;
import toy.Toy;
import pet.Pet;
import twitchInteraction.TwitchListen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class MySystemTray {
    public static File settingsFile = new File(Objects.requireNonNull(MySystemTray.class.getProtectionDomain().getClassLoader().getResource("settings.txt")).getPath());
    public static String petAssetsPath = new File(MySystemTray.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separatorChar + "pet/" + File.separatorChar + "images" + File.separatorChar).getAbsolutePath();
    public static String toyAssetsPath = new File(MySystemTray.class.getProtectionDomain().getCodeSource().getLocation().getPath() + File.separatorChar + "toy/" + File.separatorChar + "images" + File.separatorChar).getAbsolutePath();

    static File defaultPetFile = new File(petAssetsPath + File.separatorChar + "Neko.png");
    static File defaultToyFile = new File(toyAssetsPath + File.separatorChar + "wool.png");

    final static SystemTray tray = SystemTray.getSystemTray();
    public static TrayIcon trayIcon = null;

    static PopupMenu popup = new PopupMenu();

    CheckboxMenuItem twitchListenItem = new CheckboxMenuItem("chase the red pointer");
    CheckboxMenuItem chaseMouseItem = new CheckboxMenuItem("chase the mouse");
    CheckboxMenuItem catchToyItem = new CheckboxMenuItem("catch the toy");
    CheckboxMenuItem sleepToTheBasketItem = new CheckboxMenuItem("Sleep to the basket");
    CheckboxMenuItem independentItem = new CheckboxMenuItem("independent kitten");

    CheckboxMenuItem settingsItem = new CheckboxMenuItem("settings");
    MenuItem exitItem = new MenuItem("Exit");

    CheckboxMenuItem[] menuItems = {twitchListenItem, catchToyItem, sleepToTheBasketItem, chaseMouseItem, independentItem, settingsItem};

    SettingsJFrame settingsJFrame = new SettingsJFrame();

    public static int systemTrayXcoordinates;
    public static int systemTrayYcoordinates;

    Toy newToy;

    public enum KittyState {
        CHASE,
        CATCH,
        AUTONOM,
        IDLE,
        SLEEP,
        LISTEN
    }

    public static KittyState kittyState = KittyState.AUTONOM;

    public MySystemTray() {
        TrayIcon[] trayIcons = tray.getTrayIcons();

        for (TrayIcon trayIcon : trayIcons) {
            tray.remove(trayIcon);
        }

        trayIcon = new TrayIcon(PetAssets.preview());

        twitchListenItem.addItemListener(buttonsListener);
        catchToyItem.addItemListener(buttonsListener);
        sleepToTheBasketItem.addItemListener(buttonsListener);
        chaseMouseItem.addItemListener(buttonsListener);
        independentItem.addItemListener(buttonsListener);

        settingsItem.addItemListener(buttonsListener);
        exitItem.addActionListener(exitListener);

        independentItem.setState(true);
        settingsJFrame.setVisible(false);

        setSystemTrayIcons(loadKeyFromSettings("twitchEnabled").equals("true"));

        trayIcon.setPopupMenu(popup);
        try {
            PetAssets.petAssetsImage = getAssetFromSettings("pet");
            trayIcon.setImage(PetAssets.preview());
            tray.add(trayIcon);
        } catch (AWTException | IOException exception) {
            exception.printStackTrace();
        }
    }

    ItemListener buttonsListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            Pet.myNeko.basketReached = false;
            Pet.myNeko.setVisible(true);
            TwitchListen.twitchListen(false);
            if (newToy != null) newToy.dispose();
            Toy.catched = true;

            trayIcon.setImage(PetAssets.preview());

            if (e.getSource() == chaseMouseItem) {  // chase the mouse
                kittyState = KittyState.CHASE;
                setItemState(chaseMouseItem);
            }
            if (e.getSource() == catchToyItem) {  // catch the toy
                kittyState = KittyState.CATCH;
                setItemState(catchToyItem);

                Toy.catched = false;
                newToy = new Toy();
            }
            if (e.getSource() == sleepToTheBasketItem) {  // go sleep in the basket
                PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                Point mouseLocation = pointerInfo.getLocation();
                Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

                if (mouseLocation.x <= size.width / 2) {
                    systemTrayXcoordinates = 32;
                }
                if (mouseLocation.x > size.width / 2) {
                    systemTrayXcoordinates = size.width - 32;
                }
                if (mouseLocation.y <= size.height / 2) {
                    systemTrayYcoordinates = 32;
                }
                if (mouseLocation.y > size.height / 2) {
                    systemTrayYcoordinates = size.height - 32;
                }

                kittyState = KittyState.SLEEP;
                setItemState(sleepToTheBasketItem);
            }
            if (e.getSource() == independentItem) { // do what you want, don't chase the mouse
                kittyState = KittyState.AUTONOM;
                setItemState(independentItem);
            }
            if (e.getSource() == twitchListenItem) {
                kittyState = KittyState.LISTEN;
                setItemState(twitchListenItem);
                TwitchListen.twitchListen(true);
            }
            if (e.getSource() == settingsItem && !settingsJFrame.isVisible()) {
                settingsJFrame = new SettingsJFrame();
                settingsJFrame.setVisible(true);
            }
        }
    };

    ActionListener exitListener = e -> {
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
    };

    public void setSystemTrayIcons(boolean isTwitchEnabled) {
        popup.removeAll();
        if (isTwitchEnabled) {
            popup.add(twitchListenItem);
            popup.addSeparator();
        }
        popup.add(catchToyItem);
        popup.add(sleepToTheBasketItem);
        popup.add(chaseMouseItem);
        popup.add(independentItem);
        popup.addSeparator();
        popup.add(settingsItem);
        popup.add(exitItem);
    }

    public void setItemState(CheckboxMenuItem item) {
        for (CheckboxMenuItem menuItem : menuItems) {
            menuItem.setState(false);
        }
        item.setState(true);
    }

    public static String loadKeyFromSettings(String key) {
        String value = "";
        try {
            Scanner scanner = new Scanner(new FileReader(settingsFile));
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(":");
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
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Objects.requireNonNull(settingsFile)));
            Object[] settings = bufferedReader.lines().toArray();
            bufferedReader.close();

            for (int i = 0; i < settings.length; i++) {
                if (settings[i].toString().split(":")[0].equals(key)) {
                    settings[i] = key + ":" + value;
                }
            }

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Objects.requireNonNull(settingsFile), false));

            for (Object setting : settings) {
                bufferedWriter.write(setting.toString() + System.lineSeparator());
            }
            bufferedWriter.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static BufferedImage getAssetFromSettings(String assetType) throws IOException {
        String asset = loadKeyFromSettings(assetType);

        if (assetType.equals("pet")) {
            File assetFile = new File(petAssetsPath + File.separatorChar + asset);
            if (!assetFile.exists()) {
                return ImageIO.read(defaultPetFile);
            } else return ImageIO.read(assetFile);
        } else {
            File assetFile = new File(toyAssetsPath + File.separatorChar + asset);
            if (!assetFile.exists()) {
                return ImageIO.read(defaultToyFile);
            } else return ImageIO.read(assetFile);
        }
    }
}