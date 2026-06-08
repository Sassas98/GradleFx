package it.unicam.model.dto;

import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;

public record ChessPieceDTO(PlayerColor color, PieceType type, int x, int y) { }
