package main;

public class Tester {
    protected double[] hyp;
    protected double[] apr;
    protected int hypNumber;
    protected double variance;
    protected double[][]errorMatrix;
    protected int[] counter;
    protected int stepsBeforeBayes;
    protected double breakpoint;
    protected int timestotest;
    Tester(double[] inhyp,double[] inapr,double invariance,double brp, int stepsBeforeBayes,int intimestotest,int hypCount){
        timestotest=intimestotest;
        hyp=inhyp;
        apr=inapr;
        variance=invariance;
        breakpoint=brp;
        this.stepsBeforeBayes=stepsBeforeBayes;
        hypNumber=hypCount;
        errorMatrix=new double[hypCount][hypCount];
        counter=new int[hypNumber];
    }
    void test(){
        int[][] temp=new int[hypNumber][2];
        for(int i=0;i<timestotest;i++){
            Generator[] gen=new Generator[hypNumber];
            for(int j=0;j<hypNumber;j++) {
                gen[j]=new Generator(hyp,apr,breakpoint,hyp[j],variance,stepsBeforeBayes,hypNumber);
                temp[j]=gen[j].generateTest();
            }
            for(int j=0;j<hypNumber;j++){
                errorMatrix[j][temp[j][0]]++;
                counter[j]+=temp[j][1];
            }
        }
        for(int i=0;i<hypNumber;i++){
            counter[i]/=timestotest;
            for(int j=0;j<hypNumber;j++){
                errorMatrix[i][j]/=timestotest;
            }
        }
    }
    void printArray(double[] array,int indexNotToShow){
        for(int i=0;i<hypNumber;i++){
            System.out.println(array[i]);
        }
    }
    void showresults(){
        for(int i=0;i<hypNumber;i++){
          // System.out.println((i+1));
            printArray(errorMatrix[i],i);
            System.out.println(counter[i]);
        }
    }
}
