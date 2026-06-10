package it.unicam.controller.checker;

import it.unicam.model.entity.ChessboardTurnGame;

public class ChessMoveChecker extends CheckerDecorator<ChessboardTurnGame> {
    
    private final CheckerDecorator<ChessboardTurnGame> checker;

    public ChessMoveChecker(){
        checker = new TowerCheckerDecorator();
    }

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        return checker.check(c, in, out);
    }
}
