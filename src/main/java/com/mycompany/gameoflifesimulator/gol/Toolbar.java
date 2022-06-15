
package com.mycompany.gameoflifesimulator.gol;

import com.mycompany.gameoflifesimulator.gol.model.*;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationState;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class Toolbar extends ToolBar{
    
    private MainView mainView;
    private ApplicationViewModel applicationViewModel;
    private Simulator simulator;
    
    public Toolbar(MainView mainView, ApplicationViewModel applicationViewModel){
        this.mainView = mainView;
        this.applicationViewModel = applicationViewModel;
        Button draw = new Button("Draw");
        draw.setOnAction(this::handleDraw);
        Button erase = new Button("Erase");
        erase.setOnAction(this::handleErase);
        Button step = new Button("Step");
        step.setOnAction(this::handleStep);
        Button reset = new Button("Reset");
        reset.setOnAction(this::handleReset);
        Button start = new Button("Start");
        start.setOnAction(this::handleStart);
        Button stop = new Button("Stop");
        stop.setOnAction(this::handleStop);
    
        this.getItems().addAll(draw, erase, reset, start, stop, step);  
    }

    private void handleDraw(ActionEvent actionEvent) {
        System.out.println("draw");
        this.mainView.setDrawMode(CellState.ALIVE);
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("erase");
        this.mainView.setDrawMode(CellState.DEAD);
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("step");
        switchToSimulatingState();
        this.mainView.getSimulation().step();
        this.mainView.draw();
    }
    
    private void switchToSimulatingState(){
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        this.simulator = new Simulator(this.mainView, this.mainView.getSimulation());
    }

    private void handleReset(ActionEvent actionEvent) {
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
        this.simulator = null;
        this.mainView.draw();
    }

    private void handleStart(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulator.start();
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulator.stop();
    }
}
