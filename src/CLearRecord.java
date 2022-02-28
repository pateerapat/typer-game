import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class CLearRecord {
    public CLearRecord() {
        HashMap<String, String> a = new HashMap<String, String>();
        a.put("!", "!");
        try(FileOutputStream fos = new FileOutputStream("record.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(a);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CLearRecord();
    }
}
