package it.unicam.model.entity;

import java.io.Serializable;

import it.unicam.model.dto.ChessPieceDTO;

public class Piece2D implements Piece, Serializable {
    private int[] position;
    private PlayerColor color;
    private PieceType type;

    public Piece2D() {}

    public Piece2D(int[] pos, PlayerColor color, PieceType type) {
        if(pos == null || pos.length != 2)
            throw new IllegalArgumentException("Posizione non conforme alla logica 2D");
        this.position = pos;
        this.color = color;
        this.type = type;
    }
    public int[] getPosion() {
        return new int[]{position[0], position[1]};
    }
    public PlayerColor getColor() {
        return color;
    }
    public PieceType getType() {
        return type;
    }

    public ChessPieceDTO toDTO(){
        ChessPieceDTO dto = new ChessPieceDTO(color, type, position[0], position[1]);
        dto.type();
        return dto;
    }

    @Override
    public void setPosion(int[] position) {
        if(position == null || position.length != 2)
            throw new IllegalArgumentException("Posizione non conforme alla logica 2D");
        this.position = position;
    }

    @Override
    public void setType(PieceType type) {
        this.type = type;
    }
}
