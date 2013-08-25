import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import javax.swing.JFrame;

import rw.ReadWriteArray;


public class VehicleRecogShow  extends JFrame{
	BufferedReader b;
	int h=0,w=0;
	public VehicleRecogShow(String path){
		
		this.setBounds(0, 0, 300,300);
		this.setVisible(true);
		try {
			b=new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String s=b.readLine();
			s=s+" ";
			//System.out.println(s);
			h=Integer.parseInt(s.substring(0,s.indexOf(" ") ));
			s=s.substring(s.indexOf(" ")+1);
			w=Integer.parseInt(s.substring(0,s.indexOf(" ") ));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void paint(Graphics g){
		for(int i=50;i<50+h;i++){
			for(int j=50;j<50+w;j++){
				try {
					String s=b.readLine();
					s=s+" ";
					//System.out.println(s);
					int r=Integer.parseInt(s.substring(0,s.indexOf(" ") ));
					s=s.substring(s.indexOf(" ")+1);
					int gg=Integer.parseInt(s.substring(0,s.indexOf(" ") ));
					s=s.substring(s.indexOf(" ")+1);
					int bb=Integer.parseInt(s.substring(0,s.indexOf(" ") ));
					g.setColor(new Color(r,gg,bb));
					//System.out.println(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					break;
				}
				catch(Exception e){
					break;
				}
				
				
				
				g.drawOval(i,j , 0, 0);
			}
		}
	}
	
	public static void  writeImg() throws Exception{
		int t=1020;
		PrintStream p=new PrintStream(new FileOutputStream("VRtraining.txt"));
		String path="";
		BufferedReader b = null;
		
		double out[][]=new double[t][];
		for(int i=0;i<t;i++){
			
			try {
				b=new BufferedReader(new InputStreamReader(new FileInputStream(path+(i)+".raw")));
				b.readLine();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.readLine();
			
			out[i]=new double[128*128*3];
			
			for(int j=0;j<128*128*3;j=j+3){
				String s=b.readLine();
				if(s==null)
					break;
				s=s+" ";
				//System.out.println(s);
				out[i][j]=Integer.parseInt(s.substring(0,s.indexOf(" ") ));
				s=s.substring(s.indexOf(" ")+1);
				out[i][j+1]=Integer.parseInt(s.substring(0,s.indexOf(" ") ));
				s=s.substring(s.indexOf(" ")+1);
				out[i][j+2]=Integer.parseInt(s.substring(0,s.indexOf(" ") ));

			}
			System.gc();
			System.out.println("writing img =:"+i);
			
			
		}
		new ReadWriteArray().writeDoubleMatrix(out, new File("VRTPos.txt"));
	}
	
	
	
	public static void main(String args[]) throws IOException{
		new VehicleRecogShow("pixel.out");
		
	}
}
