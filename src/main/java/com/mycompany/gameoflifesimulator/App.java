package com.mycompany.gameoflifesimulator;

import com.mycompany.gameoflifesimulator.gol.MainView;
import com.mycompany.gameoflifesimulator.gol.model.Board;
import com.mycompany.gameoflifesimulator.gol.model.BoundedBoard;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationState;
import com.mycompany.gameoflifesimulator.gol.viewModel.ApplicationViewModel;
import com.mycompany.gameoflifesimulator.gol.viewModel.BoardViewModel;
import com.mycompany.gameoflifesimulator.gol.viewModel.EditorViewModel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) {
        ApplicationViewModel appViewModel = new ApplicationViewModel(ApplicationState.EDITING);
        BoardViewModel boardViewModel = new BoardViewModel();

        Board board = new BoundedBoard(10, 10);
        EditorViewModel editorViewModel = new EditorViewModel(boardViewModel, board);
        appViewModel.listenToAppState(editorViewModel::onAppStateChanged);

        MainView mainView = new MainView(appViewModel, boardViewModel, editorViewModel);
        Scene scene = new Scene(mainView, 640, 480);
        stage.setScene(scene);
        stage.show();

        boardViewModel.setBoard(board);
    }
    public static void main(String[] args) {
        launch();
    }

}