import sun.tools.serialver.resources.serialver;

public class IsaBranch {
 
    static int pc;
    static int reg[] = new int[4];

    // Here the first program hard coded as an array
    static int progr[] = {
            // As minimal RISC-V assembler example
            0x00200093, // addi x1 x0 2
            0x00300113, // addi x2 x0 3
            0x002081b3, // add x3 x1 x2
    };

    public static void main(String[] args) {

        System.out.println("Hello RISC-V World!");

        pc = 0;

        while((pc >> 2) < progr.length){

            int instr = progr[pc >> 2];
            int opcode = instr & 0x7f;
            int imm1 = (instr >> 7) & 0x1;
            int imm2 = (instr >> 8) & 0xF; 
            int funct3 = (instr >> 12) & 0x7;
            int rs1 = (instr >> 15) & 0x1f;
            int rs2 = (instr >> 20) & 0x1f;
            int imm3 = (instr >> 25) & 0x3f;
            int imm4 =(instr >> 31) & 0x1;

            switch (opcode) {

                case 0x63:
                    switch(funct3){
                    
                    case 0x00:
                    //BEQ

                    case 0x01:
                    //BNE 
                    
                    case 0x04:
                    //BLT        
                    
                    case 0x05:
                    //BGE

                    case 0x06:
                    //BLTU
                    
                    case 0x07:
                    //BGEU

                    default:
                    System.out.println("function " + funct3 + " not a function");
                }
                    break;
                default:
                    System.out.println("Opcode " + opcode + " not yet implemented");
                    break;
            }

            pc += 4; // One instruction is four bytes
            
            for (int i = 0; i < reg.length; ++i) {
                System.out.print(reg[i] + " ");
            }
            System.out.println();
        }

        System.out.println("Program exit");

    }


}
