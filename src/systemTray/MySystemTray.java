package systemTray;

import toy.Toy;
import neko.Neko;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    static MenuItem exitItem = new MenuItem("Exit");

    Toy newToy;
    public static String kittyState = "autonom";

    ItemListener buttonsListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == chaseItem) {  // chase the mouse
                basketItem.setState(false);
                toyItem.setState(false);
                autonomeItem.setState(false);
                changeSystemTrayIcon(trayIconNormal);

                if (newToy != null) newToy.dispose();

                Neko.myNeko.basketReached = false;
                Toy.catched = true;
                kittyState = "chase";
            }
            if (e.getSource() == toyItem) {  // catch the toy
                basketItem.setState(false);
                chaseItem.setState(false);
                autonomeItem.setState(false);
                changeSystemTrayIcon(trayIconNormal);

                if (newToy != null) newToy.dispose();
                newToy = new Toy();

                Neko.myNeko.basketReached = false;
                Toy.catched = false;
                kittyState = "catch";
            }
            if (e.getSource() == basketItem) {  // go sleep in the basket
                toyItem.setState(false);
                chaseItem.setState(false);
                autonomeItem.setState(false);
                changeSystemTrayIcon(trayIconNormal);

                if (newToy != null) newToy.dispose();

                Toy.catched = true;
                kittyState = "sleep";
            }
            if (e.getSource() == autonomeItem) { // do what you want, don't chase the mouse
                basketItem.setState(false);
                toyItem.setState(false);
                chaseItem.setState(false);
                changeSystemTrayIcon(trayIconNormal);

                if (newToy != null) newToy.dispose();

                Toy.catched = true;
                Neko.myNeko.basketReached = false;
                kittyState = "autonom";
            }
        }
    };

    ActionListener exitListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == exitItem) {
                System.exit(0);
            }
        }
    };

    public MySystemTray() {
        toyItem.addItemListener(buttonsListener);
        basketItem.addItemListener(buttonsListener);
        chaseItem.addItemListener(buttonsListener);
        autonomeItem.addItemListener(buttonsListener);
        exitItem.addActionListener(exitListener);

        autonomeItem.setState(true);

        try {
            trayIconImageNormal = ImageIO.read(MySystemTray.class.getResource("images/icon.gif"));
            trayIconImageBasket = ImageIO.read(MySystemTray.class.getResource("images/nekoInbasket.png"));
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
            popup.add(toyItem);
            popup.add(basketItem);
            popup.add(chaseItem);
            popup.add(autonomeItem);
            popup.addSeparator();
            popup.add(exitItem);

            icon.setPopupMenu(popup);
        }
        catch (AWTException awtException) {
            System.out.println(awtException);
        }
    }
}