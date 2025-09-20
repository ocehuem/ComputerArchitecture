package processor;

import configuration.Configuration;

import processor.memorysystem.Cache;
import processor.memorysystem.MainMemory;
import processor.memorysystem.RegisterFile;
import processor.pipeline.latch.EX_IF_LatchType;
import processor.pipeline.latch.EX_MA_LatchType;
import processor.pipeline.latch.IF_EnableLatchType;
import processor.pipeline.latch.IF_OF_LatchType;
import processor.pipeline.latch.MA_RW_LatchType;
import processor.pipeline.latch.OF_EX_LatchType;
import processor.pipeline.latch.RW_LatchType;
import processor.pipeline.stage.Execute;
import processor.pipeline.stage.InstructionFetch;
import processor.pipeline.stage.MemoryAccess;
import processor.pipeline.stage.OperandFetch;
import processor.pipeline.stage.RegisterWrite;

public class Processor {
	
	RegisterFile registerFile;
	MainMemory mainMemory;
	
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	MA_RW_LatchType MA_RW_Latch;
	RW_LatchType RW_Latch;
	
	InstructionFetch IFUnit;
	OperandFetch OFUnit;
	Execute EXUnit;
	MemoryAccess MAUnit;
	RegisterWrite RWUnit;

	Cache L1dCache;
	Cache L1iCache;

	// Control Signals
	boolean isBranchTaken;
	int branchPC;
	
	public Processor()
	{
		registerFile = new RegisterFile();
		mainMemory = new MainMemory();
		
		IF_EnableLatch = new IF_EnableLatchType();
		IF_OF_Latch = new IF_OF_LatchType();
		OF_EX_Latch = new OF_EX_LatchType();
		EX_MA_Latch = new EX_MA_LatchType();
		EX_IF_Latch = new EX_IF_LatchType();
		MA_RW_Latch = new MA_RW_LatchType();
		RW_Latch = new RW_LatchType();
		
		IFUnit = new InstructionFetch(this, IF_EnableLatch, IF_OF_Latch, EX_IF_Latch);
		OFUnit = new OperandFetch(this, IF_OF_Latch, OF_EX_Latch);
		EXUnit = new Execute(this, OF_EX_Latch, EX_MA_Latch, EX_IF_Latch);
		MAUnit = new MemoryAccess(this, EX_MA_Latch, MA_RW_Latch);
		RWUnit = new RegisterWrite(this, MA_RW_Latch, IF_EnableLatch, RW_Latch);

		L1dCache = new Cache(this, "L1dCache", Configuration.L1d_associativity, Configuration.L1d_numberOfLines, Configuration.L1d_latency);
		L1iCache = new Cache(this, "L1iCache", Configuration.L1i_associativity, Configuration.L1i_numberOfLines, Configuration.L1i_latency);
	}
	
	public void printState(int memoryStartingAddress, int memoryEndingAddress)
	{
		System.out.println(registerFile.getContentsAsString());
		
		System.out.println(mainMemory.getContentsAsString(memoryStartingAddress, memoryEndingAddress));		
	}

	public RegisterFile getRegisterFile() {
		return registerFile;
	}

	public void setRegisterFile(RegisterFile registerFile) {
		this.registerFile = registerFile;
	}

	public MainMemory getMainMemory() {
		return mainMemory;
	}

	public void setMainMemory(MainMemory mainMemory) {
		this.mainMemory = mainMemory;
	}

	public InstructionFetch getIFUnit() {
		return IFUnit;
	}

	public OperandFetch getOFUnit() {
		return OFUnit;
	}

	public Execute getEXUnit() {
		return EXUnit;
	}

	public MemoryAccess getMAUnit() {
		return MAUnit;
	}

	public RegisterWrite getRWUnit() {
		return RWUnit;
	}

	public boolean getIsBranchTaken() {
		return isBranchTaken;
	}

	public void setIsBranchTaken(boolean isBranchTaken) {
		this.isBranchTaken = isBranchTaken;
	}

	public int getBranchPC() {
		return branchPC;
	}

	public void setBranchPC(int branchPC) {
		this.branchPC = branchPC;
	}

	public IF_EnableLatchType getIF_EnableLatch() {
		return IF_EnableLatch;
	}

	public IF_OF_LatchType getIF_OF_Latch() {
		return IF_OF_Latch;
	}

	public OF_EX_LatchType getOF_EX_Latch() {
		return OF_EX_Latch;
	}

	public EX_MA_LatchType getEX_MA_Latch() {
		return EX_MA_Latch;
	}

	public MA_RW_LatchType getMA_RW_Latch() {
		return MA_RW_Latch;
	}

	public EX_IF_LatchType getEX_IF_Latch() {
		return EX_IF_Latch;
	}

	public RW_LatchType getRW_Latch() {
		return RW_Latch;
	}

	public Cache getL1dCache() {
		return L1dCache;
	}

	public Cache getL1iCache() {
		return L1iCache;
	}
}
