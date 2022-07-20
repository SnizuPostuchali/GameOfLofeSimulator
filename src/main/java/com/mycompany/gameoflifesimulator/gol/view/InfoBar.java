package com.mycompany.gameoflifesimulator.gol.view;

import com.mycompany.gameoflifesimulator.gol.model.*;
import com.mycompany.gameoflifesimulator.gol.viewModel.InfoBarViewModel;
import javafx.scene.control.*;
import javafx.scene.layout.*;


public class InfoBar extends HBox{
    private static String drawModeFormat = "Draw Mode: %s";
    private static String cursorPositionFormat = "Cursor: (%d, %d)";

    private Label cursor;
    private Label editingTool;

    public InfoBar(InfoBarViewModel infoBarViewModel){

        this.cursor = new Label();
        this.editingTool = new Label();

        infoBarViewModel.getCurrentDrawMode().listen(this::setDrawMode);
        infoBarViewModel.getCursorPosition().listen(this::setCursorPosition);

        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(this.editingTool, spacer, this.cursor);

    }

    private void setDrawMode(CellState drawMode){
        String drawModeString;
        if(drawMode == CellState.ALIVE){
            drawModeString = "Drawing";
        } else {
            drawModeString = "Erasing";
        }
        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }

    private void setCursorPosition(CellPosition cursorPosition){
        this.cursor.setText(String.format(cursorPositionFormat, cursorPosition.getX(), cursorPosition.getY()));
    }
}