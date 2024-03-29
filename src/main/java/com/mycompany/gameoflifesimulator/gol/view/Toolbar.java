package com.mycompany.gameoflifesimulator.gol.view;

import com.mycompany.gameoflifesimulator.gol.logic.editor.DrawModeEvent;
import com.mycompany.gameoflifesimulator.gol.logic.simulator.SimulatorEvent;
import com.mycompany.gameoflifesimulator.gol.model.*;
import com.mycompany.gameoflifesimulator.gol.util.event.EventBus;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class Toolbar extends ToolBar{


    private EventBus eventBus;

    public Toolbar(EventBus eventBus){
        this.eventBus = eventBus;
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
        this.eventBus.emit(new DrawModeEvent(CellState.ALIVE));
    }

    private void handleErase(ActionEvent actionEvent) {
        System.out.println("erase");
        this.eventBus.emit(new DrawModeEvent(CellState.DEAD));
    }

    private void handleStep(ActionEvent actionEvent) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STEP));
    }


    private void handleReset(ActionEvent actionEvent) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.RESET));
    }

    private void handleStart(ActionEvent actionEvent) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.START));
    }

    private void handleStop(ActionEvent actionEvent) {
        this.eventBus.emit(new SimulatorEvent(SimulatorEvent.Type.STOP));
    }
}