class IsaJALandJALR{
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
                int funct3 = (instr >> 10) & 0x7;
                int rs1 = (instr >> 15) & 0x01f;
                int imm = (instr >> 19);
                int imm1 = 0;
                int imm2 = 0;
                int imm3 = 0;
                int imm4 = 0;

            switch(opcode){
                case 0x6f:
                    rd = (instr >> 7) & 0x01f;
                    imm1 = (instr >> 12) & 0xff;
                    imm2 = (instr >> 20) & 0x1;
                    imm3 = (instr >> 21) & 0x3ff;
                    imm4 = (instr >> 31);
                case 0x67:
                    rd = (instr >> 7) & 0x01f;
                    funct3 = (instr >> 10) & 0x7;
                    rs1 = (instr >> 15) & 0x01f;
                    imm = (instr >> 19);
            }    
    
                switch (opcode) {
    
                    case 0x6f:
                        
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