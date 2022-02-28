import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.util.HashMap;

public class Main extends JFrame implements WindowListener {

    private DifficultySelect ds;
    private int highscore;
    private HashMap<String, String> record;

    public Main() {

        File f = new File("record.dat");
        if(f.exists()) {
            try(FileInputStream fis = new FileInputStream("record.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);) {
                    record = (HashMap<String, String>)ois.readObject();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        f = new File("highscore.dat");
        if(f.exists()) {
            try (FileInputStream fis = new FileInputStream("highscore.dat");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
                highscore = (Integer) ois.readObject();
                ds = new DifficultySelect(this, highscore, record);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        this.setTitle("THE AQUATYPE!");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 500);

        this.add(ds);

        this.addWindowListener(this);
    }

    public static void main(String[] args) {
        new Main();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println(highscore);
        try(FileOutputStream fos = new FileOutputStream("highscore.dat");
            FileOutputStream fos1 = new FileOutputStream("record.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos1);
            ObjectOutputStream oos1 = new ObjectOutputStream(fos);) {
            oos.writeObject(record);
            oos1.writeObject(highscore);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
