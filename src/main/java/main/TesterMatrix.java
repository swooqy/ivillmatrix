package main;

public class TesterMatrix {

    private int timesToTest;
    private double[] alpha;

    TesterMatrix(int timesToTest, double[] alpha){
        this.timesToTest = timesToTest;
        this.alpha = alpha.clone();
    }


    double[][] countMatrixB(int size){
        double[][] matrixB = new double[size][size];
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                matrixB[i][j]= Math.log((1-alpha[j])/(alpha[i]));
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

    public void test(double[][] hypothesisList, double variance){
        int size = hypothesisList[0].length;
        GeneratorMatrix generatorMatrix = new GeneratorMatrix(size, hypothesisList, countMatrixB(hypothesisList.length), variance);
        for(int i=0; i<hypothesisList.length; i++){
            int result;
            double[] counterArray = createCounterArray(hypothesisList.length);
            for(int j=0; j<timesToTest; j++){
                result = generatorMatrix.doTest(hypothesisList[i]);
                counterArray[result]++;
            }
            printResults(counterArray);
        }
    }

    private void printResults(double[] counterArray){
        for(int i=0; i<counterArray.length; i++){
            System.out.println("Hypothesis " + i + " result: " + (counterArray[i]/timesToTest));
        }
        System.out.println();
    }

}
