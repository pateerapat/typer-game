import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ButtonNew extends JButton {
    private Image i;
    public ButtonNew(String s) {
        URL urlBg = getClass().getResource(s);
        i = new ImageIcon(urlBg).getImage();
    }

    public void paintComponent(Graphics g) {
        g.drawImage(i, 0, 0, null);
    }
}
