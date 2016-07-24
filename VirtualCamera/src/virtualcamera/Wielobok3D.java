package virtualcamera;

import java.awt.Color;

public class Wielobok3D {
	Color c;
	double[] x, y, z;
	boolean draw = true, seeThrough = false;
	double[] CalcPos, newX, newY;
	Wielobok2D wielobok2D;
	double AvgDist;
	
	public Wielobok3D(double[] x, double[] y,  double[] z, Color c, boolean seeThrough)
	{
		this.x = x;
		this.y = y;
		this.z = z;		
		this.c = c;
		this.seeThrough = seeThrough; 
		createPolygon();
	}
	
	void createPolygon()
	{
		wielobok2D = new Wielobok2D(new double[x.length], new double[x.length], c, Ekran.DPolygons.size(), seeThrough);
	}
	
	void updatePolygon()
	{		
		newX = new double[x.length];
		newY = new double[x.length];
		draw = true;
		for(int i=0; i<x.length; i++)
		{
			CalcPos = Konwerter3Ddo2D.CalculatePositionP(Ekran.ViewFrom, Ekran.ViewTo, x[i], y[i], z[i]);
			newX[i] = (WirtualnaKamera.ScreenSize.getWidth()/2 - Konwerter3Ddo2D.CalcFocusPos[0]) + CalcPos[0] * Ekran.zoom;
			newY[i] = (WirtualnaKamera.ScreenSize.getHeight()/2 - Konwerter3Ddo2D.CalcFocusPos[1]) + CalcPos[1] * Ekran.zoom;			
			if(Konwerter3Ddo2D.t < 0)
				draw = false;
		}
		
		
		wielobok2D.draw = draw;
		wielobok2D.aktualizujWielobok(newX, newY);
		AvgDist = GetDist();
	}
	double GetDist()
	{
		double total = 0;
		for(int i=0; i<x.length; i++)
			total += GetDistanceToP(i);
		return total / x.length;
	}
	
	double GetDistanceToP(int i)
	{
		return Math.sqrt((Ekran.ViewFrom[0]-x[i])*(Ekran.ViewFrom[0]-x[i]) + 
						 (Ekran.ViewFrom[1]-y[i])*(Ekran.ViewFrom[1]-y[i]) +
						 (Ekran.ViewFrom[2]-z[i])*(Ekran.ViewFrom[2]-z[i]));
	}
}
