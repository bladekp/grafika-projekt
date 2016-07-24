package virtualcamera;

public class Konwerter3Ddo2D {
	static double t = 0;
	static Wektor W1, W2, ViewVector, RotationVector, DirectionVector, PlaneVector1, PlaneVector2;
	static Rzutnia P;
	static double[] CalcFocusPos = new double[2];
	
	static double[] CalculatePositionP(double[] ViewFrom, double[] ViewTo, double x, double y, double z)
	{		
		double[] drawP = PrzeniesNaRzutnie(ViewFrom, ViewTo, x, y, z, P);
		return drawP;
	}

	static double[] PrzeniesNaRzutnie(double[] ViewFrom, double[] ViewTo, double x, double y, double z, Rzutnia P)
	{
		Wektor ViewToPoint = new Wektor(x - ViewFrom[0], y - ViewFrom[1], z - ViewFrom[2]);

                /* t = odległość rzutni */
			   t = (P.NV.x*P.P[0] + P.NV.y*P.P[1] +  P.NV.z*P.P[2]
				 - (P.NV.x*ViewFrom[0] + P.NV.y*ViewFrom[1] + P.NV.z*ViewFrom[2]))
				 / (P.NV.x*ViewToPoint.x + P.NV.y*ViewToPoint.y + P.NV.z*ViewToPoint.z);

		x = ViewFrom[0] + ViewToPoint.x * t;
		y = ViewFrom[1] + ViewToPoint.y * t;
		z = ViewFrom[2] + ViewToPoint.z * t;
		
		double DrawX = W2.x * x + W2.y * y + W2.z * z;
		double DrawY = W1.x * x + W1.y * y + W1.z * z;		
		return new double[]{DrawX, DrawY};
	}
	
	
	static Wektor getRotationVector(double[] ViewFrom, double[] ViewTo)
	{
		double dx = Math.abs(ViewFrom[0]-ViewTo[0]);
		double dy = Math.abs(ViewFrom[1]-ViewTo[1]);
		double xRot, yRot;
		xRot=dy/(dx+dy);		
		yRot=dx/(dx+dy);

		if(ViewFrom[1]>ViewTo[1])
			xRot = -xRot;
		if(ViewFrom[0]<ViewTo[0])
			yRot = -yRot;

			Wektor V = new Wektor(xRot, yRot, 0);
		return V;
	}
	
	static void Init()
	{
		ViewVector = new Wektor(Ekran.ViewTo[0] - Ekran.ViewFrom[0], Ekran.ViewTo[1] - Ekran.ViewFrom[1], Ekran.ViewTo[2] - Ekran.ViewFrom[2]);			
		DirectionVector = new Wektor(1, 1, 1);				
		PlaneVector1 = ViewVector.IloczynWektorow(DirectionVector);
		PlaneVector2 = ViewVector.IloczynWektorow(PlaneVector1);
		P = new Rzutnia(PlaneVector1, PlaneVector2, Ekran.ViewTo);

		RotationVector = Konwerter3Ddo2D.getRotationVector(Ekran.ViewFrom, Ekran.ViewTo);
		W1 = ViewVector.IloczynWektorow(RotationVector);
		W2 = ViewVector.IloczynWektorow(W1);

		CalcFocusPos = Konwerter3Ddo2D.CalculatePositionP(Ekran.ViewFrom, Ekran.ViewTo, Ekran.ViewTo[0], Ekran.ViewTo[1], Ekran.ViewTo[2]);
		CalcFocusPos[0] = Ekran.zoom * CalcFocusPos[0];
		CalcFocusPos[1] = Ekran.zoom * CalcFocusPos[1];
	}
}
