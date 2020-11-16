import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class IsaFileReader {

    static int progr[] = new int[20];

    public static void main(String[] args) throws FileNotFoundException, IOException { 

        try (FileInputStream reader = new FileInputStream(new File("C:\\Users\\gthom"))) {
            int byt;
            while ((byt = reader.read()) != -1) {
                for (int i = 0; i < 4; i++){
                    
                }
            }

            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        
    }

}