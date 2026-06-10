package it.unicam.model.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Chessboard implements Serializable {
    private List<Piece> pieces;

    public Chessboard () {}
    public Chessboard (Piece[] pieces) {
        if(pieces == null || pieces.length == 0)
            throw new IllegalArgumentException("La scacchiera deve avere almeno un pezzo");
        this.pieces = Arrays.asList(pieces);
        if(this.pieces.stream().anyMatch(x -> x == null))
            throw new IllegalArgumentException("La scacchiera non può avere pezzi nulli");
    }

    public Piece[] getPieces(){
        return Arrays.copyOf(pieces.toArray(), pieces.size(), Piece[].class);
    }

    public Piece getPiece(int[] position){
        return pieces.stream().filter(x -> {
            if(position.length != x.getPosion().length) 
                return false;
            for(int i = 0; i < position.length; i++){
                if(position[i] != x.getPosion()[i]) 
                    return false;
            }
            return true;
        }).findFirst().orElse(null);
    }


    public void makeMove(int[] in, int[] out){
        if(in == null || out == null || in.length != out.length || in.length == pieces.get(0).getPosion().length)
            return;
        Piece p = getPiece(in);
        if(p == null)
            return;
        Piece target = getPiece(out);
        if(target != null)
            pieces.remove(target);
        p.setPosion(out);

    }
}
