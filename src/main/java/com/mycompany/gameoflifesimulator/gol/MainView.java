
package com.mycompany.gameoflifesimulator.gol;

import com.mycompany.gameoflifesimulator.gol.model.*;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationState;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationViewModel;
import com.mycompany.gameoflifesimulator.gol.viewModel.BoardViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.*;


public class MainView extends VBox{
    private InfoBar infoBar;
    private Canvas canvas;
    
    private Affine affine;
    private Board initialBoard;
    
    private CellState drawMode = CellState.ALIVE;

    private ApplicationViewModel appViewModel;
    private BoardViewModel boardViewModel;
    private boolean isDrawingEnabled = true;
    
    public MainView(ApplicationViewModel appViewModel, BoardViewModel boardViewModel, Board initialBoard) {
        this.appViewModel = appViewModel;
        this.boardViewModel = boardViewModel;
        this.initialBoard = initialBoard;

        this.appViewModel.listenToAppState(this::onApplicationStateChanged);
        this.boardViewModel.listenToBoard(this::onBoardChanged);
        
        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMoved);
        
        this.setOnKeyPressed(this::onKeyPressed);
        
        Toolbar toolbar = new Toolbar(this, appViewModel, boardViewModel);
        
        this.infoBar = new InfoBar();
        this.infoBar.setDrawMode(this.drawMode);
        this.infoBar.setCursorPosition(0, 0);
        
        Pane spacer = new Pane();
        spacer.setMinSize(0, 0);
        spacer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        
        this.getChildren().addAll(toolbar, this.canvas, spacer, infoBar);
        
        this.affine = new Affine();
        this.affine.appendScale(40, 40);
    }

    private void onBoardChanged(Board board) {
        draw(board);
    }

    private void onApplicationStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING){
            this.isDrawingEnabled = true;
            this.boardViewModel.setBoard(this.initialBoard);
        } else if(state == ApplicationState.SIMULATING){
            this.isDrawingEnabled = false;
        } else {
            throw new IllegalArgumentException("Unsupported application state " + state.name());
        }
    }
    
    
    private void handleMoved(MouseEvent mouseEvent){
        Point2D simCoord = this.getSimulationCoordinates(mouseEvent);
        
        this.infoBar.setCursorPosition((int)simCoord.getX(), (int)simCoord.getY());
    }
    
    
    private void onKeyPressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.D){
            this.drawMode = CellState.ALIVE;
            System.out.println("draw Mode");
        } else if(keyEvent.getCode() == KeyCode.E){
            this.drawMode = CellState.DEAD;
            System.out.println("erase Mode");
        }
    }

    private void handleDraw(MouseEvent event) {
        
        if(!isDrawingEnabled){
            return;
        }
        
        Point2D simCoord = this.getSimulationCoordinates(event);

        int simX = (int)simCoord.getX();
        int simY = (int)simCoord.getY();

        System.out.println(simX + ", " + simY);
        this.initialBoard.setState(simX, simY,  drawMode);
        this.boardViewModel.setBoard(this.initialBoard);
    }
    
    
    private Point2D getSimulationCoordinates(MouseEvent mouseEvent){
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();
        
        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
            return simCoord;
        } catch (NonInvertibleTransformException ex) {
            throw new RuntimeException("Non invertable transform");
        }
    }

    
    public void draw(Board board){
       GraphicsContext g = this.canvas.getGraphicsContext2D();
       g.setTransform(this.affine);
       
       g.setFill(Color.LIGHTGRAY);
       g.fillRect(0, 0, 400, 400);
       
       drawSimulation(board);
       
       g.setStroke(Color.BLUE);
       g.setLineWidth(0.05f);
       for(int x=0; x<= board.getWidth(); x++){
           g.strokeLine(x, 0, x, 10);
       }
       
       for(int y=0; y<= board.getHeight(); y++){
           g.strokeLine(0, y, 10, y);
       }
    }
    
    private void drawSimulation(Board simulationToDraw){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setFill(Color.BLACK);
        for(int x=0; x< simulationToDraw.getWidth(); x++){
           for(int y=0; y< simulationToDraw.getHeight(); y++){
               if(simulationToDraw.getState(x, y) == CellState.ALIVE){             
                   g.fillRect(x, y, 1, 1);
               }
           }
       }
    }
    void setDrawMode(CellState newDrawMode) {
        this.drawMode = newDrawMode;
        this.infoBar.setDrawMode(newDrawMode);
    }
}
