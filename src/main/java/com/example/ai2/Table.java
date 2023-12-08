package com.example.ai2;

public class Table {
    int sweetnessColumn;
    int colorColumn;

    public int getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(int actualOutput) {
        this.actualOutput = actualOutput;
    }

    int actualOutput;
    int dOutput;
    public Table(int sweetnessColumn, int colorColumn, int actualOutput)
    {
        this.sweetnessColumn = sweetnessColumn;
        this.colorColumn = colorColumn;
        this.actualOutput = actualOutput;
    }
    public Table(int sweetnessColumn, int colorColumn, int actualOutput, int dOutput)
    {
        this.sweetnessColumn = sweetnessColumn;
        this.colorColumn = colorColumn;
        this.actualOutput = actualOutput;
        this.dOutput = dOutput;
    }
    public Table(int dOutput)
    {
        this.dOutput = dOutput;
    }

    public int getSweetnessColumn() {
        return sweetnessColumn;
    }

    public int getColorColumn() {
        return colorColumn;
    }



    public int getdOutput() {
        return dOutput;
    }


    public void setSweetnessColumn(int sweetnessColumn) {
        this.sweetnessColumn = sweetnessColumn;
    }

    public void setColorColumn(int colorColumn) {
        this.colorColumn = colorColumn;
    }


    public void setdOutput(int dOutput) {
        this.dOutput = dOutput;
    }
}
