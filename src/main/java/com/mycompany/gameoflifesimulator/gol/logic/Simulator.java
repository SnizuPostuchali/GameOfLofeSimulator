package com.mycompany.gameoflifesimulator.gol.logic;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.Simulation;
import com.mycompany.gameoflifesimulator.gol.model.StandardRule;
import com.mycompany.gameoflifesimulator.gol.util.Property;
import javafx.animation.*;
import javafx.util.Duration;

public class Simulator {
    private Timeline timeline;
    private ApplicationStateManager applicationStateManager;
    private Simulation simulation;

    private Property<Board> initialBoard = new Property<>();
    private Property<Board> currentBoard = new Property<>();

    public Simulator(ApplicationStateManager applicationStateManager) {
        this.applicationStateManager = applicationStateManager;

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
        if(applicationStateManager.getApplicationState().get() != ApplicationState.SIMULATING){
            this.simulation = new Simulation(initialBoard.get(), new StandardRule());
            applicationStateManager.getApplicationState().set(ApplicationState.SIMULATING);
        }

        this.simulation.step();
        this.currentBoard.set(simulation.getBoard());
    }

    private void start(){
        this.timeline.play();
    }

    private void stop(){
        this.timeline.stop();
    }

    private void reset(){
//        this.simulation = new Simulation(initialBoard.get(), new StandardRule());
        this.applicationStateManager.getApplicationState().set(ApplicationState.EDITING);
    }

    public Property<Board> getInitialBoard() {
        return initialBoard;
    }

    public void setInitialBoard(Property<Board> initialBoard) {
        this.initialBoard = initialBoard;
    }

    public Property<Board> getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(Property<Board> currentBoard) {
        this.currentBoard = currentBoard;
    }
}