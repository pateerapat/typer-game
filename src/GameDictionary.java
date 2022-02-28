import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class GameDictionary extends JPanel implements ActionListener {

    private Main frame;
    private int highscore;
    private JTextArea display;
    private JScrollPane scroll;
    private String[] wordArray;
    private HashMap<String, String> wordDict;
    private JTextField search;
    private ButtonNew back;
    private HashMap<String, String> record;

    public GameDictionary(Main fr, int high, HashMap<String, String> r) {
        record = r;
        frame = fr;
        highscore = high;
        display = new JTextArea();
        scroll = new JScrollPane(display);
        search = new JTextField();
        back = new ButtonNew("resource/back.png");

        search.setText("Search For Words Here And Scroll For Highlighter");
        this.setLayout(new BorderLayout());
        display.setEditable(false);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        display.setFont(new Font("Crystal", Font.PLAIN, 25));

        // เปลี่ยนสีปุ่ม Back
        back.setFont(new Font("Crystal", Font.BOLD, 30));
        back.setFocusable(false);

        // เปลี่ยนสี Textarea
        display.setBackground(Color.decode("#0B739A"));
        // เปลี่ยนสี Font Textarea
        display.setForeground(Color.WHITE);

        try(FileInputStream fis = new FileInputStream("wordArray.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            FileInputStream fis1 = new FileInputStream("wordDict.dat");
            ObjectInputStream ois1 = new ObjectInputStream(fis1)) {
            wordArray = (String[])ois.readObject();
            wordDict = (HashMap<String, String>)ois1.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String temp = "\n";
        temp += "    Your Dictionary Has "+(record.size()-1)+" / 1000 Word(s)\n";

        for(String key : record.keySet()) {
            if(key.equals("!")) {
                continue;
            }
            temp += "    "+key+" : ";
            temp += record.get(key)+".\n";
        }

        search.setFont(new Font("Crystal", Font.BOLD, 20));
        display.setText(temp);
        this.add(scroll);
        display.setCaretPosition(0);
        this.add(search, BorderLayout.SOUTH);
        this.add(back, BorderLayout.NORTH);
        back.setPreferredSize(new Dimension(1000, 50));

        search.addActionListener(this);
        back.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(back)) {
            frame.setContentPane(new DifficultySelect(frame, highscore, record));
            frame.revalidate();
            frame.repaint();
        }else {
            if(search.getText().equals("")) {
                display.getHighlighter().removeAllHighlights();
                return;
            }
            Highlighter.HighlightPainter painter =
                    new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

            int offset = display.getText().indexOf(search.getText());
            int length = search.getText().length();
            display.getHighlighter().removeAllHighlights();
            while (offset != -1)
            {
                try
                {
                    display.getHighlighter().addHighlight(offset, offset + length, painter);
                    offset = display.getText().indexOf(search.getText(), offset+1);
                }
                catch(BadLocationException ble) { System.out.println(ble); }
            }
        }
    }
}
