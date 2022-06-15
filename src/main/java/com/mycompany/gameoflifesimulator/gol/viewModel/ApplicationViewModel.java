package com.mycompany.gameoflifesimulator.gol.viewModel;

import java.util.ArrayList;
import java.util.List;

public class ApplicationViewModel {
    private ApplicationState currentState;
    private List<SimpleChangeListener<ApplicationState>> appStateListeners;

    public ApplicationViewModel(ApplicationState currentState) {
        this.currentState = currentState;
        this.appStateListeners = new ArrayList<>();
    }

    public void listenToAppState(SimpleChangeListener<ApplicationState> listener){
        this.appStateListeners.add(listener);
    }

    public void setCurrentState(ApplicationState newState) {
        if(newState != this.currentState){
            this.currentState = newState;
            notifyAppStateListeners();
        }
    }

    private void notifyAppStateListeners() {
        for (SimpleChangeListener<ApplicationState> appStateListener : appStateListeners) {
            appStateListener.valueChanged(this.currentState);
        }
    }
}
