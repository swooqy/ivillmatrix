package main;

import java.util.ArrayList;
import java.util.Random;

public class GeneratorMatrix {

    protected int size;
    protected int numberOfHypotesis;
    protected Random randomGenerator;
    protected ArrayList<Double> observations;
    protected double[][] hypotesisList;
    protected double[][] matrixB;

    GeneratorMatrix (int size, double[][] hypotesisList){
        randomGenerator = new Random();
        this.hypotesisList = hypotesisList.clone();
        this.size = size;
        this.numberOfHypotesis= hypotesisList.length;
    }

    private double generateGaussian(double mean, double variance){
        return (randomGenerator.nextGaussian()*variance)+mean;
    }

    private double generateXt(double mean, double variance, double[] uknownVector, double [] trend){
        return multiplyVectors(uknownVector, trend) + generateGaussian(mean, variance);
    }

    private double[] countTrend(int t){
        double[] trend = new double[size];
        for(int i = 0; i<size; i++){
            trend[i] = t;
        }
        return trend;
    }

    private double multiplyVectors(double[] vector1, double[] vector2){
        double sum=0;
        for(int i= 0; i<vector1.length; i++){
            sum+=vector1[i]*vector2[i];
        }
        return sum;
    }

    private double n1(double xt, double[] hypotesis, double[] trend, double variance){
        multiplyVectors(hypotesis, trend);
        return 0;
    }

    private double countMatrixCellMultiplication(int i, int j, int n, double variance){
        double upper;
        double lower;
        double multiplication=1;
        for(int t=1; t<=n; t++){
            upper = n1(observations.get(t-1), hypotesisList[i], countTrend(t), variance);
            lower = n1(observations.get(t-1), hypotesisList[j], countTrend(t), variance);
            multiplication*=upper/lower;
        }
        return multiplication;
    }

    private double countMatrixCell(int i, int j, int n, double variance){
        return Math.log(countMatrixCellMultiplication(i, j, n, variance));
    }

    private double[][] countMatrix(int n, double variance){
        double[][] matrix = new double[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                matrix[i][j]=countMatrixCell(i, j, n, variance);
            }
        }
        return matrix;
    }

    private boolean isExceedingMatrixB(double[][] matrix){
        for(int i=0; i<numberOfHypotesis; i++){
            for(int j=0; j<numberOfHypotesis; j++){
                if(matrix[i][j] > matrixB[i][j]){
                    return true;
                }
            }
        }
        return false;
    }



    private void makeStep(double variance, double[] unknownVector, double[] trend){
        int t=1;
        double [][] currentMatrix;
        while(true){
            observations.add(generateXt(t,variance, unknownVector, trend));
            currentMatrix = countMatrix(t, variance);
            if(isExceedingMatrixB(currentMatrix)){
                break;
            }
            t++;
        }
    }



}
