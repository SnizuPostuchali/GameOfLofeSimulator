package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.CellState;
import com.mycompany.gameoflifesimulator.gol.util.Property;


public class EditorViewModel {
    private Property<CellState> drawMode = new Property<>(CellState.ALIVE);

    private BoardViewModel boardViewModel;
    private Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard){
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;
    }

    public void onAppStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING){
            drawingEnabled = true;
            this.boardViewModel.getBoard().set(editorBoard);
        } else {
            drawingEnabled = false;
        }
    }

    public void boardPressed(int simX, int simY) {
        if(drawingEnabled){
            this.editorBoard.setState(simX, simY,  drawMode.get());
            this.boardViewModel.getBoard().set(this.editorBoard);
        }
    }

    public Property<CellState> getDrawMode() {
        return drawMode;
    }
}
