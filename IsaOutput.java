import java.io.FileOutputStream; 
import java.lang.Integer;
import java.io.File;

public class IsaOutput{
    static int reg[] = new int[8];


    public static void main(String[] args) {

        /*
        FileOutputStream file = new FileOutputStream("file.bin", "+bw");
        regs.foreach(reg => file.write(reg));
        file.close();
        */
        
    //C:\\Users\\gthom\\Downloads\\cae-lab-master\\cae-lab-master\\finasgmt\\tests\\task1\\testout.txt

        for (int j = 0; j < reg.length; j++){
            reg[j] = j;
        } 
    try {
        
        FileOutputStream writer = new FileOutputStream("C:\\Users\\gthom\\Downloads\\cae-lab-master\\cae-lab-master\\finasgmt\\tests\\task1\\test1.txt");
        int i = 0, j, offset = 0;
        
        /*String fnl = "";
        String out[] = new String[reg.length];
            for (i = 0; i < reg.length; i++){
                out[i] = Integer.toBinaryString(reg[i]);
            }
            for (i = 0; i < reg.length; i++){
                fnl += out[i];
            }

            byte byt[] = fnl.getBytes();
                for (i = 0; i < byt.length; i++){
                    byt[i] = (byte) (byt[i] >>> byt.length/byt[0]);
                }

            writer.write(byt);
            */

            
            for (i = 0; i < reg.length; i++){
                for (j = 0; j < 4; j++){
                    writer.write((reg[i] >> offset) & 0xff);
                    offset += 8;
                }
                offset = 0;
            }


            writer.close();

    } catch (Exception e){System.out.println(e); }   

    }



}

