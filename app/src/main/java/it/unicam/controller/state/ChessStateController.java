package it.unicam.controller.state;

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
        Piece kb = getKing(PlayerColor.BLACK, g);
        Piece kw = getKing(PlayerColor.WHITE, g);
        boolean whiteCanMove = canMoveSomething(PlayerColor.WHITE, g);
        boolean blackCanMove = canMoveSomething(PlayerColor.BLACK, g);
        if((isUnderAttack(kw, g) && !whiteCanMove) ||
           (isUnderAttack(kb, g) && !blackCanMove))
            return GameState.WINNER;
        if(!blackCanMove || !whiteCanMove)
            return GameState.PAIR;
        return GameState.RUNNING;
    }

    @Override
    public PlayerColor getWinnerPlayer(ChessboardTurnGame g) {
        if(getGameState(g) != GameState.WINNER) 
            return null;
        Piece kb = getKing(PlayerColor.BLACK, g);
        return isUnderAttack(kb, g) && !canMoveSomething(PlayerColor.BLACK, g) ? 
            PlayerColor.WHITE : PlayerColor.BLACK;
    }

    private boolean isUnderAttack(Piece king, ChessboardTurnGame g){
        return !checker.check(g, king.getPosion(), king.getPosion());
    }

    private boolean canMoveSomething(PlayerColor pc, ChessboardTurnGame g){
        ChessboardNavigator navigator = new ChessboardNavigator(checker);
        return g.getPiecesOfPlayer(pc).stream()
                .anyMatch(x -> !navigator.getLegalMoves(x, g).isEmpty());
    }

    private Piece getKing(PlayerColor pc, ChessboardTurnGame g){
        return g.getPiecesOfPlayer(pc).stream()
            .filter(x -> x.getType() == PieceType.KING).findFirst().get();
    }

}
