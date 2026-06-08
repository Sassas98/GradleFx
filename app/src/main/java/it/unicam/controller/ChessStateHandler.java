package it.unicam.controller;

import java.util.Arrays;
import java.util.List;

import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.GameState;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;

public class ChessStateHandler implements StateTurnGameHandler<ChessboardTurnGame>{

    @Override
    public GameState getGameState(ChessboardTurnGame g, CheckerDecorator<ChessboardTurnGame> c) {
        List<Piece> kings = Arrays.asList(g.getChessboard().getPieces()).stream()
            .filter(x -> x.getType() == PieceType.KING).toList();
        Piece kb = kings.stream().filter(x -> x.getColor() == PlayerColor.BLACK).findFirst().get();
        Piece kw = kings.stream().filter(x -> x.getColor() == PlayerColor.WHITE).findFirst().get();
        if(isUnderAttack(kw, g, c) && !canMoveSomething(PlayerColor.WHITE, g, c)){
            return GameState.WINNER;
        }else if(isUnderAttack(kb, g, c) && !canMoveSomething(PlayerColor.BLACK, g, c)){
            return GameState.WINNER;
        }else{
            if(!canMoveSomething(PlayerColor.BLACK, g, c) 
                || !canMoveSomething(PlayerColor.WHITE, g, c) )
            return GameState.PAIR;
        }
        return GameState.RUNNING;
    }

    private boolean isUnderAttack(Piece king, ChessboardTurnGame g, CheckerDecorator<ChessboardTurnGame> c){
        return !c.check(g, king.getPosion(), king.getPosion());
    }

    private boolean canMoveSomething(PlayerColor pc, ChessboardTurnGame g, CheckerDecorator<ChessboardTurnGame> c){
        List<Piece> pieces = Arrays.asList(g.getChessboard().getPieces()).stream()
            .filter(x -> x.getColor() == pc).toList();
        return searchIntoEveryPlace(pos -> {
                for (Piece piece : pieces) {
                    if(piece.getPosion()[0] == pos[0] && piece.getPosion()[1] == pos[1])
                        continue;
                    if(c.check(g, piece.getPosion(), new int[]{pos[0], pos[1]}))
                        return true;
                }
                return false;
        });
    }

    private boolean searchIntoEveryPlace(SearchPredicate predicate){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(predicate.searchIn(new int[]{i, j}))
                    return true;
            }
        }
        return false;
    }

    @Override
    public int getWinnerNumber(ChessboardTurnGame g, CheckerDecorator<ChessboardTurnGame> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getWinnerNumber'");
    }



}
