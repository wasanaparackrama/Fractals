/*E/16/267-co225 project*/

public class Julia
{
	protected boolean boundSet;
	private int sIter;
	private double x_given;
	private double y_given;
	private double x;
	private double y;
	private double z_x;
	private double z_y;
	
	
	
	//Mapping the x,y coordinates in the bound into complex number 
	public void Panel(double r,double i)
	{
		z_x=(((double)r*2)/800)-1;
		z_y=(((double)i*2)/800)-1;
	}
	
	//Constructor for Julia set
	public Julia(double x,double y)
	{
		x_given=x;
		y_given=y;
	}
	//returns x,y coordinates
		public double returnX()
		{
			return x;
		}
		public double returnY()
		{
			return y;
		}
	
	
	
	//Checks the complex coordinates are in the set of Julia set 
	public boolean checkBound(double cr,double ci,int iterations)		
	{
		sIter=0;
		cr=x_given;
		ci=y_given;
		while(iterations-->0)
		{
			double zr=cr+(z_x*z_x)-(z_y*z_y);
			double zi=ci+(2*z_x*z_y);
			z_x=zr;
			z_y=zi;
			sIter++;
			double absz_2=Math.pow(z_x,2)+Math.pow(z_y,2);
			if(absz_2>4)
			{	
				return boundSet=false;
			}	
		}	
		return boundSet=true;
	}
	
	
	
	//returns the iterations in the checkBound
	public int returnIter()
	{
		return sIter;
	}
}