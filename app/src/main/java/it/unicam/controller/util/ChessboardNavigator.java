package it.unicam.controller.util;

import java.util.LinkedList;
import java.util.List;

import it.unicam.controller.checker.CheckerDecorator;
import it.unicam.model.entity.ChessboardTurnGame;
import it.unicam.model.entity.Piece;

public final class ChessboardNavigator extends ChessBoardSearcher {

    private final CheckerDecorator<ChessboardTurnGame> checker;

    public ChessboardNavigator(CheckerDecorator<ChessboardTurnGame> c){
        this.checker = c;
    }

    public List<int[]> getLegalMoves(Piece p, ChessboardTurnGame g){
        List<int[]> moves = new LinkedList<>();
        searchIntoEveryPlace(pos -> {
                if(p.getPosion()[0] == pos[0] && p.getPosion()[1] == pos[1])
                    return false;
                if(checker.check(g, p.getPosion(), new int[]{pos[0], pos[1]})){
                    moves.add(pos);
                }
                return false;
        });
        return moves;
    }
}
