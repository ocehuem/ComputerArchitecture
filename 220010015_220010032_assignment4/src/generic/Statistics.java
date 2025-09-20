package generic;

import java.io.PrintWriter;

public class Statistics {
	static int noofSI;// number of static inst
	static int noofDI;// number of dynamic inst
	static int noofC;// number of cycles
	static int noofS;// number of stalls
	static int noofIB;// number of incorrect branches
	static float IPC;// instructions per cycle
	static float freq;// frequency

	public static void printStatistics(String statFile) {
		try {
			PrintWriter writer = new PrintWriter(statFile);

			writer.println("Number of static instructions executed in the assembly file = " + noofSI);
			writer.println("Number of dynamic instructions executed in the assembly file= " + noofDI);
			writer.println("Number of cycles taken to complete = " + noofC);
			writer.println("IPC = " + IPC);
			writer.println("Frequency = " + freq + " GHz");
			writer.println("Number of stalls in the assembly file= " + noofS);
			writer.println("Number of incorrect branches in the assembly file= " + noofIB);

			writer.close();
		} catch (Exception e) {
			Misc.printErrorAndExit(e.getMessage());
		}
	}

	public static void setnoofSI(int noofSI) {
		Statistics.noofSI = noofSI;
	}

	public static int getnoofSI() {
		return noofSI;
	}

	public static void setnoofDI(int noofDI) {
		Statistics.noofDI = noofDI;
	}

	public static int getnoofDI() {
		return noofDI;
	}

	public static void setnoofC(int noofC) {
		Statistics.noofC = noofC;
	}

	public static int getnoofC() {
		return noofC;
	}

	public static void setIPC(float IPC) {
		Statistics.IPC = IPC;
	}

	public static float getIPC() {
		return IPC;
	}

	public static void setfreq(float freq) {
		Statistics.freq = freq;
	}

	public static float getfreq() {
		return freq;
	}

	public static void setnoofS(int noofS) {
		Statistics.noofS = noofS;
	}

	public static int getnoofS() {
		return noofS;
	}

	public static void setnoofIB(int noofIB) {
		Statistics.noofIB = noofIB;
	}

	public static int getnoofIB() {
		return noofIB;
	}
}
