package com.example.ai2;

public class NeuralNetwork
{


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
    public void train()
    {

    }
    public void network()
    {

    }

}
