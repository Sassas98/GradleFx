package it.unicam.controller;

import it.unicam.model.entity.ChessboardTurnGame;

public class ChessMoveChecker extends CheckerDecorator<ChessboardTurnGame> {
    
    private CheckerDecorator<ChessboardTurnGame> checker;

    public ChessMoveChecker(){
        checker = new TowerCheckerDecorator();
    }

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        return check(c, in, out);
    }
}
