package com.mycompany.gameoflifesimulator.gol;

import com.mycompany.gameoflifesimulator.gol.model.CellState;
import com.mycompany.gameoflifesimulator.gol.viewModel.EditorViewModel;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;


public class MainView extends BorderPane{

    private EditorViewModel editorViewModel;

    public MainView(EditorViewModel editorViewModel) {
        this.editorViewModel = editorViewModel;
//        this.canvas.setOnMouseMoved(this::handleMoved);
        this.setOnKeyPressed(this::onKeyPressed);
    }


    private void onKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.D){
            this.editorViewModel.getDrawMode().set(CellState.ALIVE);
            System.out.println("draw Mode");
        } else if(keyEvent.getCode() == KeyCode.E){
            this.editorViewModel.getDrawMode().set(CellState.DEAD);
            System.out.println("erase Mode");
        }
    }

//    private void handleMoved(MouseEvent mouseEvent){
//        Point2D simCoord = this.getSimulationCoordinates(mouseEvent);
//        this.infoBar.setCursorPosition((int)simCoord.getX(), (int)simCoord.getY());
//    }
}