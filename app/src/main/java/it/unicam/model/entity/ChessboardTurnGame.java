package it.unicam.model.entity;

public class ChessboardTurnGame extends BasicTurnGame {

    private Chessboard chessboard;

    public ChessboardTurnGame(){}

    public ChessboardTurnGame(GameState state, int playerNumber, int currentPlayer, int winnerPlayer, Chessboard chessboard) {
        super(state, playerNumber, currentPlayer, winnerPlayer);
        this.chessboard = chessboard;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }
}
