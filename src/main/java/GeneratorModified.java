package main;

public class GeneratorModified extends Generator {
    private double epsilon;
    private int varianceCoefficient;

    GeneratorModified(double hyp[],double apr[],double brp,double inmean,double invar,int inn,int hypCount, double epsilon, int varianceCoefficient){
        super(hyp,apr,brp,inmean,invar,inn,hypCount);
        this.epsilon=epsilon;
        this.varianceCoefficient=varianceCoefficient;
    }

    @Override
    public double getRandom(){
        if(epsilon>Math.random()){
            return (rg.nextGaussian()*variance*varianceCoefficient)+mean;
        }else{
            return getRandom();
        }
    }

}
