package systemTray;

import settings.Settings;
import toy.Toy;
import neko.Neko;
import twitchInteraction.TwitchListen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class MySystemTray {
    final static SystemTray tray = SystemTray.getSystemTray();

    public static TrayIcon trayIconNormal = null;
    public static TrayIcon trayIconBasket = null;

    public static BufferedImage trayIconImageNormal = null;
    public static BufferedImage trayIconImageBasket = null;

    public static CheckboxMenuItem chaseItem = new CheckboxMenuItem("chase the mouse");
    public static CheckboxMenuItem toyItem = new CheckboxMenuItem("catch the toy");
    public static CheckboxMenuItem basketItem = new CheckboxMenuItem("Sleep to the basket");
    public static CheckboxMenuItem autonomeItem = new CheckboxMenuItem("independent kitten");
    public static CheckboxMenuItem listenItem = new CheckboxMenuItem("chase the red pointer");

    public static CheckboxMenuItem settingsItem = new CheckboxMenuItem("settings");

    static MenuItem exitItem = new MenuItem("Exit");

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

    ItemListener buttonsListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            changeSystemTrayIcon(trayIconNormal);
            Toy.catched = true;
            Neko.myNeko.basketReached = false;

            if (e.getSource() == chaseItem) {  // chase the mouse
                kittyState = KittyState.CHASE;
                setItemState(chaseItem);
                TwitchListen.twitchListen(false);

                if (newToy != null) newToy.dispose();
            }
            if (e.getSource() == toyItem) {  // catch the toy
                kittyState = KittyState.CATCH;
                setItemState(toyItem);
                TwitchListen.twitchListen(false);

                if (newToy != null) newToy.dispose();
                newToy = new Toy();

                Toy.catched = false;
            }
            if (e.getSource() == basketItem) {  // go sleep in the basket
                kittyState = KittyState.SLEEP;
                setItemState(basketItem);
                TwitchListen.twitchListen(false);

                if (newToy != null) newToy.dispose();
            }
            if (e.getSource() == autonomeItem) { // do what you want, don't chase the mouse
                kittyState = KittyState.AUTONOM;
                setItemState(autonomeItem);
                TwitchListen.twitchListen(false);

                if (newToy != null) newToy.dispose();
            }
            if (e.getSource() == listenItem) {
                kittyState = KittyState.LISTEN;
                setItemState(listenItem);
                TwitchListen.twitchListen(true);
            }
            if (e.getSource() == settingsItem) {
                new Settings();
            }
        }
    };

    ActionListener exitListener = e -> {
        if (e.getSource() == exitItem) {
            System.exit(0);
        }
    };

    public MySystemTray() {
        listenItem.addItemListener(buttonsListener);

        toyItem.addItemListener(buttonsListener);
        basketItem.addItemListener(buttonsListener);
        chaseItem.addItemListener(buttonsListener);
        autonomeItem.addItemListener(buttonsListener);

        settingsItem.addItemListener(buttonsListener);
        exitItem.addActionListener(exitListener);

        autonomeItem.setState(true);

        try {
            trayIconImageNormal = ImageIO.read(Objects.requireNonNull(MySystemTray.class.getResource("images/icon.gif")));
            trayIconImageBasket = ImageIO.read(Objects.requireNonNull(MySystemTray.class.getResource("images/nekoInbasket.png")));
        }
        catch (IOException ioException) {
            System.out.println(ioException);
        }

        int trayIconWidth = new TrayIcon(trayIconImageNormal).getSize().width;
        trayIconNormal = new TrayIcon(trayIconImageNormal.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH));
        trayIconBasket = new TrayIcon(trayIconImageBasket.getScaledInstance(trayIconWidth, -1, Image.SCALE_SMOOTH));

        changeSystemTrayIcon(trayIconNormal);
    }

    public static void changeSystemTrayIcon(TrayIcon icon) {
        try {
            for (int i=0; i<tray.getTrayIcons().length; i++) {
                tray.remove(tray.getTrayIcons()[i]);
            }
            tray.add(icon);

            PopupMenu popup = new PopupMenu();
            popup.add(listenItem);
            popup.addSeparator();
            popup.add(toyItem);
            popup.add(basketItem);
            popup.add(chaseItem);
            popup.add(autonomeItem);
            popup.addSeparator();
            popup.add(settingsItem);
            popup.add(exitItem);

            icon.setPopupMenu(popup);
        }
        catch (AWTException awtException) {
            System.out.println(awtException);
        }
    }

    public void setItemState(CheckboxMenuItem item) {
        basketItem.setState(false);
        toyItem.setState(false);
        chaseItem.setState(false);
        listenItem.setState(false);
        autonomeItem.setState(false);

        item.setState(true);
    }
}