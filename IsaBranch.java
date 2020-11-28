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

        long lreg1 = 0;
        long lreg2 = 0;

        pc = 0;

        while((pc >> 2) < progr.length){

            int instr = progr[pc >> 2];
            int opcode = instr & 0x7f;
            int imm = (instr >> 19) & 0xfffff000 //gets bit 32 to bit 12
                    |(((instr >> 7) & 0x1) << 11) //gets bit 7 to bit 11
                    |(((instr >> 25) & 0x3f) << 5) //gets bit 30-25 to bit 10-5
                    |(((instr >> 8) & 0xf) << 1); // gets bit 11-8 to bit 4-1  
            int funct3 = (instr >> 12) & 0x7;
            int rs1 = (instr >> 15) & 0x1f;
            int rs2 = (instr >> 20) & 0x1f;

            switch (opcode) {

                case 0x63:
                    switch(funct3){
                    
                    case 0x00:
                    //BEQ
                        if(reg[rs1] == reg[rs2]){
                            pc = imm;
                        }

                    case 0x01:
                    //BNE 
                        if(reg[rs1] != reg[rs2]){
                            pc = imm;
                        }
                    case 0x04:
                    //BLT        
                        if(reg[rs1] < reg[rs2]){
                            pc = imm;
                        }
                    case 0x05:
                    //BGE
                        if(reg[rs1] >= reg[rs2]){
                            pc = imm;
                        }
                    case 0x06:
                    //BLTU
                        lreg1 = lreg1 | reg[rs1];
                        lreg2 = lreg2 | reg[rs2];
                        if(lreg1 < lreg2){
                            pc = imm;
                        }
                    case 0x07:
                    //BGEU
                        lreg1 = lreg1 | reg[rs1];
                        lreg2 = lreg2 | reg[rs2];
                        if(lreg1 >= lreg2){
                            pc = imm;
                        }
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
