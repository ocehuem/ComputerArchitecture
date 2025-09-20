package processor.pipeline.latch;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	int opcode;
	int imm;
	int op1;
	int op2;
	int pc;
	int rd;
	int r31;
	int branchPC;
	boolean isBubbled = false;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

	public int getOpcode() {
		return opcode;
	}

	public int getImm() {
		return imm;
	}

	public int getOp1() {
		return op1;
	}

	public int getOp2() {
		return op2;
	}

	public int getPc() {
		return pc;
	}


	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	
	public void setImm(int imm) {
		this.imm = imm;
	}

	public void setOp1(int op1) {
		this.op1 = op1;
	}

	public void setOp2(int op2) {
		this.op2 = op2;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public int getRd() {
		return rd;
	}

	public void setRd(int rd) {
		this.rd = rd;
	}

	public boolean isBubbled() {
		return isBubbled;
	}

	public void setIsBubbled(boolean isBubbled) {
		this.isBubbled = isBubbled;
	}

	public int getBranchPC() {
		return branchPC;
	}

	public void setBranchPC(int branchPC) {
		this.branchPC = branchPC;
	}

}
