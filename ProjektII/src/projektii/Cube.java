package projektii;

import java.awt.Color;

public class Cube {
	double x, y, z, width, length, height, rotation = Math.PI*0.75;
	double[] RotAdd = new double[4];
	Color c;
	double x1, x2, x3, x4, y1, y2, y3, y4;
	DPolygon[] Polys = new DPolygon[6];
	double[] angle;
	
	public Cube(double x, double y, double z, double width, double length, double height, Color c)
	{
		Polys[0] = new DPolygon(new double[]{x, x+width, x+width, x}, new double[]{y, y, y+length, y+length},  new double[]{z, z, z, z}, c, false);
		Screen.DPolygons.add(Polys[0]);
		Polys[1] = new DPolygon(new double[]{x, x+width, x+width, x}, new double[]{y, y, y+length, y+length},  new double[]{z+height, z+height, z+height, z+height}, c, false);
		Screen.DPolygons.add(Polys[1]);
		Polys[2] = new DPolygon(new double[]{x, x, x+width, x+width}, new double[]{y, y, y, y},  new double[]{z, z+height, z+height, z}, c, false);
		Screen.DPolygons.add(Polys[2]);
		Polys[3] = new DPolygon(new double[]{x+width, x+width, x+width, x+width}, new double[]{y, y, y+length, y+length},  new double[]{z, z+height, z+height, z}, c, false);
		Screen.DPolygons.add(Polys[3]);
		Polys[4] = new DPolygon(new double[]{x, x, x+width, x+width}, new double[]{y+length, y+length, y+length, y+length},  new double[]{z, z+height, z+height, z}, c, false);
		Screen.DPolygons.add(Polys[4]);
		Polys[5] = new DPolygon(new double[]{x, x, x, x}, new double[]{y, y, y+length, y+length},  new double[]{z, z+height, z+height, z}, c, false);
		Screen.DPolygons.add(Polys[5]);
		
		this.c = c;
		this.x = x;
		this.y = y;
		this.z = z;
		this.width = width;
		this.length = length;
		this.height = height;

	}
}
