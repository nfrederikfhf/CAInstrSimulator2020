public class IsaR2RO {

    static int pc;
    static int reg[] = new int[6];
    
    static int progr[] = {
        0x00200093, // addi x1 x0 2
        0x00300113, // addi x2 x0 3
        0x002081b3, // add x3 x1 x2 0000000 f7 00010 rs2 00001 rs1 000 f3 0001 1 rd 011 0011 op
        0x002081b3,

    };
    public static void main(String[] args) {

        pc = 0;

        while((pc >> 2) < progr.length){

            int instr = progr[pc >> 2];
            int opcode = instr & 0x7f;
            int rd = (instr >> 7) & 0x01f;
            int funct3 = (instr >> 12) & 0x7;
            int rs1 = (instr >> 15) & 0x01f;
            int rs2 = (instr >> 20) & 0x01f;
            int funct7 = (instr >> 25);
            int imm = 0;
            switch(opcode){
                    
                case 0x13:    
                    rd = (instr >> 7) & 0x01f;
                    rs1 = (instr >> 15) & 0x01f;
                    imm = (instr >> 20);
            
                case 0x33:
                    rd = (instr >> 7) & 0x01f;
                    funct3 = (instr >> 12) & 0x7;
                    rs1 = (instr >> 15) & 0x01f;
                    rs2 = (instr >> 20) & 0x01f;
                    funct7 = (instr >> 25);
                
            }
            switch (opcode) {

                case 0x13:
                    reg[rd] = reg[rs1] + imm;
                    break;
                case 0x33:
                    //ADD
                    if (funct3 == 0x00 && funct7 == 0x00){
                        reg[rd] = reg[rs1] + reg[rs2];
                    }
                    //SLT
                    if (funct3 == 0x02 && funct7 == 0x00){
                        if(reg[rs1]>reg[rs2]){
                            reg[rd] = reg[rs1];
                        }
                        else{
                            reg[rd] = reg[rs2];
                        }
                    }
                    //SLTU
                    if (funct3 == 0x03 && funct7 == 0x00){
                        if(reg[rs1]>reg[rs2]){
                            reg[rd] = reg[rs1];
                        }
                        else{
                            reg[rd] = reg[rs2];
                        }
                    }
                    //AND
                    if(funct3 == 0x07 && funct7 == 0x00){
                        reg[rd] = reg[rs1] & reg[rs2];
                    }
                    //OR
                    if(funct3 == 0x06 && funct7 == 0x00){
                        reg[rd] = reg[rs1] | reg[rs2];
                    }
                    //XOR
                    if(funct3 == 0x04 && funct7 == 0x00){
                        reg[rd] = reg[rs1] ^ reg[rs2];
                    }
                    //SLL
                    if(funct3 == 0x01 && funct7 == 0x00){
                        reg[rd] = (reg[rs1]<<reg[rs2]); 
                    }
                    //SRL
                    if(funct3 == 0x05 && funct7 == 0x00){
                        reg[rd] = (reg[rs1]<<reg[rs2]); 
                    }
                    //SUB
                    if(funct3 == 0x00 && funct7 == 0x20){
                        reg[rd] = reg[rs1] - reg[rs2];
                    }
                    //SRA
                    if(funct3 == 0x05 && funct7 == 0x20){
                        reg[rd] = (reg[rs1]>>reg[rs2]);    
                    }

                    break;
                default:
                    System.out.println("Opcode " + opcode + "not implemented");
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
    

