package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.CellPosition;
import com.mycompany.gameoflifesimulator.gol.util.Property;


public class BoardViewModel {

    private Property<Board> board = new Property<>();


    private Property<CellPosition> cursorPosition = new Property<>();

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }

    public BoardViewModel() {
    }

    public Property<Board> getBoard(){
        return board;
    }

}