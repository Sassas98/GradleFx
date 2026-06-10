package it.unicam.controller;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.controller.checker.ChessMoveChecker;
import it.unicam.controller.state.BoardHandler;
import it.unicam.controller.state.ChessStateController;
import it.unicam.controller.state.ChessboardHandler;
import it.unicam.controller.state.StateTurnGameController;
import it.unicam.model.dto.ChessGameDTO;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.persistence.ChessPersistenceContext;

public class ChessGameController implements GameController<ChessInput, ChessGameDTO>{

    private final BoardHandler<ChessboardTurnGame, ChessGameDTO> handler;
    private final StateTurnGameController<ChessboardTurnGame> state;

    public ChessGameController(ChessboardTurnGame chessboard) {
        CheckerDecorator<ChessboardTurnGame> check = new ChessMoveChecker();
        handler = new ChessboardHandler(new ChessPersistenceContext(), check);
        state = new ChessStateController(check);
    }

    @Override
    public ChessGameDTO execute(ChessInput input) {
        return handler.makeMove(input.moves()[0], input.moves()[1])
        .updateState(state)
        .changeTurn()
        .saveChanges()
        .getDTO();
    }

    @Override
    public void makePCAction() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'makePCAction'");
    }
    

}
