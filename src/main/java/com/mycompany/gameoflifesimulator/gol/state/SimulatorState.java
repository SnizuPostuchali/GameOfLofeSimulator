package com.mycompany.gameoflifesimulator.gol.state;

import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.util.Property;

public class SimulatorState {
    private Property<Board> board = new Property<>();

    public SimulatorState(Board board){
        this.board.set(board);
    }

    public Property<Board> getBoard(){
        return board;
    }
}
