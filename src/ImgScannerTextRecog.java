import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import ml.NeuralNet;
import rw.ReadWriteArray;


public class ImgScannerTextRecog   extends JFrame {
	int in[];
	int h,w;
	public ImgScannerTextRecog(int in[],int h,int w){
		this.setVisible(true);
		this.setBounds(200, 200, 600, 600);
		this.in=in;
		this.h=h;
		this.w=w;
		
	}
	
	public ImgScannerTextRecog(){
		this.setVisible(true);
		
		
	}
	
	public void setPerm(int in[],int h,int w){
		this.setVisible(true);
		this.setBounds(200, 200, 600, 600);
		this.in=in;
		this.h=h;
		this.w=w;
	}
	
	
	public void paint(Graphics g){
		int ii=50,jj=50;
		for(int i=0;i<h;i++){
			for(int j=0;j<w;j++){
				//System.out.print(in[i*w+j]);
				if(in[i*w+j]==1)
					g.setColor(Color.black);
				else 
					g.setColor(Color.white);
				g.fillRect(jj,ii , 4, 4);
				jj=jj+4;
				g.setColor(Color.red);
				g.fillRect(jj, ii, 1, 4);
				jj=jj+1;
				
			}
			
			//System.out.println("");
			ii=ii+5;
			jj=50;
			g.setColor(Color.red);
			g.fillRect(jj, ii-1, w*5,1 );
			
		}
		
		
	}
	
	
	public static int[] getImgNew(String ss) throws IOException{
		DataInputStream d=new DataInputStream(new FileInputStream(ss));
		
		
		
		int h=7;//Integer.parseInt(d.readLine());
		int w=7;//Integer.parseInt(d.readLine());
		int in[]=new int[h*w];
		String s=d.readLine();
		System.out.println(s+s.length());
		for(int i=0;i<in.length;i++){
			
		
				
				in[i]=Integer.parseInt(s.substring(i,i+1));
			
		}
		return in;
	}
	
	public static int[] getHW(String s) throws NumberFormatException, IOException{
		DataInputStream d=new DataInputStream(new FileInputStream(s));
		
		
		int h[]=new int[2];
		h[0]=Integer.parseInt(d.readLine());
		h[1]=Integer.parseInt(d.readLine());
		return h;
	}
	
	
	public static int[] getImg(String ss) throws IOException{
		DataInputStream d=new DataInputStream(new FileInputStream(ss));
		
		
		
		int h=Integer.parseInt(d.readLine());
		int w=Integer.parseInt(d.readLine());
		int in[]=new int[h*w];
		for(int i=0;i<h;i++){
			String s=d.readLine();
			for(int j=0;j<w;j++){
				
				in[i*w+j]=Integer.parseInt(s.substring(j,j+1));
			}
		}
		return in;
	}
	
	public static void seeOrg(char aa) throws Exception, IOException{
		ImgScannerTextRecog ii=new ImgScannerTextRecog();
		for(int i=8;i<29;i++){
			String s="letters\\"+aa+"P"+i+".txt";
//			ImgScannerTextRecog i=new ImgScannerTextRecog(getImg(s),a[0],a[1]);
			
			int a[]=getHW(s);
			
			ii.setPerm(getImg(s),a[0],a[1]);
			ii.repaint();
			Thread.sleep(1000);
			
			}

	}
	
	public static void main(String args[]) throws Exception{
		
		
		
		
		
		
		
		seeOrg('O');
		/*
		ImgScannerTextRecog ii=new ImgScannerTextRecog();
		for(int i=0;i<400;i++){
		String ss="newletters\\EB8("+i+").txt";
//		ImgScannerTextRecog i=new ImgScannerTextRecog(getImg(s),a[0],a[1]);
		
	
		
		ii.setPerm(getImgNew(ss),7,7);
		ii.repaint();
		Thread.sleep(1000);
		
		}
		
		
		*/
		/*ReadWriteArray rw=new ReadWriteArray();
		int size[]={49,25,52};
		double[][] input=rw.readDoubleMatrix(new File(""), 100, 49);
		
		
		
		NeuralNet n=new NeuralNet(size);
		*/
		
		
		
		
		
	}
	
}
