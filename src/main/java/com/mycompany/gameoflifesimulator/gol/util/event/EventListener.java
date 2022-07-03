package com.mycompany.gameoflifesimulator.gol.util.event;

public interface EventListener<T extends Event> {

    void handle(T event);

}
