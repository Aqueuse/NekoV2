package neko;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import settings.Settings;
import systemTray.MySystemTray;
import toy.Toy;
import twitchInteraction.TwitchListen;

import static neko.NekoAssets.*;

public class Neko extends javax.swing.JWindow {
    private int loopCounter = 0;

    private final javax.swing.JLabel imageLabel;
    private ImageIcon[] kittySprites;

    private int sleepCounter = 0;
    public Point mouseLocation;

    private int moveCounter = ThreadLocalRandom.current().nextInt(100, 200);
    private int randomX;
    private int randomY;
    private int listen_x = 0;
    private  int listen_y = 0;
    final double pi = Math.PI;
    private String orientation;

    public boolean basketReached = false;

    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static Settings settings;
    public static Neko myNeko;

    public Neko() {
        getRootPane().putClientProperty("Window.shadow", false);
        setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 0));

        imageLabel = new javax.swing.JLabel();
        getContentPane().add(imageLabel, BorderLayout.CENTER);
        pack();

        setSize(32, 32);
        setLocation(500, 500);
        setVisible(true);

        Timer timer = new Timer(200, e -> {
            setVisible(true);

            // redraw the JWindow to clean the visual artefacts
            setSize(31, 31);
            setSize(32, 32);
            switch (MySystemTray.kittyState) {
                case CHASE:
                    PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                    mouseLocation = pointerInfo.getLocation();
                    moveCatToPosition(mouseLocation.x, mouseLocation.y);
                    break;
                case CATCH:
                    if (!Toy.catched) {
                        moveCatToPosition(Toy.toyPositionX, Toy.toyPositionY);
                    } else {
                        Toy.catched = false;
                        MySystemTray.kittyState = MySystemTray.KittyState.AUTONOM;
                    }
                    break;
                case SLEEP:
                    basketReached = moveCatToPosition(screenWidth - 220, screenHeight - 30);
                    if (basketReached) {
                        setVisible(false);
                        MySystemTray.changeSystemTrayIcon(MySystemTray.trayIconBasket);
                        MySystemTray.kittyState = MySystemTray.KittyState.IDLE;
                    }
                    break;
                case AUTONOM:
                    moveCounter = moveCounter - 1;
                    if (moveCounter < 100) {
                        randomX = ThreadLocalRandom.current().nextInt(0, screenWidth);
                        randomY = ThreadLocalRandom.current().nextInt(0, screenHeight);
                        moveCounter = ThreadLocalRandom.current().nextInt(100, 200);
                    }
                    moveCatToPosition(randomX, randomY);
                    break;
                case IDLE:
                    break;
                case LISTEN:
                    moveCounter = moveCounter - 5;
                    if (moveCounter < 100) {
                        listen_x = TwitchListen.x_coordinates;
                        listen_y = TwitchListen.y_coordinates;
                        moveCounter = 200;
                    }
                    moveCatToPosition(listen_x, listen_y);
                    break;
                default:
                    MySystemTray.kittyState = MySystemTray.KittyState.CHASE;
            }

        });
        timer.setRepeats(true);
        timer.start();
    }

    public boolean moveCatToPosition(int coordinateX, int coordinateY) {
        boolean destinationReached = false;

        int nekoX = this.getX();
        int nekoY = this.getY();

        double deltaX = coordinateX - nekoX;
        double deltaY = nekoY - coordinateY;

        double theta_angle = Math.atan2(deltaY, deltaX); // angle from coordinates to cat

        /* Determines what the cat should do, if he is not in the coordinates x,y */
        int nextCoordinateX = (int) (nekoX + Math.cos(theta_angle) * 16);
        int nextCoordinateY = (int) (nekoY - Math.sin(theta_angle) * 16);

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // distance formula (from coordinates to cat)

        if (distance <= 32) {
            // Find kitty position
            if (nekoX <= 0) {
                animateScratch("left");
            }
            if (nekoY <= 0) {
                animateScratch("top");
            }
            if (nekoX >= screenWidth - 40) {
                animateScratch("right");
            }
            if (nekoY >= screenHeight - 50) {
                animateScratch("bottom");
            }
            if (nekoX > 0 && nekoX < screenWidth - 32 &&
                nekoY > 0 && nekoY < screenHeight - 32) {
                sleepCounter = sleepCounter + 1;
                if (sleepCounter < 14) animatePrepareToSleep();
                if (sleepCounter >= 14) {
                    animateSleep();
                }
                destinationReached = true;
            }
        }
        else {
            sleepCounter = 0;
            setLocation(nextCoordinateX, nextCoordinateY);

            animateMove(theta_angle);
        }
        return destinationReached;
    }

    private void animateMove(double theta) {
        // recalculate orientation
        if (theta >= 7 * pi / 8 || theta <= -7 * pi / 8) orientation = "left";
        if (theta >= -pi / 8 && theta <= pi / 8) orientation = "right";
        if (theta >= 3 * pi / 8 && theta <= 5 * pi / 8) orientation = "top";
        if (theta >= -5 * pi / 8 && theta <= -3 * pi / 8) orientation = "bottom";
        if (theta > 5 * pi / 8 && theta < 7 * pi / 8) orientation = "leftTop";
        if (theta > -7 * pi / 8 && theta < -5 * pi / 8) orientation = "leftBottom";
        if (theta > pi / 8 && theta < 3 * pi / 8) orientation = "rightTop";
        if (theta > -3 * pi / 8 && theta < -pi / 8) orientation = "rightBottom";

        switch (orientation) {
            case "left" -> kittySprites = animateLeft;
            case "right" -> kittySprites = animateRight;
            case "top" -> kittySprites = animateTop;
            case "bottom" -> kittySprites = animateBottom;
            case "leftTop" -> kittySprites = animateLeftTop;
            case "leftBottom" -> kittySprites = animateLeftBottom;
            case "rightTop" -> kittySprites = animateRightTop;
            case "rightBottom" -> kittySprites = animateRightBottom;
        }

        imageLabel.repaint();
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private void animateScratch(String kittyPosition) {
        switch (kittyPosition) {
            case "left" -> kittySprites = animateScratchLeft;
            case "right" -> kittySprites = animateScratchRight;
            case "top" -> kittySprites = animateScratchTop;
            case "bottom" -> kittySprites = animateScratchBottom;
        }
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private void animatePrepareToSleep() {
        kittySprites = animatePrepareToSleep;
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private void animateSleep() {
        kittySprites = animateSleep;
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private int loopIndex(ImageIcon[] arraySprites) {
        loopCounter = loopCounter + 1;
        if (loopCounter >= arraySprites.length) {
            loopCounter = 0;
        }
        return loopCounter;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new MySystemTray();
            settings = new Settings();
            myNeko = new Neko();
        });
    }
}
