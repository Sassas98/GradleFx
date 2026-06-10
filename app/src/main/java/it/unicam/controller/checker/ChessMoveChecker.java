package it.unicam.controller.checker;

import java.util.Arrays;

import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;

public class ChessMoveChecker extends CheckerDecorator<ChessboardTurnGame> {
    
    private CheckerDecorator<ChessboardTurnGame> checker;

    public ChessMoveChecker(){
        checker = new RookCheckerDecorator()
            .setAsNext(new BishopCheckerDecorator())
            .setAsNext(new KingCheckerDecorator())
            .setAsNext(new PawnCheckerDecorator())
            .setAsNext(new KnightCheckerDecorator());
    }

    @Override
    public boolean check(ChessboardTurnGame c, int[] in, int[] out) {
        return checker.check(c, in, out) && kingCheck(c, c.getChessboard().getPiece(in), out);
    }

    private boolean kingCheck(ChessboardTurnGame c, Piece p, int[] pos){
        if(p.getType() != PieceType.KING) return true;
        return Arrays.asList(c.getChessboard().getPieces())
                    .stream().allMatch(x -> x.getColor() == p.getColor() 
                                        || !checker.check(c, x.getPosion(), pos));
    }
}
