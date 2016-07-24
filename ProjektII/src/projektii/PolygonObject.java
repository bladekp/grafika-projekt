package projektii;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class PolygonObject {
	Polygon P;
	Color c;
	boolean draw = true, visible = true, seeThrough;
	double lighting = 1;
	
	public PolygonObject(double[] x, double[] y, Color c, int n, boolean seeThrough)
	{
		P = new Polygon();
		for(int i = 0; i<x.length; i++)
			P.addPoint((int)x[i], (int)y[i]);
		this.c = c;
		this.seeThrough = seeThrough;
	}
	
	void updatePolygon(double[] x, double[] y)
	{
		P.reset();
		for(int i = 0; i<x.length; i++)
		{
			P.xpoints[i] = (int) x[i];
			P.ypoints[i] = (int) y[i];
			P.npoints = x.length;
		}
	}
        
        int trim(int x){
            if (x<0) return 0;
            if (x>255) return 255;
            return x;
        }
	
	void drawPolygon(Graphics g)
	{
		if(draw && visible)
		{
                    double wypolerowanie = Math.pow(Math.cos(lighting), Screen.n);
                    double rozproszenie = Screen.kd * Math.cos(lighting);
                    double otoczenie = Screen.ka;
                    
                    double i = wypolerowanie + rozproszenie + otoczenie;
                    
                    int re = trim((int) (Screen.c.getRed() * i));
                    int gr = trim((int) (Screen.c.getGreen() * i));
                    int bl = trim((int) (Screen.c.getBlue() * i));
                    
                    //System.out.println(re + " " + gr + " " + bl + " " + wypolerowanie + " " + lighting);
                    
                    g.setColor(new Color(re,gr,bl));
                    g.fillPolygon(P);
		}
	}
	
	boolean MouseOver()
	{
		return P.contains(ProjektII.ScreenSize.getWidth()/2, ProjektII.ScreenSize.getHeight()/2);
	}
}
