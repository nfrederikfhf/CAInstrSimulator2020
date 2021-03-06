public class IsaALUIPC {
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
            int rd = (instr >> 7) & 0x01f;
            int imm = (instr >> 12);

            switch (opcode) {

                case 37: //LUI
                    reg[rd] = imm << 12;
                    break;
                case 17: //AUIPC
                    reg[rd] = pc + (imm << 12);
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
    