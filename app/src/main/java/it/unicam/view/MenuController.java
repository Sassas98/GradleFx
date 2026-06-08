package it.unicam.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {

    @FXML
    private Button startButtun;

    private SceneChangeEvent sc;

    public MenuController(SceneChangeEvent sc){
        this.sc = sc;
    }

    //...
}
