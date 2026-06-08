package it.unicam.controller;

import it.unicam.model.entity.BasicTurnGame;
import it.unicam.model.entity.GameState;

public interface StateTurnGameHandler<G extends BasicTurnGame> {
    public GameState getGameState(G g, CheckerDecorator<G> c);
    public int getWinnerNumber(G g, CheckerDecorator<G> c);
}
