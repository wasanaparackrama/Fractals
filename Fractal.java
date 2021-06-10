/*E/16/267-co225 project*/


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

//inherits JPanel 
@SuppressWarnings("serial")
public class Fractal extends JPanel
{ 	
	//to get inputs
	private int iterations;
	private double real_1;
	private double real_2;
	private double img_1;
	private double img_2;
	//set the plot
	private boolean boundSet;
	private int height=800;
	private int width=800;
	private int complexVal=5;
	
	
	
	Mandelbrot ms=null;
	Julia js=null;
	
	//constructor for Mandelbrot set
	public Fractal(double x_s,double x_e,double y_s,double y_e,int iter)
	{
		
		// set the plot size 
		setPreferredSize(new Dimension(width,height));
		
		this.real_1=x_s;
		this.real_2=x_e;
		this.img_1=y_s;
		this.img_2=y_e;
		this.iterations=iter;
		complexVal=0;
		//create Mandelbrot object
		ms = new Mandelbrot(real_1,real_2,img_1,img_2);
		
	}
	
	//constructor for Julia set
	public Fractal(double x,double y,int iterations)
	{
		// set the plot size 
		setPreferredSize(new Dimension(width,height));
		
		this.real_1=x;
		this.img_1=y;
		this.iterations=iterations;
		complexVal=1;
		//create Julia object
		js = new Julia(x,y);
		
	}
	
	
	 
	//calling paintComponent from parent class 
	public void paintComponent(Graphics g) 
	{ 
		//creating threads
		Thread[] thread=new Thread[4];
		//to returns the current time in milliseconds 
		long startTime = System.currentTimeMillis();
		
		// call paintComponent from parent class 
		super.paintComponent(g); 
		Color color=null;
		//creating thread by implementing Runnable
		thread[0] = new Thread(new Runnable()
		{	
			public void run()
			{
				printPlot(g,color,0,400,0,400);
			}	
		});
		thread[0].start();
	    thread[1] = new Thread(new Runnable()
		{	
			public void run()
			{
				printPlot(g,color,400,800,0,400);
			}	
		});
		thread[1].start();
		 thread[2] = new Thread(new Runnable()
		{	
			public void run()
			{
				printPlot(g,color,0,400,400,800);
			}	
		});
		 thread[2].start();
		 thread[3]= new Thread(new Runnable()
		{	
			public void run()
			{
				printPlot(g,color,400,800,400,800);
			}	
		});
		 thread[3].start();
		
		try {thread[0].join();
		     thread[1].join();
		     thread[2].join() ;
		     thread[3].join() ;//wait to finish
		     
		                 
	   }catch(InterruptedException ex){
		   System.out.println(ex.toString());
			      }
		
		long endTime = System.currentTimeMillis();
		long totalTime=endTime-startTime;
		System.out.println("Processing.....");
		System.out.println("Finished in "+ totalTime +" ms");
	}
	
	//print points in the given values with a given color from graphic class
		private static void printPoint(Graphics2D frame, Color c,double x,double y) 
		{
		    frame.setColor(c); 
		    frame.draw(new Line2D.Double(x,y,x,y)); 
		}	
	
	public synchronized void printPlot(Graphics g,Color color,int x1,int y1,int x2,int y2)
	{
		for(int i=x1;i<=y1;i++)
		{	
			for(int j=x2;j<=y2;j++)
			{	
				if(complexVal==0)//checks mandelbrot
				{	
					ms.Panel(i,j);
					boundSet = ms.checkBound(ms.returnX(),ms.returnY(),iterations);
					//color iteration for different points
					color = Color.getHSBColor((float)ms.returnIter()*20.0f/(float)iterations,//color map
							1.0f,//saturation,intensity
							1.0f //brightness
							);
				}
				else if(complexVal==1)//checks Julia
				{	
					js.Panel(i,j);
					this.boundSet = js.checkBound(js.returnX(),js.returnY(),iterations);
					//color iteration for different points
					color = Color.getHSBColor((float)js.returnIter()*10.0f/(float)iterations,
							1.0f,
							1.0f);
				}	
				//checks the point exists in or out of the set
				if(boundSet)
				{	 
					printPoint((Graphics2D)g,Color.BLACK,i,j); 
				}	 
				else
				{
					printPoint((Graphics2D)g,color,i,j);
				}
			}	
		}		
	}
	
	//Usage Details for handle wrong inputs
	public static void printUse()
	{
		System.out.println("Usage:");
    	System.out.println("java Fractal Arguments Mandelbrot <real_1> <real_2> <imaginary_1> <imaginary_2> <iterations>");
    	System.out.println("java Fractal Julia <real_1> <imaginary_1> <iterations>");
    	System.exit(0);
	}
	
	public static void main(String[]args)
	{	 
		
	    JFrame frame=null;
	    if(args.length>=1)
	    {	
	    	if(args[0].equals("Mandelbrot")) //for 0 arguments
	    	{
	    		double real_1=0;
	    		double real_2=0;
	    		double img_1=0;
	    		double img_2=0;
	    		int iterations=0;
	    		if(args.length==1)
	    		{
	    			real_1=-1;
	    			real_2=1;
	    			img_1=-1;
	    			img_2=1;
	    			iterations=1000;
	    		}
	    		else if(args.length==6) //for 5 arguments
	    		{
	    			real_1 = Double.parseDouble(args[1]);
	    			real_2 = Double.parseDouble(args[2]);
	    			img_1 = Double.parseDouble(args[3]);
	    			img_2 = Double.parseDouble(args[4]);
	    			iterations = Integer.parseInt(args[5]);
	    		}
	    		else if(args.length==5) //for 4 arguments
	    		{
	    			real_1 = Double.parseDouble(args[1]);
	    			real_2 = Double.parseDouble(args[2]);
	    			img_1 = Double.parseDouble(args[3]);
	    			img_2 = Double.parseDouble(args[4]);
	    			iterations = 1000;
	    		}
	    		else
	    		{
	    			System.out.println("\nAn Error Occured");
	    			printUse();
	    		}
	    		frame = new JFrame("Mandelbrot Set");     
	    		// set the content of the frame in the panel
	    		frame.setContentPane(new Fractal(real_1,real_2,img_1,img_2,iterations));
	    		
	    	}	
	    	else if(args[0].equals("Julia")) 
	    	{
	    		
	    		double real_1=0;
	    		double img_1=0;
	    		int iterations=0;
	    		if(args.length==1)     // for 0 arguments  
	    		{
	    			real_1=-0.4;
	    			img_1=0.6;
	    			iterations=1000;
	    		}
	    		else if(args.length==4)		//for 3 arguments
	    		{	
	    			real_1 = Double.parseDouble(args[1]);
	    			img_1 = Double.parseDouble(args[2]);
	    			iterations = Integer.parseInt(args[3]);
	    		}
	    		else if(args.length==3)		//for 2 arguments
	    		{	
	    			real_1 = Double.parseDouble(args[1]);
	    			img_1 = Double.parseDouble(args[2]);
	    			iterations = 1000;
	    		}
	    		else
	    		{
	    			System.out.println("\nAn Error Occured");
	    			printUse();
	    		}
	    		frame = new JFrame("Julia Set"); 
	    		// set the content of the frame in the panel
	    		frame.setContentPane(new Fractal(real_1,img_1,iterations));
	    		
	    	}
	    	else
	    	{
	    		System.out.println("\nThe Passed Argument for the Fractal Type can't Identified");
		    	printUse();
	    	}
	    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack(); 
			frame.setLocationRelativeTo(null); 
			frame.setVisible(true); 	
	    }	
	    else
	    {
	    	System.out.println("\nNot enough arguments");
	    	printUse();
	    }
	   		
	}
}