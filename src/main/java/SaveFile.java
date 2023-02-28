import java.io.FileWriter;
import java.io.IOException;

public class SaveFile {
    SaveFile(String nameFile, String s) throws IOException {
        FileWriter writer = new FileWriter(nameFile, true);
        writer.write(s);
        writer.flush();

    }

}
