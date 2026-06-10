package it.unicam.controller;

public interface GameController<I, O> {
    
    public O execute(I input);

    public O makePCAction();
    
}
