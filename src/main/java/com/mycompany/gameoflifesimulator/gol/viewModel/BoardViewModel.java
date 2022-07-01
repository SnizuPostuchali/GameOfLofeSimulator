package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.util.Property;


public class BoardViewModel {

    private Property<Board> board = new Property<>();

    public BoardViewModel() {
    }

    public Property<Board> getBoard(){
        return board;
    }

}