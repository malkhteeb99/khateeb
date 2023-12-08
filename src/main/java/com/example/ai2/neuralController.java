package com.example.ai2;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class neuralController implements Initializable {

    @FXML
    private Button idAdd;

    @FXML
    private TextField idColor;

    @FXML
    private TextField idPerceptron;

    @FXML
    private TextField idRate;

    @FXML
    private Button idRun;

    @FXML
    private TextField idSweetnss;

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


