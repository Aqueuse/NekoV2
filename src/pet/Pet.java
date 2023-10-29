package pet;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import settings.SettingsFileManagement;
import systemTray.MySystemTray;
import toy.Toy;
import twitchInteraction.TwitchListen;

public class Pet extends javax.swing.JWindow {
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

    public static Timer timer;

    public Rectangle screenBounds = getVirtualScreenRectangle();

    public Pet() {
        int delay = Integer.parseInt(SettingsFileManagement.loadKeyFromSettings("petDelay"));

        getRootPane().putClientProperty("Window.shadow", false);
        setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 0));

        imageLabel = new javax.swing.JLabel();
        getContentPane().add(imageLabel, BorderLayout.CENTER);
        pack();

        setSize(32, 32);
        setLocation((int) screenBounds.getMaxX()-32, (int)screenBounds.getMaxY()-32);
        setVisible(true);

        timer = new Timer(delay, e -> {
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
                    basketReached = moveCatToPosition(MySystemTray.systemTrayXcoordinates, MySystemTray.systemTrayYcoordinates);
                    if (basketReached) {
                        MySystemTray.trayIcon.setImage(PetAssets.basket());
                        MySystemTray.kittyState = MySystemTray.KittyState.IDLE;
                    }
                    break;
                case AUTONOM:
                    moveCounter = moveCounter - 1;
                    if (moveCounter < 100) {
                        randomX = ThreadLocalRandom.current().nextInt((int)screenBounds.getMinX(), (int)screenBounds.getMaxX());
                        randomY = ThreadLocalRandom.current().nextInt((int)screenBounds.getMinY(), (int)screenBounds.getMaxY());
                        moveCounter = ThreadLocalRandom.current().nextInt(100, 200);
                    }
                    moveCatToPosition(randomX, randomY);
                    break;
                case IDLE:
                    setVisible(false);
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

        /* Determines what the kitty should do, if he is not in the coordinates x,y */
        int nextCoordinateX = (int) (nekoX + Math.cos(theta_angle) * 16);
        int nextCoordinateY = (int) (nekoY - Math.sin(theta_angle) * 16);

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // distance formula (from coordinates to cat)

        screenBounds = getVirtualScreenRectangle();
        System.out.println(screenBounds.getMinX() + " / " + screenBounds.getMaxX());

        if (distance <= 32) {
            // Find kitty position
            if (nekoX <= screenBounds.getMinX()) {
                animateScratch("left");
            }
            if (nekoY <= screenBounds.getMinY()) {
                animateScratch("top");
            }
            if (nekoX >= screenBounds.getMaxX()-64) {
                animateScratch("right");
            }
            if (nekoY >= screenBounds.getMaxY() - 50) {
                animateScratch("bottom");
            }
            if (nekoX < screenBounds.width - 32 && nekoY < screenBounds.height - 32) {
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
            case "left" -> kittySprites = PetAssets.animateLeft();
            case "right" -> kittySprites = PetAssets.animateRight();
            case "top" -> kittySprites = PetAssets.animateTop();
            case "bottom" -> kittySprites = PetAssets.animateBottom();
            case "leftTop" -> kittySprites = PetAssets.animateLeftTop();
            case "leftBottom" -> kittySprites = PetAssets.animateLeftBottom();
            case "rightTop" -> kittySprites = PetAssets.animateRightTop();
            case "rightBottom" -> kittySprites = PetAssets.animateRightBottom();
        }

        imageLabel.repaint();
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private void animateScratch(String kittyPosition) {
        switch (kittyPosition) {
            case "left" -> kittySprites = PetAssets.animateScratchLeft();
            case "right" -> kittySprites = PetAssets.animateScratchRight();
            case "top" -> kittySprites = PetAssets.animateScratchTop();
            case "bottom" -> kittySprites = PetAssets.animateScratchBottom();
        }
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private void animatePrepareToSleep() {
        kittySprites = PetAssets.animatePrepareToSleep();
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private void animateSleep() {
        kittySprites = PetAssets.animateSleep();
        imageLabel.setIcon(kittySprites[loopIndex(kittySprites)]);
    }

    private int loopIndex(ImageIcon[] arraySprites) {
        loopCounter = loopCounter + 1;
        if (loopCounter >= arraySprites.length) {
            loopCounter = 0;
        }
        return loopCounter;
    }

    public Rectangle getVirtualScreenRectangle() {
        Rectangle virtualBounds = new Rectangle();

        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();

        for (GraphicsDevice graphicsDevice : screenDevices) {
            GraphicsConfiguration[] graphicsConfigurations = graphicsDevice.getConfigurations();
            for (GraphicsConfiguration graphicsConfiguration : graphicsConfigurations) {
                virtualBounds = virtualBounds.union(graphicsConfiguration.getBounds());
            }
        }

        return virtualBounds;
    }
}
