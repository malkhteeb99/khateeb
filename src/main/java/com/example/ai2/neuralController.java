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
    Alert alert = new Alert(Alert.AlertType.ERROR);
    Alert info = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    private TextField accuracy;
    @FXML
    private TextField epochs;
    @FXML
    private TextField errorRate;
    @FXML
    private ChoiceBox<String> myChoiceBox1;
    private String []hiddenActivation={"Tanh" , "ReLU" , "Sigmoid"};
    private String []outputActivation={"Sigmoid" , "Softmax"};
    ObservableList<Table> data = FXCollections.observableArrayList();
    Random random = new Random();
    double inMin = -2.4 / 2;
    double inMax = 2.4 / 2;
    double inputHiddenRange = inMin + (inMax - inMin) * random.nextDouble();
    double []  bOutput = new double[100000];
    double[] inputSweetness = new double[100000];
    double[] inputColor = new double[100000];
    @Override
    public void initialize(URL arg0 , ResourceBundle arg1)
    {
        myChoiceBox.getItems().addAll(hiddenActivation);
        myChoiceBox1.getItems().addAll(outputActivation);
    }
    private void initializeWeights()
    {
        int numHidden = Integer.parseInt(idPerceptron.getText());
        double hMin = -2.4 / numHidden;
        double hMax = 2.4 / numHidden;
        double hiddenOutputRange = hMin + (hMax - hMin) * random.nextDouble();
        String hActivationFunction = myChoiceBox.getValue();
        String oActivationFunction = myChoiceBox1.getValue();
        double rangeInputHidden = inputHiddenRange;
        double rangeHiddenOutput = hiddenOutputRange;
        double thresholdHidden[] = new double[numHidden];
        double thresholdOutput[] = new double[3];
        int numInputs = 2;
        int numOutputs = 3;
        double[][] weightsInputToHidden = new double[numHidden][numInputs];;
        double[][] weightsHiddenToOutput= new double[numOutputs][numHidden];
        //input to hidden layer
        for (int i = 0; i < numHidden; i++)
        {
            for (int j = 0; j < numInputs; j++)
                weightsInputToHidden[i][j] = (random() * 2 * rangeInputHidden) - rangeInputHidden;
            thresholdHidden[i] = (random() * 2 * rangeInputHidden) - rangeInputHidden;
        }
        //hidden to output layer
        for (int i = 0; i < numOutputs; i++)
        {
            for (int j = 0; j < numHidden; j++)
                weightsHiddenToOutput[i][j] = (random() * 2 * rangeHiddenOutput) - rangeHiddenOutput;
            thresholdOutput[i] = (random() * 2 * rangeHiddenOutput) - rangeHiddenOutput;
        }
        // Calculate output of hidden layer
        double hiddenLayerOutputs[] = new double[numHidden];
        for (int i = 0; i < numHidden; i++)
        {
            for (int j = 0; j < numInputs; j++)
            {
                hiddenLayerOutputs[i] += weightsInputToHidden[i][j] * weightsInputToHidden[i][j];
            }
            if(hActivationFunction.equalsIgnoreCase("sigmoid"))
                hiddenLayerOutputs[i] = sigmoid(hiddenLayerOutputs[i] + thresholdHidden[i]);
            if(hActivationFunction.equalsIgnoreCase("tanh"))
                hiddenLayerOutputs[i] = tanh(hiddenLayerOutputs[i] + thresholdHidden[i]);
            if(hActivationFunction.equalsIgnoreCase("relu"))
                hiddenLayerOutputs[i] = relu(hiddenLayerOutputs[i] + thresholdHidden[i]);
        }
        // Calculate output of output layer
        double outputLayerOutputs[] = new double[numOutputs];
        for (int i = 0; i < outputLayerOutputs.length; i++) {
            for (int j = 0; j < hiddenLayerOutputs.length; j++) {
                outputLayerOutputs[i] += hiddenLayerOutputs[j] * weightsHiddenToOutput[i][j];
            }
            if(oActivationFunction.equalsIgnoreCase("sigmoid"))
                outputLayerOutputs[i] = sigmoid(outputLayerOutputs[i] + thresholdOutput[i]);
            if(oActivationFunction.equalsIgnoreCase("softMax"))
                outputLayerOutputs[i] = tanh(outputLayerOutputs[i] + thresholdOutput[i]);
        }
        //Train
        double lRate = Double.parseDouble(idRate.getText());
        double [] dw = new double[inputSweetness.length];
        Alert alert = new Alert(Alert.AlertType.ERROR);

        int epoch = Integer.parseInt(epochs.getText());
        for(int l=0 ; l<epoch ; l++)
        {
            double[] errorArray = meanSquareLoss(bOutput , outputLayerOutputs);
            for (int i = 0; i < outputLayerOutputs.length; i++)
            {
                if (errorArray[i] != 0)
                {
                    for (int z = 0; z < errorArray.length; z++)
                        dw[z] = inputSweetness[z] * inputColor[z] * lRate * errorArray[z];
                    for (int k = 0; k < numHidden; k++)
                    {
                        for (int j = 0; j < numInputs; j++)
                            weightsInputToHidden[k][j] = weightsInputToHidden[k][j] + dw[i];
                    }
                }
                errorRate.setText(String.valueOf(Double.parseDouble(String.valueOf(meanSquareLoss(bOutput , outputLayerOutputs)))));
            }
        }
        infoMessage("Training ended successfully");
        //print();
    }
    public void infoMessage(String message)
    {
        info.setHeaderText(message);
        info.showAndWait();
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
    public double softMax(double x)
    {
        return x;
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
                for (int i = 0; i< parts.length; i++)
                {
                    bOutput[i] = aOutput;
                    inputSweetness[i] = sweetness;
                    inputColor[i] = color;
                }
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
    //    public void createNeurons ()
//    {
//        int num = Integer.parseInt(idPerceptron.getText());
//        if(num == 1)
//        {
//        }
//        else if(num>1)
//        {
//            for(int i=0 ; i<num ; i++)
//                for(int j=2 ; j<num ; j++);
//        }
//        else
//        {
//            errorMessage("Please enter the neuron number!");
//        }
//    }
    public double[] meanSquareLoss(double bOutput[], double outputLayerOutputs[])
    {
        double[] error = new double[outputLayerOutputs.length];
        for (int i = 0; i < outputLayerOutputs.length; i++)
        {
            error[i] = bOutput[i] - outputLayerOutputs[i];
        }
        return error ;
    }
    public void errorMessage(String message)
    {
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(message);
        alert.showAndWait();
    }
}