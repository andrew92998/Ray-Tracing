package src;


public class Camera extends Point {
	public double[] attitude = new double[3];
	public double angleFromHorizon;
	public double zoom;
	public Camera(double[] position, int origColor) {
		super(position, origColor);
		attitude[0]=0;
		attitude[1]=1;
		attitude[2]=0;
		angleFromHorizon = 0;
		zoom = 1;
	}
	
	public Camera(double x, double y, double z, int origColor) {
		super(x,y, z, origColor);
		attitude[0]=0;
		attitude[1]=1;
		attitude[2]=0;
		angleFromHorizon = 0;
		zoom = 400;
	}
	
	public double[][] getLight(World w){
		double[][] image = new double[1000][600];
		for(int i = 0; i < 1000; i++){
			for(int j = 0; j < 600; j++){
				double[] slope = new double[3];
				slope[0] = i-500;
				slope[1] = 300-j;
				slope[2] = 600;
				Ray ray = new Ray(this.position, slope, 1.333, 4, w);
				image[i][j] = ray.getColor();
			}
		}
		return image;
		
	}

}
