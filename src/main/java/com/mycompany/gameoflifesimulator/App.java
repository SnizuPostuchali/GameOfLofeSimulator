package com.mycompany.gameoflifesimulator;

import com.mycompany.gameoflifesimulator.gol.InfoBar;
import com.mycompany.gameoflifesimulator.gol.MainView;
import com.mycompany.gameoflifesimulator.gol.Toolbar;
import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.BoundedBoard;
import com.mycompany.gameoflifesimulator.gol.view.SimulationCanvas;
import com.mycompany.gameoflifesimulator.gol.viewModel.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel();
        BoardViewModel boardViewModel = new BoardViewModel();
        Board board = new BoundedBoard(20, 12);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);
        SimulationViewModel simulationViewModel = new SimulationViewModel(boardViewModel);

        appViewModel.getApplicationState().listen(editorViewModel::onAppStateChanged);
        appViewModel.getApplicationState().listen(simulationViewModel::onAppStateChanged);

        boardViewModel.getBoard().set(board);

        SimulationCanvas simulationCanvas = new SimulationCanvas(editorViewModel, boardViewModel);
        Toolbar toolbar = new Toolbar(editorViewModel, appViewModel, simulationViewModel);

        InfoBar infoBar = new InfoBar(editorViewModel);

        MainView mainView = new MainView(editorViewModel);
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