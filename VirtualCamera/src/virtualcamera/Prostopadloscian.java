package virtualcamera;

import java.awt.Color;

public class Prostopadloscian {
	Punkt3D punktStartu;
        double width, length, height, rotation = Math.PI*0.75;
	double[] RotAdd = new double[4];
	Color c;
	double x1, x2, x3, x4, y1, y2, y3, y4;
	Wielobok3D[] Polys = new Wielobok3D[6];
	double[] angle;
	
	public Prostopadloscian(Punkt3D p, double width, double length, double height, Color c)
	{
		Polys[0] = new Wielobok3D(new double[]{p.x, p.x+width, p.x+width, p.x}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z, p.z, p.z, p.z}, c, false);
		Ekran.DPolygons.add(Polys[0]);
		Polys[1] = new Wielobok3D(new double[]{p.x, p.x+width, p.x+width, p.x}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z+height, p.z+height, p.z+height, p.z+height}, c, false);
		Ekran.DPolygons.add(Polys[1]);
		Polys[2] = new Wielobok3D(new double[]{p.x, p.x, p.x+width, p.x+width}, new double[]{p.y, p.y, p.y, p.y},  new double[]{p.z, p.z+height, p.z+height, p.z}, c, false);
		Ekran.DPolygons.add(Polys[2]);
		Polys[3] = new Wielobok3D(new double[]{p.x+width, p.x+width, p.x+width, p.x+width}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z, p.z+height, p.z+height, p.z}, c, false);
		Ekran.DPolygons.add(Polys[3]);
		Polys[4] = new Wielobok3D(new double[]{p.x, p.x, p.x+width, p.x+width}, new double[]{p.y+length, p.y+length, p.y+length, p.y+length},  new double[]{p.z, p.z+height, p.z+height, p.z}, c, false);
		Ekran.DPolygons.add(Polys[4]);
		Polys[5] = new Wielobok3D(new double[]{p.x, p.x, p.x, p.x}, new double[]{p.y, p.y, p.y+length, p.y+length},  new double[]{p.z, p.z+height, p.z+height, p.z}, c, false);
		Ekran.DPolygons.add(Polys[5]);
		
		this.c = c;
		this.punktStartu = p;
		this.width = width;
		this.length = length;
		this.height = height;
		
		setRotAdd();
		updatePoly();
	}
	
	void setRotAdd()
	{
		angle = new double[4];
		
		double xdif = - width/2 + 0.00001;
		double ydif = - length/2 + 0.00001;
		
		angle[0] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[0] += Math.PI;
		
		xdif = width/2 + 0.00001;
		ydif = - length/2 + 0.00001;
		
		angle[1] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[1] += Math.PI;
		xdif = width/2 + 0.00001;
		ydif = length/2 + 0.00001;
		
		angle[2] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[2] += Math.PI;
		
		xdif = - width/2 + 0.00001;
		ydif = length/2 + 0.00001;
		
		angle[3] = Math.atan(ydif/xdif);
		
		if(xdif<0)
			angle[3] += Math.PI;	
		
		RotAdd[0] = angle[0] + 0.25 * Math.PI;
		RotAdd[1] =	angle[1] + 0.25 * Math.PI;
		RotAdd[2] = angle[2] + 0.25 * Math.PI;
		RotAdd[3] = angle[3] + 0.25 * Math.PI;

	}
	
	void UpdateDirection(double toX, double toY)
	{
		double xdif = toX - (punktStartu.x + width/2) + 0.00001;
		double ydif = toY - (punktStartu.y + length/2) + 0.00001;
		
		double anglet = Math.atan(ydif/xdif) + 0.75 * Math.PI;

		if(xdif<0)
			anglet += Math.PI;

		rotation = anglet;
		updatePoly();		
	}

	void updatePoly()
	{
		for(int i = 0; i < 6; i++)
		{
			Ekran.DPolygons.add(Polys[i]);
			Ekran.DPolygons.remove(Polys[i]);
		}
		
		double radius = Math.sqrt(width*width + length*length);
		
			   x1 = punktStartu.x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[0]);
			   x2 = punktStartu.x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[1]);
			   x3 = punktStartu.x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[2]);
			   x4 = punktStartu.x+width*0.5+radius*0.5*Math.cos(rotation + RotAdd[3]);
			   
			   y1 = punktStartu.y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[0]);
			   y2 = punktStartu.y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[1]);
			   y3 = punktStartu.y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[2]);
			   y4 = punktStartu.y+length*0.5+radius*0.5*Math.sin(rotation + RotAdd[3]);
   
		Polys[0].x = new double[]{x1, x2, x3, x4};
		Polys[0].y = new double[]{y1, y2, y3, y4};
		Polys[0].z = new double[]{punktStartu.z, punktStartu.z, punktStartu.z, punktStartu.z};

		Polys[1].x = new double[]{x4, x3, x2, x1};
		Polys[1].y = new double[]{y4, y3, y2, y1};
		Polys[1].z = new double[]{punktStartu.z+height, punktStartu.z+height, punktStartu.z+height, punktStartu.z+height};
			   
		Polys[2].x = new double[]{x1, x1, x2, x2};
		Polys[2].y = new double[]{y1, y1, y2, y2};
		Polys[2].z = new double[]{punktStartu.z, punktStartu.z+height, punktStartu.z+height, punktStartu.z};

		Polys[3].x = new double[]{x2, x2, x3, x3};
		Polys[3].y = new double[]{y2, y2, y3, y3};
		Polys[3].z = new double[]{punktStartu.z, punktStartu.z+height, punktStartu.z+height, punktStartu.z};

		Polys[4].x = new double[]{x3, x3, x4, x4};
		Polys[4].y = new double[]{y3, y3, y4, y4};
		Polys[4].z = new double[]{punktStartu.z, punktStartu.z+height, punktStartu.z+height, punktStartu.z};

		Polys[5].x = new double[]{x4, x4, x1, x1};
		Polys[5].y = new double[]{y4, y4, y1, y1};
		Polys[5].z = new double[]{punktStartu.z, punktStartu.z+height, punktStartu.z+height, punktStartu.z};
		
	}

	void removeCube()
	{
		for(int i = 0; i < 6; i ++)
			Ekran.DPolygons.remove(Polys[i]);
		Ekran.Kostki.remove(this);
	}
}
