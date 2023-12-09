package com.example.ai2;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class neuralController implements Initializable {

    @FXML
    private Button idAdd;

    @FXML
    private Label idAccuracy;

    @FXML
    private Label idPerformance;

    @FXML
    private TextField idPerceptron;

    @FXML
    private TextField idRate;

    @FXML
    private Button idRun;

    @FXML
    private TableView<String> idTable;

    @FXML
    private Button idUpload;

    @FXML
    private ChoiceBox<String> myChoiceBox;

    private String []activation={"Tanh" , "ReLU" , "Sigmoid"};

    @Override
    public void initialize(URL arg0 , ResourceBundle arg1) {
        myChoiceBox.getItems().addAll(activation);
    }
}


