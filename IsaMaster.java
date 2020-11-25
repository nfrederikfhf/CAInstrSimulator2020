public class IsaMaster {

        static int pc;
        static int reg[] = new int[6];
        static int memory[] = new int[256000];

        static int progr[] = {
            

        };
        public static void main(String[] args) {

            long lreg1 = 0;
            long lreg2 = 0;
            int i;
            int temp = 0;
    
            pc = 0;
    
            while((pc >> 2) < progr.length){
    
                int instr = 0;
                int opcode = 0;
                int rd = 0;
                int funct3 = 0;
                int rs1 = 0;
                int rs2 = 0;
                int funct7 = 0;
                int imm = 0;
                int funct12 = 0;
                long uimm = 0;
                int shamt = 0;
                
                switch(opcode){
                //Switches formats relative to opcode        
                    case 0x3:
                    //LOAD
                        opcode = instr & 0x7f;
                        rd = (instr >> 7) & 0x1f;
                        funct3 = (instr >> 12) & 0x07;
                        rs1 = (instr >> 15) & 0x1f;
                        imm = (instr >> 20);
                        break;
                    case 0x13:
                    //Immediate functions  
                        rd = (instr >> 7) & 0x01f;
                        rs1 = (instr >> 15) & 0x01f;
                        imm = (instr >> 20);
                        break;
                    case 0x17:
                    //AUIPC
                    
                        instr = progr[pc >> 2];
                        opcode = instr & 0x7f;
                        rd = (instr >> 7) & 0x01f;
                        imm = (instr >> 12);
                        break;
                    case 0x23:
                    //STORE 
                        instr = progr[pc >> 2];
                        opcode = instr & 0x7f;
                        funct3 = (instr >> 12) & 0x7;
                        rs1 = (instr >> 15) & 0x01f;
                        rs2 = (instr >>20) & 0x1f;
                        imm = ((instr >> 7) & 0x1f) | ((instr >> 25) << 5);
                        break;
                    case 0x33:
                    //R2R functions
                        rd = (instr >> 7) & 0x01f;
                        funct3 = (instr >> 12) & 0x7;
                        rs1 = (instr >> 15) & 0x01f;
                        rs2 = (instr >> 20) & 0x01f;
                        funct7 = (instr >> 25);
                        break;
                    case 0x37:
                    //LUI
                    
                        instr = progr[pc >> 2];
                        opcode = instr & 0x7f;
                        rd = (instr >> 7) & 0x01f;
                        imm = (instr >> 12);
                        break;

                    case 0x63:
                    //BRANCH Functions

                        instr = progr[pc >> 2];
                        opcode = instr & 0x7f;
                        imm = ((instr >> 25) << 5) | ((instr >> 7) & 0x1f);  
                        funct3 = (instr >> 12) & 0x7;
                        rs1 = (instr >> 15) & 0x1f;
                        rs2 = (instr >> 20) & 0x1f;
                        break;
                    case 0x67:
                    //JALR
                        rd = (instr >> 7) & 0x01f;
                        funct3 = (instr >> 10) & 0x7;
                        rs1 = (instr >> 15) & 0x01f;
                        imm = (instr >> 19);
                        break;
                    case 0x6f:
                    //JAL
                        rd = (instr >> 7) & 0x01f;
                        imm = (instr >> 12);
                        break;
                    case 0x73:
                    //ECALL
                        instr = progr[pc >> 2];
                        opcode = instr & 0x7f;
                        rd = (instr >> 7) & 0x01f;
                        rs1 = (instr >> 15) & 0x01f;
                        imm = (instr >> 20);
                        funct12 = instr >> 20;
                        break;
                    default:
                        System.out.println("Opcode " + opcode + " not implemented");
                        break;
                }
                
                switch (opcode) {
    
                    case 0x3:
                    //LOAD
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

                    case 0x13:
                        //ADDI
                        switch (funct3) {
                            case 0: //addi
                                reg[rd] = reg[rs1] + imm;
                                break;
                            case 1: // SLLI
                                imm = 0x000 | shamt; // making sure signed bit is 0
                                reg[rd] = reg[rs1] << imm;
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
                            case 5: //SRLI or SRAI
                                if (((imm >> 5) | 0) == 0){ // testing whether it is SRLI or SRAI
                                    reg[rd] = reg[rs1] >> uimm; // logical
                                } else {
                                    reg[rd] = reg[rs1] >> shamt; // arithmetic
                                }
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
                    
                    case 0x17:
                        //AUIPC
                        reg[rd] = pc + (imm << 12);
                        break;
                        

                    case 0x23:
                        //STORE
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
                    case 0x37:
                        //LUI
                        reg[rd] = imm << 12;
                        break;
                    case 0x63:
                        //BRANCH Functions    
                        switch(funct3){
                    
                            case 0x00:
                            //BEQ
                                if(reg[rs1] == reg[rs2]){
                                    pc = imm;
                                }
                                break;
                            case 0x01:
                            //BNE 
                                if(reg[rs1] != reg[rs2]){
                                    pc = imm;
                                }
                                break;
                            case 0x04:
                            //BLT        
                                if(reg[rs1] < reg[rs2]){
                                    pc = imm;
                                }
                                break;
                            case 0x05:
                            //BGE
                                if(reg[rs1] >= reg[rs2]){
                                    pc = imm;
                                }
                                break;
                            case 0x06:
                            //BLTU
                                lreg1 = lreg1 | reg[rs1];
                                lreg2 = lreg2 | reg[rs2];
                                if(lreg1 < lreg2){
                                    pc = imm;
                                }
                                break;
                            case 0x07:
                            //BGEU
                                lreg1 = lreg1 | reg[rs1];
                                lreg2 = lreg2 | reg[rs2];
                                if(lreg1 >= lreg2){
                                    pc = imm;
                                }
                                break;
                            default:
                                System.out.println("Function3 " + funct3 + " Doens't exist");
                                break;
                        }
                    case 0x67:
                        //JALR
                        reg[rd] = pc + 4;
                        pc = reg[rs1] + imm;
                        break;
                    case 0x6f:
                        //JAL
                        reg[rd] = pc+4;
                        pc = imm;
                        break;
                    case 0x73:
                        //ECALL
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

                    default:
                        System.out.println("Opcode " + opcode + "not implemented");
                        break;
                }
                pc += 4;    
                
                for (i = 0; i < reg.length; i++) {
                    System.out.print(reg[i] + " ");
                }
                System.out.println();
            }
    
            System.out.println("Program exit");
    
        }
    
}
