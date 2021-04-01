package main;

import javafx.util.Pair;

public class TesterMatrix {

    private int timesToTest;
    private double[] alpha;

    TesterMatrix(int timesToTest, double[] alpha){
        this.timesToTest = timesToTest;
        this.alpha = alpha.clone();
    }


    public static double[][] countMatrixB(int size, double[] alpha){
        double[][] matrixB = new double[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                matrixB[i][j]= Math.log(2/(alpha[i]));
            }
        }
        return matrixB;
    }

    public double[] createCounterArray(int size){
        double[] counterArray = new double[size];
        for(int i=0; i<size; i++){
            counterArray[i]=0;
        }
        return counterArray;
    }

    public void test(double[][] hypothesisList, double variance, double epsilon, double k){
        int size = hypothesisList[0].length;
        GeneratorMatrix generatorMatrix = new GeneratorMatrix(size, hypothesisList, countMatrixB(hypothesisList.length, alpha), variance);
        for(int i=0; i<hypothesisList.length; i++){
            Pair<Integer, Integer> result;
            double[] counterArray = createCounterArray(hypothesisList.length);
            int stepsForEveryTest = 0;
            for(int j=0; j<timesToTest; j++){
                result = generatorMatrix.doTest(hypothesisList[i], epsilon, k);
                counterArray[result.getKey()]++;
                stepsForEveryTest += result.getValue();
            }
            printResults(counterArray, stepsForEveryTest);
        }
    }

    private void printResults(double[] counterArray, int stepsForEveryTest){
        for(int i=0; i<counterArray.length; i++){
            System.out.println("Hypothesis " + i + " result: " + (counterArray[i]/timesToTest));
        }
        System.out.println("Average number of iterations: " + (double)stepsForEveryTest/timesToTest);
        System.out.println();
    }

}
