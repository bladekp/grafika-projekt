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
public class Vertex {
    public double x,y,z;
    
    public Vertex(double x, double y, double z){
        double length = Math.sqrt(x*x + y*y + z*z);
        this.x = x/length;
        this.y = y/length;
        this.z = z/length;
    }
}
