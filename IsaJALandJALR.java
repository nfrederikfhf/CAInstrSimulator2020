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
    
            pc = 0;

            while((pc >> 2) < progr.length){
    
                int instr = progr[pc >> 2];
                int opcode = instr & 0x7f;
                int rd = (instr >> 7) & 0x01f;
                int funct3 = (instr >> 10) & 0x7;
                int rs1 = (instr >> 15) & 0x01f;
                int imm = (instr >> 20);

            switch(opcode){
                case 0x6f:
                //JAL
                    rd = (instr >> 7) & 0x01f;
                    imm =  (instr >> 31) & 0x1 
                    |((instr >> 12) & 0xff) 
                    |((instr >> 20) & 0x1) 
                    |((instr >> 21) & 0x3ff); 
                case 0x67:
                //JALR
                    rd = (instr >> 7) & 0x01f;
                    funct3 = (instr >> 10) & 0x7;
                    rs1 = (instr >> 15) & 0x01f;
                    imm = (instr >> 19);
                }    
    
                switch (opcode) {
                    
                    case 0x6f:
                    //JAL
                    
                    pc = imm;
                    rd = pc+4;
                        
                    case 0x67: 
                    //JALR

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