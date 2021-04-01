package main;


import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
	    /*double[] hyp=new double[5];
	    double[] apr=new double[5];
	    double epsilon=0;
	    int k=4;
	    hyp[0]=0;
	    hyp[1]=1;
	    hyp[2]=2;
	    hyp[3]=3;
	    apr[0]=0.25;
	    apr[1]=0.25;
	    apr[2]=0.25;
	    apr[3]=0.25;
	    double breakingpoint=0.9;
	    int stepsBeforeBayes=10;
	    int hypCount=4;
	    //Tester tester=new Tester(hyp,apr,1,breakingpoint,stepsBeforeBayes,1000,hypCount);
	    TesterModified testerModified=new TesterModified(hyp,apr,1,breakingpoint,stepsBeforeBayes,1000,hypCount,epsilon,k);
		testerModified.test();
		testerModified.showresults();*/
		double epsilon = 0;
		double k = 2;
		double[] alpha = {0.001, 0.001, 0.001};
		double[][] hypothesisList = {{0}, {0.5}, {1}};
		double variance = 100;
		TesterMatrix tester = new TesterMatrix(100, alpha);
		tester.test(hypothesisList, variance, epsilon, k);

		int size = hypothesisList[0].length;
		GeneratorMatrixCovid generatorMatrixCovid = new GeneratorMatrixCovid(size, hypothesisList, TesterMatrix.countMatrixB(hypothesisList.length, alpha), variance);
		Pair<Integer, Integer> resultPair = generatorMatrixCovid.doTest(null, 0, 0);
		System.out.println("Chosen hypotesis: " + resultPair.getKey());
		System.out.println("Steps performed: " + resultPair.getValue());

    }
}
