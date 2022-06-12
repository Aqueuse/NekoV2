package toy;

import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import init.Init;

public class Toy extends JWindow {
    public static int toyPositionX;
    public static int toyPositionY;
    public static boolean catched = false;

    final Random random = new Random();

    final int screenWidth = Init.getScreenWidth();
    final int screenHeight = Init.getScreenHeight();

    enum ToyDirection { TOPLEFT, TOPRIGHT, BOTTOMLEFT, BOTTOMRIGHT }
    final int randomDirection = ThreadLocalRandom.current().nextInt(0, ToyDirection.values().length);
    ToyDirection direction = ToyDirection.values()[randomDirection];

    int loopCounter = 0;
    int curvature = 14;

    Timer animateTimer;
    Timer moveTimer;

    final ImageIcon[] toySprites;
    final JLabel imageLabel = new JLabel();

    public Toy() {
        toyPositionX = random.nextInt(0, Init.getScreenWidth());
        toyPositionY = random.nextInt(0, Init.getScreenHeight());

        getRootPane().putClientProperty("Window.shadow", false);
        this.add(imageLabel);

        toySprites = ToyAssets.toySprites();
        imageLabel.setIcon(toySprites[0]);

        this.getContentPane().add(imageLabel, BorderLayout.CENTER);
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
            boolean reachedX = Init.myNeko.getX() >= getX()-16 && Init.myNeko.getX() <= getX()+16;
            boolean reachedY = Init.myNeko.getY() >= getY()-16 && Init.myNeko.getY() <= getY()+16;
            if (reachedX && reachedY) {
                catched = true;
            }
            if (!catched) {
                setLocation(toyPositionX, toyPositionY);

                if (direction == ToyDirection.TOPLEFT) {
                    curvature = random.nextInt(0, 4)+14;
                    moveTopLeft();
                }

                if (direction == ToyDirection.TOPRIGHT) {
                    curvature = random.nextInt(0, 4)+14;
                    moveTopRight();
                }

                if (direction == ToyDirection.BOTTOMLEFT) {
                    curvature = random.nextInt(0, 4)+14;
                    moveBottomLeft();
                }

                if (direction == ToyDirection.BOTTOMRIGHT) {
                    curvature = random.nextInt(0, 4)+14;
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
            toyPositionX = toyPositionX + curvature;
            toyPositionY = toyPositionY - 16;
        }
    }

    private void moveBottomRight() {
        if (toyPositionX >= screenWidth) {
            direction = ToyDirection.BOTTOMLEFT;
        }

        if (toyPositionY >= screenHeight) {
            direction = ToyDirection.TOPRIGHT;
        } else {
            toyPositionX = toyPositionX + curvature;
            toyPositionY = toyPositionY + 16;
        }
    }

    private void moveBottomLeft() {
        if (toyPositionX <= 0) {
            direction = ToyDirection.BOTTOMRIGHT;
        }

        if (toyPositionY >= screenHeight) {
            direction = ToyDirection.TOPLEFT;
        } else {
            toyPositionX = toyPositionX - curvature;
            toyPositionY = toyPositionY + 16;
        }
    }

    private void moveTopLeft() {
        if (toyPositionX <= 0) {
            direction = ToyDirection.TOPRIGHT;
        }
        if (toyPositionY <= 0) {
            direction = ToyDirection.BOTTOMLEFT;
        } else {
            toyPositionX = toyPositionX - curvature;
            toyPositionY = toyPositionY - 16;
        }
    }
}