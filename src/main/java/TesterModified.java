package main;

public class TesterModified extends Tester{
    protected double epsilon;
    protected int varianceCoefficient;
    TesterModified(double[] inhyp,double[] inapr,double invariance,double brp, int stepsBeforeBayes,int intimestotest,int hypCount,double epsilon,int varianceCoefficient){
        super(inhyp,inapr,invariance,brp,stepsBeforeBayes,intimestotest,hypCount);
        this.epsilon=epsilon;
        this.varianceCoefficient=varianceCoefficient;
    }
    @Override
    void test(){
        int[][] temp=new int[hypNumber][2];
        for(int i=0;i<timestotest;i++){
            Generator[] gen=new Generator[hypNumber];
            for(int j=0;j<hypNumber;j++) {
                gen[j]=new GeneratorModified(hyp,apr,breakpoint,hyp[j],variance,stepsBeforeBayes,hypNumber,epsilon,varianceCoefficient);
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
    @Override
    void printArray(double[] array,int indexNotToShow){
        for(int i=0;i<hypNumber;i++){
            System.out.println(array[i]);
        }
    }
    @Override
    void showresults(){
        for(int i=0;i<hypNumber;i++){
            //System.out.println("Hypotesis "+(i+1));
            printArray(errorMatrix[i],i);
            System.out.println(counter[i]);
            //System.out.println("----------------");
        }
    }

}
