public class IsaImmediate {

    static int pc;
    static int reg[] = new int[6];

    // Here the first program hard coded as an array
    static int progr[] = {
            // As minimal RISC-V assembler example
            0x00200093, // addi x1 x0 2
            0xFFD0A213, // SLTI x4, x1,-3 ecpected 0 
            0x8000B293, // SLTIU x5, x1,4096 expected 1
            0x00300113, // addi x2 x0 3
            //0x002081b3, // add x3 x1 x2
    };

    public static void main(String[] args){

        int pc = 0;
        

        while((pc >> 2) < progr.length){

            int instruction = progr[pc >> 2];
            //int opcode = instruction & 0x7f;

            int rd = (instruction >> 7) & 0x1f;
            int funct3 = (instruction >> 12) & 0x07;
            int rs1 = (instruction >> 15) & 0x1f;
            int imm = (instruction >> 20);
            long uimm = Long.parseLong(Integer.toBinaryString(imm), 2); //copies imm to a long i.e. unsigned 
            
    
/* 
addi: 0x13 function 3: 000
slti: funct3: 010
slti(u):   funt3: 011
andi:    func3: 111
ori:     func3: 110
xori:     func3: 100
*/

            switch (funct3) {
                case 0: //addi
                    reg[rd] = reg[rs1] + imm;
                    break;
                case 2: //SLTI
                    imm = imm >> (32-12);
                    if (reg[rs1] < imm){
                        reg[rd] = 1;
                    } else {
                        reg[rd] = 0;
                    }
                    break;
                case 3: //SLTIU
                    if(reg[rs1] < uimm){
                        reg[rd] = 1;
                    } else{
                        reg[rd] = 0;
                    }
                    break;
                case 4: //XORI
                    reg[rd] = rs1 ^ (imm);
                    break;
                case 6: //ORI
                    reg[rd] = rs1 | (imm);
                    break;
                case 7: //ANDI
                    reg[rd] = rs1 & (imm);
                default:
                System.out.println("Function3 " + funct3 + " Doens't exist");
                    break;
            }
    
            pc += 4; // One instruction is four bytes            
        
            for (int i = 0; i < reg.length; i++) {
            System.out.print(reg[i] + " ");
            }
            System.out.println();
        }
        System.out.println("Program exit");
    }
}