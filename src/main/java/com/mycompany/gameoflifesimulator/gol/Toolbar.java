package com.mycompany.gameoflifesimulator.gol;

import com.mycompany.gameoflifesimulator.gol.model.*;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationState;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationViewModel;
import com.mycompany.gameoflifesimulator.gol.viewModel.BoardViewModel;
import com.mycompany.gameoflifesimulator.gol.viewModel.EditorViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class Toolbar extends ToolBar{

    private ApplicationViewModel applicationViewModel;
    private BoardViewModel boardViewModel;
    private Simulator simulator;
    private EditorViewModel editorViewModel;

    public Toolbar(EditorViewModel editorViewModel, ApplicationViewModel applicationViewModel, BoardViewModel boardViewModel){
        this.editorViewModel = editorViewModel;
        this.applicationViewModel = applicationViewModel;
        this.boardViewModel = boardViewModel;
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
        this.editorViewModel.setDrawMode(CellState.ALIVE);
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("erase");
        this.editorViewModel.setDrawMode(CellState.DEAD);
    }

    private void handleStep(ActionEvent actionEvent) {
        System.out.println("step");
        switchToSimulatingState();
        this.simulator.doStep();
    }

    private void switchToSimulatingState(){
        this.applicationViewModel.setCurrentState(ApplicationState.SIMULATING);
        Simulation simulation = new Simulation(boardViewModel.setBoard(), new StandardRule());
        this.simulator = new Simulator(this.boardViewModel, simulation);
    }

    private void handleReset(ActionEvent actionEvent) {
        this.applicationViewModel.setCurrentState(ApplicationState.EDITING);
        this.simulator = null;
    }

    private void handleStart(ActionEvent actionEvent) {
        switchToSimulatingState();
        this.simulator.start();
    }

    private void handleStop(ActionEvent actionEvent) {
        this.simulator.stop();
    }
}