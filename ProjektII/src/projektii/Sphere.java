package projektii;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Sphere {
	int recursionLevel = 4;
	DPolygon[] Polys = new DPolygon[20 *(4*4*4*4)];
	public Sphere(double x, double y, double z, double r, Color color)
	{
            double t = (1.0 + Math.sqrt(5.0))/2.0;
            List<Vertex> lw = new ArrayList<Vertex>();
            lw.add(new Vertex(-1, t, 0));
            lw.add(new Vertex(1, t, 0));
            lw.add(new Vertex(-1, -t, 0));
            lw.add(new Vertex(1, -t, 0));
            
            lw.add(new Vertex(0, -1, t));
            lw.add(new Vertex(0, 1, t));
            lw.add(new Vertex(0, -1, -t));
            lw.add(new Vertex(0, 1, -t));
            
            lw.add(new Vertex(t, 0, -1));
            lw.add(new Vertex(t, 0, 1));
            lw.add(new Vertex(-t, 0, -1));
            lw.add(new Vertex(-t, 0, 1));
            
            List<Triangle> lt = new ArrayList<Triangle>();
            lt.add(new Triangle(lw.get(0), lw.get(11), lw.get(5)));
            lt.add(new Triangle(lw.get(0), lw.get(5), lw.get(1)));
            lt.add(new Triangle(lw.get(0), lw.get(1), lw.get(7)));
            lt.add(new Triangle(lw.get(0), lw.get(7), lw.get(10)));
            lt.add(new Triangle(lw.get(0), lw.get(10), lw.get(11)));
            
            lt.add(new Triangle(lw.get(1), lw.get(5), lw.get(9)));
            lt.add(new Triangle(lw.get(5), lw.get(11), lw.get(4)));
            lt.add(new Triangle(lw.get(11), lw.get(10), lw.get(2)));
            lt.add(new Triangle(lw.get(10), lw.get(7), lw.get(6)));
            lt.add(new Triangle(lw.get(7), lw.get(1), lw.get(8)));
            
            lt.add(new Triangle(lw.get(3), lw.get(9), lw.get(4)));
            lt.add(new Triangle(lw.get(3), lw.get(4), lw.get(2)));
            lt.add(new Triangle(lw.get(3), lw.get(2), lw.get(6)));
            lt.add(new Triangle(lw.get(3), lw.get(6), lw.get(8)));
            lt.add(new Triangle(lw.get(3), lw.get(8), lw.get(9)));
            
            lt.add(new Triangle(lw.get(4), lw.get(9), lw.get(5)));
            lt.add(new Triangle(lw.get(2), lw.get(4), lw.get(11)));
            lt.add(new Triangle(lw.get(6), lw.get(2), lw.get(10)));
            lt.add(new Triangle(lw.get(8), lw.get(6), lw.get(7)));
            lt.add(new Triangle(lw.get(9), lw.get(8), lw.get(1)));
            
            for(int j=0; j<recursionLevel; j++){
                List<Triangle> lt2 = new ArrayList<Triangle>();
                for(Triangle tri:lt){
                    //replace triangle with 4 triangles
                    Vertex a = getMiddlePoint(tri.a, tri.b);
                    Vertex b = getMiddlePoint(tri.b, tri.c);
                    Vertex c = getMiddlePoint(tri.c, tri.a);
                    
                    lt2.add(new Triangle(tri.a, a, c));
                    lt2.add(new Triangle(tri.b, b, a));
                    lt2.add(new Triangle(tri.c, c, b));
                    lt2.add(new Triangle(a, b, c));
                }
                lt = lt2;
            }
            
            int i = 0;
            for(Triangle tri:lt){
                Polys[i] = new DPolygon(tri.getA(), tri.getB(), tri.getC(), color, false);
                Screen.DPolygons.add(Polys[i]);
                i++;
            }
	}
        
        public Vertex getMiddlePoint(Vertex a, Vertex b){
            return new Vertex((a.x + b.x) / 2.0, (a.y + b.y) / 2.0, (a.z + b.z) / 2.0);
        }
}
