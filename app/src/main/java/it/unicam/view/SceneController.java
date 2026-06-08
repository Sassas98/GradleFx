package it.unicam.view;

import java.io.IOException;
import java.net.URL;

import it.unicam.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private Stage stage;

    public SceneController(Stage stage){
        this.stage = stage;
    }

    public void setScene(int sceneIndex){
        if(sceneIndex == 0) this.stage.setScene(getMenuScene());
        else if(sceneIndex == 1) this.stage.setScene(getGameScene());
    }

    private Scene getMenuScene(){
        URL fxmlUrl = App.class.getResource("/home.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        loader.setControllerFactory(type -> new MenuController(y -> this.setScene(y)));
        try {
            return loader.load();
        } catch (IOException e) {
            return null;
        }
    }

    private Scene getGameScene(){
        URL fxmlUrl = App.class.getResource("/game.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        try {
            return loader.load();
        } catch (IOException e) {
            return null;
        }
    }

}
