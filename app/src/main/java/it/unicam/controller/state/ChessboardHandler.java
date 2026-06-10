package it.unicam.controller.state;

import java.util.Arrays;
import java.util.List;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.controller.util.ChessBoardFactory;
import it.unicam.model.dto.ChessGameDTO;
import it.unicam.model.dto.ChessPieceDTO;
import it.unicam.model.entity.Chessboard;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.GameState;
import it.unicam.model.entity.Piece2D;
import it.unicam.model.entity.PlayerColor;
import it.unicam.model.persistence.PersistenceContext;

public class ChessboardHandler implements BoardHandler<ChessboardTurnGame, ChessGameDTO> {

    private final PersistenceContext<ChessboardTurnGame> db;
    private final ChessboardTurnGame chessboard;
    private final CheckerDecorator<ChessboardTurnGame> checker;

    public ChessboardHandler(PersistenceContext<ChessboardTurnGame> db, CheckerDecorator<ChessboardTurnGame> moveChecker){
        this.db = db;
        this.checker = moveChecker;
        ChessboardTurnGame loaded = db.load();
        if (loaded == null){
            Chessboard cb = new ChessBoardFactory().build();
            loaded = new ChessboardTurnGame(GameState.RUNNING, 2, PlayerColor.WHITE, cb);
            db.save(loaded);
        }
        chessboard = loaded;
    }

    @Override
    public BoardHandler<ChessboardTurnGame, ChessGameDTO> makeMove(int[] in, int[] out){
        if(chessboard.getState() == GameState.RUNNING &&
           checker.check(chessboard, in, out))
            chessboard.getChessboard().makeMove(in, out);
        return this;
    }

    @Override
    public BoardHandler<ChessboardTurnGame, ChessGameDTO> changeTurn(){
        if(chessboard.getState() == GameState.RUNNING)
            chessboard.setCurrentPlayer(chessboard.getCurrentPlayer() == PlayerColor.WHITE ? 
                                        PlayerColor.BLACK : PlayerColor.WHITE);
        return this;
    }

    @Override
    public BoardHandler<ChessboardTurnGame, ChessGameDTO> updateState(StateTurnGameController<ChessboardTurnGame> stateController){
        chessboard.setState(stateController.getGameState(chessboard));
        chessboard.setWinnerPlayer(stateController.getWinnerPlayer(chessboard));
        return this;
    }

    @Override
    public BoardHandler<ChessboardTurnGame, ChessGameDTO> saveChanges(){
        db.save(chessboard);
        return this;
    }

    @Override
    public ChessGameDTO getDTO(){
        List<ChessPieceDTO> ps = Arrays.asList(chessboard.getChessboard().getPieces())
                                       .stream().map(x -> ((Piece2D)x).toDTO()).toList();
        return new ChessGameDTO(this.chessboard.getCurrentPlayer(), ps);
    }
}
