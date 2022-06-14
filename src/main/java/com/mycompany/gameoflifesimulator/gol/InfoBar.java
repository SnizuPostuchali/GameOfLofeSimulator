
package com.mycompany.gameoflifesimulator.gol;

import com.mycompany.gameoflifesimulator.gol.model.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 *
 * @author Master
 */
public class InfoBar extends HBox{
    private static String drawModeFormat = "Draw Mode: %s";
    private static String cursorPositionFormat = "Cursor: (%d, %d)"; 
    
    private Label cursor;
    private Label editingTool;
    
    public InfoBar(){
        this.cursor = new Label();
        this.editingTool = new Label();
        
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        this.getChildren().addAll(this.editingTool, spacer, this.cursor);
        
    }
    
    public void setDrawMode(CellState drawMode){
        String drawModeString;
        if(drawMode == CellState.ALIVE){
            drawModeString = "Drawing";
        } else {
            drawModeString = "Erasing";
        }
        this.editingTool.setText(String.format(drawModeFormat, drawModeString));
    }
    
    public void setCursorPosition(int x, int y){
        this.cursor.setText(String.format(cursorPositionFormat, x, y));
    }
}
