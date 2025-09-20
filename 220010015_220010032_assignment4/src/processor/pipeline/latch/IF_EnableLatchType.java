package processor.pipeline.latch;

public class IF_EnableLatchType {

	boolean IF_enable;
	boolean isBubbled = false;
	boolean isStalled;

	public IF_EnableLatchType() {
		IF_enable = true;
		isStalled = false;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}

	public boolean isBubbled() {
		return isBubbled;
	}

	public void setIsBubbled(boolean isBubbled) {
		this.isBubbled = isBubbled;
	}

	public boolean isStalled() {
		return isStalled;
	}

	public void setIsStalled(boolean isStalled) {
		this.isStalled = isStalled;
	}

}
