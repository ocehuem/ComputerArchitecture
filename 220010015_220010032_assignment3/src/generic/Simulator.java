package generic;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import processor.Clock;
import processor.Processor;

public class Simulator {

	static Processor processor;
	static boolean ISsimulationComplete;

	public static void setupSimulation(String assemblyProgramFile, Processor p) {
		Simulator.processor = p;
		try {
			loadProgram(assemblyProgramFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ISsimulationComplete = false;
	}

	static void loadProgram(String assemblyProgramFile) throws IOException {
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 * in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 * x0 = 0
		 * x1 = 65535
		 * x2 = 65535
		 */
		InputStream file = null;
		try {
			file = new FileInputStream(assemblyProgramFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		DataInputStream data = new DataInputStream(file);

		int address = -1;
		while (data.available() > 0) {
			int next = data.readInt();
			if (address == -1) {
				processor.getRegisterFile().setProgramCounter(next);
			} else {
				processor.getMainMemory().setWord(address, next);
			}
			address += 1;
		}

		processor.getRegisterFile().setValue(0, 0);
		processor.getRegisterFile().setValue(1, 65535);
		processor.getRegisterFile().setValue(2, 65535);

	}

	public static void simulate() {
		while (ISsimulationComplete == false) {
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();

			Statistics.setnoofI(Statistics.getnoofI() + 1);
			Statistics.setnoofC(Statistics.getnoofC() + 1);
		}

	}

	public static void setISsimulationComplete(boolean value) {
		ISsimulationComplete = value;
	}
}
