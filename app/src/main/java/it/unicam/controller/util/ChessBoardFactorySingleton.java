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
            pieces.add(new Piece2D(new int[]{i, 1}, PlayerColor.WHITE, PieceType.PAWN));
            pieces.add(new Piece2D(new int[]{i, 6}, PlayerColor.BLACK, PieceType.PAWN));
        }
    }

    private static void otherPiecesGenerator(List<Piece2D> pieces){
        for(int i = 0; i < 16; i++){
            PlayerColor color = i < 8 ? PlayerColor.WHITE : PlayerColor.BLACK;
            int row = i < 8 ? 0 : 7;
            PieceType type = switch (i % 8) {
                case 0, 7 -> PieceType.ROOK;
                case 1, 6 -> PieceType.KNIGHT;
                case 2, 5 -> PieceType.BISHOP;
                case 3 -> PieceType.QUEEN;
                case 4 -> PieceType.KING;
                default -> throw new IllegalStateException("Unexpected value: " + (i % 8));
            };
            pieces.add(new Piece2D(new int[]{i % 8, row}, color, type));
        }
    }
}
