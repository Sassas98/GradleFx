package it.unicam.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import it.unicam.controller.ChessInput;
import it.unicam.controller.GameController;
import it.unicam.model.dto.ChessGameDTO;
import it.unicam.model.dto.ChessPieceDTO;
import it.unicam.model.entity.PieceType;
import it.unicam.model.entity.PlayerColor;

public class ChessViewController {

    @FXML
    private GridPane boardGrid;

    @FXML
    private Label statusLabel;

    private final GameController<ChessInput, ChessGameDTO> game;
    private final PlayerColor playerColor;
    private final Runnable restartAction;

    private ChessGameDTO currentState;

    private int selectedX = -1;
    private int selectedY = -1;

    public ChessViewController(
            GameController<ChessInput, ChessGameDTO> game,
            PlayerColor playerColor,
            Runnable restartAction
    ) {
        this.game = game;
        this.playerColor = playerColor;
        this.restartAction = restartAction;
    }

    @FXML
    public void initialize() {
        currentState = game.makePCAction();

        if (playerColor == PlayerColor.WHITE) {
            currentState = game.makePCAction();
        }

        render();
    }

    @FXML
    private void onNewGame() {
        restartAction.run();
    }

    private void handleCellClick(int x, int y) {
        ChessPieceDTO clickedPiece = findPiece(x, y);

        if (selectedX == -1) {
            if (clickedPiece == null || clickedPiece.color() != playerColor) {
                return;
            }

            selectedX = x;
            selectedY = y;
            render();
            return;
        }

        ChessInput input = new ChessInput(new int[][]{
                {selectedX, selectedY},
                {x, y}
        });

        selectedX = -1;
        selectedY = -1;

        currentState = game.execute(input);

        if (isGameFinished()) {
            showEndDialog();
            return;
        }

        currentState = game.makePCAction();

        if (isGameFinished()) {
            showEndDialog();
            return;
        }

        render();
    }

    private void render() {
        boardGrid.getChildren().clear();

        statusLabel.setText("Stai giocando con: " + playerColor);

        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                StackPane cell = createCell(x, y);
                boardGrid.add(cell, x, y);
            }
        }
    }

    private StackPane createCell(int x, int y) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("chess-cell");

        boolean light = (x + y) % 2 == 0;
        cell.getStyleClass().add(light ? "cell-light" : "cell-dark");

        if (x == selectedX && y == selectedY) {
            cell.getStyleClass().add("cell-selected");
        }

        ChessPieceDTO piece = findPiece(x, y);

        if (piece != null) {
            Label pieceLabel = new Label(toSymbol(piece));
            pieceLabel.getStyleClass().add("piece");

            if (piece.color() == PlayerColor.WHITE) {
                pieceLabel.getStyleClass().add("piece-white");
            } else {
                pieceLabel.getStyleClass().add("piece-black");
            }

            cell.getChildren().add(pieceLabel);
        }

        cell.setOnMouseClicked(event -> handleCellClick(x, y));

        return cell;
    }

    private ChessPieceDTO findPiece(int x, int y) {
        if (currentState == null || currentState.pieces() == null) {
            return null;
        }

        return currentState.pieces()
                .stream()
                .filter(p -> p.x() == x && p.y() == y)
                .findFirst()
                .orElse(null);
    }

    private boolean isGameFinished() {
        if (currentState == null || currentState.pieces() == null) {
            return true;
        }

        boolean whiteKingAlive = hasKing(PlayerColor.WHITE);
        boolean blackKingAlive = hasKing(PlayerColor.BLACK);

        return !whiteKingAlive || !blackKingAlive;
    }

    private boolean hasKing(PlayerColor color) {
        return currentState.pieces()
                .stream()
                .anyMatch(p -> p.color() == color && p.type() == PieceType.KING);
    }

    private void showEndDialog() {
        render();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Partita finita");
        alert.setHeaderText("La partita è terminata");
        alert.setContentText("Vuoi iniziarne una nuova?");

        alert.showAndWait();

        restartAction.run();
    }

    private String toSymbol(ChessPieceDTO piece) {
        return switch (piece.type()) {
            case KING -> "KING";
            case QUEEN -> "QUEEN";
            case ROOK -> "ROOK";
            case BISHOP -> "BISHOP";
            case KNIGHT -> "KNIGHT";
            case PAWN -> "PAWN";
        };
    }
}
