package it.unicam;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class NoteController {
    
    @FXML
    public TextArea input;
    @FXML
    private VBox box;

    @FXML
    public void initialize(){
    }

    @FXML
    public void create(){
        String in = input.getText();
        if(in.isEmpty()) return;
        input.setText("");
        FlowPane pane = new FlowPane();
        pane.setStyle("-fx-background-color: #F0D3F7; -fx-cursor: hand;");
        pane.getChildren().add(new Label(in));
        box.getChildren().add(pane);
    }

}
