package systemTray;

import init.Init;
import pet.PetAssets;
import settings.SettingsJFrame;
import toy.Toy;
import twitchInteraction.TwitchListen;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;

import static settings.SettingsFileManagement.getAssetFromSettings;
import static settings.SettingsFileManagement.loadKeyFromSettings;

public class MySystemTray {
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
            Init.myNeko.basketReached = false;
            Init.myNeko.setVisible(true);
            Init.twitchListen.setActivated(false);
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
                Init.twitchListen = new TwitchListen(loadKeyFromSettings("twitchChannelId"));
                Init.twitchListen.setActivated(true);
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
}