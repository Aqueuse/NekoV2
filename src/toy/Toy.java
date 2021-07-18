package toy;

import java.awt.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

import neko.Neko;
import static toy.ToyAssets.toySprites;

public class Toy extends javax.swing.JWindow {
    JLabel imageLabel = new JLabel();

    int loopCounter = 0;
    enum ToyDirection { TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT }
    int randomDirection = ThreadLocalRandom.current().nextInt(0, ToyDirection.values().length);
    ToyDirection direction = ToyDirection.values()[randomDirection];

    public static final int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static int toyPositionX = ThreadLocalRandom.current().nextInt(0, screenWidth);
    public static int toyPositionY = ThreadLocalRandom.current().nextInt(0, screenHeight);
    public static boolean catched = false;

    Timer animateTimer;
    Timer moveTimer;

    public Toy() {
        getRootPane().putClientProperty("Window.shadow", false);
        this.add(imageLabel);

        imageLabel.setIcon(toySprites[0]);

        this.getContentPane().add(imageLabel, java.awt.BorderLayout.CENTER);
        pack();

        setAlwaysOnTop(true);
        this.setBackground(new Color(0, 0, 0, 0));

        setSize(32, 32);
        setLocation(toyPositionX, toyPositionY);
        this.setVisible(true);
        animateToy();
        startBounceToy();
    }

    private void animateToy() {
        // animate the toy by switching images in a loop with folder content
        animateTimer = new Timer(200, e -> {
            imageLabel.setIcon(toySprites[loopCounter]);
            loopCounter = loopCounter + 1;
            if (loopCounter == toySprites.length) loopCounter = 0;
        });
        animateTimer.setRepeats(true);
        animateTimer.start();
    }

    private void startBounceToy() {
        moveTimer = new Timer(200, e -> {
            boolean reachedX = Neko.myNeko.getX() >= getX()-16 && Neko.myNeko.getX() <= getX()+16;
            boolean reachedY = Neko.myNeko.getY() >= getY()-16 && Neko.myNeko.getY() <= getY()+16;
            if (reachedX && reachedY) {
                catched = true;
            }
            if (!catched) {
                setLocation(toyPositionX, toyPositionY);

                if (direction == ToyDirection.TOPLEFT) {
                    moveTopLeft();
                }

                if (direction == ToyDirection.TOPRIGHT) {
                    moveTopRight();
                }

                if (direction == ToyDirection.BOTTOMLEFT) {
                    moveBottomLeft();
                }

                if (direction == ToyDirection.BOTTOMRIGHT) {
                    moveBottomRight();
                }
            }
            if (catched) {
                moveTimer.stop();
                animateTimer.stop();
                setVisible(false);
            }
        });
        moveTimer.setRepeats(true);
        moveTimer.start();
    }

    private void moveTopRight() {
        if (toyPositionX >= screenWidth) {
            direction = ToyDirection.TOPLEFT;
        }
        if (toyPositionY <= 0) {
            direction = ToyDirection.BOTTOMRIGHT;
        } else {
            toyPositionX = toyPositionX + 15;
            toyPositionY = toyPositionY - 15;
        }
    }

    private void moveBottomRight() {
        if (toyPositionX >= screenWidth) {
            direction = ToyDirection.BOTTOMLEFT;
        }

        if (toyPositionY >= screenHeight) {
            direction = ToyDirection.TOPRIGHT;
        } else {
            toyPositionX = toyPositionX + 15;
            toyPositionY = toyPositionY + 15;
        }
    }

    private void moveBottomLeft() {
        if (toyPositionX <= 0) {
            direction = ToyDirection.BOTTOMRIGHT;
        }

        if (toyPositionY >= screenHeight) {
            direction = ToyDirection.TOPLEFT;
        } else {
            toyPositionX = toyPositionX - 15;
            toyPositionY = toyPositionY + 15;
        }
    }

    private void moveTopLeft() {
        if (toyPositionX <= 0) {
            direction = ToyDirection.TOPRIGHT;
        }
        if (toyPositionY <= 0) {
            direction = ToyDirection.BOTTOMLEFT;
        } else {
            toyPositionX = toyPositionX - 15;
            toyPositionY = toyPositionY - 15;
        }
    }

}