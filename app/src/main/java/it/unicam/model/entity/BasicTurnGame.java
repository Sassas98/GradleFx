package it.unicam.model.entity;

public class BasicTurnGame {
    private GameState state;
    private int playerNumber;
    private int currentPlayer;
    private int winerPlayer;

    public BasicTurnGame(){}

    public BasicTurnGame(GameState state, int playerNumber, int currentPlayer, int winerPlayer) {
        this.state = state;
        this.playerNumber = playerNumber;
        this.currentPlayer = currentPlayer;
        this.winerPlayer = winerPlayer;
    }

    public GameState getState() {
        return state;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public int getWinerPlayer() {
        return winerPlayer;
    }
    
}
