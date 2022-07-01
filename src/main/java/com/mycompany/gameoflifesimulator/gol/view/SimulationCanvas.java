package com.mycompany.gameoflifesimulator.gol.view;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.CellState;
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

    public SimulationCanvas(EditorViewModel editorViewModel, BoardViewModel boardViewModel) {
        this.editorViewModel = editorViewModel;
        this.boardViewModel = boardViewModel;
        boardViewModel.getBoard().listen(this::draw);

        this.canvas = new Canvas(400, 400);
        this.canvas.setOnMousePressed(this::handleDraw);
        this.canvas.setOnMouseDragged(this::handleDraw);

        this.canvas.widthProperty().bind(this.widthProperty());
        this.canvas.heightProperty().bind(this.heightProperty());

        this.getChildren().add(this.canvas);

        this.affine = new Affine();
        this.affine.appendScale(40, 40);
    }

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        draw(boardViewModel.getBoard().get());
    }

    private void handleDraw(MouseEvent event) {
        Point2D simCoord = this.getSimulationCoordinates(event);

        int simX = (int)simCoord.getX();
        int simY = (int)simCoord.getY();

        System.out.println(simX + ", " + simY);
        this.editorViewModel.boardPressed(simX, simY);
    }


    private Point2D getSimulationCoordinates(MouseEvent mouseEvent){
        double mouseX = mouseEvent.getX();
        double mouseY = mouseEvent.getY();

        try {
            return this.affine.inverseTransform(mouseX, mouseY);
        } catch (NonInvertibleTransformException ex) {
            throw new RuntimeException("Non invertible transform");
        }
    }

    private void draw(Board board){
        GraphicsContext g = this.canvas.getGraphicsContext2D();
        g.setTransform(this.affine);

        g.setFill(Color.LIGHTGRAY);
        g.fillRect(0, 0, 400, 400);

        drawSimulation(board);

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
