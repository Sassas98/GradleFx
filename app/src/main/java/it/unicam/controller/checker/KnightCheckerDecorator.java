package it.unicam.controller.checker;

import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;

public class KnightCheckerDecorator extends CheckerDecorator<ChessboardTurnGame> {

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        Piece piece = c.getChessboard().getPiece(in);

        if(piece == null) return false;

        if(piece.getType() == PieceType.KNIGHT){
            return check(c, in, out, piece);
        }

        return checkNext(c, in, out);
    }

    private boolean check(ChessboardTurnGame c, int[] in, int[] out, Piece knight) {
        Piece target = c.getChessboard().getPiece(out);

        if(target != null && target.getColor() == knight.getColor())
            return false;

        int diffX = Math.abs(out[0] - in[0]);
        int diffY = Math.abs(out[1] - in[1]);

        return (diffX == 1 && diffY == 2)
            || (diffX == 2 && diffY == 1);
    }
}
