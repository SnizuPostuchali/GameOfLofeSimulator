package com.mycompany.gameoflifesimulator.gol.command;

public interface Command<T> {
    void execute(T t);
}
