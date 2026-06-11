package it.unicam;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceDialog;

import java.util.List;
import java.util.Random;

import it.unicam.controller.ChessInput;
import it.unicam.controller.ChessSinglePlayerGameController;
import it.unicam.controller.GameController;
import it.unicam.model.dto.ChessGameDTO;
import it.unicam.model.entity.PlayerColor;
import it.unicam.view.ChessViewController;

public class App extends Application {

    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        startNewGame();
        stage.setTitle("Simple Chess");
        stage.show();
    }

    public void startNewGame() {
        PlayerColor color = askColor();

        GameController<ChessInput, ChessGameDTO> game =
                createGameController(color);

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/chess-view.fxml")
            );

            loader.setControllerFactory(clazz -> {
                if (clazz == ChessViewController.class) {
                    return new ChessViewController(game, color, this::startNewGame);
                }

                try {
                    return clazz.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            Scene scene = new Scene(loader.load(), 720, 760);
            scene.getStylesheets().add(
                    getClass().getResource("/chess-style.css").toExternalForm()
            );

            stage.setScene(scene);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PlayerColor askColor() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(
                "WHITE",
                List.of("WHITE", "BLACK", "RANDOM")
        );

        dialog.setTitle("Nuova partita");
        dialog.setHeaderText("Scegli il tuo colore");
        dialog.setContentText("Colore:");

        String choice = dialog.showAndWait().orElse("WHITE");

        if (choice.equals("RANDOM")) {
            return new Random().nextBoolean() ? PlayerColor.WHITE : PlayerColor.BLACK;
        }

        return PlayerColor.valueOf(choice);
    }

    private GameController<ChessInput, ChessGameDTO> createGameController(PlayerColor color) {
        /*
         * Qui devi mettere il tuo vero costruttore.
         * Esempio:
         *
         * return new ChessSinglePlayerGameController(color);
         */

        return new ChessSinglePlayerGameController(color);
    }

    public static void main(String[] args) {
        launch(args);
    }
}