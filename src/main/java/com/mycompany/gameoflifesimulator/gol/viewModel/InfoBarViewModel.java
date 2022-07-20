package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.model.CellPosition;
import com.mycompany.gameoflifesimulator.gol.model.CellState;
import com.mycompany.gameoflifesimulator.gol.util.Property;

public class InfoBarViewModel {

    private Property<CellState> currentDrawMode = new Property<>(CellState.ALIVE);
    private Property<CellPosition> cursorPosition = new Property<>();

    public Property<CellState> getCurrentDrawMode() {
        return currentDrawMode;
    }

    public Property<CellPosition> getCursorPosition() {
        return cursorPosition;
    }
}
