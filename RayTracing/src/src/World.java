package src;
import java.util.ArrayList;

public class World {
	public boolean inPlay=false;
	public ArrayList<Point> points = new ArrayList<Point>();
	public Camera camera;
	public int groundColor = 255;
	private boolean requestedReCalc = false;
	public ArrayList<Interactable> Interactables = new ArrayList<Interactable>();
	public World(){
		camera = new Camera(0,0,0,0);
		Interactable s1 = new Sphere(0,0,20, 1, 1.0, 0.1, this);
		Interactable s6 = new Sphere(-2.2,0,20, 1, 1.0, 0.1, this);
		Interactable s2 = new StripedSphere(1,1,25, 3.6, 1.8, 0.0, this);
		Interactable s3 = new Sphere(5.3,0,17, 3, 1.0, 0.0, this);
		Interactable s4 = new Sphere(-3,10, 10, 1, 1.0, 0.5, this);
		
		
		

		double[] p11 = {20, -13, 35};
		double[] p12 = {-20, -13, 35};
		double[] p13 = {20, -5, 45};
		double[] p21 = {20, 20, 45};
		double[] p22 = {-20, -5, 45};
		Interactable p1 = new Plane(p11,p12,p13,30000,0.0,this);
		Interactable p2 = new Plane(p13,p22,p21,30000,0.0,this);
		Interactables.add(p2);
		Interactables.add(s2);
		Interactables.add(s6);
		Interactables.add(s3);
		Interactables.add(s4);
		Interactables.add(p1);
		
	}
	public void render(int toRight,int toUp, int toTilt, int forward, int sideways, int upward, int x, int y) {
		
	}
	public void requestReCalculation(){
		requestedReCalc = true;
	}
}
