package com.mycompany.gameoflifesimulator.gol.view;

import com.mycompany.gameoflifesimulator.gol.logic.DrawModeEvent;
import com.mycompany.gameoflifesimulator.gol.model.CellState;
import com.mycompany.gameoflifesimulator.gol.util.event.EventBus;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;


public class MainView extends BorderPane{

    private EventBus eventBus;

    public MainView(EventBus eventBus) {
        this.eventBus = eventBus;
        this.setOnKeyPressed(this::onKeyPressed);
    }


    private void onKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.D){
            this.eventBus.emit(new DrawModeEvent(CellState.ALIVE));
            System.out.println("draw Mode");
        } else if(keyEvent.getCode() == KeyCode.E){
            this.eventBus.emit(new DrawModeEvent(CellState.DEAD));
            System.out.println("erase Mode");
        }
    }
}