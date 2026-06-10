package it.unicam.model.entity;

public interface Piece {
    public int[] getPosion();
    public void setPosion(int[] position);
    public PlayerColor getColor();
    public PieceType getType();
}
