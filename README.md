# Optical-character Recognition using JAVA

Digit Recognition

## Objective
The objective was to build a machine learning system(neural network) to train the data set provided to successfully classify the handwritten UCI digits into numbers between 0-9.

## Data
The data is from the University of California at Irvine's Machine Learning Repository. It's the Optical Recognition of Handwritten Digits Data Set. This gives you two data sets, training set and a test set. I've converted them to two data sets data set 1 , and data set 2 respectively.

## Method
The Artificial neural network was used to help identify the digits with the labelled dataset. It was a supervised learning model as we were provided with random raw data and labelled digits to match with. There are essentially three main layers of the model. The input layer with digits, the Hidden layer is where all the training and learning takes place and transformation of the inputs to a non-linear function. The output layer holds the output values that will then be used to match with provided labelled data. 

I have used sigmoid in my initial layer where the inputs are being multiplied by the weights at the hidden layer. For the output and hidden layer, I used the threshold activation function which is mapped against the desired output to calculate the error. 

The code has several methods each performing one function of the multilayer perceptron model. For the desired output array, I used the one hot encoding method and used that to match against my actual output array that I got after applying the specified activation functions.

I decided to use the multilayer perceptron model (MLP) for this project because the MLP is often applied to supervised learning problems and train on inputs and outputs. Therefore, with having the inputs and outputs given the MLP was the best approach for training the datasets.


# Results
![image](https://user-images.githubusercontent.com/42086991/125522550-57dd6bfa-0196-4d5f-a3e2-22c640e4eb9c.png)

As the screenshot illustrates above the accuracy on the testing set is 93%. In my approach I only used one hidden layer in the neural network however used the number of neurons as a parameter to increase my accuracy percentage.
To reach the output illustrated above in the fig 1.1 the learning rate I applied on the code was 0.03 and the cycles as 130 with no of hidden neurons being 50. Initially the learning rate and cycles parameters had to be tuned to reach a good accuracy. The best results were found from keeping the learning rate 0.03 and the cycles as 130.
