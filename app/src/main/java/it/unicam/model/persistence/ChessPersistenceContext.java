package it.unicam.model.persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import it.unicam.model.entity.ChessboardTurnGame;

public class ChessPersistenceContext implements PersistenceContext<ChessboardTurnGame> {

    @Override
    public ChessboardTurnGame load() {
        try {
            String json = Files.readString(Path.of("game.json"));
            return new Gson().fromJson(json, ChessboardTurnGame.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void save(ChessboardTurnGame t) {
        Gson gson = new Gson();
        String json = gson.toJson(t);
        try {
            Files.writeString(Path.of("game.json"), json);
        } catch (IOException e) {
            throw new RuntimeException("Errore nel salvataggio");
        }
    }

}
