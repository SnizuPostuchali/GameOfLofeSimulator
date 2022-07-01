package com.mycompany.gameoflifesimulator.gol.viewModel;

import com.mycompany.gameoflifesimulator.gol.util.Property;


public class ApplicationViewModel {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public ApplicationViewModel() {
    }

    public Property<ApplicationState> getApplicationState(){
        return applicationState;
    }
}