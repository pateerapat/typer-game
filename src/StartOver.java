import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;

public class StartOver extends JPanel implements ActionListener {

    private final Main frame;
    private final ButtonNew restart;
    private final Game game;
    private int highscore;
    private HashMap<String, String> record;
    private Image bg;

    public StartOver(Main fr, Game g, int high, HashMap<String, String> r) {

        URL urlBg = getClass().getResource("/resource/endgame.png/");
        bg = new ImageIcon(urlBg).getImage();
        record = r;
        highscore = high;
        game = g;
        frame = fr;
        restart = new ButtonNew("/resource/restart.png/");

        this.setLayout(new GridLayout(5, 1));
        restart.setFont(new Font("Crystal", Font.BOLD, 40));
        restart.setFocusable(false);

        this.add(new JLabel());
        this.add(new JLabel());
        this.add(new JLabel());
        this.add(new JLabel());
        this.add(restart);

        restart.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.setContentPane(new DifficultySelect(frame, highscore, record));
        frame.revalidate();
        frame.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bg, 0, 0, null);
        g.setFont(new Font("Crystal", Font.BOLD, 40));
        g.setColor(Color.white);
        if(game.getScore() > highscore) {
            highscore = game.getScore();
            g.drawString("You Get A Highscore Of :", 50, 125);
        }else {
            g.drawString("Your Score Is :", 50, 125);
        }
        g.setFont(new Font("Crystal", Font.BOLD, 120));
        g.drawString(game.getScore()+"", 40, 250);
        g.setFont(new Font("Crystal", Font.BOLD, 30));
        g.drawString("All Words Has Been Update In Dictionary.", 55, 300);
    }
}
