package it.unicam;

import java.net.URL;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane root = new Pane();
        Button b = new Button("click me!");
        root.getChildren().add(b);
        b.setOnAction(x -> {
            TranslateTransition anim = new TranslateTransition(Duration.seconds(0.3), b);
            anim.setByX(b.getLayoutX() + 50);
            anim.setByY(b.getLayoutY() + 50);
            anim.setCycleCount(3);
            anim.setAutoReverse(true);
            anim.play();
        });
        Scene scene = new Scene(root, 1050, 700);
        scene.getStylesheets().add(App.class.getResource("/style.css").toExternalForm());
        stage.setTitle("FX Base");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}