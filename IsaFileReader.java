import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class IsaFileReader {

    static int progr[] = new int[32];
    
    public static void main(String[] args) throws FileNotFoundException, IOException { 

        try {
            FileInputStream reader = new FileInputStream(new File("C:\\Users\\gthom\\Downloads\\cae-lab-master\\cae-lab-master\\finasgmt\\tests\\task1\\shift.bin"));

            int byt;
            int j = 0;
            int offset = 0;
            int i = 0;

            while ((byt = reader.read()) != -1) {
                
                progr[j] |= (byt << offset);
                offset += 8;
                i += 1;
                if(i >= 4){
                    j += 1;
                    i = 0; 
                    offset = 0;
                }
            
            }

            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < progr.length; ++i) {
            System.out.print( Integer.toHexString(progr[i]) + " ");
    }

}
}