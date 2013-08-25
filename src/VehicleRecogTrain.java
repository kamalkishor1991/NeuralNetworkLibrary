import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import ml.NeuralNetworkMGD;

import rw.ReadWriteArray;

public class VehicleRecogTrain {

	public VehicleRecogTrain() {

	}

	public static void main(String args[]) throws Exception {
		// System.out.println(new
		// VehicleRecognition().classifyImage(writeImg(0)));
		//for(int i=0;i<10;i++)
		 //createTrainingSet(100,100,"TT"+i,i*100,300*i);
		 
		 
		
		 
		 
		//train();
		//check();
	}
	
	public static void check() throws Exception, IOException{
		int size[] = { 128 * 128 * 3 + 1, 100, 1 };
		
		System.out.println("loading....");
		NeuralNetworkMGD n=new NeuralNetworkMGD(size);
		ReadWriteArray rw=new ReadWriteArray();
		//n.initializeTheta();
		n.setTheta(rw.readMultArray(new File("VRTheta.txt")));
		System.out.println("loading....");
		double inTrain[][]=rw.readDoubleMatrix(new File("tst r=200 c=49153.train"),200,49153);
		int outTrain[][]=rw.readIntMatrix(new File("tst r=200 c=1.tout"), 200, 1);
		System.out.println("loading....");
		int cp=0,cn=0;
		for(int i=0;i<inTrain.length;i++){
			double d[]=n.calculateOutput(inTrain[i]);
			if(d[0]<.5&&outTrain[i][0]==0){
				cn++;
			}
			else if(d[0]>.5&&outTrain[i][0]==1)
			{
				cp++;
			}
			System.out.println("prob="+d[0]+" out"+outTrain[i][0]);
		}
		System.out.println("correct neg="+cn+"  cp="+cp+"  cost"+n.cost(inTrain, outTrain));
	}
	
	public static void train1300() throws Exception{
		ReadWriteArray rw=new ReadWriteArray();
		System.out.println("loading....");
		double inTrain[][]=rw.readDoubleMatrix(new File("testsetVRr=300 c=49153.train"),300,49153);
		int outTrain[][]=rw.readIntMatrix(new File("testsetVRr=300 c=1.tout"), 300, 1);
		
		int size[] = { 128 * 128 * 3 + 1, 50, 1 };
		System.out.println("loading....");
		double in[][]=rw.readDoubleMatrix(new File("training r=1300 c=49153.train"), 1300, 49153);
		int out[][]=rw.readIntMatrix(new File("training r=1300 c=1.tout"), 1300, 1);
		
		
		NeuralNetworkMGD n = new NeuralNetworkMGD(size);
		n.setTheta(rw.readMultArray(new File("VRTheta.txt")));
		System.out.println("loaded");
		System.gc();
		n.setParameter(1, 0);
		//n.initializeTheta();
		int ir=5;
		double cost[]=new double[ir];
		int ci=0;
		//int out[]=new int[1];
		int t=0,f=0;
		try{
		for(int j=0;j<ir;j++){
			for(int i=0;i<1300;i++){
				n.learnNeuralNetwork(in[i],out[i]);
				
				
			}
			
			cost[ci++]=n.cost(inTrain, outTrain);
			System.out.println("learning "+ci+" ci"+cost[ci-1]);
			
		}
		}catch(java.lang.OutOfMemoryError e){
			System.out.println("error"+e);
			rw.writeMultArray(n.getTheta(), new File("VRTheta.txt"));
			rw.writeDoubleVector(cost, new File("Cost.txt"));

		}
		rw.writeMultArray(n.getTheta(), new File("VRTheta.txt"));
		rw.writeDoubleVector(cost, new File("Cost.txt"));
		
		int cp=0,cn=0;
		for(int i=0;i<inTrain.length;i++){
			double d[]=n.calculateOutput(inTrain[i]);
			if(d[0]<.5&&outTrain[i][0]==0){
				cn++;
			}
			else if(d[0]>.5&&outTrain[i][0]==1)
			{
				cp++;
			}
			System.out.println("prob="+d[0]+" out"+outTrain[i][0]);
		}
		System.out.println("correct neg="+cn+"  cp="+cp);
		
	}
	
	
	public static void train() throws Exception{
		ReadWriteArray rw=new ReadWriteArray();
		System.out.println("loading....");
		double inTrain[][]=rw.readDoubleMatrix(new File("tst r=200 c=49153.train"),200,49153);
		int outTrain[][]=rw.readIntMatrix(new File("tst r=200 c=1.tout"), 200, 1);
		
		int size[] = { 128 * 128 * 3 + 1, 50,5, 1 };
		System.out.println("loading....");
		double in[][][]=new double[4][][];
		int out[][][]=new int[4][][];
		
		
		NeuralNetworkMGD n = new NeuralNetworkMGD(size);
		//n.setTheta(rw.readMultArray(new File("VRTheta.txt")));
		System.out.println("loaded");
		System.gc();
		n.setParameter(1, 0);
		n.initializeTheta();
		int ir=20 ;
		
		double cost[]=new double[ir*8];
		int ci=0;
		//int out[]=new int[1];
		int t=0,f=0;
		try{
		for(int j=0;j<ir;j++){
			in=null;
			out=null;
			
			in=new double[4][][];
			out=new int[4][][];	
							
			for(int i=0;i<4;i++){
				in[i]=rw.readDoubleMatrix(new File("TT"+i+" r=200 c=49153.train"), 200, 49153);
				out[i]=rw.readIntMatrix(new File("TT"+i+" r=200 c=1.tout"), 200, 1);
				System.out.println("load comp::"+i);
			}
			for(int k=0;k<4;k++){
				cost[ci++]=n.cost(inTrain, outTrain);	
				for(int i=0;i<200;i++){
					n.learnNeuralNetwork(in[k][i],out[k][i]);
				}
				System.out.println("learning "+ci+" ci"+cost[ci-1]);
			}
			in=null;
			out=null;
			
			in=new double[4][][];
			out=new int[4][][];	
			System.gc();
			for(int i=0;i<4;i++){
				in[i]=rw.readDoubleMatrix(new File("TT"+(i+4)+" r=200 c=49153.train"), 200, 49153);
				out[i]=rw.readIntMatrix(new File("TT"+(i+4)+" r=200 c=1.tout"), 200, 1);
				System.out.println("load comp::"+i);
			}	
			for(int k=0;k<4;k++){
				cost[ci++]=n.cost(inTrain, outTrain);	
				for(int i=0;i<200;i++){
					n.learnNeuralNetwork(in[k][i],out[k][i]);
				}
				System.out.println("learning "+ci+" ci"+cost[ci-1]);
			}
			int cp=0,cn=0;
			for(int i=0;i<inTrain.length;i++){
				double d[]=n.calculateOutput(inTrain[i]);
				if(d[0]<.5&&outTrain[i][0]==0){
					cn++;
				}
				else if(d[0]>.5&&outTrain[i][0]==1)
				{
					cp++;
				}
				//System.out.println("prob="+d[0]+" out"+outTrain[i][0]);
			}
			System.out.println("correct neg="+cn+"  cp="+cp);

			
			
		}
		}catch(java.lang.OutOfMemoryError e){
			System.out.println("err"+e);
			//rw.writeMultArray(n.getTheta(), new File("VRTheta.txt"));
			//rw.writeDoubleVector(cost, new File("Cost.txt"));
		}
		rw.writeMultArray(n.getTheta(), new File("VRTheta.txt"));
		rw.writeDoubleVector(cost, new File("Cost.txt"));
		
		int cp=0,cn=0;
		for(int i=0;i<inTrain.length;i++){
			double d[]=n.calculateOutput(inTrain[i]);
			if(d[0]<.5&&outTrain[i][0]==0){
				cn++;
			}
			else if(d[0]>.5&&outTrain[i][0]==1)
			{
				cp++;
			}
			System.out.println("prob="+d[0]+" out"+outTrain[i][0]);
		}
		System.out.println("correct neg="+cn+"  cp="+cp);
		
	}
	

	
	
	/**
	 * add bias autometically
	 * 
	 * @param p
	 *            no of pos
	 * @param n
	 *            no of neg
	 * @param name
	 *            name of file
	 * @throws Exception
	 */
	public static void createTrainingSet(int p, int n, String name)
			throws Exception {
		double in[][] = new double[p + n][];
		
		
		int out[][] = new int[p + n][1];

		BufferedReader b = null;
		int it[] = new int[p];
		for (int i = 0; i < it.length; i++)
			it[i] = -1;

		int iti = 0;
		int r = -1;
		for (int i = 0; i < p; i++) {
			while (true) {
				boolean t = true;
				r = (int) (Math.random() * 1020);
				for (int j = 0; j < it.length; j++) {
					if (it[j] == r) {
						t = false;
						break;
					}
				}
				if (t) {
					it[iti++] = r;
					break;
				}
			}
			System.out.println("adding positive example no" + i + "  img" + r);
			in[i] = getImg(r, true);
			out[i][0] = 1;

			System.gc();
		}

		it = new int[n];
		for (int i = 0; i < it.length; i++)
			it[i] = -1;

		iti = 0;
		r = -1;
		for (int i = 0; i < n; i++) {
			while (true) {
				boolean t = true;
				r = (int) (Math.random() * 3014);
				for (int j = 0; j < it.length; j++) {
					if (it[j] == r) {
						t = false;
						break;
					}
				}
				if (t) {
					it[iti++] = r;
					break;
				}
			}
			System.out
					.println("adding negative example no=" + i + "  img=" + r);
			in[i + p] = getImg(r, false);
			out[i + p][0] = 0;

		}
		System.out.println("shuffling data");
		shuffle(in, out);
		System.out.println("writing data =" + name + " r=" + in.length + " c="
				+ in[0].length + ".train" + " and " + name + " r=" + out.length
				+ " c=" + out[0].length + ".tout");
		ReadWriteArray rw = new ReadWriteArray();
		rw.writeDoubleMatrix(in, new File(name + " r=" + in.length + " c="
				+ in[0].length + ".train"));
		rw.writeIntMatrix(out, new File(name + " r=" + out.length + " c="
				+ out[0].length + ".tout"));
		System.out.println("Complete");

	}

	
	public static void createTrainingSet(int p, int n, String name,int sp,int sn)
			throws Exception {
		double in[][] = new double[p + n][];
		
		
		int out[][] = new int[p + n][1];

		BufferedReader b = null;
		int it[] = new int[p];
		for (int i = 0; i < it.length; i++)
			it[i] = -1;

		int iti = 0;
		int r = -1;
		for (int i = 0; i < p; i++) {
			while (true) {
				boolean t = true;
				r = (int) (Math.random() * p);
				for (int j = 0; j < it.length; j++) {
					if (it[j] == r) {
						t = false;
						break;
					}
				}
				if (t) {
					it[iti++] = r;
					break;
				}
			}
			System.out.println("adding positive example no" + i + "  img" + r);
			in[i] = getImg(r+sp, true);
			out[i][0] = 1;

			System.gc();
		}

		it = new int[n];
		for (int i = 0; i < it.length; i++)
			it[i] = -1;

		iti = 0;
		r = -1;
		for (int i = 0; i < n; i++) {
			while (true) {
				boolean t = true;
				r = (int) (Math.random() * n);
				for (int j = 0; j < it.length; j++) {
					if (it[j] == r) {
						t = false;
						break;
					}
				}
				if (t) {
					it[iti++] = r;
					break;
				}
			}
			System.out
					.println("adding negative example no=" + i + "  img=" + r);
			in[i + p] = getImg(r+sn, false);
			out[i + p][0] = 0;

		}
		System.out.println("shuffling data");
		shuffle(in, out);
		System.out.println("writing data =" + name + " r=" + in.length + " c="
				+ in[0].length + ".train" + " and " + name + " r=" + out.length
				+ " c=" + out[0].length + ".tout");
		ReadWriteArray rw = new ReadWriteArray();
		rw.writeDoubleMatrix(in, new File(name + " r=" + in.length + " c="
				+ in[0].length + ".train"));
		rw.writeIntMatrix(out, new File(name + " r=" + out.length + " c="
				+ out[0].length + ".tout"));
		System.out.println("Complete");

	}


	
	
	public static void shuffle(double rdata[][], int out[][]) {
		for (int i = 0; i < 50000; i++) {
			int r1 = (int) (Math.random() * rdata.length);
			int r2 = (int) (Math.random() * rdata.length);
			// System.out.println(r1+","+r2);
			double d[] = new double[rdata[r1].length];
			for (int j = 0; j < rdata[r1].length; j++) {
				d[j] = rdata[r1][j];
			}
			for (int j = 0; j < rdata[r2].length; j++) {
				rdata[r1][j] = rdata[r2][j];
			}
			for (int j = 0; j < rdata[r2].length; j++) {
				rdata[r2][j] = d[j];
			}
			int dt[] = new int[out[r1].length];
			for (int j = 0; j < out[r1].length; j++) {
				dt[j] = out[r1][j];
			}
			for (int j = 0; j < out[r2].length; j++) {
				out[r1][j] = out[r2][j];
			}
			for (int j = 0; j < out[r2].length; j++) {
				out[r2][j] = dt[j];
			}

		}

	}

	public static int[] writeImg(int i) throws Exception {
		// PrintStream p=new PrintStream(new FileOutputStream("img0.raw"));
		String path = "train\\train\\vehicles\\img";
		BufferedReader b = null;

		int out[] = new int[128 * 128 * 3];

		try {
			b = new BufferedReader(new InputStreamReader(new FileInputStream(
					path + (i) + ".raw")));
			b.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b.readLine();

		for (int j = 0; j < 128 * 128 * 3; j = j + 3) {
			String s = b.readLine();
			if (s == null)
				break;
			s = s + " ";
			// System.out.println(s);
			out[j] = Integer.parseInt(s.substring(0, s.indexOf(" ")));
			s = s.substring(s.indexOf(" ") + 1);
			out[j + 1] = Integer.parseInt(s.substring(0, s.indexOf(" ")));
			s = s.substring(s.indexOf(" ") + 1);
			out[j + 2] = Integer.parseInt(s.substring(0, s.indexOf(" ")));

		}
		System.gc();

		new ReadWriteArray().writeIntVector(out, new File("img0"));
		return out;
	}

	public static double[] getImg(int i, boolean isTrue) throws Exception {
		// PrintStream p=new PrintStream(new FileOutputStream("img0.raw"));
		String path = "";

		if (isTrue)
			path = "train\\train\\vehicles\\img";
		else
			path = "train\\train\\background\\img";
		BufferedReader b = null;

		double out[] = new double[128 * 128 * 3 + 1];

		try {
			b = new BufferedReader(new InputStreamReader(new FileInputStream(
					path + (i) + ".raw")));
			b.readLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b.readLine();

		out[0] = 1;

		for (int j = 1; j < 128 * 128 * 3 + 1; j = j + 3) {
			String s = b.readLine();
			if (s == null)
				break;
			s = s + " ";
			// System.out.println(s);
			out[j] = Integer.parseInt(s.substring(0, s.indexOf(" "))) / 255.0;
			s = s.substring(s.indexOf(" ") + 1);
			out[j + 1] = Integer.parseInt(s.substring(0, s.indexOf(" "))) / 255.0;
			s = s.substring(s.indexOf(" ") + 1);
			out[j + 2] = Integer.parseInt(s.substring(0, s.indexOf(" "))) / 255.0;

		}
		System.gc();

		return out;
	}

}
