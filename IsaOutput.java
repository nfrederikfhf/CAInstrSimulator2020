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
        
        FileOutputStream writer = new FileOutputStream("C:\\Users\\gthom\\Downloads\\cae-lab-master\\cae-lab-master\\finasgmt\\tests\\task1\\testout.txt");
        int i = 0;
        String fnl = "";
        String out[] = new String[reg.length];
            for (i = 0; i < reg.length; i++){
                out[i] = Integer.toBinaryString(reg[i]);
            }
            for (i = 0; i < reg.length; i++){
                fnl += out[i];
            }

            byte byt[] = fnl.getBytes();
                

            writer.write(byt);
            
            writer.close();

    } catch (Exception e){System.out.println(e); }   

    }



}

