import java.io.*;
import javax.swing.*;

import ml.NeuralNet;

import rw.ReadWriteArray;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
public class TextRecogMouse extends JFrame implements ActionListener
{
	double theta[][];
	newpanel np;
	JButton b1,b2;
	NeuralNet n;
	public TextRecogMouse() throws Exception{
		setBounds(50,50,500,500);
		setLayout(null);
		np=new newpanel();
		np.setBounds(50,50,100,100);
		add(np);
		setVisible(true);
		np.init();
		
		b1=new JButton("Clear");
		b1.setBounds(50,200,80,25);
		b2=new JButton("Get");
		b2.setBounds(150,200,80,25);
		add(b1);
		add(b2);
		b1.addActionListener(this);
		b2.addActionListener(this);
		
		ReadWriteArray r=new ReadWriteArray();
		int size[]={401,25,10};
		n=new NeuralNet(size);
		n.setTheta(r.readMultArray(new File("theta a=1.0,l=0.0,size=,401,25,10,nr=800,%=94.62,cost=-0.0837995148113301 .txt")));
		
		/*theta=new double[10][401];
		BufferedReader bis=new BufferedReader(new InputStreamReader(new FileInputStream("thetavalues.val")));
		for(int i=0;i<10;i++){
			String str=bis.readLine()+",";
			for(int j=0;j<401;j++){
				theta[i][j]=new Double(str.substring(0,str.indexOf(','))).doubleValue();
				str=str.substring(str.indexOf(',')+1);
			}
		}
		
		bis.close();*/
	}
	public   int recognise(double a[]){
		double[] pob=n.calculateOutput(a);
		double max=-1;
		int in=-1;
		for(int i=0;i<10;i++){
			System.out.println("prob=:"+pob[i]+":  i="+i);
			if(pob[i]>max){
				max=pob[i];
				in=i;
			}
		}
		return in;
	}
	public static void main(String args[]) throws Exception{
		
		TextRecogMouse t=new TextRecogMouse();
	
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Clear"))
		{
				np.clear();
			
		}
		else
		{
			//np.changeImage();
			System.out.println(this.recognise(np.getPixels()));
		}
	}
}





class newpanel extends JPanel implements MouseMotionListener
{
	Image img;
	int sx,sy,ex,ey;
	boolean state=true;
	
	
	public newpanel()
	{
		
	}
	
	// This method returns a buffered image with the contents of an image
	
	public void init() throws Exception
	{
		
		setBackground(Color.black);
		setVisible(true);
		
		img=this.createImage(this.getWidth(),this.getHeight());
		img.getGraphics().fillRect(0,0,getWidth(),getHeight());
		BufferedReader bis=new BufferedReader(new InputStreamReader(new FileInputStream("xy.txt")));
		String str=bis.readLine()+",";
		double tx2[]=new double[401];
		tx2[0]=1;
		Graphics g=img.getGraphics();
		g.setColor(Color.white);
		int x=0,y=0;
		for(int k=1;k<401;k++)
		{
							
				tx2[k]=new Double(str.substring(0,str.indexOf(','))).doubleValue();
				if(tx2[k]>80)
				{
					g.setColor(new Color((int)tx2[k],(int)tx2[k],(int)tx2[k]));
					g.drawLine(x,y,x,y);
					
				}
				y++;
					if(y%20==0)
					{
						y=0;
						x++;
					}
				str=str.substring(str.indexOf(',')+1);
						
							
							
		}
		
	
		repaint();
		addMouseMotionListener(this);
	}
	
	
	public void paint(Graphics g)
	{
		if(img!=null)
		{
			g.drawImage(img,0,0,this);
		}
	}
	public void clear()
	{
		img=this.createImage(this.getWidth(),this.getHeight());
		img.getGraphics().fillRect(0,0,getWidth(),getHeight());
		repaint();
	}
	public double[] getPixels()
	{
		
		try{
		double pix[]=new double[401];
		Image img2=img.getScaledInstance(20,20,img.SCALE_SMOOTH);
		img=this.createImage(this.getWidth(),this.getHeight());
		Graphics g=img.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0,0,img.getWidth(this),img.getHeight(this));
		g.drawImage(img2,0,0,this);
		
		repaint();
		
		
		
		//repaint();
		int pixt[]=new int[img.getWidth(this)*img.getHeight(this)];
		PixelGrabber pg = new PixelGrabber(img2, 0, 0, img2.getWidth(this), img2.getHeight(this), pixt, 0, img2.getWidth(this));
		pg.grabPixels();
		try{
			PrintStream ps=new PrintStream(new FileOutputStream("pixel.out"));
			ps.println("rrtrtr");
		
		pix[0]=1;
		int m=1;
		int pc;
		for(int j=0;j<20;j++)
			for(int i=0;i<20;i++)
			{
				
				
				
				pix[m]=(((pixt[i*(img2.getWidth(this))+j])<<24)>>>24);
				if(pix[m]>=100)
					pix[m]=1;
				else
					pix[m]=0;
				
				ps.print((int)pix[m]*255+" ");
				ps.print((int)pix[m]*255+" ");
				ps.println((int)pix[m]*255);
				System.out.println("pixm=:"+pix[m]);
				//try{Thread.sleep(200);}catch(Exception e){}
				m++;
			}
			
			ps.println("");
		return pix;
		
		}catch(Exception e){System.out.println(e);}
		}catch(Exception e){System.out.println(e);}
		return null;
		
	}
		
	
				
	
	public void mouseDragged(MouseEvent e)
	{
	//	Graphics g=img.getGraphics();
		if(state)
		{
			sx=e.getX();
			sy=e.getY();
			
			Graphics g=img.getGraphics();
			g.setColor(Color.white);
			g.fillOval(sx-5,sy-5,10,10);
			repaint();
			
		}
	
		
		
	
	
	
	
	}
	public void mouseMoved(MouseEvent e){}
	
}
