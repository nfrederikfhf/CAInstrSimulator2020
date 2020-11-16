import java.io.FileInputStream;



public class IsaLoad {
    
    static int pc;
    static int reg[] = new int[12];

    static int memory[] = new int[256000];
    static int progr[] = {
    
    
    };

    
    public static void main(String[] args) {
        int pc = 0;




        while((pc >> 2) < progr.length){

            int instruction = progr[pc >> 2];

            int opcode = instruction & 0x7f;
            int rd = (instruction >> 7) & 0x1f;
            int funct3 = (instruction >> 12) & 0x07;
            int rs1 = (instruction >> 15) & 0x1f;
            int imm = (instruction >> 20);
            int temp = 0;

            //opcode must be 0000011 / 0x03

            switch (funct3) {
                case 0: //LB
                    reg[rd] = memory[rs1] & (0xFF << imm); //loading 8 bits
                    break;
                case 1: //LH
                    temp = memory[rs1] & (0xFFFF << imm); // loading 16 bits into temp
                    reg[rd] = (temp >> 16); //sign extending to 32 bits
                    break;
                case 2: //LW
                    reg[rd] = memory[rs1];
                    break;
                case 4: //LBU
                    temp = memory[rs1] & 0xFF;
                    reg[rd] = temp >>> 24;
                    break;
                case 5: //LHU
                    temp = memory[rs1] & 0xFFFF;
                    reg[rd] = temp >>> 16;
                    break;
                default:
                    System.out.println("Not a load instruction");
                    break;
            } 

            pc += 4;

            for (int i = 0; i < reg.length; i++) {
                System.out.print(reg[i] + " ");
                }
            System.out.println();

        }

        System.out.println("Program exit");

    }
}
