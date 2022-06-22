package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.Simulation;
import com.mycompany.gameoflifesimulator.gol.model.StandardRule;
import com.mycompany.gameoflifesimulator.gol.viewModel.BoardViewModel;
import javafx.animation.*;
import javafx.util.Duration;

public class SimulationViewModel {
    private Timeline timeline;

    private BoardViewModel boardViewModel;
    private Simulation simulation;

    public SimulationViewModel(BoardViewModel boardViewModel) {
        this.boardViewModel = boardViewModel;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> this.doStep()));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void onAppStateChanged(ApplicationState state){
        if(state == ApplicationState.SIMULATING){
            this.simulation = new Simulation(boardViewModel.getBoard(), new StandardRule());
        }
    }

    public void doStep(){
        this.simulation.step();
        this.boardViewModel.setBoard(this.simulation.getBoard());
    }

    public void start(){
        this.timeline.play();
    }

    public void stop(){
        this.timeline.stop();
    }
}