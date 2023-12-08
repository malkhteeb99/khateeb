package com.example.ai2;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
    private TableView<Table> idTable;

    @FXML
    private Button idUpload;

    @FXML
    private ChoiceBox<String> myChoiceBox;
    private Stage stage;
    @FXML
    private TableColumn<Table, Integer> sweetnessColumn;
    @FXML
    private TableColumn<Table, Integer> actualOutput;
    @FXML
    private TableColumn<Table, Integer> colorColumn;

    @FXML
    private TableColumn<Table, Integer> dOutput;

    private String []activation={"Tanh" , "ReLU" , "Sigmoid"};
    ObservableList<Table> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL arg0 , ResourceBundle arg1)
    {
        myChoiceBox.getItems().addAll(activation);
    }

    public void readFromFile() throws IOException
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                int sweetness = Integer.parseInt(parts[0]);
                int color = Integer.parseInt(parts[1]);
                int aOutput = Integer.parseInt(parts[2]);
                data.add(new Table(sweetness, color, aOutput));           //1 = red, 2 = orange, 3 = purple   //1 = apple, 2 = orange, 3 = grape
            }
        }
        sweetnessColumn.setCellValueFactory(new PropertyValueFactory<>("sweetnessColumn"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<>("colorColumn"));
        actualOutput.setCellValueFactory(new PropertyValueFactory<>("actualOutput"));
        idTable.setItems(data);
    }
    public void train()
    {

    }
}


