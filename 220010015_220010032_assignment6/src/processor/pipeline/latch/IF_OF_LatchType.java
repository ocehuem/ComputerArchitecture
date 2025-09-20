package processor.pipeline.latch;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	int instruction;
	int pc;
	boolean isBubbled = false;
	boolean isBusy = false;
	
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int getInstruction() {
		return instruction;
	}

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public int getPc() {
		return pc;
	}

	public void setPc(int pc) {
		this.pc = pc;
	}

	public boolean isBubbled() {
		return isBubbled;
	}

	public void setIsBubbled(boolean isBubbled) {
		this.isBubbled = isBubbled;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setIsBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}





}
