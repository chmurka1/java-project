package code.controls;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class NodeControl extends AbstractNode {
    @FXML
    private Label title;

    @FXML
    Socket s1in;
    @FXML
    Socket s2in;
    @FXML
    Socket s1out;
    @FXML
    Socket s2out;
    @FXML
    Pane topPane;

    public NodeControl(Canvas canvas) {
        super(canvas);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NodeControl.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try { fxmlLoader.load(); } catch (IOException exception) { throw new RuntimeException(exception); }
        s1in.node=this; s2in.node=this;
        s1in.id="in1"; s2in.id="in2";
        s1out.node=this; s2out.node=this;
        s1out.id="out1"; s2out.id="out2";

        String[] listOfFilters ={"filter1","filter2","filter3"};
        ComboBox<String> comboBox= new ComboBox<>(FXCollections.observableArrayList(listOfFilters));
        comboBox.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(final ActionEvent e){
                        //TODO
                        //set filter to appropriate type
                        //comboBox.getValue()
                    }
                }
        );
        topPane.getChildren().add(comboBox);
    }
}
