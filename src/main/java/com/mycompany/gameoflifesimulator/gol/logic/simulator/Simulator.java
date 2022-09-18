package com.mycompany.gameoflifesimulator.gol.logic.simulator;

import com.mycompany.gameoflifesimulator.gol.logic.ApplicationState;
import com.mycompany.gameoflifesimulator.gol.logic.ApplicationStateManager;
import com.mycompany.gameoflifesimulator.gol.model.Simulation;
import com.mycompany.gameoflifesimulator.gol.model.StandardRule;
import com.mycompany.gameoflifesimulator.gol.state.SimulatorState;
import javafx.animation.*;
import javafx.util.Duration;

public class Simulator {
    private Timeline timeline;
    private ApplicationStateManager applicationStateManager;
    private Simulation simulation;

    private SimulatorState state;
    private boolean reset = true;

    public Simulator(ApplicationStateManager applicationStateManager, SimulatorState state) {
        this.applicationStateManager = applicationStateManager;
        this.state = state;

        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void handle(SimulatorEvent event){
        switch (event.getEventType()){
            case START:     start();    break;
            case STOP:      stop();     break;
            case STEP:      doStep();   break;
            case RESET:     reset();    break;
        }
    }

    private void doStep(){
        if(reset){
            reset = false;
            this.simulation = new Simulation(state.getBoard().get(), new StandardRule());
            applicationStateManager.getApplicationState().set(ApplicationState.SIMULATING);
        }

        this.simulation.step();
        SimulatorCommand command = (state) -> {
            state.getBoard().set(simulation.getBoard());
        };
        command.execute(this.state);
    }

    private void start(){
        this.timeline.play();
    }

    private void stop(){
        this.timeline.stop();
    }

    private void reset(){
        reset = true;
        this.applicationStateManager.getApplicationState().set(ApplicationState.EDITING);
    }
}