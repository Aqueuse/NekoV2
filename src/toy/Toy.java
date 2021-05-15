package toy;

import neko.Neko;

import java.awt.*;
import javax.swing.*;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Toy extends javax.swing.JWindow {
    ImageIcon[] toySprites = {
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool1.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool2.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool3.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool4.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool5.png"))),
            new ImageIcon(Objects.requireNonNull(Toy.class.getResource("images/" + "wool6.png"))),
    };
    JLabel imageLabel = new JLabel();

    int loopCounter = 0;
    String direction = "topLeft";

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
        moveToy();
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

    private void moveToy() {
        moveTimer = new Timer(200, e -> {
            boolean reachedX = Neko.myNeko.getX() >= getX()-16 && Neko.myNeko.getX() <= getX()+16;
            boolean reachedY = Neko.myNeko.getY() >= getY()-16 && Neko.myNeko.getY() <= getY()+16;
            if (reachedX && reachedY) {
                catched = true;
            }

            if (!catched) {
                setLocation(toyPositionX, toyPositionY);

                if (direction.equals("topLeft")) {
                    moveTopLeft();
                }

                if (direction.equals("topRight")) {
                    moveTopRight();
                }

                if (direction.equals("bottomLeft")) {
                    moveBottomLeft();
                }

                if (direction.equals("bottomRight")) {
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
            direction = "topLeft";
        }
        if (toyPositionY <= 0) {
            direction = "bottomRight";
        } else {
            toyPositionX = toyPositionX + 15;
            toyPositionY = toyPositionY - 15;
        }
    }

    private void moveBottomRight() {
        if (toyPositionX >= screenWidth) {
            direction = "bottomLeft";
        }

        if (toyPositionY >= screenHeight) {
            direction = "topRight";
        } else {
            toyPositionX = toyPositionX + 15;
            toyPositionY = toyPositionY + 15;
        }
    }

    private void moveBottomLeft() {
        if (toyPositionX <= 0) {
            direction = "bottomRight";
        }

        if (toyPositionY >= screenHeight) {
            direction = "topLeft";
        } else {
            toyPositionX = toyPositionX - 15;
            toyPositionY = toyPositionY + 15;
        }
    }

    private void moveTopLeft() {
        if (toyPositionX <= 0) {
            direction = "topRight";
        }
        if (toyPositionY <= 0) {
            direction = "bottomLeft";
        } else {
            toyPositionX = toyPositionX - 15;
            toyPositionY = toyPositionY - 15;
        }
    }

}