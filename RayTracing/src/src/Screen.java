package src;
import java.awt.Color;
import java.util.ArrayList;

public class Screen {
	public static int[] pixels = new int[1000 * 600];
	public static int backdrop = 0;
	public static boolean drawing = false;
	public static double radius;
	public static int x;
	public static int y;
	public static int moldColor = 255;
	private static double pixelRadius = 600;

	public static void render(World world) {
		double[][] lights = world.camera.getLight(world);
		for(int i = 0; i < 1000; i++){
			for(int j = 0; j < 600; j++){
				float light = (float)lights[i][j];
			//	if(light>0)System.out.println(light);
				pixels[1000*j+i] = Color.HSBtoRGB(0, 0, light);
			}
		}
	}

	public static void clearPoints() {


	}

	public static void drawMoldCircle() {
		for (int i = x - (int) radius; i < x + (int) radius; i++)
			for (int j = y - (int) radius; j < y + (int) radius; j++)
				if (((double) i - (double) x) * ((double) i - (double) x)
						+ ((double) j - (double) y) * ((double) j - (double) y) < radius * radius
						&& ((double) i - x) * ((double) i - x) + ((double) j - y) * ((double) j - y) > radius * radius
								- 100) {

					if (i > 0 && i < 1000 && j > 0 && j < 600)
						pixels[i + j * 1000] = moldColor;
				}

	}

	public static void white() {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = 255 * 256 * 256 + 255 * 256 + 255;
			//pixels[i] = 0;
	}

	public static double[] getInfo(Point p, Camera camera){
		double[] c = Utilities.subtract(p.position, camera.position);
		double aMag = Utilities.dot(c, Utilities.normal(camera.attitude));
		double[] a = Utilities.multiply(Utilities.normal(camera.attitude), aMag);
		double[] b = Utilities.subtract(c, a);

		double[] flat = new double[3];
		flat[0] = -camera.attitude[1];
		flat[1] = camera.attitude[0];
		flat[2] = 0;
		flat = Utilities.normal(flat);

		double[] perpFlat = Utilities.normal(Utilities.cross(camera.attitude, flat));

		double[] POV = new double[3];
		POV[0] = -Utilities.dot(b, flat);
		POV[1] = Utilities.dot(b, perpFlat);

		double[] POVAdjusted = Utilities.multiply(POV, 1 / aMag * camera.zoom);

		double[] info = new double[5];
		info[0] = POVAdjusted[0];
		info[1] = POVAdjusted[1];
		info[2] = Utilities.mag(c);
		info[3] = p.trueColor + 0.1; // just in case some floating number
										// business goes on when converting it
										// back to int
		info[4] = aMag;
		return info;
	}


	public static void drawPoints() {


	}

	public static void setXY(int x2, int y2) {
		x = x2;
		y = y2;
	}

}
