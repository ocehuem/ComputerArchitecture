package generic;

public class MemoryWriteResponseEvent extends Event {

	int value;
	
	public MemoryWriteResponseEvent(long eventTime, Element requestingElement, Element processingElement) {
		super(eventTime, EventType.MemoryWriteResponse, requestingElement, processingElement);
	}

}
