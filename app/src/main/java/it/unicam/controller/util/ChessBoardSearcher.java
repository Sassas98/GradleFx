package it.unicam.controller.util;

public abstract class ChessBoardSearcher {
    protected boolean searchIntoEveryPlace(ChessSearchPredicate predicate){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(predicate.searchIn(new int[]{i, j}))
                    return true;
            }
        }
        return false;
    }
}
