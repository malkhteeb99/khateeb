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
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import static java.lang.Math.random;
public class neuralController implements Initializable
{
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
    Alert alert;
    private String []activation={"Tanh" , "ReLU" , "Sigmoid"};
    ObservableList<Table> data = FXCollections.observableArrayList();
    Random random = new Random();


    public int numOfPerceptron = 0;
    @Override
    public void initialize(URL arg0 , ResourceBundle arg1)
    {
        myChoiceBox.getItems().addAll(activation);
    }
    private void initializeWeights()
    {
        int numHidden = Integer.parseInt(idPerceptron.getText());
        double rangeInputHidden = 2.4/2;
        double rangeHiddenOutput = 2.4/numHidden;
        double thresholdHidden[] = new double[numHidden];
        double thresholdOutput[] = new double[3];
        int numInputs = 2; // This should be set according to your actual number of inputs
        int numOutputs = 3; // Assuming 3 outputs as
        double[][] weightsInputToHidden = new double[numHidden][numInputs];;
        double[][] weightsHiddenToOutput= new double[numOutputs][numHidden];
        //input to hidden layer
        for (int i = 0; i < numHidden; i++)
        {
            for (int j = 0; j < 2; j++)
                weightsInputToHidden[i][j] = (random() * 2 * rangeInputHidden) - rangeInputHidden;
            thresholdHidden[i] = (random() * 2 * rangeInputHidden) - rangeInputHidden;
        }
        //hidden to output layer
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < numHidden; j++)
                weightsHiddenToOutput[i][j] = (random() * 2 * rangeHiddenOutput) - rangeHiddenOutput;
            thresholdOutput[i] = (random() * 2 * rangeHiddenOutput) - rangeHiddenOutput;
        }
        //print();
    }

    public void computeTanh(double input1, double input2, double input3)
    {
        //double activation = this.weight1*input1 + this.weight2*input2 + this.weight3*input3;
        //double output = tanh(activation);
    }
    public void computeSigmoid(double input1, double input2, double input3)
    {
        //double activation = this.weight1*input1 + this.weight2*input2 + this.weight3*input3;
        //double output = sigmoid(activation);
    }
    public void computerelu(double input1, double input2, double input3)
    {
        //double activation = this.weight1*input1 + this.weight2*input2 + this.weight3*input3;
        //double output = relu(activation);
    }
    public double sigmoid(double x)
    {
        return 1.0 / (1.0 + Math.exp(-x));
    }
    public double tanh(double x)
    {
        return (Math.exp(x) - Math.exp(-x)) / (Math.exp(x) + Math.exp(-x));
    }
    public double relu(double x)
    {
        return Math.max(0, x);
    }
//    public void print()
//    {
//        int numHidden = Integer.parseInt(idPerceptron.getText());
//        double thresholdHidden[] = new double[numHidden];
//        double thresholdOutput[] = new double[3];
//        //input to hidden layer
//        for (int i = 0; i < numHidden; i++)
//        {
//            for (int j = 0; j < 2; j++)
//                System.out.println(weightsInputToHidden[i][j]);
//            System.out.println(thresholdHidden[i]);
//        }
//        //hidden to output layer
//        for (int i = 0; i < 3; i++)
//        {
//            for (int j = 0; j < numHidden; j++)
//                System.out.println(weightsHiddenToOutput[i][j]);
//            System.out.println(thresholdOutput[i]);
//        }
//    }
    public void run() throws IOException
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
        initializeWeights();
    }

    public void train()
    {

    }
    public void createNeurons ()
    {
        int num = Integer.parseInt(idPerceptron.getText());
        if(num == 1)
        {
        }
        else if(num>1)
        {
            for(int i=0 ; i<num ; i++)
                for(int j=2 ; j<num ; j++);
        }
        else
        {
            errorMessage("Please enter the neuron number!");
        }
    }
    public static Double meanSquareLoss(List<Double> correctAnswers, List<Double> predictedAnswers){
        double sumSquare = 0;
        for (int i = 0; i < correctAnswers.size(); i++){
            double error = correctAnswers.get(i) - predictedAnswers.get(i);
            sumSquare += (error * error);
        }
        return sumSquare / (correctAnswers.size());
    }
    public void errorMessage(String message)
    {
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }
}


