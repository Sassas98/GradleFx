package it.unicam.model.entity;

import java.io.Serializable;

public class BasicTurnGame implements Serializable {
    private GameState state;
    private int playerNumber;
    private PlayerColor currentPlayer;
    private PlayerColor winnerPlayer;

    public BasicTurnGame(){}

    public BasicTurnGame(GameState state, int playerNumber, PlayerColor currentPlayer) {
        this.state = state;
        this.playerNumber = playerNumber;
        this.currentPlayer = currentPlayer;
        this.winnerPlayer = null;
    }

    public GameState getState() {
        return state;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public PlayerColor getCurrentPlayer() {
        return currentPlayer;
    }

    public PlayerColor getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public void setCurrentPlayer(PlayerColor currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setWinnerPlayer(PlayerColor winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }
    
}
