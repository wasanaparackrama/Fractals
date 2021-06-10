/*E/16/267-co225 project*/

public class Mandelbrot
{
	protected  static boolean boundSet;
	private static double x;
	private static double y;
	private int sIter;
	
	private double real_1;     
	private double real_2;
	private double img_1;
	private double img_2;
	
	
	//Mapping the x,y coordinates into complex number within the region of interest 
		public void Panel(int r,int i)
		{
			x=(((double)r*(real_2-real_1))/800)-Math.abs(real_1);
			y=(((double)i*(img_2-img_1))/800)-Math.abs(img_1);
		}
	
	//Constructor for Mandelbrot set
	public Mandelbrot(double arg_r1,double arg_r2,double arg_i1,double arg_i2)
	{
		this.real_1=arg_r1;
		this.real_2=arg_r2;
		this.img_1=arg_i1;
		this.img_2=arg_i2;
	}
	
	
	//returns x,y coordinates in the region
		public double returnX()
		{
			return x;
		}
		public double returnY()
		{
			return y;
		}
	
	//Checks the complex coordinates are in the set of Mandelbrot
	public  boolean checkBound(double cr,double ci,int iterations)		
	{
		sIter=0;
		double z_x=0; double z_y=0;
		while(iterations-->0)
		{
			double zr=cr+(z_x*z_x)-(z_y*z_y);
			double zi=ci+(2*z_x*z_y);
			z_x=zr;
			z_y=zi;
			double absz_2=Math.pow(z_x,2)+Math.pow(z_y,2);
			sIter++;
			if(absz_2>4)
			{	
				return boundSet=false;
			}	
		}	
		return boundSet=true;
	}
		
	
	//returns the number of iterations took on the boundCheck
	public int returnIter()
	{
		return sIter;
	}
}