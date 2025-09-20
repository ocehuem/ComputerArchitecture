package processor.pipeline.stage;

import generic.*;
import generic.Event.EventType;
import processor.Clock;
import processor.Processor;
import processor.pipeline.latch.EX_MA_LatchType;
import processor.pipeline.latch.MA_RW_LatchType;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	int address;
	int data;
	
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable() && !EX_MA_Latch.isBusy())
		{	
			// Pass the bubble signal to MA_RW_Latch
			MA_RW_Latch.setIsBubbled(EX_MA_Latch.isBubbled());

			if(!EX_MA_Latch.isBubbled())
			{
				int opcode = EX_MA_Latch.getOpcode();
		
				if(opcode == 22)
				{
					// load
					address = EX_MA_Latch.getAluResult();
					// int data = containingProcessor.getMainMemory().getWord(address);
					Event event = new MemoryReadEvent(Clock.getCurrentTime(), this, containingProcessor.getL1dCache(), address);
					Simulator.getEventQueue().addEvent(event);
					if(Simulator.isDebugMode())
					{
						System.out.println("[Debug] (MA) Load request sent for address: " + address);
					}
					EX_MA_Latch.setIsBusy(true);
					// MA_RW_Latch.setLdResult(data);
					// if(Simulator.isDebugMode())
					// {
					// 	System.out.println("[Debug] (MA) Load from address " + address + " data " + data);
					// }
				}
				else if(opcode == 23)
				{
					// store
					data = EX_MA_Latch.getOp1();
					address = EX_MA_Latch.getAluResult();
					// containingProcessor.getMainMemory().setWord(address, data);
					Event event = new MemoryWriteEvent(Clock.getCurrentTime(), this, containingProcessor.getL1dCache(), address, data);
					Simulator.getEventQueue().addEvent(event);
					if(Simulator.isDebugMode())
					{
						System.out.println("[Debug] (MA) Store request sent for address: " + address + " data: " + data);
					}
					EX_MA_Latch.setIsBusy(true);
					// if(Simulator.isDebugMode())
					// {
					// 	System.out.println("[Debug] (MA) Store to address " + address + " data " + data);
					// }
				}
				else
				{
					MA_RW_Latch.setRW_enable(true);
				}

				// Set the values in MA_RW_Latch
				MA_RW_Latch.setAluResult(EX_MA_Latch.getAluResult());
				MA_RW_Latch.setOpcode(opcode);
				MA_RW_Latch.setRd(EX_MA_Latch.getRd());
				MA_RW_Latch.setR31(EX_MA_Latch.getR31());
				MA_RW_Latch.setPc(EX_MA_Latch.getPc());

				EX_MA_Latch.setMA_enable(false);
			}
			else
			{
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
			}
		}
	}

	@Override
	public void handleEvent(Event e) {
		if(e.getEventType() == EventType.MemoryResponse)
		{
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			int result = event.getValue();
			MA_RW_Latch.setLdResult(result);
			if(Simulator.isDebugMode())
			{
				System.out.println("[Debug] (MA) Load from address " + address + " data " + result);
			}
			EX_MA_Latch.setIsBusy(false);
			MA_RW_Latch.setRW_enable(true);
		}
	}
}
