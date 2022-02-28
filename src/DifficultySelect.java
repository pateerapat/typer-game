import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.HashMap;

public class DifficultySelect extends JPanel implements ActionListener {

    private Main frame;
    private ButtonNew te, tn, th, e, n, h, start;
    private JLabel bTime, bDiff;
    private int time;
    private String diff;
    private int highscore;
    private JButton dictionary;
    private String[] wordArray;
    private HashMap<String, String> wordDict;
    private HashMap<String, String> record;
    private Image bg;

    public DifficultySelect(Main fr, int high, HashMap<String, String> r) {

        URL urlBg = getClass().getResource("/resource/start_bg.png/");
        bg = new ImageIcon(urlBg).getImage();
        record = r;
        frame = fr;
        time = 100;
        diff = "Easy";
        highscore = high;

        te = new ButtonNew("/resource/100.png/");
        tn = new ButtonNew("/resource/80.png/");
        th = new ButtonNew("/resource/60.png/");
        e = new ButtonNew("/resource/word6.png/");
        n = new ButtonNew("/resource/word9.png/");
        h = new ButtonNew("/resource/word10.png/");
        start = new ButtonNew("/resource/Start.png/");
        dictionary = new ButtonNew("/resource/dict.png/");

        bTime = new JLabel("   Set Time To : ");
        bDiff = new JLabel("   Set Difficulty To : ");
        bTime.setForeground(Color.white);
        bDiff.setForeground(Color.white);

        try(FileInputStream fis = new FileInputStream("wordArray.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            FileInputStream fis1 = new FileInputStream("wordDict.dat");
            ObjectInputStream ois1 = new ObjectInputStream(fis1);) {
            wordArray = (String[])ois.readObject();
            wordDict = (HashMap<String, String>)ois1.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        bTime.setFont(new Font("Crystal", Font.BOLD, 20));
        bDiff.setFont(new Font("Crystal", Font.BOLD, 20));
        start.setFont(new Font("Crystal", Font.BOLD, 20));

        start.setFont(new Font("Crystal", Font.BOLD, 30));
        start.setBackground(Color.PINK);
        start.setFocusable(false);

        te.setFont(new Font("Crystal", Font.BOLD, 30));
        te.setFocusable(false);

        tn.setFont(new Font("Crystal", Font.BOLD, 30));
        tn.setFocusable(false);

        th.setFont(new Font("Crystal", Font.BOLD, 30));
        th.setFocusable(false);

        e.setFont(new Font("Crystal", Font.BOLD, 30));
        e.setFocusable(false);

        n.setFont(new Font("Crystal", Font.BOLD, 30));
        n.setFocusable(false);

        h.setFont(new Font("Crystal", Font.BOLD, 30));
        h.setFocusable(false);

        dictionary.setFont(new Font("Crystal", Font.BOLD, 30));
        dictionary.setFocusable(false);

        this.setLayout(new GridLayout(6, 4));
        this.add(new JLabel());this.add(new JLabel());this.add(new JLabel());this.add(new JLabel());
        this.add(new JLabel());this.add(new JLabel());this.add(new JLabel());this.add(new JLabel());
        this.add(new JLabel());this.add(new JLabel());this.add(new JLabel());this.add(new JLabel());
        this.add(bTime);this.add(te);this.add(tn);this.add(th);
        this.add(bDiff);this.add(e);this.add(n);this.add(h);
        this.add(start);this.add(new JLabel());this.add(new JLabel());this.add(dictionary);

        e.addActionListener(this);
        n.addActionListener(this);
        h.addActionListener(this);
        te.addActionListener(this);
        tn.addActionListener(this);
        th.addActionListener(this);
        start.addActionListener(this);
        dictionary.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.e)) {
            diff = "Easy";
        }else if(e.getSource().equals(n)) {
            diff = "Normal";
        }else if(e.getSource().equals(h)) {
            diff = "Hard";
        }else if(e.getSource().equals(te)) {
            time = 100;
        }else if(e.getSource().equals(tn)) {
            time = 80;
        }else if(e.getSource().equals(th)) {
            time = 60;
        }else if(e.getSource().equals(start)) {
            frame.setContentPane(new Game(frame, this, highscore, record));
            frame.revalidate();
            frame.repaint();
        }else if(e.getSource().equals(dictionary)) {
            frame.setContentPane(new GameDictionary(frame, highscore, record));
            frame.revalidate();
            frame.repaint();
        }
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bg, 0, 0, null);
        g.setColor(Color.white);
        g.setFont(new Font("Crystal", Font.BOLD, 40));
        g.drawString("Time : "+time, 50, 100);
        g.setFont(new Font("Crystal", Font.BOLD, 40));
        g.drawString("Difficulty : "+diff, 50, 175);
        g.drawString("Highscore : "+highscore, 275, 435);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDiff() {
        return diff;
    }

    public void setDiff(String diff) {
        this.diff = diff;
    }
}
