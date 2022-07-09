package com.mycompany.gameoflifesimulator.gol.view;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.CellPosition;
import com.mycompany.gameoflifesimulator.gol.model.CellState;
import com.mycompany.gameoflifesimulator.gol.util.event.EventBus;
import com.mycompany.gameoflifesimulator.gol.viewModel.BoardEvent;
import com.mycompany.gameoflifesimulator.gol.viewModel.BoardViewModel;
import com.mycompany.gameoflifesimulator.gol.viewModel.EditorViewModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.NonInvertibleTransformException;

public class SimulationCanvas extends Pane {

    private Canvas canvas;
    private Affine affine;
    private EditorViewModel editorViewModel;
    private BoardViewModel boardViewModel;
    private EventBus eventBus;

    public SimulationCanvas(EditorViewModel editorViewModel, BoardViewModel boardViewModel, EventBus eventBus) {
        this.editorViewModel = editorViewModel;
        this.boardViewModel = boardViewModel;
        this.eventBus = eventBus;
        boardViewModel.getBoard().listen(this::draw);
        editorViewModel.getCursorPosition().listen(cellPosition -> draw(boardViewModel.getBoard().get()));

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);
        this.canvas.setOnMouseMoved(this::handleMouseMoved);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(40, 40);
    }

    private void handleMouseMoved(MouseEvent event) {
        CellPosition cursorPosition = this.getSimulationCoordinates(event);
        BoardEvent boardEvent = new BoardEvent(BoardEvent.Type.CURSOR_MOVED, cursorPosition);
        eventBus.emit(boardEvent);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw(boardViewModel.getBoard().get());
    }

    private void handleDraw(MouseEvent event) {
        CellPosition cursorPosition = this.getSimulationCoordinates(event);
        BoardEvent boardEvent = new BoardEvent(BoardEvent.Type.PRESSED, cursorPosition);
        eventBus.emit(boardEvent);
    }


    private CellPosition getSimulationCoordinates(MouseEvent event){
        double mouseX = event.getX();
        double mouseY = event.getY();

        try {
            Point2D simCoord = this.affine.inverseTransform(mouseX, mouseY);
            return new CellPosition((int)simCoord.getX(), (int)simCoord.getY());
        } catch (NonInvertibleTransformException ex) {
            throw new RuntimeException("Non invertible transform");
        }
    }

    private void draw(Board board){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 400, 400);

        this.drawSimulation(board);


        if(editorViewModel.getCursorPosition().isPresent()){
            CellPosition cursor = editorViewModel.getCursorPosition().get();
            g.setFill(new Color(0.3, 0.3, 0.3, 0.5));
            g.fillRect(cursor.getX(), cursor.getY(), 1, 1);
        }

        g.setStroke(Color.BLUE);
        g.setLineWidth(0.05f);
        for(int x=0; x<= board.getWidth(); x++){
            g.strokeLine(x, 0, x, board.getHeight());
        }

        for(int y=0; y<= board.getHeight(); y++){
            g.strokeLine(0, y, board.getWidth(), y);
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
}
