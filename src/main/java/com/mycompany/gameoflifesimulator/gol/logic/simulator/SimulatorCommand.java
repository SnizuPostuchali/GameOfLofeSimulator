package com.mycompany.gameoflifesimulator.gol.logic.simulator;

import com.mycompany.gameoflifesimulator.gol.command.Command;
import com.mycompany.gameoflifesimulator.gol.state.SimulatorState;

public interface SimulatorCommand extends Command<SimulatorState> {
    @Override
    void execute(SimulatorState simulatorState);
}
