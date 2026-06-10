package it.unicam.controller.state;

import java.util.Arrays;
import java.util.List;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.controller.util.ChessBoardFactorySingleton;
import it.unicam.model.dto.ChessGameDTO;
import it.unicam.model.dto.ChessPieceDTO;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.GameState;
import it.unicam.model.entity.Piece;
import it.unicam.model.entity.Piece2D;
import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;
import it.unicam.model.persistence.PersistenceContext;

public class ChessboardHandler implements BoardHandler<ChessboardTurnGame, ChessGameDTO> {

    private final PersistenceContext<ChessboardTurnGame> db;
    private final ChessboardTurnGame chessboard;
    private final CheckerDecorator<ChessboardTurnGame> checker;

    public ChessboardHandler(PersistenceContext<ChessboardTurnGame> db, CheckerDecorator<ChessboardTurnGame> moveChecker){
        this.db = db;
        this.checker = moveChecker;
        chessboard = ChessBoardFactorySingleton.getOrBuild(db);
    }

    @Override
    public BoardHandler<ChessboardTurnGame, ChessGameDTO> makeMove(int[] in, int[] out){
        if(chessboard.getState() == GameState.RUNNING &&
            checker.check(chessboard, in, out)){
            chessboard.getChessboard().makeMove(in, out);
            Piece p = chessboard.getChessboard().getPiece(out);
            if(p.getType() == PieceType.PAWN && isPromotionSquare(p))
                p.setType(PieceType.QUEEN);
            changeTurn();
        }
        return this;
    }

    private boolean isPromotionSquare(Piece pawn) {
        if(pawn.getColor() == PlayerColor.WHITE)
            return pawn.getPosion()[1] == 0;

        return pawn.getPosion()[1] == 7;
    }

    private void changeTurn(){
        if(chessboard.getState() == GameState.RUNNING)
            chessboard.setCurrentPlayer(chessboard.getCurrentPlayer() == PlayerColor.WHITE ? 
                                        PlayerColor.BLACK : PlayerColor.WHITE);
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
