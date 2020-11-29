import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileOutputStream; 
import java.io.FileNotFoundException;

public class IsaMaster {


    

        static int pc;
        static int reg[] = new int[32];
        static int memory[] = new int[1024*1024];
        
        static int progr[] = new int[200];
        public static void main(String[] args) throws FileNotFoundException, IOException {
            
            try {
                File file = new File("C:\\Users\\gthom\\Downloads\\loop.bin");
                
                FileInputStream reader = new FileInputStream(file);
                
                
                int byt;
                int j = 0;
                int offset = 0;
                int i = 0;
    
                while ((byt = reader.read()) != -1) {
                    
                    progr[j] |= (byt << offset);
                    
                    offset += 8;
                    i += 1;
                    if(i >= 4){
                        j += 1;
                        i = 0; 
                        offset = 0;
                    }
                
                }
    
                reader.close();
               
    
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            long lreg1 = 0;
            long lreg2 = 0;
            int i;
            int temp = 0;
            int rd = 0;
            int funct3 = 0;
            int rs1 = 0;
            int rs2 = 0;
            int funct7 = 0;
            int imm = 0;
            int funct12 = 0;
            long uimm = 0;
            int shamt = 0;
            reg[2] = 0x7ffffff0;
            reg[3] = 0x10000000;
            
            pc = 0;
    
            while((pc >> 2) < progr.length){
    
                int instr = progr[pc >> 2];
                int opcode = instr & 0x7f;
                
                
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
                        rd = (instr >> 7) & 0x1f;
                        funct3 = (instr >> 12) & 0x07;
                        rs1 = (instr >> 15) & 0x1f;
                        imm = (instr >> 20);
                        uimm = Long.parseLong(Integer.toBinaryString(imm), 2); //copies imm to a long i.e. unsigned 
                        shamt = imm & 0x1f;

                        
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
                        imm = (instr >>> 12);
                        //uimm = Long.parseLong(Integer.toBinaryString(imm), 2);

                       
                        break;

                    case 0x63:
                    //BRANCH Functions

                        instr = progr[pc >> 2];
                        opcode = instr & 0x7f;
                        imm = (instr >> 19) & 0xfffff000 //gets bit 32 to bit 12
                            |(((instr >> 7) & 0x1) << 11) //gets bit 7 to bit 11
                            |(((instr >> 25) & 0x3f) << 5) //gets bit 30-25 to bit 10-5
                            |(((instr >> 8) & 0xf) << 1); // gets bit 11-8 to bit 4-1  
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
                        imm = (instr >> 11) & 0xfff00000 
                        |(instr & 0xff000) 
                        |((instr & 0x100000) >> 9)
                        |((instr & 0x7fe00000) >> 20);
                        System.out.println(imm);
                        break;
                    case 0x73:
                    //ECALL
                        //instr = progr[pc >> 2];
                        //opcode = instr & 0x7f;
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
                    System.out.println("instr: " + Integer.toHexString(instr));
                    switch (funct3) {
                        case 0: //LB
                            reg[rd] = memory[reg[rs1] + imm] & (0xFF << imm); //loading 8 bits
                            break;
                        case 1: //LH
                            temp = memory[reg[rs1] + imm] & (0xFFFF << imm); // loading 16 bits into temp
                            reg[rd] = (temp >> 16); //sign extending to 32 bits
                            break;
                        case 2: //LW
                            
                            reg[rd] = memory[reg[rs1] + imm];
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
                    break; 

                    case 0x13:
                        //IMM
                        System.out.println("instr: " + Integer.toHexString(instr));
                        switch (funct3) {
                            case 0: //addi
                                //System.out.println("imm " + imm);
                                //System.out.println("rs1 " + rs1);
                                //System.out.println("rd " + rd);
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
                                break;
                            default:
                                System.out.println("Function3 " + funct3 + " Doens't exist");
                                break;
                        }
                        break;
                
                    case 0x17:
                        //AUIPC
                        System.out.println("instr: " + Integer.toHexString(instr));
                        reg[rd] = pc + (imm << 12);
                        break;
                        

                    case 0x23:
                        //STORE
                        System.out.println("instr: " + Integer.toHexString(instr));
                        switch (funct3) {
                            case 0: //SB
                                memory[(reg[rs1] + imm)] = (reg[rs2] & 0xff);
                                break;
                            case 1: //SH
                                memory[(reg[rs1] + imm)] = (reg[rs2] & 0xffff);
                                break;
                            case 2: //SW
                                memory[(reg[rs1] + imm)] = reg[rs2];
                                break;
                            default:
                                System.out.println("funct3 " + funct3 + " not yet implemented");
                                break;
                        }
                        break;
                    case 0x33:
                        System.out.println("instr: " + Integer.toHexString(instr));
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
                        System.out.println("instr: " + Integer.toHexString(instr));
                        reg[rd] = imm << 12;
                        break;
                    case 0x63:
                        //BRANCH Functions  
                        System.out.println("instr: " + Integer.toHexString(instr));  
                        switch(funct3){
                    
                            case 0x00:
                            //BEQ
                                if(reg[rs1] == reg[rs2]){
                                    pc += imm - 4;
                                }
                                break;
                            case 0x01:
                            //BNE 
                                if(reg[rs1] != reg[rs2]){
                                    pc += imm - 4;
                                }
                                break;
                            case 0x04:
                            //BLT        
                                if(reg[rs1] < reg[rs2]){
                                    pc += imm - 4;
                                }
                                break;
                            case 0x05:
                            //BGE
                                if(reg[rs1] >= reg[rs2]){
                                    pc += imm - 4;
                                }
                                break;
                            case 0x06:
                            //BLTU
                                lreg1 = lreg1 | reg[rs1];
                                lreg2 = lreg2 | reg[rs2];
                                if(lreg1 < lreg2){
                                    pc += imm - 4;
                                }
                                break;
                            case 0x07:
                            //BGEU
                                lreg1 = lreg1 | reg[rs1];
                                lreg2 = lreg2 | reg[rs2];
                                if(lreg1 >= lreg2){
                                    pc += imm - 4;
                                }
                                break;
                            default:
                                System.out.println("Function3 " + funct3 + " Doens't exist");
                                break;
                        }
                        break;
                    case 0x67:
                        //JALR
                        System.out.println("instr: " + Integer.toHexString(instr));
                        temp = pc + 8;
                        if(rd == 0){ //pc=(x[rs1]+sext(offset))&∼1
                            pc = (reg[rs1] + imm - 8);
                            //pc = (reg[rs1] + imm - 4)<<1;
                        }else{
                            pc = (reg[rs1] + imm - 8);
                            //pc = (reg[rs1] + imm - 4)<<1;
                            reg[rd] = temp;
                        }
                        break;
                    case 0x6f:
                        //JAL
                        System.out.println("instr: " + Integer.toHexString(instr));
                        if(rd == 0){
                            pc += imm - 4;
                        } else{
                            reg[rd] = pc + 8; 
                            pc += imm - 4;
                        }
                        break;
                    case 0x73:
                        //ECALL
                        System.out.println("instr: " + Integer.toHexString(instr));
                        
                        if(funct12 == 0){
                            switch (reg[10]) {
                                    case 10:
                                        for (i = 0; i < reg.length; ++i) {
                                            System.out.print(reg[i] + " ");
                                        }
                                        System.out.println();
                                        System.out.println("BYE");
                                        System.exit(0);
                                        break;
                                    default:
                                        System.out.println("a0: " + reg[10] + " can only be 10");
                                        break;
                            }
                        }
                        break;
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

            try {
        
                FileOutputStream writer = new FileOutputStream("C:\\Users\\gthom\\Downloads\\cae-lab-master\\cae-lab-master\\finasgmt\\tests\\task1\\test1.txt");
                int offset = 0;
        
                    
                    for (i = 0; i < reg.length; i++){
                        for (int j = 0; j < 4; j++){
                            writer.write((reg[i] >> offset) & 0xff);
                            offset += 8;
                        }
                        offset = 0;
                    }
        
        
                    writer.close();
        
            } catch (Exception e){System.out.println(e); }   
        
        }
    
}
    

