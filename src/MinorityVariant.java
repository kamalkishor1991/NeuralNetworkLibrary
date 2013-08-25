import java.io.*;


import rw.ReadWriteArray;

import ml.NeuralNet;
import ml.NeuralNetworkMGD;

public class MinorityVariant
{

        
    
        int max=2000003,last,high=0;
        Chain ch[]=new Chain[max];
        long con=1000000000*(long)1000000000;
        double range[];
        int siz=0;
        
        
        
    public int[] classifyReads(String[] reads)throws Exception 
    {	
        int a[]=new int[reads.length];
        range=new double[reads.length];
        int M,Q;
        double K;
        double s=0;
        fill(reads);
        
       
			
        int sizelayer[]={5,5,1};
		  NeuralNet nn=new NeuralNet(sizelayer);
		 
		  double th[][][]=new ReadWriteArray().readMultArray(new File("MVtheta MGD"));
		  nn.setTheta(th );//read from file
	
			
			double input[][]=new double[a.length][4];
			int  output1[][]=new int[a.length][1];
			
			
		  for(int i=0;i<a.length;i++)
	        {    
	            
	           
	            
	        
	            
	            Double fffff = new Double(reads[i].substring(reads[i].indexOf(' ')+1,reads[i].lastIndexOf(' ')));
	            K = fffff.doubleValue();
		
	            long t=(long)((K)*con);
	            int hash=(int)(t%max);
	          
	            
	           
	            
	            
	            
	            
	        }
		  
		  
	
		  
		  //    PrintStream fake=new PrintStream(new FileOutputStream("fake.out"));
        //PrintStream org=new PrintStream(new FileOutputStream("org.out"));
        for(int i=0;i<a.length;i++)
        {    
            
            Q=Integer.parseInt(reads[i].substring(0,reads[i].indexOf(' ')));
            M=Integer.parseInt(reads[i].substring(reads[i].lastIndexOf(' ')+1,reads[i].length()));
            
        
            
            Double fffff = new Double(reads[i].substring(reads[i].indexOf(' ')+1,reads[i].lastIndexOf(' ')));
            K = fffff.doubleValue();
			
			
			
			
            long t=(long)((K)*con);
            int hash=(int)(t%max);
		
			
			double inp[]=new double[5];
			inp[0]=1;
			inp[1]=ch[hash].getQScore(K)/40.0;
			inp[2]=ch[hash].getMScore(K)/250.0;
			inp[3]=ch[hash].getFreq(K)/802.0;
			inp[4]=K;
			
			double out[]=nn.calculateOutput(inp);
			s=out[0];
			s=s*1000000000;
			
			
            
            
            
            
            a[i]=(int)s;
           
        
                
        }
       
    //    fake.println("Q avg="+(qavgf/lnf)+"\tM avg="+(mavgf/lnf));
    //    org.println("Q avg="+(qavgo/lno)+"\tM avg="+(mavgo/lno));
        return a;
    }
    
    
    double score(double k,double q,double m)
    {
        double res;
        q=q/60.0;
        m=m/249.0;
    //    k=getRange(k);
        
    //    k=k;
        long t=(long)((k)*con);
        int hash=(int)(t%max);
        
        res=((q+ch[hash].getQScore(k))/2)*(m+ch[hash].getMScore(k))/2;
     
        return res;
    }
    
    void fill(String str[])
    {
        
        String sub;
        double p;
        long t;
        int rep=0;
        last=0;
        boolean av=false;
        int hash;
        
        long tim;
        for(int i=0;i<str.length;i++)
        {
        
            av=false;
            sub=str[i].substring(str[i].indexOf(' ')+1,str[i].lastIndexOf(' '));
            double qf=Integer.parseInt(str[i].substring(0,str[i].indexOf(' ')));
            double mf=Integer.parseInt(str[i].substring(str[i].lastIndexOf(' ')+1,str[i].length()));
            
            double dk=(new Double(sub).doubleValue());
            t=(long)((dk)*con);
            hash=(int)(t%max);
            if(ch[hash]==null)
            {
                rep=1;
                
                if(high<1)
                high=rep;
                ch[hash]=new Chain(dk,rep);
                
                sort(dk);
                
            }
            else 
            {
                
                av=false;
                Chain tc,ptc;
                tc=ch[hash];
                do{
                    
                    
                    ptc=tc;
                    if(tc.getVal()==dk)
                    {
                        av=true;
                        break;
                    }
                    
                    
                    
                }while((tc=tc.next())!=null);
                
                if(!av)
                {
                    rep=1;
                    ptc.next=new Chain(dk,rep);
                    if(high<rep)
                    high=rep;
                    
                    sort(dk);
                    
                    
                }
                else
                {
                    
                    tc.inc();
                    if(high<tc.getFreq(dk))
                    high=tc.getFreq(dk);
                }
                    
            }
                
                
        }
                
            
        for(int i=0;i<str.length;i++)
        {
        
        
            sub=str[i].substring(str[i].indexOf(' ')+1,str[i].lastIndexOf(' '));
            double qf=Integer.parseInt(str[i].substring(0,str[i].indexOf(' ')));
            double mf=Integer.parseInt(str[i].substring(str[i].lastIndexOf(' ')+1,str[i].length()));
            
            double dk=(new Double(sub).doubleValue());
            t=(long)((dk)*con);
            hash=(int)(t%max);
            
                
                
            
                
                
                
                
                ch[hash].groupScore(qf,mf,dk);
                    
                
                    
            
                
                
        }
                
            
    }
   
   double getRange(double val)
    {
        //int part=siz/40;
        for(int i=0;i<siz;i++)
        {
            if(val==range[i])
            return (double)(i+1)/(double)siz;
        }
        return -1;
    }
                
                
    void sort(double val)
    {
        int i;
        for(i=0;i<siz;i++)
        {
            if(range[i]>val)
            {
                for(int j=siz;j>i;j--)
                range[j]=range[j-1];
                
                range[i]=val;
                siz++;
                return;
            }
        }
        range[i]=val;
        siz++;
    }
                
        
        
    
    
    double frequency(String rd[],int id)
    {
        
        String kmer;
        double dt;
        String org=rd[id].substring(rd[id].indexOf(' ')+1,rd[id].lastIndexOf(' '));
        dt=new Double(org).doubleValue();
        long t=(long)(dt*con);
    
        int hash=(int)(t%max);
    
        
        
        return ch[hash].getFreq(dt)/high;
    }
    

    
    
	
    
  	String[] setReads() throws Exception{
		String arg[]=new String[3];
		arg[0]="1.tsv";
		arg[1]="";
		arg[2]="";
		BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(arg[0])));
		BufferedReader b=new BufferedReader(new InputStreamReader(new FileInputStream("locations.tsv")));
		String str;
		
		int i=0,j=0,k=0;;
		String c="",pc="",r="";
		String reads[]=new String[500000];
		boolean cc=true;
		String lo;
		for(int g=0;g<2;g++){
		
		
		
		
		while((str=br.readLine())!=null)
		{
			c=str.substring(0,str.indexOf('\t'));//pos
			pc=str.substring(str.indexOf('\t')+1);
			r=str.substring(str.indexOf('\t')+1);
			pc=pc.substring(0,pc.indexOf('\t'));//
		
			
			
			i++;
			if(c.equals(arg[1]))
			{
				if(j==0){
				if(!pc.equals(arg[2]))
				{	r=r.substring(r.indexOf('\t'));//
					r=r.substring(r.indexOf('\t')+1);//
					r=r.replaceAll("\t"," ");
					//System.out.println(str+"\t"+i);
					reads[k]=r;
					k++;
					//sc.nextLine();
				}
				}
				else if(j==1){
						if(pc.equals(arg[2]))
						{
						r=r.substring(r.indexOf('\t'));//
						r=r.substring(r.indexOf('\t')+1);//
						r=r.replaceAll("\t"," ");
						//System.out.println(str+"\t"+i);
						reads[k]=r;
						k++;
						//sc.nextLine();
						}
				}
				cc=true;
			}
			else{
				if(cc){
					
					 lo=b.readLine();
					 if(lo==null)
						break;
					arg[1]=lo.substring(0,lo.indexOf('\t'));
					arg[2]=lo.substring(lo.indexOf("\t")+1,lo.indexOf("\t",lo.indexOf("\t")+1));
					String p=lo.substring(lo.lastIndexOf('\t'));
					p=p.substring(1,p.indexOf('.'));
					//System.out.println(","+arg[1]+","+arg[2]+","+p);
					int pp=Integer.parseInt(p);
					if(!p.equals("6.5%")){
					if(pp==100)
						j=0;
					else if(pp<=6)
						j=1;
					else
						j=2;
					}
					else
						j=1;
					cc=false;
				}
			
				if(c.equals(arg[1]))
			{
				if(j==0){
				if(!pc.equals(arg[2]))
				{	r=r.substring(r.indexOf('\t'));//
					r=r.substring(r.indexOf('\t')+1);//
					r=r.replaceAll("\t"," ");
					//System.out.println(str+"\t"+i);
					reads[k]=r;
					k++;
					//sc.nextLine();
				}
				}
				else if(j==1){
						if(pc.equals(arg[2]))
						{
						r=r.substring(r.indexOf('\t'));//
						r=r.substring(r.indexOf('\t')+1);//
						r=r.replaceAll("\t"," ");
						//System.out.println(str+"\t"+i);
						reads[k]=r;
						k++;
						//sc.nextLine();
						}
				}
				cc=true;
				}
				
				
			}
			
		}
		System.out.println("k="+k);

		br.close();
		br=new BufferedReader(new InputStreamReader(new FileInputStream(arg[0])));
		}
		String rs[]=new String[k];
		for(int g=0;g<k;g++)
			rs[g]=reads[g];
		return rs;
		//System.out.println(i);
	}
  	
  	public static void train() throws IOException{
  		double input[][]=null;
		
		ReadWriteArray rw=new ReadWriteArray();
		
		input=rw.readDoubleMatrix(new File("input 1,2 K"),393254 , 5);
		int out[][]=new int[input.length][1];
		for(int i=0;i<out.length;i++){
			if(i<158141||(i>204137&&i<355401))
				out[i][0]=1;
		}
		
		for(int i=0;i<100000;i++){
			int r1=(int) (Math.random()*input.length);
			int r2=(int )(Math.random()*input.length);
			//System.out.println(r1+","+r2);
			double d[] = new double[input[r1].length];
			for(int j=0;j<input[r1].length;j++){
				d[j]=input[r1][j];
			}
			for(int j=0;j<input[r2].length;j++){
				input[r1][j]=input[r2][j];
			}
			for(int j=0;j<input[r2].length;j++){
				input[r2][j]=d[j];
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

		
		int size[]={5,5,1};
		NeuralNetworkMGD nnn=new NeuralNetworkMGD(size,input,out);
		
		nnn.initializeTheta();
		int ir=10;
		int ci=0;
		nnn.setParameter(.5, 0);
		double cost[]=new double[ir];
		cost[ci]=nnn.cost();
		System.out.println("cost="+cost[ci]+" ,i="+ci);
		for(int j=0;j<ir;j++){
		for(int i=0;i<input.length;){

			double in[][]=new double[1][input[0].length];
			int ot[][]=new int[1][out[0].length];
			try{
			for(int j1=0;j1<ot.length;j1++){
				in[j1]=input[i];
				ot[j1]=out[i];
				i++;
			}
			}catch(Exception e){
				break;
			}
			nnn.learnNeuralNetwork(in,ot);
			
			
		}
	
		
		cost[ci++]=nnn.cost();
		System.out.println("cost="+cost[ci-1]+" ,i="+ci);
		}
		rw.writeDoubleVector(cost, new File("mvalueK hl=2.txt"));
		rw.writeMultArray(nnn.getTheta(), new File("MVtheta MGD"));
  	}
  	
	public static void main(String args[]) throws Exception{
		train();
		
		MinorityVariant m=new MinorityVariant();
	//	PrintStream exact=new PrintStream(new FileOutputStream("err.out"));
		String s[]=m.setReads();
		System.out.println("length"+s.length);
		
		int r[]=m.classifyReads(s);
		int most=0,wrg=0,wct=0;
		double score=0;
		int v=158141;
	
		for(int j=v;j<r.length;j++)
			{
				String org=s[j].substring(s[j].indexOf(' ')+1,s[j].lastIndexOf(' '));
				double dt=new Double(org).doubleValue();
				long t=(long)(dt*m.con);
    
				int hash=(int)(t%m.max);
				if(m.ch[hash].getFreq(dt)>=40&&!m.ch[hash].getPrint(dt))
				{
					
					//exact.println(s[j]+"\tF="+m.ch[hash].getFreq(dt)+"\tQ="+m.ch[hash].getQScore(dt)+"\tM="+m.ch[hash].getMScore(dt));
					m.ch[hash].setPrint(dt);
				}
			}
		
		
		
	
		for(int i=0;i<v;i++)
		{
			wct=0;
			
			
			for(int j=v;j<r.length;j++)
			{
			
			
			
			
			
				if(r[i]<r[j])
				{
					
					wct++;
				}
				else if(r[i]>r[j])
					score++;
				else
				{
				score+=.5;
				}
				
			}
			  String org=s[i].substring(s[i].indexOf(' ')+1,s[i].lastIndexOf(' '));
				double dt=new Double(org).doubleValue();
				long t=(long)(dt*m.con);
    
				int hash=(int)(t%m.max);
			
			wct*=m.ch[hash].getFreq(dt);
			
			
			
			if(wct>wrg)
			{
				wrg=wct;
				most=i;
			}
		}
		score=(score)/7273853436.0;
		score=score*1000000;
		String sub=s[most].substring(s[most].indexOf(' ')+1,s[most].lastIndexOf(' '));					
					double dki=(new Double(sub).doubleValue());
					long t=(long)((dki)*m.con);
					int hashi=(int)(t%m.max);
		System.out.println("Wrong="+s[most]+"\tTimes="+wrg+"\tF="+m.ch[hashi].getFreq(dki)+"\tQ="+m.ch[hashi].getQScore(dki)+"\tM="+m.ch[hashi].getMScore(dki));
		System.out.println("Score="+score);
		
	}
	

        
    
    
    
    
        
}


    class Chain
    {
    
        double val;
        double totalq=0,totalm=0;
        int freq;
        boolean write=false;
		boolean print=false;
        Chain next=null;
        public Chain(double v,int f)
        {
            val=v;
            freq=f;
            
        }
        public void setWrite(double v)
        {
            if(val==v)
            write=true;
            else next.setWrite(v);
        }
        public void inc()
        {
            
            freq++;
        }
        public double getVal()
        {
            return val;
        }
        public int getFreq(double v)
        {
            if(v==val)
            return freq;
            
            return next.getFreq(v);
        }
        public Chain next()
        {
            return next;
        }
        public void groupScore(double qsc,double msc,double v)
        {
            if(val==v)
            {
                totalq+=qsc;
                totalm+=msc;
            }
            else
            {
                next.groupScore(qsc,msc,v);
            }
        }
        public double getQScore(double v)
        {
            if(v==val)
            {
                
                return totalq/freq;
                
            }
            else 
            return next.getQScore(v);
        }
        public double getMScore(double v)
        {
            if(v==val)
            {
                
                return totalm/freq;
            }
            else 
            return next.getMScore(v);
        }
        public boolean getWrite(double v)
        {
            if(val==v)
            return write;
            else
            return next.getWrite(v);
        }
		public void resetPrint()
		{
			print=false;
			if(next!=null)
			next.resetPrint();
		}
		public boolean getPrint(double v)
		{
			if(v==val)
			return print;
			
			return next.getPrint(v);
		}
		public void setPrint(double v)
		{
			if(v==val)
			print=true;
			else
			next.setPrint(v);
		}
        
        
    }