package com.mycompany.gameoflifesimulator.gol;

import com.mycompany.gameoflifesimulator.gol.logic.*;
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

        Editor editor = new Editor(board);
        eventBus.listenFor(DrawModeEvent.class, editor::handle);
        eventBus.listenFor(BoardEvent.class, editor::handle);
        editor.getCursorPosition().listen(cursorPosition -> {
            boardViewModel.getCursorPosition().set(cursorPosition);
        });

        Simulator simulator = new Simulator(appViewModel);
        eventBus.listenFor(SimulatorEvent.class, simulator::handle);
        editor.getBoard().listen(editorBoard -> {
            simulator.getInitialBoard().set(editorBoard);
            boardViewModel.getBoard().set(editorBoard);
        });
        simulator.getCurrentBoard().listen(simulationBoard -> {
            boardViewModel.getBoard().set(simulationBoard);
        });

        appViewModel.getApplicationState().listen(editor::onAppStateChanged);

        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(boardViewModel, eventBus);
        Toolbar toolbar = new Toolbar(eventBus);

        InfoBarViewModel infoBarViewModel = new InfoBarViewModel();
        editor.getCursorPosition().listen(cursorPosition -> {
            infoBarViewModel.getCursorPosition().set(cursorPosition);
        });
        editor.getDrawMode().listen(drawMode -> {
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