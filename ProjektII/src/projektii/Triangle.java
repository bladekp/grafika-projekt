/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projektii;

/**
 *
 * @author bladekp
 */
public class Triangle {
    public Vertex a,b,c;
    public Triangle(Vertex a, Vertex b, Vertex c){
        this.a = a;
        this.b = b;
        this.c = c;
    }   
    public double [] getA(){
        return new double [] {a.x, b.x, c.x};
    }
    public double [] getB(){
        return new double [] {a.y, b.y, c.y};
    }
    public double [] getC(){
        return new double [] {a.z, b.z, c.z};
    }
}
