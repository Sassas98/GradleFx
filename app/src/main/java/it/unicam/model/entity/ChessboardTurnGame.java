package it.unicam.model.entity;

import java.util.Arrays;
import java.util.List;

public class ChessboardTurnGame extends BasicTurnGame {

    private Chessboard chessboard;

    public ChessboardTurnGame(){}

    public ChessboardTurnGame(GameState state, int playerNumber, PlayerColor currentPlayer, Chessboard chessboard) {
        super(state, playerNumber, currentPlayer);
        this.chessboard = chessboard;
    }

    public Chessboard getChessboard() {
        return chessboard;
    }

    public List<Piece> getPiecesOfPlayer(PlayerColor color){
        return Arrays.asList(chessboard.getPieces()).stream().filter(x -> x.getColor() == color).toList();
    }
}
