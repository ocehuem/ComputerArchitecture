package generic;

import java.io.PrintWriter;

public class Statistics {
	static int noofI;// number of instructions
	static int noofC;// number of cycles

	public static void printStatistics(String statFile) {
		try {
			PrintWriter writer = new PrintWriter(statFile);

			writer.println("Number of instructions executed = " + noofI);
			writer.println("Number of cycles taken = " + noofC);

			writer.close();
		} catch (Exception e) {
			Misc.printErrorAndExit(e.getMessage());
		}
	}

	public static void setnoofI(int noofI) {
		Statistics.noofI = noofI;
	}

	public static void setnoofC(int noofC) {
		Statistics.noofC = noofC;
	}

	public static int getnoofI() {
		return noofI;
	}

	public static int getnoofC() {
		return noofC;
	}
}
