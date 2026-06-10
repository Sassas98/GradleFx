package it.unicam.controller.util;

import java.util.Arrays;
import java.util.List;

import it.unicam.model.entity.Chessboard;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.GameState;
import it.unicam.model.entity.Piece2D;
import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;
import it.unicam.model.persistence.PersistenceContext;

public class ChessBoardFactorySingleton {

    private static ChessboardTurnGame board;

    public static synchronized ChessboardTurnGame getOrBuild(PersistenceContext<ChessboardTurnGame> db){
        if(board != null) return board;
        board = db.load();
        if (board == null) {
            Chessboard cb = build();
            board = new ChessboardTurnGame(GameState.RUNNING, 2, PlayerColor.WHITE, cb);
            db.save(board);
        }
        return board;
    }

    private static Chessboard build(){
        List<Piece2D> pieces = new java.util.ArrayList<>();
        pawnGenerator(pieces);
        otherPiecesGenerator(pieces);
        return new Chessboard(Arrays.copyOf(pieces.toArray(), pieces.size(), Piece2D[].class));
    }

    private static void pawnGenerator(List<Piece2D> pieces){
        for(int i = 0; i < 8; i++){
            pieces.add(new Piece2D(new int[]{1, i}, PlayerColor.WHITE, PieceType.PAWN));
            pieces.add(new Piece2D(new int[]{6, i}, PlayerColor.BLACK, PieceType.PAWN));
        }
    }

    private static void otherPiecesGenerator(List<Piece2D> pieces){
        for(int i = 0; i < 8; i++){
            PlayerColor color = i < 4 ? PlayerColor.WHITE : PlayerColor.BLACK;
            int row = i < 4 ? 0 : 7;
            PieceType type = switch (i % 4) {
                case 0 -> PieceType.ROOK;
                case 1 -> PieceType.KNIGHT;
                case 2 -> PieceType.BISHOP;
                case 3 -> PieceType.QUEEN;
                default -> throw new IllegalStateException("Unexpected value: " + (i % 4));
            };
            pieces.add(new Piece2D(new int[]{row, i % 4}, color, type));
        }
    }
}
