package processor.pipeline.latch;

public class IF_EnableLatchType {
	
	boolean IF_enable;
	boolean isBubbled;
	boolean isStalled;
	boolean isBusy;
	
	public IF_EnableLatchType()
	{
		IF_enable = true;
		isBubbled = false;
		isStalled = false;
		isBusy = false;
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

	public boolean isBusy() {
		return isBusy;
	}

	public void setIsBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

}
