package it.unicam.controller.state;

import it.unicam.model.entity.ChessboardTurnGame;

public interface BoardHandler<G extends ChessboardTurnGame, DTO> {
    public BoardHandler<G, DTO> makeMove(int[] in, int[] out);

    public BoardHandler<G, DTO> changeTurn();

    public BoardHandler<G, DTO> updateState(StateTurnGameController<G> stateController);

    public BoardHandler<G, DTO> saveChanges();

    public DTO getDTO();
}
