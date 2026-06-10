package it.unicam.controller.checker;

import it.unicam.controller.util.ChessBoardSearcher;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;

public class KingCheckerDecorator extends CheckerDecorator<ChessboardTurnGame> {

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        Piece piece = c.getChessboard().getPiece(in);

        if(piece == null) return false;

        if(piece.getType() == PieceType.KING){
            return check(c, in, out, piece);
        }

        return checkNext(c, in, out);
    }

    private boolean check(ChessboardTurnGame c, int[] in, int[] target, Piece king) {
        Piece p = c.getChessboard().getPiece(target);

        if(p != null && p.getColor() == king.getColor())
            return false;

        int diffX = Math.abs(target[0] - in[0]);
        int diffY = Math.abs(target[1] - in[1]);

        if(diffX > 1 || diffY > 1)
            return false;
        if(diffX == 0 && diffY == 0)
            return false;
        return true;
    }
}