package it.unicam.model.entity;

import java.io.Serializable;
import java.util.Arrays;

public class Chessboard implements Serializable {
    private Piece[] pieces;

    public Chessboard () {}
    public Chessboard (Piece[] pieces) {
        // controlli x pezzi in posizioni diverse
        // di dimensioni diverse
        // null o che contiene null
        this.pieces = pieces; // andrebbe copiato
    }

    public Piece[] getPieces(){
        return Arrays.copyOf(pieces, pieces.length);
    }

    public Piece getPiece(int[] position){
        return Arrays.asList(pieces).stream().filter(x -> {
            if(position.length != x.getPosion().length) 
                return false;
            for(int i = 0; i < position.length; i++){
                if(position[i] != x.getPosion()[i]) 
                    return false;
            }
            return true;
        }).findFirst().orElse(null);
    }
}
