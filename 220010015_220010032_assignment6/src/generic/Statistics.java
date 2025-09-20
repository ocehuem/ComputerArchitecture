package generic;

import java.io.PrintWriter;

public class Statistics {
	
	// TODO add your statistics here
	static int noofSI;
	static int noofDI;
	static int noofC;
	static int noofS;
	static int noofIB;
	static float IPC;
	static float freq;
	

	public static void printStatistics(String statFile)
	{
		try
		{
			PrintWriter writer = new PrintWriter(statFile);
			
			writer.println(IPC);

			writer.close();
		}
		catch(Exception e)
		{
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

	public static void setFreq(float freq) {
		Statistics.freq = freq;
	}

	public static float getFreq() {
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
