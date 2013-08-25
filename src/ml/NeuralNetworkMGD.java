package ml;
/**
 * class for training mini batch gradient decent
 * @author kamal
 *
 */
public class NeuralNetworkMGD extends NeuralNet {
	
	//private int batchSize=0;
	
	
	/**
	 * 
	 * @param sizeLayer
	 */
	public NeuralNetworkMGD(int[] sizeLayer) {
		super(sizeLayer);
		
	
		
		
	}
	
	public NeuralNetworkMGD(int[] sizeLayer,double input[][],int out[][]) {
		super(sizeLayer,input,out);
		
		
		
		
	}
	
	
	/**
	 * learn using batch of input.
	 * @param input mini  batch of input
	 * @param out output
	 */
	public void learnNeuralNetwork(double input[][],int out[][])
	{	//int variable for function
		int m=input.length;
		double D[][][];
		
		//int out[][] = null;
		double cdelta[][][]=new double[theta.length][][];
		D=new double[theta.length][][];   //derivative
		
		for(int i=0;i<cdelta.length;i++){
			cdelta[i]=new double[theta[i].length][];
			D[i]=new double[theta[i].length][];
			for(int j=0;j<cdelta[i].length;j++){
				cdelta[i][j]=new double[theta[i][j].length];
				D[i][j]=new double[theta[i][j].length];
			}
		}
	
		
		
	
			for(int l=0;l<m;l++){
				this.calculateOutput(input[l]);
		
				double delta[][]=new double[layers-1][];//layers to 1. not for 0 th layer [(layer-2) to 0]
			
				delta[layers-2]=new double[sizeLayer[layers-1]];
			
				for(int i=0;i<delta[layers-2].length;i++)
				{
					delta[layers-2][i]=act[layers-1][i]-out[l][i];
				}
		
		
				for(int i=layers-3;i>=0;i--)
				{
					delta[i]=new double[sizeLayer[i+1]];
				
					for(int j=0;j<delta[i].length;j++){
						for(int k=0;k<sizeLayer[i+2];k++){
						
							delta[i][j]=delta[i][j]+(theta[i+1][k][j+1]*delta[i+1][k]);
						}
						delta[i][j]=delta[i][j]*act[i+1][j+1]*(1.0-act[i+1][j+1]);//indexing of act and delta defer by 1  //first value of act is bias.	
					}
				}
		
				//end of calculation of delta
		
				for(int i=0;i<cdelta.length;i++){
			
					for(int j=0;j<cdelta[i].length;j++){
				
						for(int k=0;k<cdelta[i][j].length;k++){
						
							cdelta[i][j][k]=cdelta[i][j][k]+act[i][k]*delta[i][j];//delta i correspond to next layer (0-layers-1)
							
						}
					}
				}
		
			
			}//end of loop l
		
			for(int i=0;i<cdelta.length;i++){
				for(int k=0;k<cdelta[i][0].length;k++){
					D[i][0][k]=(1.0/m)*cdelta[i][0][k];//j=0
						
				}
			
			}	
			for(int i=0;i<cdelta.length;i++){
		
				for(int j=1;j<cdelta[i].length;j++){
			
					for(int k=0;k<cdelta[i][j].length;k++){
						//System.out.println(cdelta[i][j][k]);
						D[i][j][k]=(1.0/m)*cdelta[i][j][k]+lambda*theta[i][j][k];//j!=0
						//System.out.println(cdelta[i][j][k]+","+	D[i][j][k]);	
					}
				}
			}
	
		
		
			for(int i=0;i<theta.length;i++)
			{
			
				for(int j=0;j<theta[i].length;j++)
				{
			
					for(int k=0;k<theta[i][j].length;k++)
					{
						theta[i][j][k]=theta[i][j][k]-alpha*D[i][j][k];	
					}
				}
			}
		
		
	
		
	}

	
	
	/**
	 * learn using single input and output of that input.
	 * @param input input
	 * @param out output
	 */
	public void learnNeuralNetwork(double input[],int out[])
	{	//int variable for function
		int m=input.length;
		double D[][][];
		
		//int out[][] = null;
		double cdelta[][][]=new double[theta.length][][];
		D=new double[theta.length][][];   //derivative
		
		for(int i=0;i<cdelta.length;i++){
			cdelta[i]=new double[theta[i].length][];
			D[i]=new double[theta[i].length][];
			for(int j=0;j<cdelta[i].length;j++){
				cdelta[i][j]=new double[theta[i][j].length];
				D[i][j]=new double[theta[i][j].length];
			}
		}
	
		
		
	
			//for(int l=0;l<m;l++){
				this.calculateOutput(input);
		
				double delta[][]=new double[layers-1][];//layers to 1. not for 0 th layer [(layer-2) to 0]
			
				delta[layers-2]=new double[sizeLayer[layers-1]];
			
				for(int i=0;i<delta[layers-2].length;i++)
				{
					delta[layers-2][i]=act[layers-1][i]-out[i];
				}
		
		
				for(int i=layers-3;i>=0;i--)
				{
					delta[i]=new double[sizeLayer[i+1]];
				
					for(int j=0;j<delta[i].length;j++){
						for(int k=0;k<sizeLayer[i+2];k++){
						
							delta[i][j]=delta[i][j]+(theta[i+1][k][j+1]*delta[i+1][k]);
						}
						delta[i][j]=delta[i][j]*act[i+1][j+1]*(1.0-act[i+1][j+1]);//indexing of act and delta defer by 1  //first value of act is bias.	
					}
				}
		
				//end of calculation of delta
		
				for(int i=0;i<cdelta.length;i++){
			
					for(int j=0;j<cdelta[i].length;j++){
				
						for(int k=0;k<cdelta[i][j].length;k++){
						
							cdelta[i][j][k]=cdelta[i][j][k]+act[i][k]*delta[i][j];//delta i correspond to next layer (0-layers-1)
							
						}
					}
				}
		
			
			
		
			for(int i=0;i<cdelta.length;i++){
				for(int k=0;k<cdelta[i][0].length;k++){
					D[i][0][k]=(1.0/m)*cdelta[i][0][k];//j=0
						
				}
			
			}	
			for(int i=0;i<cdelta.length;i++){
		
				for(int j=1;j<cdelta[i].length;j++){
			
					for(int k=0;k<cdelta[i][j].length;k++){
						//System.out.println(cdelta[i][j][k]);
						D[i][j][k]=(1.0/m)*cdelta[i][j][k]+lambda*theta[i][j][k];//j!=0
						//System.out.println(cdelta[i][j][k]+","+	D[i][j][k]);	
					}
				}
			}
	
		
		
			for(int i=0;i<theta.length;i++)
			{
			
				for(int j=0;j<theta[i].length;j++)
				{
			
					for(int k=0;k<theta[i][j].length;k++)
					{
						theta[i][j][k]=theta[i][j][k]-alpha*D[i][j][k];	
					}
				}
			}
		
		
	
		
	}

	
	 /**
	  * 
	  * @param input 
	  * @param out
	  * @return cost J of given input and output.
	  */
	public double cost(double input[][],int out[][]){
		double J=0;
		int m=input.length;
		for(int i=0;i<m;i++){
			double o[]=this.calculateOutput(input[i]);
			for(int j=0;j<sizeLayer[layers-1];j++){
				J+=(out[i][j]*Math.log(o[j]))-(1-out[i][j])*(Math.log(1-o[j]));
			}
		}
		J=J/m;
		
		double rg=0;
		if(lambda!=0){
			for(int i=0;i<theta.length;i++){
				for(int j=0;j<theta[i].length;j++){
					for(int k=0;k<theta[i][j].length;k++){
						rg+=Math.pow(theta[i][j][k], 2);
					}
				}
			}
			rg=(lambda/(2*m))*rg;
			J=J+rg;
		}
		return J;
	}

	
}
