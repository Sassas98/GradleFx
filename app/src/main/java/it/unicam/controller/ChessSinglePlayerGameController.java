package it.unicam.controller;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.controller.checker.ChessMoveChecker;
import it.unicam.controller.npc.ChessEasyNPC;
import it.unicam.controller.npc.RandomMoveGetter;
import it.unicam.controller.state.BoardHandler;
import it.unicam.controller.state.ChessStateController;
import it.unicam.controller.state.ChessboardHandler;
import it.unicam.controller.state.StateTurnGameController;
import it.unicam.controller.util.ChessBoardFactorySingleton;
import it.unicam.model.dto.ChessGameDTO;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.PlayerColor;
import it.unicam.model.persistence.ChessPersistenceContext;
import it.unicam.model.persistence.PersistenceContext;

public class ChessSinglePlayerGameController implements GameController<ChessInput, ChessGameDTO>{

    private final BoardHandler<ChessboardTurnGame, ChessGameDTO> handler;
    private final StateTurnGameController<ChessboardTurnGame> state;
    private final PlayerColor playerColor;
    private final RandomMoveGetter npc;

    public ChessSinglePlayerGameController(PlayerColor color) {
        CheckerDecorator<ChessboardTurnGame> check = new ChessMoveChecker();
        PersistenceContext<ChessboardTurnGame> db = new ChessPersistenceContext();
        handler = new ChessboardHandler(new ChessPersistenceContext(), check);
        state = new ChessStateController(check);
        playerColor = color;
        npc = new ChessEasyNPC(ChessBoardFactorySingleton.getOrBuild(db), color, check);
    }

    @Override
    public ChessGameDTO execute(ChessInput input) {
        ChessGameDTO dto = handler.getDTO();
        if(dto.color() != playerColor) 
            return dto;
        if(!dto.pieces().stream()
                .anyMatch(x -> x.x() == input.moves()[0][0] 
                            && x.y() == input.moves()[0][1]
                            && x.color() == playerColor))
            return dto;
        return handler.makeMove(input.moves()[0], input.moves()[1])
                        .updateState(state)
                        .saveChanges()
                        .getDTO();
    }

    @Override
    public ChessGameDTO makePCAction() {
        ChessGameDTO dto = handler.getDTO();
        if(dto.color() == playerColor) 
            return dto;
        int[][] move = npc.getRandomMove();
        if(move == null)
            return dto;
        return handler.makeMove(move[0], move[1])
                        .updateState(state)
                        .saveChanges()
                        .getDTO();
    }
    

}
