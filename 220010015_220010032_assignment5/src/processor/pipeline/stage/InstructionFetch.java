package processor.pipeline.stage;


import processor.Clock;
import generic.Element;
import generic.Event;
import generic.MemoryReadResponseEvent;
import generic.Simulator;
import generic.Statistics;
import generic.*;
import processor.Processor;
import processor.pipeline.latch.EX_IF_LatchType;
import processor.pipeline.latch.IF_EnableLatchType;
import processor.pipeline.latch.IF_OF_LatchType;

public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	int PC;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{
		
		if(IF_EnableLatch.isIF_enable() && !IF_EnableLatch.isBusy())
		{
			// Pass the bubble signal to the next stage
			IF_OF_Latch.setIsBubbled(IF_EnableLatch.isBubbled());
			
			if(!IF_EnableLatch.isBubbled())
			{
				// Update PC
				if(containingProcessor.getIsBranchTaken())
				{
					containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getBranchPC());
					containingProcessor.setIsBranchTaken(false);
					if(Simulator.isDebugMode())
					{
						System.out.println("[Debug] (IF) Branch taken, PC updated to " + containingProcessor.getBranchPC());
					}
				}
				// IF stage
				PC = containingProcessor.getRegisterFile().getProgramCounter();

				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (IF) Fetching instruction from memory");
				}
				// Create MemoryReadEvent
				Event event = new MemoryReadEvent(Clock.getCurrentTime(), this, containingProcessor.getMainMemory(), PC);
				// Add event to the event queue
				Simulator.getEventQueue().addEvent(event);

				// Set IF busy
				IF_EnableLatch.setIsBusy(true);
			}
			else
			{
				IF_OF_Latch.setOF_enable(true);
			}
		}
	}

	@Override
	public void handleEvent(Event e)
	{
		if(IF_OF_Latch.isBusy())
		{
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			if(e.getEventType() == Event.EventType.MemoryReadResponse)
			{
				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (IF) Instruction fetched from " + containingProcessor.getRegisterFile().getProgramCounter());
				}

				MemoryReadResponseEvent event = (MemoryReadResponseEvent) e;
				
				int instruction = event.getValue();
				IF_OF_Latch.setPc(PC);
				IF_OF_Latch.setInstruction(instruction);

				Statistics.setnoofDI(Statistics.getnoofDI() + 1);

				containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter() + 1);

				if(Simulator.isDebugMode())
				{
					System.out.println("[Debug] (IF) PC incremented to " + containingProcessor.getRegisterFile().getProgramCounter());
				}

				// Set IF not busy
				IF_EnableLatch.setIsBusy(false);

				// Set OF_enable
				IF_OF_Latch.setOF_enable(true);
			}
		}
	}
}
