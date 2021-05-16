/*
 * @(#)Neko.java  1.0  2010-07-16
 *
 * Copyright (c) 2010 Werner Randelshofer
 * Hausmatt 10, Immensee, CH-6405, Switzerland.
 *
 * This source code is free to everyone.
 *
 * This is a desktop adaptation of the applet
 * JAVA NEKO V1.0 by Chris Parent, 1999.
 * http://mysite.ncnetwork.net/res8t1xo/class/neko.htm
 */

package neko;

import systemTray.MySystemTray;
import toy.Toy;

import java.awt.*;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Neko extends javax.swing.JWindow {

    private int loopCounter = 0;

    private final javax.swing.JLabel imageLabel;
    private ImageIcon[] kittySprites;

    ImageIcon[] animateLeft = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "left1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "left2.GIF")))
    };
    ImageIcon[] animateRight = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "right1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "right2.GIF")))
    };
    ImageIcon[] animateTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "top1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "top2.GIF")))
    };
    ImageIcon[] animateBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "bottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "bottom2.GIF")))
    };
    ImageIcon[] animateLeftTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftTop1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftTop2.GIF")))
    };
    ImageIcon[] animateLeftBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftBottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "leftBottom2.GIF")))
    };
    ImageIcon[] animateRightTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightTop1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightTop2.GIF")))
    };
    ImageIcon[] animateRightBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightBottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "rightBottom2.GIF")))
    };

    ImageIcon[] animateScratchLeft = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchLeft1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchLeft2.GIF")))
    };
    ImageIcon[] animateScratchRight = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchRight1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchRight2.GIF")))
    };
    ImageIcon[] animateScratchTop = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchTop1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchTop2.GIF")))
    };
    ImageIcon[] animateScratchBottom = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchBottom1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "scratchBottom2.GIF")))
    };

    ImageIcon[] animatePrepareToSleep = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "await2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "lick2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "baille.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "baille.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "baille.GIF")))
    };
    ImageIcon[] animateSleep = {
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep1.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF"))),
            new ImageIcon(Objects.requireNonNull(Neko.class.getResource("images/" + "sleep2.GIF")))
    };

    private int sleepCounter = 0;
    public Point mouseLocation;

    private int moveCounter = ThreadLocalRandom.current().nextInt(100, 200);
    private int randomX;
    private int randomY;
    final double pi = Math.PI;
    private String orientation;

    public boolean basketReached = false;

    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static Neko myNeko;

    public Neko() {
        getRootPane().putClientProperty("Window.shadow", false);
        setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 0));

        imageLabel = new javax.swing.JLabel();
        getContentPane().add(imageLabel, java.awt.BorderLayout.CENTER);
        pack();

        setSize(32, 32);
        setLocation(500, 500);
        setVisible(true);

        Timer timer = new Timer(200, e -> {
            switch (MySystemTray.kittyState) {
                case "chase":
                    // chase the mouse
                    setVisible(true);
                    PointerInfo pointerInfo = MouseInfo.getPointerInfo();
                    mouseLocation = pointerInfo.getLocation();
                    moveCatToPosition(mouseLocation.x, mouseLocation.y);
                    break;
                case "catch":
                    // catch the ball of yarn
                    setVisible(true);
                    if (!Toy.catched) {
                        moveCatToPosition(Toy.toyPositionX, Toy.toyPositionY);
                    } else {
                        Toy.catched = false;
                        MySystemTray.kittyState = "autonom";
                    }
                    break;
                case "sleep":
                    // go to basket and sleep in
                    basketReached = moveCatToPosition(screenWidth - 220, screenHeight - 30);
                    if (basketReached) {
                        setVisible(false);
                        MySystemTray.changeSystemTrayIcon(MySystemTray.trayIconBasket);
                        MySystemTray.kittyState = "idle";
                    }
                    break;
                case "autonom":
                    // go where you want
                    setVisible(true);
                    moveCounter = moveCounter - 1;
                    if (moveCounter < 100) {
                        randomX = ThreadLocalRandom.current().nextInt(0, screenWidth);
                        randomY = ThreadLocalRandom.current().nextInt(0, screenHeight);
                        moveCounter = ThreadLocalRandom.current().nextInt(100, 200);
                    }
                    moveCatToPosition(randomX, randomY);
                    break;
                case "idle":
                    break;
                default:
                    MySystemTray.kittyState = "chase";
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
        java.awt.EventQueue.invokeLater(() -> {
            new MySystemTray();
            myNeko = new Neko();
        });
    }
}
