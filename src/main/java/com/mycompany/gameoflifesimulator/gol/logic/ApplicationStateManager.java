package com.mycompany.gameoflifesimulator.gol.logic;

import com.mycompany.gameoflifesimulator.gol.util.Property;


public class ApplicationStateManager {

    private Property<ApplicationState> applicationState = new Property<>(ApplicationState.EDITING);

    public ApplicationStateManager() {
    }

    public Property<ApplicationState> getApplicationState(){
        return applicationState;
    }
}