import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import rw.ReadWriteArray;
import ml.NeuralNet;
import ml.NeuralNetworkMGD;


public class TextRecog {
	
	public static void learnNN() throws Exception{
		double data[][]=new ReadWriteArray().readDoubleMatrix(new File("ABC.txt"), 5000, 400);
		
		double rdata[][]=new double[5000][401];
		for(int i=0;i<5000;i++)
		{
			rdata[i][0]=1;
			for(int j=0;j<400;j++)
				rdata[i][j+1]=data[i][j];
		}
		int size[]={401,25,10};
		int out[][]=new int[5000][10];
		
		for(int i=0;i<5000;i++)
		{
			
				out[i][i/500]=1;
			
		}
		NeuralNet nn=new NeuralNet(size,rdata,out);
		//nn.setOut(out);
		nn.initializeTheta();
	
		double th[][][]=new double[2][][];
		int nr=100;
		double cost[]=new double [nr];
		nn.setParameter(1, 0);
		ReadWriteArray rwa=new ReadWriteArray();
		for(int i=0;i<nr;i++){
			cost[i]=nn.learnNeuralNetwork();
			Thread.sleep(1000);
			System.out.println("cost"+cost[i]+"  ="+nn.cost());
			
			//cost[i]=nn.cost();
			//System.out.println("cost="+nn.cost());
		}
		
		//th[0]=rwa.readDoubleMatrix(new File("Th1.txt"),25,401);
		///th[1]=rwa.readDoubleMatrix(new File("Th2.txt"), 10, 26);
		
		String str="";
		//nn.setTheta(th);
		for(int i=0;i<size.length;i++){
			str=str+","+size[i];
		}
		
		int c=0;
		for(int j=0;j<5000;j++)
		{
		double xx[]=nn.calculateOutput(rdata[j]);
		double max=0;
		int in=0;
		for(int i=0;i<10;i++){
			System.out.println(xx[i]+" , "+i);
			if(xx[i]>max){
				max=xx[i];
				in=i;
			}
		}
		System.out.println("in="+in);
		if((j)/500==(in))
			c++;
		
		}
		
		System.out.println("currect="+c+","+nn.cost());
		rwa.writeDoubleVector(cost, new File("cost.txt"));
		rwa.writeMultArray(nn.getTheta(), new File("theta a="+nn.getAlpha()+",l="+nn.getLambda()+",size="+str+",nr="+nr+",%="+(c/50.0)+",cost="+nn.cost()+" .txt"));

	}
	
	public void trainMGD() throws Exception{
		double data[][]=new ReadWriteArray().readDoubleMatrix(new File("ABC.txt"), 5000, 400);
		
		double rdata[][]=new double[5000][401];
		
		for(int i=0;i<5000;i++)
		{
			rdata[i][0]=1;
			for(int j=0;j<400;j++)
				rdata[i][j+1]=data[i][j];
		}
		double input[][]=new double[rdata.length][rdata[0].length];
		for(int i=0;i<input.length;i++)
		{	for(int j=0;j<input[0].length;j++){
				input[i][j]=rdata[i][j];
			}
			
		}
		
		
		
		int size[]={401,25,10};
		int out[][]=new int[5000][10];
		
		for(int i=0;i<5000;i++)
		{
			
				out[i][i/500]=1;
			
		}
		
		for(int i=0;i<50000;i++){
			int r1=(int) (Math.random()*5000);
			int r2=(int )(Math.random()*5000);
			//System.out.println(r1+","+r2);
			double d[] = new double[rdata[r1].length];
			for(int j=0;j<rdata[r1].length;j++){
				d[j]=rdata[r1][j];
			}
			for(int j=0;j<rdata[r2].length;j++){
				rdata[r1][j]=rdata[r2][j];
			}
			for(int j=0;j<rdata[r2].length;j++){
				rdata[r2][j]=d[j];
			}
			int dt[] = new int[out[r1].length];
			for(int j=0;j<out[r1].length;j++){
				dt[j]=out[r1][j];
			}
			for(int j=0;j<out[r2].length;j++){
				out[r1][j]=out[r2][j];
			}
			for(int j=0;j<out[r2].length;j++){
				out[r2][j]=dt[j];
			}
			
			
		}
		
		
		NeuralNetworkMGD nn=new NeuralNetworkMGD(size);
		NeuralNet nnn=new NeuralNet(size,rdata,out);
		//nn.setOut(out);
		nn.initializeTheta();
		nnn.setTheta(nn.getTheta());
		double th[][][]=new double[2][][];
		int nr=60;
		double cost[]=new double [nr];
		int ci=0;
		nn.setParameter(1, 0);
		ReadWriteArray rwa=new ReadWriteArray();
		for(int jj=0;jj<nr;jj++){
		for(int i=0;i<5000;){
			
			double in[][]=new double[10][rdata[0].length];
			int ot[][]=new int[10][out[0].length];
			
			for(int j=0;j<10;j++){
				in[j]=rdata[i];
				ot[j]=out[i];
				i++;
			}
			
			nn.learnNeuralNetwork(in,ot);
			
			
			//cost[i]=nn.cost();
			//System.out.println("cost="+nn.cost());
		}
		cost[ci]=nnn.cost();
		ci++;
		//Thread.sleep(1000);
		//System.out.println("cost"+cost[ci++]);
		}
		//th[0]=rwa.readDoubleMatrix(new File("Th1.txt"),25,401);
		///th[1]=rwa.readDoubleMatrix(new File("Th2.txt"), 10, 26);
		
		String str="";
		//nn.setTheta(th);
		for(int i=0;i<size.length;i++){
			str=str+","+size[i];
		}
		
		int c=0;
		for(int j=0;j<5000;j++)
		{
		double xx[]=nn.calculateOutput(input[j]);
		double max=0;
		int in=0;
		for(int i=0;i<10;i++){
			System.out.println(xx[i]+" , "+i);
			if(xx[i]>max){
				max=xx[i];
				in=i;
			}
		}
		System.out.println("in="+in);
		if((j)/500==(in))
			c++;
		
		}
		
		System.out.println("currect="+c+","+nnn.cost());
		rwa.writeDoubleVector(cost, new File("costMGD.txt"));
		rwa.writeMultArray(nn.getTheta(), new File("theta a="+nn.getAlpha()+",l="+nn.getLambda()+",size="+str+",nr="+nr+",%="+(c/50.0)+",cost="+nnn.cost()+" .txt"));
	}
	
	
	
	public static void main(String args[]) throws Exception{
		learnNN();
		System.out.println("fasdf");
		double data[][]=new ReadWriteArray().readDoubleMatrix(new File("ABC.txt"), 5000, 400);
	
		double rdata[][]=new double[5000][401];
		
		for(int i=0;i<5000;i++)
		{
			rdata[i][0]=1;
			for(int j=0;j<400;j++){
				if(data[i][j]>0.392156862745098){
				rdata[i][j+1]=1;
				}
				else{
					rdata[i][j+1]=0;
				}
			}
		}
		PrintStream p=new PrintStream(new FileOutputStream("pixel.out"));
		p.println("20 20");
		for(int i=1;i<401;i++){
			p.println((int)rdata[0][i]*255+" "+(int)rdata[0][i]*255+" "+(int)rdata[0][i]*255);
		}
		double input[][]=new double[rdata.length][rdata[0].length];
		for(int i=0;i<input.length;i++)
		{	for(int j=0;j<input[0].length;j++){
				input[i][j]=rdata[i][j];
				//System.out.println(input[i][j]);
			}
			
		}
		
		
		
		int size[]={401,25,10};
		int out[][]=new int[5000][10];
		
		for(int i=0;i<5000;i++)
		{
			
				out[i][i/500]=1;
			
		}
		
		for(int i=0;i<50000;i++){
			int r1=(int) (Math.random()*5000);
			int r2=(int )(Math.random()*5000);
			//System.out.println(r1+","+r2);
			double d[] = new double[rdata[r1].length];
			for(int j=0;j<rdata[r1].length;j++){
				d[j]=rdata[r1][j];
			}
			for(int j=0;j<rdata[r2].length;j++){
				rdata[r1][j]=rdata[r2][j];
			}
			for(int j=0;j<rdata[r2].length;j++){
				rdata[r2][j]=d[j];
			}
			int dt[] = new int[out[r1].length];
			for(int j=0;j<out[r1].length;j++){
				dt[j]=out[r1][j];
			}
			for(int j=0;j<out[r2].length;j++){
				out[r1][j]=out[r2][j];
			}
			for(int j=0;j<out[r2].length;j++){
				out[r2][j]=dt[j];
			}
			
			
		}
		
		
		NeuralNetworkMGD nn=new NeuralNetworkMGD(size,rdata,out);
		//NeuralNet nnn=new NeuralNet(size,rdata,out);
		//nn.setOut(out);
		nn.initializeTheta();
		//nn.setTheta(nn.getTheta());
		double th[][][]=new double[2][][];
		int nr=10;
		double cost[]=new double [nr];
		int ci=0;
		nn.setParameter(1, 0);
		ReadWriteArray rwa=new ReadWriteArray();
		for(int jj=0;jj<nr;jj++){
		for(int i=0;i<5000;i++){
			
			//double in[][]=new double[10][rdata[0].length];
			//int ot[][]=new int[10][out[0].length];
			
			/*for(int j=0;j<10;j++){
				in[j]=rdata[i];
				ot[j]=out[i];
				i++;
			}*/
			
			nn.learnNeuralNetwork(rdata[i],out[i]);
			
			
			//cost[i]=nn.cost();
			//System.out.println("cost=");
		}
		cost[ci]=nn.cost(rdata,out);
		ci++;
		//Thread.sleep(1000);
		//System.out.println("cost"+cost[ci++]);
		}
		//th[0]=rwa.readDoubleMatrix(new File("Th1.txt"),25,401);
		///th[1]=rwa.readDoubleMatrix(new File("Th2.txt"), 10, 26);
		
		String str="";
		//nn.setTheta(th);
		for(int i=0;i<size.length;i++){
			str=str+","+size[i];
		}
		
		int c=0;
		for(int j=0;j<5000;j++)
		{
		double xx[]=nn.calculateOutput(input[j]);
		double max=0;
		int in=0;
		for(int i=0;i<10;i++){
			System.out.println(xx[i]+" , "+i);
			if(xx[i]>max){
				max=xx[i];
				in=i;
			}
		}
		System.out.println("in="+in);
		if((j)/500==(in))
			c++;
		
		}
		
		System.out.println("currect="+c+","+nn.cost());
		rwa.writeDoubleVector(cost, new File("costMGD.txt"));
		rwa.writeMultArray(nn.getTheta(), new File("theta a="+nn.getAlpha()+",l="+nn.getLambda()+",size="+str+",nr="+nr+",%="+(c/50.0)+",cost="+nn.cost()+" .txt"));
	}
	
	
}
