package it.unicam.controller.checker;

import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;

public class PawnCheckerDecorator extends CheckerDecorator<ChessboardTurnGame> {

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        Piece piece = c.getChessboard().getPiece(in);

        if(piece == null) return false;

        if(piece.getType() == PieceType.PAWN){
            return check(c, in, out, piece);
        }

        return checkNext(c, in, out);
    }

    private boolean check(ChessboardTurnGame c, int[] in, int[] out, Piece pawn) {
        int direction = pawn.getColor() == PlayerColor.WHITE ? -1 : 1;

        int diffX = out[0] - in[0];
        int diffY = out[1] - in[1];

        Piece target = c.getChessboard().getPiece(out);

        if(diffX == 0 && diffY == direction && target == null)
            return true;

        if(diffX == 0 && diffY == direction * 2 && target == null && isStartPosition(in, pawn)) {
            int[] middle = new int[]{in[0], in[1] + direction};

            return c.getChessboard().getPiece(middle) == null;
        }

        if(Math.abs(diffX) == 1 && diffY == direction && target != null && target.getColor() != pawn.getColor())
            return true;

        return false;
    }

    private boolean isStartPosition(int[] in, Piece pawn) {
        if(pawn.getColor() == PlayerColor.WHITE)
            return in[1] == 6;

        return in[1] == 1;
    }
}
