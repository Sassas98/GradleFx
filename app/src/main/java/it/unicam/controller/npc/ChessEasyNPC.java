package it.unicam.controller.npc;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.controller.util.ChessboardNavigator;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.PlayerColor;

public class ChessEasyNPC implements RandomMoveGetter{

    private final ChessboardTurnGame game;
    private final PlayerColor color;
    private final ChessboardNavigator navigator;

    public ChessEasyNPC(ChessboardTurnGame game, PlayerColor color, CheckerDecorator<ChessboardTurnGame> checker){
        this.game = game;
        this.color = color;
        this.navigator = new ChessboardNavigator(checker);
    }

    @Override
    public int[][] getRandomMove() {
        return game.getPiecesOfPlayer(color).stream()
            .flatMap(x -> navigator.getLegalMoves(x, game)
                .stream().map(m -> new int[][]{x.getPosion(), m}))
            .findAny().orElse(null);
    }


}
