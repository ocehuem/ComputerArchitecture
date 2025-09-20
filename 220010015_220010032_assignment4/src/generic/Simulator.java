package generic;

import java.io.FileInputStream;
import java.math.BigInteger;

import processor.Clock;
import processor.Processor;

public class Simulator {

	static Processor processor;
	static boolean ISsimulationComplete;
	static boolean debugMode = true;

	public static void setupSimulation(String assemblyProgramFile, Processor p) {
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);

		ISsimulationComplete = false;
	}

	static void loadProgram(String assemblyProgramFile) {

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
			while (file.read(b) != -1) {
				int val = new BigInteger(b).intValue();
				processor.getMainMemory().setWord(i, val);
				i++;
			}
			file.close();
			Statistics.setnoofSI(i - pc);
		} catch (Exception e) {
			Misc.printErrorAndExit("[Error]: (Load Program) " + e.getMessage());
		}
	}

	public static void simulate() {
		Statistics.setnoofDI(0);
		Statistics.setnoofC(0);

		int i = 0;
		while (ISsimulationComplete == false) {
			i++;
			processor.getIF_EnableLatch().setIsBubbled(false);
			processor.getRWUnit().performRW();
			if (ISsimulationComplete == true) {
				break;
			}
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();

			// Update statistics
			if (i % 5 == 0) {
				Statistics.setnoofC(Statistics.getnoofC() + 1);
			}
		}

		// Set statistics
		Statistics.setIPC((float) Statistics.getnoofDI() / Statistics.getnoofC());
		Statistics.setfreq((float) Statistics.getnoofC() / Clock.getCurrentTime());
	}

	public static void setISsimulationComplete(boolean value) {
		ISsimulationComplete = value;
	}

	public static boolean isDebugMode() {
		return debugMode;
	}
}
