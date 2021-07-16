# Optical-character Recognition using JAVA

Digit Recognition

Objective - This work was done to build a machine learning system to categorise one of the UCI digits. Then I ran a two-fold test, and reported the results.

The data is from the University of California at Irvine's Machine Learning Repository. It's the Optical Recognition of Handwritten Digits Data Set. This gives you two data sets, training set and a test set. I've converted them to two data sets data set 1 , and data set 2 respectively.

The Artificial neural network was used to help identify the digits with the labelled dataset. It was a supervised learning model as we were provided with random raw data and labelled digits to match with. There are essentially three main layers of the model. The input layer with digits, the Hidden layer is where all the training and learning takes place and transformation of the inputs to a non-linear function. The output layer holds the output values that will then be used to match with provided labelled data. 

I decided to use the multilayer perceptron model for this project because the MLP is often applied to supervised learning problems and train on inputs and outputs. Therefore, with having the inputs and outputs given the MLP was the best approach for training the datasets.


Best results
![image](https://user-images.githubusercontent.com/42086991/125522550-57dd6bfa-0196-4d5f-a3e2-22c640e4eb9c.png)


Initially the learning rate and cycles parameters had to be tuned to reach a good accuracy. The best results were found from keeping the learning rate 0.03 and the cycles as 130.
