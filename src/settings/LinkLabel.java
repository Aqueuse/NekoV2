package settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class LinkLabel extends JLabel {
    public LinkLabel (String text, String URL) {
        setText("<HTML><U>"+text+"</U></HTML>");
        setForeground(Color.BLUE.darker());
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(URL));
                }
                catch (IOException | URISyntaxException exception) {
                    exception.printStackTrace();
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                setText(text);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                setText(text);
            }
        });
    }
}
