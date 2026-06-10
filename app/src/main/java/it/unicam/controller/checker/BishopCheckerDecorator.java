package it.unicam.controller.checker;

import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;

public class BishopCheckerDecorator extends CheckerDecorator<ChessboardTurnGame> {

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        Piece piece = c.getChessboard().getPiece(in);
        if(piece == null) return false;

        if(piece.getType() == PieceType.BISHOP || piece.getType() == PieceType.QUEEN){
            return check(c, out, piece);
        }

        return checkNext(c, in, out);
    }

    private boolean check(ChessboardTurnGame c, int[] target, Piece bishop){
        Piece p = c.getChessboard().getPiece(target);

        if(p != null && p.getColor() == bishop.getColor())
            return false;

        int diffX = target[0] - bishop.getPosion()[0];
        int diffY = target[1] - bishop.getPosion()[1];

        if(Math.abs(diffX) != Math.abs(diffY))
            return false;

        int[] direction = new int[]{
            diffX > 0 ? 1 : -1,
            diffY > 0 ? 1 : -1
        };

        int steps = Math.abs(diffX);

        return checkBetweenSpaces(c, bishop.getPosion(), direction, steps);
    }

    private boolean checkBetweenSpaces(ChessboardTurnGame c, int[] start, int[] direction, int steps){
        for (int i = 1; i < steps; i++) {
            int[] pos = new int[]{
                start[0] + direction[0] * i,
                start[1] + direction[1] * i
            };

            if(c.getChessboard().getPiece(pos) != null)
                return false;
        }

        return true;
    }
}