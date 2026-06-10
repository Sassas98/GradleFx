package it.unicam.controller.state;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.controller.util.ChessBoardSearcher;
import it.unicam.controller.util.ChessboardNavigator;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.GameState;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;

public class ChessStateController implements StateTurnGameController<ChessboardTurnGame>{

    private final CheckerDecorator<ChessboardTurnGame> checker;

    public ChessStateController(CheckerDecorator<ChessboardTurnGame> c){
        this.checker = c;
    }

    @Override
    public GameState getGameState(ChessboardTurnGame g) {
        PlayerColor color = g.getCurrentPlayer();
        Piece k = getKing(color, g);
        boolean canMove = canMoveSomething(color, g);
        if((isUnderAttack(k, g) && !canMove))
            return GameState.WINNER;
        return canMove ? GameState.RUNNING : GameState.PAIR;
    }

    @Override
    public PlayerColor getWinnerPlayer(ChessboardTurnGame g) {
        return getGameState(g) != GameState.WINNER ? null :
            g.getCurrentPlayer() == PlayerColor.BLACK ?
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
