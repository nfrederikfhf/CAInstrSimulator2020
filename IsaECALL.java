public class IsaECALL {
    
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
            int rd = (instr >> 7) & 0x01f;
            int rs1 = (instr >> 15) & 0x01f;
            int imm = (instr >> 20);
            int funct12 = instr >> 20;
            int i;

            // opcode: 0x73
            if(funct12 == 0){
            switch (reg[11]) {

                    case 1:
                        System.out.println(reg[12]);
                        break;
                    case 4:
                        System.out.println(String.valueOf(reg[11]));
                        break;
                    case 10:
                        for (i = 0; i < reg.length; ++i) {
                            System.out.print(reg[i] + " ");
                        }
                        System.exit(0);
                        break;
                    case 11: 
                        System.out.println((char)(reg[12]));
                        break;
                    case 17:
                        for (i = 0; i < reg.length; ++i) {
                            System.out.print(reg[i] + " ");
                        }
                        System.exit(reg[12]);
                        break;
                    default:
                        System.out.println("funct12 " + funct12 + " not yet implemented");
                        break;
                }
            }

            pc += 4; // One instruction is four bytes
            
            for (i = 0; i < reg.length; ++i) {
                System.out.print(reg[i] + " ");
            }
            System.out.println();
        }

        System.out.println("Program exit");

    }

}


