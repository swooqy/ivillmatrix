package main;


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
		double[] alpha = {0.001, 0.001, 0.001};
		double[][] hypothesisList = {{0}, {1}, {2}};
		double variance = 1;
		TesterMatrix tester = new TesterMatrix(10000, alpha);
		tester.test(hypothesisList, variance);
    }
}
