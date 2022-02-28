import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

public class Game extends JPanel implements ActionListener {

    private final Main frame;
    private Timer timer;
    private final Random rand;
    private String[] wordArray;
    private HashMap<String, String> wordDict;
    private HashMap<String, String> record;
    private final JPanel textPanel;
    private final TextField textEnter;
    private String onScreenWord;
    private int milli;
    private int score;
    private int diff;
    private int highscore;
    private Image bg;

    private final int EASY = 7;
    private final int NORMAL = 11;
    private final int HARD = 20;

    public Game(Main fr, DifficultySelect differ, int high, HashMap<String, String> r) {

        URL urlBg = getClass().getResource("/resource/bg.png");
        this.bg = new ImageIcon(urlBg).getImage();

        try(FileInputStream fis = new FileInputStream("wordArray.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            FileInputStream fis1 = new FileInputStream("wordDict.dat");
            ObjectInputStream ois1 = new ObjectInputStream(fis1)) {
            wordArray = (String[])ois.readObject();
            wordDict = (HashMap<String, String>)ois1.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        record = r;
        if(differ.getDiff() == "Easy") {
            diff = EASY;
        }else if(differ.getDiff() == "Normal") {
            diff = NORMAL;
        }else if(differ.getDiff() == "Hard") {
            diff = HARD;
        }if(differ.getTime() == 100) {
            milli = 1000;
        }else if(differ.getTime() == 80) {
            milli = 800;
        }else if(differ.getTime() == 60) {
            milli = 600;
        }

        highscore = high;
        score = 0;

        this.frame = fr;
        rand = new Random();
        textPanel = new JPanel();
        textEnter = new TextField();
        onScreenWord = wordArray[rand.nextInt(wordArray.length)];
        while (onScreenWord.length() >= diff) {
            onScreenWord = wordArray[rand.nextInt(wordArray.length)];
        }

        textPanel.setFont(new Font("Crystal", Font.BOLD, 30));
        this.setLayout(new BorderLayout());
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textEnter);

        this.add(textPanel, BorderLayout.SOUTH);

        textEnter.addActionListener(this);

        timeRunner();
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.bg, 0, 0, null);
        g.setFont(new Font("Crystal", Font.BOLD, 40));
        g.setColor(Color.white);
        g.drawString("Your Word Is :", 50, 125);
        g.setFont(new Font("Crystal", Font.BOLD, 120));
        g.setColor(Color.black);
        g.drawString(onScreenWord, 40, 250);
        g.setFont(new Font("Crystal", Font.BOLD, 20));
        g.drawString(wordDict.get(onScreenWord)+".", 55, 300);
        g.setFont(new Font("Crystal", Font.BOLD, 40));
        g.setColor(Color.white);
        g.drawString((milli/10)+"", 925, 60);
        g.setFont(new Font("Crystal", Font.BOLD, 50));
        g.drawString("Score : "+score, 35, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        TextField temp = (TextField) e.getSource();
        if(onScreenWord.toLowerCase().equals(temp.getText().toLowerCase())){
            if(diff == EASY){
                score += onScreenWord.length();
            }
            else if(diff == NORMAL){
                score += onScreenWord.length()+2;
            }
            else if(diff == HARD){
                score += onScreenWord.length()+4;
            }
            record.put(onScreenWord, wordDict.get(onScreenWord));
            onScreenWord = wordArray[rand.nextInt(wordArray.length)];
            while (onScreenWord.length() >= diff) {
                onScreenWord = wordArray[rand.nextInt(wordArray.length)];
            }
            textEnter.setText("");
        }
        this.repaint();
    }

    public void timeRunner() {
        timer = new Timer(100, e -> {
            milli--;
            this.repaint();
            if(milli == 0){
                frame.setContentPane(new StartOver(frame, this, highscore, record));
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
