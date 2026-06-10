package it.unicam.controller;

import java.util.Arrays;
import java.util.stream.Stream;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.controller.util.ChessBoardSearcher;
import it.unicam.controller.util.ChessboardNavigator;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.GameState;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;

public class ChessStateController extends ChessBoardSearcher implements StateTurnGameController<ChessboardTurnGame>{

    private final CheckerDecorator<ChessboardTurnGame> checker;

    public ChessStateController(CheckerDecorator<ChessboardTurnGame> c){
        this.checker = c;
    }

    @Override
    public GameState getGameState(ChessboardTurnGame g) {
        Stream<Piece> kings = Arrays.asList(g.getChessboard().getPieces()).stream()
            .filter(x -> x.getType() == PieceType.KING);
        Piece kb = kings.filter(x -> x.getColor() == PlayerColor.BLACK).findFirst().get();
        Piece kw = kings.filter(x -> x.getColor() == PlayerColor.WHITE).findFirst().get();
        if((isUnderAttack(kw, g) && !canMoveSomething(PlayerColor.WHITE, g)) ||
           (isUnderAttack(kb, g) && !canMoveSomething(PlayerColor.BLACK, g))){
            return GameState.WINNER;
        }else{
            if(!canMoveSomething(PlayerColor.BLACK, g) 
                || !canMoveSomething(PlayerColor.WHITE, g) )
            return GameState.PAIR;
        }
        return GameState.RUNNING;
    }

    private boolean isUnderAttack(Piece king, ChessboardTurnGame g){
        return !checker.check(g, king.getPosion(), king.getPosion());
    }

    private boolean canMoveSomething(PlayerColor pc, ChessboardTurnGame g){
        ChessboardNavigator navigator = new ChessboardNavigator(checker);
        return Arrays.asList(g.getChessboard().getPieces()).stream()
            .filter(x -> x.getColor() == pc)
            .anyMatch(x -> !navigator.getLegalMoves(x, g).isEmpty());
    }

    @Override
    public PlayerColor getWinnerNumber(ChessboardTurnGame g) {
        if(getGameState(g) != GameState.WINNER) 
            return null;
        Piece kb = Arrays.asList(g.getChessboard().getPieces()).stream()
            .filter(x -> x.getType() == PieceType.KING && x.getColor() == PlayerColor.BLACK).findFirst().get();
        return isUnderAttack(kb, g) && !canMoveSomething(PlayerColor.BLACK, g) ? 
            PlayerColor.BLACK : PlayerColor.WHITE;
    }



}
