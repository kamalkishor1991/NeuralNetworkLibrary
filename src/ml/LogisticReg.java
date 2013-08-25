package ml;

public class LogisticReg{
	private double x[][],theta[];
	private int y[];
	private double t[];
	
	private double alpha=0.00001,lambda=5,nr=1200; 
	int m=0;
	public LogisticReg(double x[][],double theta[],int y[]){
		this.x=new double[x.length][x[0].length];
		for(int i=0;i<x.length;i++){
			for(int j=0;j<x[0].length;j++){
				if(x[i][j]>80)
					this.x[i][j]=10;
				else
					this.x[i][j]=0;
			}
		}
	
		this.theta=theta;
		this.y=y;
		m=y.length;
		t=new double[m];
	}
	public LogisticReg(){
	}
	public void setAlpha(double alpha){
		this.alpha=alpha;
	}
	public void setLambda(double l){
		this.lambda=l;
	}
	
	public void setTheta(double t[]){
		this.theta=t;
	}
	public double[] updateTheta(){
		
		for(int i=0;i<nr;i++){
			double r[]=calcGrad();
			for(int j=0;j<theta.length;j++){
				theta[j]-=r[j];
			}
		}
		return theta;
	}
	
	private double[] calcGrad(){
		double r[]=new double[y.length];
		for(int i=0;i<m;i++)
				t[i]=(hypothesis(x[i],theta)-y[i]);
		
		for(int j=0;j<x[0].length;j++){
			for(int i=0;i<m;i++){
				
				r[j]+=t[i]*x[i][j];
			}
			
		}
		for(int j=0;j<x[0].length;j++)
			r[j]=(alpha/m)*r[j]-(lambda*theta[j])/m;
		return r;
	}
	
	/**
		x input vector t weight vector
	*/
	private double sigma(double x1[],double t1[]){
		double sum=0;
		//System.out.println(x1.length);
		for(int i=0;i<x1.length;i++){
			sum+=(x1[i]*t1[i]);
			if(sum<-100)
			System.out.println(x1[i]+",    "+t1[i]);
		}
		//System.out.println(sum);
		return sum;
	}
	private double sigmoid(double v){
		 
		double ret=1.0/(1+Math.exp(-1*v));
		
		return ret;
	}
	public double hypothesis(double x1[],double t1[]){
		return sigmoid(sigma(x1,t1));
	}
	

	
	
}