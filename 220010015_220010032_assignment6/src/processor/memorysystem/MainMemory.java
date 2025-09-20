package processor.memorysystem;

import configuration.Configuration;
import generic.*;
import generic.Event.EventType;
import processor.Clock;

public class MainMemory implements Element {
	int[] memory;
	
	public MainMemory()
	{
		memory = new int[65536];
	}
	
	public int getWord(int address)
	{
		return memory[address];
	}
	
	public void setWord(int address, int value)
	{
		memory[address] = value;
	}
	
	public String getContentsAsString(int startingAddress, int endingAddress)
	{
		if(startingAddress == endingAddress)
			return "";
		
		StringBuilder sb = new StringBuilder();
		sb.append("\nMain Memory Contents:\n\n");
		for(int i = startingAddress; i <= endingAddress; i++)
		{
			sb.append(i + "\t\t: " + memory[i] + "\n");
		}
		sb.append("\n");
		return sb.toString();
	}

	@Override
	public void handleEvent(Event e)
	{
		// Handle Memory Read
		if (e.getEventType() == EventType.MemoryRead)
		{
			// Get the memory request
			MemoryReadEvent event = (MemoryReadEvent) e;
			
			// Add response event to the event queue
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, event.getRequestingElement(), getWord(event.getAddressToReadFrom()), event.getAddressToReadFrom()));
		}

		// Handle Memory Write
		else if (e.getEventType() == EventType.MemoryWrite)
		{
			// Get the memory request
			MemoryWriteEvent event = (MemoryWriteEvent) e;
			
			// Write the word to memory
			setWord(event.getAddressToWriteTo(), event.getValue());
			
			// Add response event to the event queue
			Simulator.getEventQueue().addEvent(new MemoryResponseEvent(Clock.getCurrentTime() + Configuration.mainMemoryLatency, this, event.getRequestingElement(), event.getValue(), event.getAddressToWriteTo()));
		}
	}
}
