package processor.pipeline.stage;

import generic.Misc;
import generic.Simulator;
import generic.Statistics;
import processor.Processor;
import processor.pipeline.latch.IF_EnableLatchType;
import processor.pipeline.latch.IF_OF_LatchType;
import processor.pipeline.latch.OF_EX_LatchType;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performOF()
	{
		IF_OF_Latch.setIsBusy(OF_EX_Latch.isBusy());

		if(IF_OF_Latch.isOF_enable() && !OF_EX_Latch.isBusy())
		{

			// Pass the bubble to the next latch
			containingProcessor.getOF_EX_Latch().setIsBubbled(IF_OF_Latch.isBubbled());
			
			if (!IF_OF_Latch.isBubbled())
			{
				String binaryInstruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());
				while(binaryInstruction.length() < 32)
				{
					binaryInstruction = "0" + binaryInstruction;
				}

				// OF stage
				// opcode
				int opcode = Integer.parseInt(binaryInstruction.substring(0, 5), 2);

				// imm
				int imm = Misc.getIntFromBinaryString(binaryInstruction.substring(15, 32));

				// branchPC
				int branchPC;
				if(opcode != 24)
				{
					// R2I
					int Imm = Misc.getIntFromBinaryString(binaryInstruction.substring(15, 32));
					branchPC = IF_OF_Latch.getPc()+Imm;
				}
				else
				{
					// RI
					Integer rd = Integer.parseInt(binaryInstruction.substring(5, 10), 2);
					Integer Imm = Misc.getIntFromBinaryString(binaryInstruction.substring(10, 32));
					branchPC = IF_OF_Latch.getPc()+rd+Imm;
				}

				// op1
				int rs1 = Integer.parseInt(binaryInstruction.substring(5, 10), 2);
				int op1 = containingProcessor.getRegisterFile().getValue(rs1);

				// op2
				int rs2 = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				int op2 = containingProcessor.getRegisterFile().getValue(rs2);

				// rd
				int rd;
				if(opcode <= 21 && opcode%2 == 0)
				{
					// R3 Type
					rd = Integer.parseInt(binaryInstruction.substring(15, 20), 2);
				}
				else if(opcode <= 21 && opcode%2 == 1)
				{
					// R2I Type
					rd = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				}
				else if(opcode >= 22 && opcode <= 23)
				{
					// R2I Type
					rd = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				}
				else if(opcode == 24)
				{
					// RI
					rd = Integer.parseInt(binaryInstruction.substring(5, 10), 2);
				}
				else
				{
					// R1I
					rd = Integer.parseInt(binaryInstruction.substring(10, 15), 2);
				}
				
				// Set in latch
				OF_EX_Latch.setOpcode(opcode);
				OF_EX_Latch.setImm(imm);
				OF_EX_Latch.setOp1(op1);
				OF_EX_Latch.setOp2(op2);
				OF_EX_Latch.setPc(IF_OF_Latch.getPc());
				OF_EX_Latch.setRd(rd);
				OF_EX_Latch.setBranchPC(branchPC);

				// Check for stall

				boolean stall = false;

				// R3 Type
				if(opcode <= 21 && opcode%2 == 0)
				{


					// Check EX_MA_Latch

					int next_rd = containingProcessor.getEX_MA_Latch().getRd();
					boolean bubbled = containingProcessor.getEX_MA_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || next_rd == rs2 || 31 == rs1 || 31 == rs2))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to EX_MA_Latch, next_rd: " + next_rd + ", rs1: " + rs1 + ", rs2: " + rs2 + " bubble: " + bubbled);
						}
					}

					// Check MA_RW_Latch

					next_rd = containingProcessor.getMA_RW_Latch().getRd();
					bubbled = containingProcessor.getMA_RW_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || next_rd == rs2 || 31 == rs1 || 31 == rs2))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to MA_RW_Latch, next_rd: " + next_rd + ", rs1: " + rs1 + ", rs2: " + rs2 + " bubble: " + bubbled);
						}
					}

					// Check RW_Latch

					next_rd = containingProcessor.getRW_Latch().getRd();
					bubbled = containingProcessor.getRW_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || next_rd == rs2 || 31 == rs1 || 31 == rs2))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to RW_Latch, next_rd: " + next_rd + ", rs1: " + rs1 + ", rs2: " + rs2 + " bubble: " + bubbled);
						}
					}

				}

				//R2I type Arithmetic

				if((opcode <= 21 && opcode%2 == 1) || (opcode == 22))
				{
					// Check EX_MA_Latch

					int next_rd = containingProcessor.getEX_MA_Latch().getRd();
					boolean bubbled = containingProcessor.getEX_MA_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || 31 == rs1))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to EX_MA_Latch, next_rd = " + next_rd + ", rs1 = " + rs1 + " bubble: " + bubbled);
						}
					}

					// Check MA_RW_Latch

					next_rd = containingProcessor.getMA_RW_Latch().getRd();
					bubbled = containingProcessor.getMA_RW_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || 31 == rs1))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to MA_RW_Latch, next_rd = " + next_rd + ", rs1 = " + rs1 + " bubble: " + bubbled);
						}
					}

					// Check RW_Latch

					next_rd = containingProcessor.getRW_Latch().getRd();
					bubbled = containingProcessor.getRW_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || 31 == rs1))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to RW_Latch, next_rd = " + next_rd + ", rs1 = " + rs1 + " bubble: " + bubbled);
						}
					}
				}

				// R2I Contol
				if(opcode == 23 || (opcode >= 25 && opcode <= 28))
				{
					// Check EX_MA_Latch

					int next_rd = containingProcessor.getEX_MA_Latch().getRd();
					boolean bubbled = containingProcessor.getEX_MA_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || next_rd == rd || 31 == rd || 31 == rs1))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to EX_MA_Latch, next_rd = " + next_rd + ", rs1 = " + rs1 + " bubble: " + bubbled);
						}
					}

					// Check MA_RW_Latch

					next_rd = containingProcessor.getMA_RW_Latch().getRd();
					bubbled = containingProcessor.getMA_RW_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || next_rd == rd || 31 == rd || 31 == rs1))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to MA_RW_Latch, next_rd = " + next_rd + ", rs1 = " + rs1 + " bubble: " + bubbled);
						}
					}

					// Check RW_Latch

					next_rd = containingProcessor.getRW_Latch().getRd();
					bubbled = containingProcessor.getRW_Latch().isBubbled();

					if(!bubbled && (next_rd == rs1 || next_rd == rd || 31 == rd || 31 == rs1))
					{
						stall = true;
						if (Simulator.isDebugMode())
						{
							System.out.println("[Debug] (OF) Stalled due to RW_Latch, next_rd = " + next_rd + ", rs1 = " + rs1 + " bubble: " + bubbled);
						}
					}
				}

				if(stall)
				{
					// Set stall
					IF_OF_Latch.setIsBusy(true);
					// Set bubble in latch
					OF_EX_Latch.setIsBubbled(true);
					Statistics.setnoofS(Statistics.getnoofS() + 1);

					OF_EX_Latch.setEX_enable(true);
				}
				else
				{
					OF_EX_Latch.setEX_enable(true);
					IF_OF_Latch.setOF_enable(false);
				}

				// Check if end
				if (opcode == 29)
				{
					containingProcessor.getIF_EnableLatch().setIF_enable(false);
					if(Simulator.isDebugMode())
					{
						System.out.println("[Debug] (OF) End detected, disabling IF");
					}
				}


				if(Simulator.isDebugMode())
				{	
					System.out.println("[Debug] (OF) Stalled: " + stall);
					System.out.println("[Debug] (OF) PC: " + IF_OF_Latch.getPc());
					System.out.println("[Debug] (OF) Opcode: " + opcode);
					System.out.println("[Debug] (OF) Rs1: " + rs1);
					System.out.println("[Debug] (OF) Rs2: " + rs2);
					System.out.println("[Debug] (OF) Rd: " + rd);
					System.out.println("[Debug] (OF) Op1: " + op1);
					System.out.println("[Debug] (OF) Op2: " + op2);
					System.out.println("[Debug] (OF) Imm: " + imm);
					System.out.println("[Debug] (OF) BranchPC: " + branchPC);

				}
			}
			else
			{
				// Set EX_enable
				OF_EX_Latch.setEX_enable(true);
				IF_OF_Latch.setOF_enable(false);
			}
		}
	}
}
