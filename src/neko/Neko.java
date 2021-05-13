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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * Neko the cat.
 * <p>
 * This program loads in 32 images of Neko. Neko will chase you mouse cursor
 * around the desktop. Once he's over it and the mouse doesn't move he'll
 * prepare to take a nap. If the mouse go's outside the desktop he will
 * reach the border and try to dig for it. He'll eventually give up, and
 * fall asleep.
 *
 * @author Werner Randelshofer (adaption for desktop)
 * Chris Parent (original code)
 * @version 1.0.1 2010-07-17 Fixes timers. Sets longer sleep times when the
 * cat sleeps.
 * <br>1.0 2010-07-16 Created.
 */

public class Neko extends javax.swing.JWindow {
    private final javax.swing.JLabel imageLabel;
    public static ImageIcon[] image; // image

    private int imageNumber;           // image number
    private int imageLoopCounter1 = 0;     // image loop counter
    private int imageLoopCounter2 = 0;     // second loop counter

    private int sleepCounter = 0;
    public Point mouseLocation;

    private int moveCounter = ThreadLocalRandom.current().nextInt(500, 700);
    private int randomX;
    private int randomY;
    final double pi = Math.PI;

    public boolean basketReached = false;

    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static Neko myNeko;

    public Neko() {
        getRootPane().putClientProperty("Window.shadow", false);
        setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 0));

        loadKitten();
        imageLabel = new javax.swing.JLabel();
        getContentPane().add(imageLabel, java.awt.BorderLayout.CENTER);

        pack();

        setSize(image[1].getIconWidth(), image[1].getIconHeight());
        setLocation(500, 500);

        this.setVisible(true);

        Timer timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                        }
                        else {
                            Toy.catched = false;
                            MySystemTray.kittyState = "autonom";
                        }
                        break;
                    case "sleep":
                        // go to basket and sleep in
                        basketReached = moveCatToPosition(screenWidth - 220, screenHeight-30);
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
                        if (moveCounter == 500) {
                            randomX = ThreadLocalRandom.current().nextInt(0, screenWidth);
                            randomY = ThreadLocalRandom.current().nextInt(0, screenHeight);
                            moveCounter = ThreadLocalRandom.current().nextInt(500, 700);
                        }
                        moveCatToPosition(randomX, randomY);
                        break;
                    case "idle":
                        break;
                    default:
                        MySystemTray.kittyState = "chase";
                }
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void loadKitten() {
        image = new ImageIcon[33];
        for (int i = 1; i <= 32; i++) {
            image[i] = new ImageIcon(Neko.class.getResource("images/" + i + ".GIF"));
        }
    }

    private void animateCat(double theta, boolean sleep, boolean out, String orientation) {
        if (sleep) {
            sleepCounter++;
            if (sleepCounter == 20) {
                imageNumber = 29;
            }
            //cat lick (6  times)
            if (imageNumber == 31) {
                imageNumber = 25;
                imageLoopCounter1++;
                if (imageLoopCounter1 == 6) {
                    imageNumber = 27;
                    imageLoopCounter1 = 0;
                }
            }

            //cat scratch (27 & 28, 4 times)
            if (imageNumber == 27) {
                imageNumber = 28;
            }

            if (imageNumber == 28) {
                imageNumber = 27;
                imageLoopCounter2++;
                if (imageLoopCounter2 == 4) {
                    imageNumber = 26;
                    imageLoopCounter2 = 0;
                }
            }

            //cat yawn (26)
            if (imageNumber == 26 || imageNumber == 30) {
                imageNumber = 29;
            }

            //cat sleep (29 & 30, forever)
            if (imageNumber == 29) {
                imageNumber = 30;
            }
        }

        if (!sleep) {
            // gratouilles management
            sleepCounter = 0;
            if (out) {
                if (orientation.equals("top")) {
                    imageNumber = 18;    // show images 17 & 18, 6 times
                    imageLoopCounter1++;
                    if (imageLoopCounter1 == 6) {
                        imageNumber = 27;
                        imageLoopCounter1 = 0;
                    }
                }
                if (orientation.equals("bottom")) {
                    imageNumber = 22;
                    imageLoopCounter1++;
                    if (imageLoopCounter1 == 6) {
                        imageNumber = 27;
                        imageLoopCounter1 = 0;
                    }
                }
                if (orientation.equals("left")) {
                    imageNumber = 24;
                    imageLoopCounter1++;
                    if (imageLoopCounter1 == 6) {
                        imageNumber = 27;
                        imageLoopCounter1 = 0;
                    }
                }
                if (orientation.equals("right")) {
                    imageNumber = 20;
                    imageLoopCounter1++;
                    if (imageLoopCounter1 == 6) {
                        imageNumber = 27;
                        imageLoopCounter1 = 0;
                    }
                }
            }
            else {
                // orientation management
                if (theta >= -pi / 8 && theta <= pi / 8) { //right
                    imageNumber = (imageNumber == 5) ? 6 : 5;
                }
                if (theta > pi / 8 && theta < 3 * pi / 8) { //upper-right
                    imageNumber = (imageNumber == 3) ? 4 : 3;
                }
                if (theta >= 3 * pi / 8 && theta <= 5 * pi / 8) { //up
                    imageNumber = (imageNumber == 1) ? 2 : 1;
                }
                if (theta > 5 * pi / 8 && theta < 7 * pi / 8) { //upper-left
                    imageNumber = (imageNumber == 15) ? 16 : 15;
                }
                if (theta >= 7 * pi / 8 || theta <= -7 * pi / 8) { //left
                    imageNumber = (imageNumber == 13) ? 14 : 13;
                }
                if (theta > -7 * pi / 8 && theta < -5 * pi / 8) { //bottom-left
                    imageNumber = (imageNumber == 11) ? 12 : 11;
                }
                if (theta >= -5 * pi / 8 && theta <= -3 * pi / 8) { //down
                    imageNumber = (imageNumber == 9) ? 10 : 9;
                }
                if (theta > -3 * pi / 8 && theta < -pi / 8) { //bottom-right
                    imageNumber = (imageNumber == 7) ? 8 : 7;
                }
            }
        }

        //draw the new image
        imageLabel.setIcon(image[imageNumber]);
    }

    public boolean moveCatToPosition(int coordinateX, int coordinateY) {
        boolean destinationReached = false;

        int nekoX = this.getX();
        int nekoY = this.getY();

        double deltaX = coordinateX - nekoX;
        double deltaY = nekoY - coordinateY;

        double theta = Math.atan2(deltaY, deltaX);     // angle from coordinates to cat

        /* Determines what the cat should do, if he is not in the coordinates x,y */
        int nextCoordinateX = (int) (nekoX + Math.cos(theta) * 16);
        int nextCoordinateY = (int) (nekoY - Math.sin(theta) * 16);

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); // distance formula (from coordinates to cat)

        if (distance <= 32) {
            //The coordinate is outside, above applet
            if (nekoY == 0) {
                System.out.println("scratch scratch");
                animateCat(0, false, true, "top");
            }

            //The coordinate is outside, under applet
            if (nekoY >= screenHeight - 16) {
                System.out.println("scratch scratch");
                animateCat(0, false, true, "bottom");
            }

            //The coordinate is outside, left
            if (nekoX == 0) {
                System.out.println("scratch scratch");
                animateCat(0, false, true, "left");
            }

            //The coordinate is outside, right
            if (nekoX >= screenWidth - 16) {
                System.out.println("scratch scratch");
                animateCat(0, false, true, "right");
            }
            else {
                animateCat(0, true, false, "osef");
                System.out.println("sleep sleep");
                destinationReached = true;
            }
        }
        else {
            destinationReached = false;
            System.out.println("moving moving");
            setLocation(nextCoordinateX, nextCoordinateY);
            animateCat(theta, false, false, "osef");
        }
        return destinationReached;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MySystemTray();
                myNeko = new Neko();
            }
        });
    }
}
