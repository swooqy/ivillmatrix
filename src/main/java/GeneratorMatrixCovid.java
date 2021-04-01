package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class GeneratorMatrixCovid extends GeneratorMatrix{
    GeneratorMatrixCovid(int size, double[][] hypothesisList, double[][] matrixB, double variance) {
        super(size, hypothesisList, matrixB, variance);
    }

    private String[] readDataFromFile(String path) throws  Exception{
        String row;
        String[] data = new String[0];
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        while ((row = csvReader.readLine()) != null) {
            data = row.split(",");
        }
        return data;
    }

    private int[] parseValues(int startFromDay, int length){
        int[] resultArray = new int[length+1];
        try{
            resultArray = new int[length+1];
            String[] dataFromFile = readDataFromFile("C:\\Users\\Huliankou_AA\\Documents\\belarusdata.csv");
            for(int i = 0; i< length; i++){
                resultArray[i] = Integer.parseInt(dataFromFile[i+startFromDay]);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return resultArray;
    }

    @Override
    protected double[][] countMatrix(double[] trueValue, double epsilon, double k){
        int[] realValues = parseValues(60, 100);
        int t=1;
        double [][] currentMatrix;
        while(true){
            observations.add((double)realValues[t]); //здесь вместо генерации использовать реальные данные
            currentMatrix = countMatrix(t);
            if(isExceedingMatrixB(currentMatrix)){
                break;
            }
            t++;
        }
        return currentMatrix;
    }
}
