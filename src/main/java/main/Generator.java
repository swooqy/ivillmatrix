package main;

import java.util.Random;

public class Generator {
    protected double[] hypotesis;
    protected double[] aprior;
    protected double[] apostrior;
    protected double mean,variance;
    protected double breakpoint;
    protected double[] pba;
    protected int currentstep;
    protected Random rg;
    protected int hypCount;
    protected int stepsBeforeBayes;
    protected double epsilon;
    protected int varianceCoefficient;
    Generator (double hyp[],double apr[],double brp,double inmean,double invar,int inn,int hypCount){
        hypotesis=hyp;
        aprior=apr;
        stepsBeforeBayes=inn;
        breakpoint=brp;
        mean=inmean;
        variance=invar;
        currentstep=0;
        this.hypCount=hypCount;
        pba=new double[hypCount];
        for(int i=0;i<hypCount;i++){
            pba[i]=1;
        }
        apostrior=new double[hypCount];
        rg=new Random();
    }

    protected double getRandom(){
        return (rg.nextGaussian()*variance)+mean;
    }

    protected double densityFunction(double x,double innermean) {
        return (1 / variance * Math.sqrt(2 * Math.PI)) * Math.exp((-(x - innermean) * (x - innermean)) / (2 * variance * variance));
    }
    protected void getCurrentpba(){
        double x=getRandom();
        for(int i=0;i<hypCount;i++) {
            pba[i] *= densityFunction(x, hypotesis[i]);
        }
    }
    protected void getCurrentApostreors(){
        double sum=0;
        for(int i=0;i<hypCount;i++){
            sum+=aprior[i]*pba[i];
        }
        for(int i=0;i<hypCount;i++){
            apostrior[i]=aprior[i]*pba[i]/sum;
        }
    }
    protected void makeStep(){
        currentstep++;
        getCurrentpba();
        getCurrentApostreors();
    }
    protected void makeNSteps(){
        for(int i=0;i<stepsBeforeBayes;i++){
            makeStep();
        }
    }
    protected int checkPs(){
        for(int i=0;i<hypCount;i++) {
            if(apostrior[i]>breakpoint)
                return (i+1);
        }
        return 0;
    }
    public int[] generateTest(){
        makeNSteps();
        int x=checkPs();
        while(x==0){
            makeStep();
            x=checkPs();
        }
        int[] returner=new int[2];
        returner[0]=--x;
        returner[1]=currentstep;
        return returner;
    }
}
