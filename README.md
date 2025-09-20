Java, Apache Ant, Git, Python (testing scripts), ToyRISC Emulator, Linux CLI
Developed a series of projects on the ToyRISC ISA, including writing assembly programs, building an assembler, and implementing single-cycle, pipelined, and cache-integrated processor simulators in Java. Enhanced the simulator with discrete-event modeling, hazard handling, and performance analysis (IPC, stalls, cache impact), gaining hands-on experience with computer architecture design and evaluation.

# Computer Architecture Laboratory Projects

This repository contains my work for the **Computer Architecture Laboratory** course, where I progressively developed and enhanced a simulator for the ToyRISC processor.  
Each assignment builds upon the previous one, introducing new architectural features and performance modeling.

Assignment 1: Programming in ToyRISC

Assignment 2: ToyRISC Assembler

Assignment 3: Simple Processor

Assignment 4: Pipelined Processor

Assignment 5: Discrete Event Simulation

Assignment 6: Caches



---

## 📌 Assignment 3: Single-Cycle Processor Simulator (ToyRISC)

- Implemented a **single-cycle processor simulator** in Java for the ToyRISC ISA.  
- Developed pipeline stages (IF, OF, EX, MA, RW) and corresponding latch mechanisms.  
- Implemented program loading, instruction execution, and tracking of cycles and instructions.  
- Verified correctness using benchmark programs and **hash-based validation**.  
- **Outcome:** Established the base framework for subsequent processor enhancements.

---

## 📌 Assignment 4: Pipelined Core Simulator  

- Upgraded the ToyRISC simulator to a **5-stage pipelined core model**.  
- Implemented pipeline stages:  
  - **IF** (Instruction Fetch)  
  - **OF** (Operand Fetch / Decode)  
  - **EX** (Execute)  
  - **MA** (Memory Access)  
  - **RW** (Register Writeback)  
- Added **pipeline latches** (IF-OF, OF-EX, EX-MA, MA-RW) to transfer results between stages.  
- Handled hazards with:  
  - **Data interlocks** – stalling OF stage until operands are ready.  
  - **Control interlocks** – flushing wrong-path instructions on branch misprediction.  
- Measured **performance metrics**:  
  - Number of cycles taken by each benchmark program.  
  - Number of stalls in OF stage due to data hazards.  
  - Number of wrong-path instructions entering the pipeline.  
- **Outcome:** Enabled instruction-level parallelism and realistic hazard handling compared to the non-pipelined version.  

---

## 📌 Assignment 5: Discrete Event Simulator

- Upgraded the ToyRISC simulator to a **discrete-event simulation (DES)** model.  
- Implemented event-driven components with custom classes:
  - `EventQueue`, `Event`, `MemoryReadEvent`, `MemoryResponseEvent`, etc.  
- Modeled latencies for:
  - Main memory (for instruction fetch, load/store).  
  - Functional units such as ALU, multiplier, divider.  
- Measured **performance metrics**:
  - Number of cycles taken by each benchmark program.  
  - Throughput in terms of **Instructions Per Cycle (IPC)**.  
- **Outcome:** Enabled fine-grained performance analysis with realistic latency modeling.

---

## 📌 Assignment 6: Cache-Integrated Simulator

- Extended the processor simulator by adding **L1 caches**:
  - **L1 Instruction Cache (L1i)** between IF stage and main memory.  
  - **L1 Data Cache (L1d)** between MA stage and main memory.  
- Implemented a unified `Cache` class with:
  - Fully associative mapping.  
  - Write-through policy.  
  - Configurable cache sizes (16B – 1kB) and latencies.  
- Conducted experiments:
  - Varied cache sizes and measured effect on **IPC** across benchmarks.  
  - Correlated benchmark characteristics with cache performance trends.  
- **Outcome:** Demonstrated the impact of cache hierarchy on processor performance.

---

## 🔧 Skills & Tools

- **Programming:** Java  
- **Concepts:** Computer Architecture, Processor Pipelines, Caches, Discrete Event Simulation  
- **Performance Metrics:** Instruction Count, Cycles, IPC, Cache Analysis  
- **Version Control:** Git for iterative development across assignments  

---

## 📊 Summary

These projects collectively simulate a **ToyRISC processor with pipelining, event-driven execution, and cache hierarchy**.  
They demonstrate architectural trade-offs and performance analysis across different benchmarks.
