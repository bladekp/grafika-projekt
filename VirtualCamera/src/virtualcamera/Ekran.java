package virtualcamera;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Ekran extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener{
	
	public Ekran()
	{		
		this.addKeyListener(this);
		setFocusable(true);		
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		
		Kostki.add(new Prostopadloscian(new Punkt3D(18, -5, 0), 
                        2, 2, 5, Color.CYAN));
		Kostki.add(new Prostopadloscian(new Punkt3D(22, -5, 0), 
                        4, 2, 6, Color.gray));
                Kostki.add(new Prostopadloscian(new Punkt3D(18, -10, 0), 
                        1, 1, 9, Color.WHITE));
                Kostki.add(new Prostopadloscian(new Punkt3D(20, -10, 0), 
                        5, 2, 4, Color.ORANGE));
	}	
	
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(99, 111, 144));
		g.fillRect(0, 0, (int)WirtualnaKamera.ScreenSize.getWidth(), (int)WirtualnaKamera.ScreenSize.getHeight());

		CameraMovement();
		
		Konwerter3Ddo2D.Init();

		for(int i = 0; i < DPolygons.size(); i++)
			DPolygons.get(i).updatePolygon();
		
		elementyZasloniete();
                
		for(int i = 0; i < NewOrder.length; i++)
			DPolygons.get(NewOrder[i]).wielobok2D.narysujWielobok(g);
				
		SleepAndRefresh();
	}
	
	void elementyZasloniete()
	{
		double[] k = new double[DPolygons.size()];
		NewOrder = new int[DPolygons.size()];
		
		for(int i=0; i<DPolygons.size(); i++)
		{
			k[i] = DPolygons.get(i).AvgDist;
			NewOrder[i] = i;
		}
		
	    double temp;
	    int tempr;	    
		for (int a = 0; a < k.length-1; a++)
			for (int b = 0; b < k.length-1; b++)
				if(k[b] < k[b + 1])
				{
					temp = k[b];
					tempr = NewOrder[b];
					NewOrder[b] = NewOrder[b + 1];
					k[b] = k[b + 1];
					   
					NewOrder[b + 1] = tempr;
					k[b + 1] = temp;
				}
	}
		
	void SleepAndRefresh()
	{
		long timeSLU = (long) (System.currentTimeMillis() - LastRefresh); 

		Checks ++;			
		if(Checks >= 15)
		{
			drawFPS = Checks/((System.currentTimeMillis() - LastFPSCheck)/1000.0);
			LastFPSCheck = System.currentTimeMillis();
			Checks = 0;
		}
		
		if(timeSLU < 1000.0/MaxFPS)
		{
			try {
				Thread.sleep((long) (1000.0/MaxFPS - timeSLU));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		}
				
		LastRefresh = System.currentTimeMillis();
		
		repaint();
	}
	
	void CameraMovement()
	{
		Wektor ViewVector = new Wektor(ViewTo[0] - ViewFrom[0], ViewTo[1] - ViewFrom[1], ViewTo[2] - ViewFrom[2]);
		double xMove = 0, yMove = 0, zMove = 0;
		Wektor VerticalVector = new Wektor (0, 0, 1);
		Wektor SideViewVector = ViewVector.IloczynWektorow(VerticalVector);
		
		if(Keys[0])
		{
			xMove += ViewVector.x ;
			yMove += ViewVector.y ;
			zMove += ViewVector.z ;
		}

		if(Keys[2])
		{
			xMove -= ViewVector.x ;
			yMove -= ViewVector.y ;
			zMove -= ViewVector.z ;
		}
			
		if(Keys[1])
		{
			xMove += SideViewVector.x ;
			yMove += SideViewVector.y ;
			zMove += SideViewVector.z ;
		}

		if(Keys[3])
		{
			xMove -= SideViewVector.x ;
			yMove -= SideViewVector.y ;
			zMove -= SideViewVector.z ;
		}
                
                if(Keys[4])
		{
                    zMove += Math.abs(ViewVector.z);
                }
		
                if(Keys[5])
		{
                    zMove -= Math.abs(ViewVector.z);
                }
                
                if(Keys[6])
		{
                    xMove += SideViewVector.x ;
                    yMove += SideViewVector.y ;
                    zMove += SideViewVector.z ;
                }
		
                if(Keys[7])
		{
                    xMove -= SideViewVector.x ;
                    yMove -= SideViewVector.y ;
                    zMove -= SideViewVector.z ;
                }
                
		Wektor MoveVector = new Wektor(xMove, yMove, zMove);
		MoveTo(ViewFrom[0] + MoveVector.x * MovementSpeed, ViewFrom[1] + MoveVector.y * MovementSpeed, ViewFrom[2] + MoveVector.z * MovementSpeed);
	}

	void MoveTo(double x, double y, double z)
	{
		ViewFrom[0] = x;
		ViewFrom[1] = y;
		ViewFrom[2] = z;
		updateView();
	}

	void MouseMovement(double NewMouseX, double NewMouseY)
	{		
			double difX = (NewMouseX - WirtualnaKamera.ScreenSize.getWidth()/2);
			double difY = (NewMouseY - WirtualnaKamera.ScreenSize.getHeight()/2);
			difY *= 6 - Math.abs(VertLook) * 5;
			VertLook -= difY  / VertRotSpeed;
			HorLook += difX / HorRotSpeed;
	
			if(VertLook>0.999)
				VertLook = 0.999;
	
			if(VertLook<-0.999)
				VertLook = -0.999;
			
			updateView();
	}
	
	void updateView()
	{
		double r = Math.sqrt(1 - (VertLook * VertLook));
		ViewTo[0] = ViewFrom[0] + r * Math.cos(HorLook);
		ViewTo[1] = ViewFrom[1] + r * Math.sin(HorLook);		
		ViewTo[2] = ViewFrom[2] + VertLook;
	}
	
	void CenterMouse() 
	{
			try {
				r = new Robot();
				r.mouseMove((int)WirtualnaKamera.ScreenSize.getWidth()/2, (int)WirtualnaKamera.ScreenSize.getHeight()/2);
			} catch (AWTException e) {
				e.printStackTrace();
			}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			Keys[0] = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			Keys[1] = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			Keys[2] = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			Keys[3] = true;
                if(e.getKeyCode() == KeyEvent.VK_W)
			Keys[4] = true;
                if(e.getKeyCode() == KeyEvent.VK_S)
			Keys[5] = true;
                if(e.getKeyCode() == KeyEvent.VK_A)
			Keys[6] = true;
                if(e.getKeyCode() == KeyEvent.VK_D)
			Keys[7] = true;
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			System.exit(0);
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP)
			Keys[0] = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			Keys[1] = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			Keys[2] = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			Keys[3] = false;
                if(e.getKeyCode() == KeyEvent.VK_W)
			Keys[4] = false;
                if(e.getKeyCode() == KeyEvent.VK_S)
			Keys[5] = false;
                if(e.getKeyCode() == KeyEvent.VK_A)
			Keys[6] = false;
                if(e.getKeyCode() == KeyEvent.VK_D)
			Keys[7] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseDragged(MouseEvent arg0) {
		MouseMovement(arg0.getX(), arg0.getY());
		MouseX = arg0.getX();
		MouseY = arg0.getY();
		CenterMouse();
	}
	
	public void mouseMoved(MouseEvent arg0) {
		MouseMovement(arg0.getX(), arg0.getY());
		MouseX = arg0.getX();
		MouseY = arg0.getY();
		CenterMouse();
	}
	
	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}

	public void mouseWheelMoved(MouseWheelEvent arg0) {
		if(arg0.getUnitsToScroll()>0)
		{
			if(zoom > MinZoom)
				zoom -= 25 * arg0.getUnitsToScroll();
		}
		else
		{
			if(zoom < MaxZoom)
				zoom -= 25 * arg0.getUnitsToScroll();
		}	
	}
        static ArrayList<Wielobok3D> DPolygons = new ArrayList<Wielobok3D>();
	
	static ArrayList<Prostopadloscian> Kostki = new ArrayList<Prostopadloscian>();
	
	static Wielobok2D PolygonOver = null;

	Robot r;

	static double[] ViewFrom = new double[] { 5, -7, 10},	
					ViewTo = new double[] {20, 20, 100};

	static double zoom = 1000, MinZoom = 500, MaxZoom = 2500, MouseX = 0, MouseY = 0, MovementSpeed = 0.04;
	
	double drawFPS = 0, MaxFPS = 1000, SleepTime = 1000.0/MaxFPS, LastRefresh = 0, StartTime = System.currentTimeMillis(), LastFPSCheck = 0, Checks = 0;
        
	double VertLook = -0.9, HorLook = 0, aimSight = 4, HorRotSpeed = 10, VertRotSpeed = 22, SunPos = 0;

	int[] NewOrder;

	static boolean OutLines = true;
	boolean[] Keys = new boolean[8];
	
	long repaintTime = 0;
	
}
