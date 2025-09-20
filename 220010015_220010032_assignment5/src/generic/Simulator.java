package generic;

import java.io.FileInputStream;
import java.math.BigInteger;

import processor.Clock;
import processor.Processor;

public class Simulator {
		
	static Processor processor;
	static boolean ISsimulationComplete;
	static boolean debugMode = true;
	static EventQueue eventQueue = new EventQueue();
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		processor = p;
		loadProgram(assemblyProgramFile);
		
		ISsimulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		try {
			FileInputStream file = new FileInputStream(assemblyProgramFile);
			byte[] b = new byte[4];
			file.read(b);
			int pc = new BigInteger(b).intValue();
			processor.getRegisterFile().setProgramCounter(pc);
			processor.getRegisterFile().setValue(0, 0);
			processor.getRegisterFile().setValue(1, 65535);
			processor.getRegisterFile().setValue(2, 65535);
			int i = 0;
			while(file.read(b) != -1) {
				int val = new BigInteger(b).intValue();
				processor.getMainMemory().setWord(i, val);
				i++;
			}
			file.close();
			Statistics.setnoofSI(i-pc);
		} catch (Exception e) {
			Misc.printErrorAndExit("[Error]: (Load Program) " + e.getMessage());
		}
	}
	
	public static void simulate()
	{
		Statistics.setnoofDI(0);
		Statistics.setnoofC(0);

		int i = 0;
		while(ISsimulationComplete == false)
		{
			i++;

			if(Simulator.isDebugMode()) 
			{
				System.out.println();
			}

			processor.getIF_EnableLatch().setIsBubbled(false);

			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			eventQueue.processEvents();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();

			// Update statistics
			Statistics.setnoofC(Statistics.getnoofC() + 1);
		}
		
		// Set statistics
		Statistics.setIPC((float)Statistics.getnoofDI() / Statistics.getnoofC());
		Statistics.setfreq((float)Statistics.getnoofC() / Clock.getCurrentTime());
	}
	
	public static void setSimulationComplete(boolean value)
	{
		ISsimulationComplete = value;
	}

	public static boolean isDebugMode() {
		return debugMode;
	}

	public static EventQueue getEventQueue() {
		return eventQueue;
	}
}
