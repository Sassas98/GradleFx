package it.unicam.controller.state;

import it.unicam.model.entity.BasicTurnGame;
import it.unicam.model.entity.GameState;
import it.unicam.model.entity.PlayerColor;

public interface StateTurnGameController<G extends BasicTurnGame> {
    public GameState getGameState(G g);
    public PlayerColor getWinnerPlayer(G g);
}
