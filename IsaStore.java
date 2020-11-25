public class IsaStore {
    static int pc;
    static int reg[] = new int[4];
    static int memory[] = new int[256000];

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
            int funct3 = (instr >> 12) & 0x7;
            int rs1 = (instr >> 15) & 0x01f;
            int rs2 = (instr >>20) & 0x1f;
            int imm = ((instr >> 7) & 0x1f) | ((instr >> 25) << 5);

            //opcode 0x23
            switch (funct3) {
                case 0:
                    memory[(rs1 << imm)] = (reg[rs2] & 0xff);
                    break;
                case 1:
                    memory[(rs1 << imm)] = (reg[rs2] & 0xffff);
                    break;
                case 2: 
                    memory[(rs1 << imm)] = reg[rs2];
                    break;
                default:
                    System.out.println("funct3 " + funct3 + " not yet implemented");
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
    


    

