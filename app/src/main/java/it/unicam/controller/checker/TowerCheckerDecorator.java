package it.unicam.controller;

import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;

public class TowerCheckerDecorator extends CheckerDecorator<ChessboardTurnGame> {

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        Piece piece = c.getChessboard().getPiece(in);
        if(piece == null) return false;
        if(piece.getType() == PieceType.TOWER){
            return check(c, out, piece);
        }
        return checkNext(c, in, out);
    }

    private boolean check(ChessboardTurnGame c, int[] target, Piece tower){
        if(c.getChessboard().getPiece(target) != null)
            return false;
        if(target[0] == tower.getPosion()[0]){
            int[] vector = new int[]{0, (tower.getPosion()[1] > target[1] ? -1 : 1)};
            int steps = Math.abs(tower.getPosion()[1] - target[1]);
            return checkBetweenSpaces(c, tower.getPosion(), vector, steps);
        }else if(target[1] == tower.getPosion()[1]){
            int[] vector = new int[]{(tower.getPosion()[0] > target[0] ? -1 : 1), 0};
            int steps = Math.abs(tower.getPosion()[0] - target[0]);
            return checkBetweenSpaces(c, tower.getPosion(), vector, steps);
        }else return false;
    }

    private boolean checkBetweenSpaces(ChessboardTurnGame c, int[] start, int[] sum, int steps){
        for (int i = 0; i < steps; i++) {
            start[0] += sum[0];
            start[1] += sum[1];
            if(c.getChessboard().getPiece(start) != null)
                return false;
        }
        return true;
    }

}
