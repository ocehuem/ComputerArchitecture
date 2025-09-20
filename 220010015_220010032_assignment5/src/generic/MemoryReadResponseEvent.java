package generic;

public class MemoryReadResponseEvent extends Event {

	int value;
	
	public MemoryReadResponseEvent(long eventTime, Element requestingElement, Element processingElement, int value) {
		super(eventTime, EventType.MemoryReadResponse, requestingElement, processingElement);
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
