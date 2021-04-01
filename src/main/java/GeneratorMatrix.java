package main;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GeneratorMatrix {

    protected int size;
    protected int numberOfHypothesis;
    protected Random randomGenerator;
    protected ArrayList<Double> observations;
    protected double[][] hypothesisList;
    protected double[][] matrixB;
    protected double variance;

    GeneratorMatrix (int size, double[][] hypothesisList, double[][] matrixB, double variance){
        randomGenerator = new Random();
        this.hypothesisList = hypothesisList.clone();
        this.size = size;
        this.numberOfHypothesis = hypothesisList.length;
        this.matrixB = matrixB.clone();
        observations = new ArrayList<>();
        this.variance = variance;
    }

    private double generateGaussian(){
        return (randomGenerator.nextGaussian()*variance);
    }

    private double generateGaussian(double epsilon, double k){
        if(epsilon>Math.random()){
            return generateGaussian()*k;
        } else {
            return generateGaussian();
        }
    }

    private double generateXt(double[] trueValue, double [] trend, double epsilon, double k){
        double multiplication = multiplyVectors(trueValue, trend);
        double gaussian = generateGaussian(epsilon, k);
        return multiplication + gaussian;
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

    private double n1(double xt, double[] hypothesis, double[] trend){
        double mean = multiplyVectors(hypothesis, trend);
        return (1 / variance * Math.sqrt(2 * Math.PI)) * Math.exp((-(xt - mean) * (xt - mean)) / (2 * variance * variance));
    }

    private double countMatrixCellMultiplication(int i, int j, int n){
        double upper;
        double lower;
        double multiplication=1;
        for(int t=1; t<=n; t++){
            upper = n1(observations.get(t-1), hypothesisList[i], countTrend(t));
            lower = n1(observations.get(t-1), hypothesisList[j], countTrend(t));
            multiplication*=upper/lower;
        }
        return multiplication;
    }

    private double countMatrixCell(int i, int j, int n){
        return Math.log(countMatrixCellMultiplication(i, j, n));
    }

    protected double[][] countMatrix(int n){
        double[][] matrix = new double[numberOfHypothesis][numberOfHypothesis];
        for(int i = 0; i< numberOfHypothesis; i++){
            for(int j = 0; j< numberOfHypothesis; j++){
                matrix[i][j]=countMatrixCell(i, j, n);
            }
        }
        return matrix;
    }

    protected boolean isExceedingMatrixB(double[][] matrix){
        for(int i = 0; i< numberOfHypothesis; i++){
            for(int j = 0; j< numberOfHypothesis; j++){
                if(matrix[i][j] > matrixB[i][j]){
                    if(i!=j){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    protected double[][] countMatrix(double[] trueValue, double epsilon, double k){
        int t=1;
        double [][] currentMatrix;
        while(true){
            observations.add(generateXt(trueValue, countTrend(t), epsilon, k)); //здесь вместо генерации использовать реальные данные
            currentMatrix = countMatrix(t);
            if(isExceedingMatrixB(currentMatrix)){
                break;
            }
            t++;
        }
        return currentMatrix;
    }

    private double[] getTiList(double[][] matrix){
        double[] tiList = new double[numberOfHypothesis];
        for(int i = 0; i< numberOfHypothesis; i++){
            double ti = 9999999;
            for(int j = 0; j< numberOfHypothesis; j++){
                if(matrix[i][j] > matrixB[i][j]){
                    if(i!=j){
                        if(matrix[i][j]<ti){
                            ti=matrix[i][j];
                        }
                    }
                }
            }
            tiList[i]=ti;
        }
        return tiList;
    }

    private int chooseHypothesis(double[] tiList){
        double nb = 9999999;
        for(int i = 0; i < numberOfHypothesis; i++){
            if(tiList[i]<nb){
                nb=tiList[i];
            }
        }
        for(int i = 0; i< numberOfHypothesis; i++){
            if(tiList[i]==nb){
                return i;
            }
        }
        return -1;
    }

    public Pair<Integer, Integer> doTest(double[] trueValue, double epsilon, double k){
        observations = new ArrayList<>();
        int resultHypotesis = chooseHypothesis(getTiList(countMatrix(trueValue, epsilon, k)));
        int resultSteps = observations.size();
        Pair<Integer, Integer> resultPair = new Pair<>(resultHypotesis, resultSteps);
        return resultPair;
    }

}
