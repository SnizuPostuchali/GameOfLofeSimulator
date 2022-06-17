package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.CellState;

import java.util.LinkedList;
import java.util.List;

public class EditorViewModel {
    private CellState drawMode = CellState.ALIVE;
    private List<SimpleChangeListener<CellState>> drawModListeners;
    private BoardViewModel boardViewModel;
    private Board editorBoard;
    private boolean drawingEnabled = true;

    public EditorViewModel(BoardViewModel boardViewModel, Board initialBoard){
        this.boardViewModel = boardViewModel;
        this.editorBoard = initialBoard;
        this.drawModListeners = new LinkedList<>();
    }

    public void onAppStateChanged(ApplicationState state){
        if(state == ApplicationState.EDITING){
            drawingEnabled = true;
            this.boardViewModel.setBoard(editorBoard);
        } else {
            drawingEnabled = false;
        }
    }

    public void listenToDrawMode(SimpleChangeListener<CellState> listener){
        drawModListeners.add(listener);
    }

    public void setDrawMode(CellState drawMode){
        this.drawMode = drawMode;
        notifyDrawModeListeners();
    }

    private void notifyDrawModeListeners() {
        for (SimpleChangeListener<CellState> drawModListener : drawModListeners) {
            drawModListener.valueChanged(drawMode);
        }
    }

    public void boardPressed(int simX, int simY) {
        if(drawingEnabled){
            this.editorBoard.setState(simX, simY,  drawMode);
            this.boardViewModel.setBoard(this.editorBoard);
        }
    }
}
