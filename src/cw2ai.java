
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.*;


public class cw2ai {

    //input layer
    static double[] inputvalues;  //array to hold the input values
    static double summation;
    static double[] a = new double[64];
    static double[] hiddenlayerinputs;
    //weights
    static double[][] hiddeninputweights; //the weights for the input and hidden layer
    static double[][] hiddenoutputweights; //the weights for the hidden layer and output layer
    //tuning this will affect accuracy
    static double hiddenneurons;       //An input variable for the user to enter how many hidden neurons he wants for the model
    static double cycles;
    //output layer
    static double[] outputneurons = new double[10];   //array to hold output for the hiddenlayer values
    static double[] map = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // an array of zeroes for the one hot encoding method that will hold the desired output value later
    static double[] outputlayer;
    // static double[] desiredoutput;      //array that saves the last column in the file which is the desired output
    static double desiredoutput;
    static double[] errorsignal = new double[10];
    static double success;
    static double[] hiddenerror;
    static double rows;
    static double calculateaccuracy;
    static double noforows;

    //-----------------START MAIN HERE---------------
    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter the desired number of hidden neurons you want ");//user input

        hiddenneurons = myObj.nextDouble(); // Read user input
        double minimum = -0.3; //helps in making the array have a range of values from negative0.5 to positive 0.5
        double maximum = 0.3;


        String path = "/Users/reham/Downloads/coursework2.csv";//path to the file with dataset 1
        String line = ""; //reads each row
        Random rand = new Random();

        hiddeninputweights = new double[(int) hiddenneurons][a.length]; //a 2d array to hold the weights for each hidden row


        for (int row = 0; row < hiddeninputweights.length; row++) {// a nested for loop to calculate the weights for each row and column of the 2d array

            for (int col = 0; col < hiddeninputweights[row].length; col++) {

                hiddeninputweights[row][col] = minimum + (maximum - minimum) * rand.nextDouble(); //double values between 0.5 and -0.5

            }

        }
        //System.out.println(Arrays.deepToString(hiddeninputweights));
        hiddenoutputweights = new double[(int) outputneurons.length][(int) hiddenneurons]; //a 2d array to hold weights between hidden and output layer

        for (int row = 0; row < hiddenoutputweights.length; row++) {

            for (int col = 0; col < hiddenoutputweights[row].length; col++) {

                hiddenoutputweights[row][col] = minimum + (maximum - minimum) * rand.nextDouble();

            }
        }

        //--------------Main File reader here----------------------------------//
        while (cycles < 500) {//no of times the whole file is read //tune this to improve accuracy rate
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));//file reader
                while ((line = br.readLine()) != null) {//loop to read all lines and then complete cycle
                    //success = 0;
                    String[] values = line.split(",");
                    a = new double[values.length]; //Makes new int array
                    for (double i = 0; i < a.length; i++) {
                        a[(int) i] = Double.parseDouble(values[(int) i]);
                    }
                    desiredoutput = a[64];//the last value saved in a variable


                    inputvalues = Arrays.copyOfRange(a, 0, 64);//save the first 64 inputs in the inputs array


                    map[(int) desiredoutput] = 1.0;//one hot encoding the last value in the file
                    feedforward(inputvalues);//feedforward function called to calculate summation

                    checksuccess();//ccheck if the output is equal to desired output array //trains the model

                    map[(int) desiredoutput ]= 0; //reinitualizing to 0 to calculate for the next row

                    rows++;//go to the next row

                }

                calculateaccuracy = (success / rows) * 100;//accurac rate calculation
                cycles++;//increase cycles after the whole file has been read
                success = 0;
                rows = 0;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        noforows = 2810;
        double learningratee = 0.068;
        System.out.println("The learning rate is " + learningratee);
        System.out.println("The no of hidden neurons" + hiddenneurons);
        System.out.println("the training rows is " + noforows);
        System.out.println("The accuracy for the training set is " + Math.round(calculateaccuracy) + "%");

        testing();

    }

    public static double transfer(double sum) { //sigmoid function for the input to hidden layer
        return 1d / (1.0 + Math.exp(-sum));
    }

    public static double threshold(double sum) {//threshold activation function for the output and hidden layer
        if (sum > 0) {//sum if the summation value that is passed when the function is called in the main method
            sum = 1;//step function that helps divide values into 0 and 1
        } else {
            sum = 0;

        }
        return sum;//this sum is saved in the output layer array
    }


    public static double[] feedforward(double[] inputvalues) { //summation function to multiply the inputs to the weights for both layers
        hiddenlayerinputs = new double[(int) hiddenneurons];//array to hold the inputs that will be used in the hidden to output layer- this is the feedforward
        for (int r = 0; r < hiddenneurons; r++) {
            for (int q = 0; q < hiddeninputweights[0].length; q++) {
                summation = summation + hiddeninputweights[r][q] * inputvalues[q];//summation
            }
            hiddenlayerinputs[r] = transfer(summation);//sigmoid applied here
            summation = 0;//initializing the value to 0 for it to be used for the next layer

        }
        outputlayer = new double[outputneurons.length];//output layer of length 10 that will hold the actual output values
        for (int out = 0; out < outputneurons.length; out++) {
            for (int l = 0; l < hiddenoutputweights[0].length; l++) {
                summation = summation + hiddenoutputweights[out][l] * hiddenlayerinputs[l];//summation for output layer and hiddden layer inputs

            }

            outputlayer[out] = threshold(summation);//threshold function applied here
            summation = 0;
        }

        return outputlayer;
    }





    public static void checksuccess() {//function to chek if the output and desired output are equal
        if (testerror(map, outputlayer)) {//calls the testerror function if it returns true then increase counter by 1
            success = success + 1;

        } else {
            train(0.068);//else train the model //calls the training function
        }
    }


    public static boolean testerror(double[] map, double[] outputlayer) {//function that returns a boolean

        if (map.length != outputlayer.length)//checks if the array length of the desired output array is equal to the actual output length array
            return false;

        for (int i = 0; i < map.length; i++) {//if the values in the array do not match between the desired outpiut array and actual output array then return false and train model
            if (map[i] != outputlayer[i]) {
                return false;
            }
        }
        return true;//else return true which increases success by 1
    }


    public static void train(double learningrate) {//train function gets called when the output and desired output are not equal

        backpropogateerror(); //backporpogate once
        updateweights(learningrate);//update weigts then to reduce error row by row

    }


    public static void backpropogateerror() {//backpropogating the calculated error to increase accuracy and train the model

        hiddenerror = new double[(int) hiddenneurons];//array to hold the error between hidden layer and input layer
        for (int i = 0; i < outputlayer.length; i++) {

            errorsignal[i] = map[i] - outputlayer[i];// array to hold the error for the output layer//actual is compared againt desired to see how far we are from the desired output
        }

        for (int g = 0; g < hiddenneurons; g++) {//calculate the weighted error of each neuron in the output layer for hidden
            double errortemp = 0;
            for (int t = 0; t < outputneurons.length; t++) {
                errortemp += errorsignal[t] * hiddenoutputweights[t][g];
            }
            hiddenerror[g] = hiddenlayerinputs[g] * (1 - hiddenlayerinputs[g]) * errortemp;//calculate the error with the array that holds sigmoid values
        }

    }

    public static void updateweights(double learningrate) {//recalculate weights

        for (int i = 0; i < outputlayer.length; i++) {// nested for loop that traverses through output to hidden layer and updates the weights acordingly
            for (int j = 0; j < hiddenneurons; j++) {

                hiddenoutputweights[i][j] = hiddenoutputweights[i][j] + learningrate * hiddenlayerinputs[j] * errorsignal[i];//applying the learning rate

            }
        }
        for (int i = 0; i < hiddenneurons; i++) {//traverse through hidden and input layer and update the weights

            for (int j = 0; j < inputvalues.length; j++) {

                hiddeninputweights[i][j] = hiddeninputweights[i][j] + learningrate * inputvalues[j] * hiddenerror[i];//apply learning rate

            }

        }
    }

    public static void testing() {//testing method
        String path = "/Users/reham/Downloads/cw2DataSet2.csv";//reads the testing file
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));//read file
            while ((line = br.readLine()) != null) {

                String[] values = line.split(",");
                a = new double[values.length]; //initialise a new double array
                for (double i = 0.0; i < a.length; i++) {//convert the string array to a double array to use it to further caluclate values in the code
                    a[(int) i] = Double.parseDouble(values[(int) i]);//save the rows in a new array
                }
                desiredoutput = a[64];//save the last numbr in the file which is the desiredoutput
                inputvalues = Arrays.copyOfRange(a, 0, 64);//save the first 64 inputs in the inputs array


                map[(int) desiredoutput] = 1.0;
                feedforward(inputvalues);//calling the feedforward function

                if (testerror(map, outputlayer)) {
                    success = success + 1;//checks if output matches the desired output array
                    //no trainin here as its the testing method
                    //System.out.println(success);
                }

                map[(int) desiredoutput] = 0;//the map is reinitialised to 0 for the next row to have the right hot encoding and to avoid indexes reading over each other
                rows++;
            }

            System.out.println("the testing rows is " + rows);
            System.out.println("the testing success is " + success);
            System.out.println(("The testing accuracy is ") + Math.round((success / rows) * 100) + "%"); //calculates accuracy rate for the testing file
            success = 0;

            rows = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
