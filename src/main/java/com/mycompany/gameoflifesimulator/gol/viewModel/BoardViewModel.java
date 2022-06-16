package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.model.Board;

import java.util.LinkedList;
import java.util.List;

public class BoardViewModel {
    private Board board;
    private List<SimpleChangeListener<Board>> boardListeners;

    public BoardViewModel() {
        boardListeners = new LinkedList<>();
    }

    public void listenToBoard(SimpleChangeListener<Board> listener){
        boardListeners.add(listener);
    }

    public void setBoard(Board board){
        this.board = board;
        notifyBoardsListeners();
    }

    private void notifyBoardsListeners() {
        for (SimpleChangeListener<Board> boardListener : boardListeners) {
            boardListener.valueChanged(this.board);
        }
    }

    public Board setBoard() {
        return board;
    }
}
