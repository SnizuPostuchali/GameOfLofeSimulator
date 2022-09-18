package com.mycompany.gameoflifesimulator.gol;

import com.mycompany.gameoflifesimulator.gol.logic.*;
import com.mycompany.gameoflifesimulator.gol.logic.editor.BoardEvent;
import com.mycompany.gameoflifesimulator.gol.logic.editor.DrawModeEvent;
import com.mycompany.gameoflifesimulator.gol.logic.editor.Editor;
import com.mycompany.gameoflifesimulator.gol.logic.simulator.Simulator;
import com.mycompany.gameoflifesimulator.gol.logic.simulator.SimulatorEvent;
import com.mycompany.gameoflifesimulator.gol.state.EditorState;
import com.mycompany.gameoflifesimulator.gol.state.SimulatorState;
import com.mycompany.gameoflifesimulator.gol.view.InfoBar;
import com.mycompany.gameoflifesimulator.gol.view.MainView;
import com.mycompany.gameoflifesimulator.gol.view.Toolbar;
import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.BoundedBoard;
import com.mycompany.gameoflifesimulator.gol.util.event.EventBus;
import com.mycompany.gameoflifesimulator.gol.view.SimulationCanvas;
import com.mycompany.gameoflifesimulator.gol.viewModel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        EventBus eventBus = new EventBus();

        ApplicationStateManager appViewModel = new ApplicationStateManager();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board board = new BoundedBoard(20, 12);

        EditorState editorState = new EditorState(board);
        Editor editor = new Editor(editorState);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editorState.getCursorPosition().listen(cursorPosition -> {
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        SimulatorState simulatorState = new SimulatorState(board);
        Simulator simulator = new Simulator(appViewModel, simulatorState);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);
        editorState.getEditorBoard().listen(editorBoard -> {
            simulatorState.getBoard().set(editorBoard);
            boardViewModel.getBoard().set(editorBoard);
        });
        simulatorState.getBoard().listen(simulationBoard -> {
            boardViewModel.getBoard().set(simulationBoard);
        });

        appViewModel.getApplicationState().listen(editor::onAppStateChanged);
        appViewModel.getApplicationState().listen(newState -> {
            if(newState == ApplicationState.EDITING){
                boardViewModel.getBoard().set(editorState.getEditorBoard().get());
                simulatorState.getBoard().set(editorState.getEditorBoard().get());
            }
        });

        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);

        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editorState.getCursorPosition().listen(cursorPosition -> {
            infoBarViewModel.getCursorPosition().set(cursorPosition);
        });
        editorState.getDrawMode().listen(drawMode -> {
            infoBarViewModel.getCurrentDrawMode().set(drawMode);
        });
        InfoBar infoBar = new InfoBar(infoBarViewModel);

        MainView mainView = new MainView(eventBus);
        mainView.setTop(toolbar);
        mainView.setCenter(simulationCanvas);
        mainView.setBottom(infoBar);

        Scene scene = new Scene(mainView, 1200, 800);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

}