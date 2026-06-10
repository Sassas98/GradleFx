package it.unicam.model.dto;

import java.util.List;

import it.unicam.model.entity.PlayerColor;

public record ChessGameDTO(PlayerColor color, List<ChessPieceDTO> pieces) {}
