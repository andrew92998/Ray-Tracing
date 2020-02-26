package src;

public class StripedSphere implements Interactable {
	private double[] center;
	private double radius;
	private double rIndex;
	private double light;
	public World w;

	public StripedSphere(double[] center, double radius, double rIndex, double light, World w) {
		this.center = center;
		this.radius = radius;
		this.rIndex = rIndex;
		this.light = light;
		this.w = w;
	}

	public StripedSphere(double x, double y, double z, double r, double rIndex, double light, World w) {
		this.radius = r;
		this.center = new double[3];
		this.rIndex = rIndex;
		center[0] = x;
		center[1] = y;
		center[2] = z;
		this.light = light;
		this.w = w;
	}

	@Override
	public double[] findIntersection(Ray ray) {
		double[] temp = Utilities.subtract(ray.startPos, center);
		double a = Utilities.dot(ray.slope, ray.slope);
		double b = 2 * (Utilities.dot(ray.slope, temp));
		double c = Utilities.dot(temp, temp) - radius * radius;
		double underSqrt = b * b - 4 * a * c;
		if (underSqrt >= 0) {
			double distance1 = (-b + Math.sqrt(underSqrt)) / (2 * a);
			double distance2 = (-b - Math.sqrt(underSqrt)) / (2 * a);
			if (distance2 > 0.00001) {
				return Utilities.add(ray.startPos, Utilities.multiply(ray.slope, distance2));
			} else if (distance1 > 0.00001) {
				return Utilities.add(ray.startPos, Utilities.multiply(ray.slope, distance1));
			}
		}
		return null;
	}

	@Override
	public double[] getNorm(double[] pos) {
		return Utilities.normal(Utilities.subtract(pos, center));
	}

	@Override
	public double rIndex(double[] pos) {
		return 1.5;
	}

	@Override
	public double light(double[] pos) {
		if((int)(pos[0]*5)%2 == 0) return 0;
		return light;
	}

	@Override
	public double reflection(Ray ray) {
		return 0;
	}

	@Override
	public double refraction(Ray ray) {
		return 0;
	}

	public double[] center() {
		return center;
	}

	@Override
	public double shadow(Ray ray) {
		double[] intersect = findIntersection(ray);

		double shadow = 0;
		for (Interactable i : ray.w.Interactables) {
			if (i != this) {
				double[] reSlope = Utilities.subtract(i.center(), intersect);
				Ray shadowRay = new Ray(intersect, reSlope, 1, ray.numSpawn - 1, w);
				double minDist = -1;
				Interactable closest = null;
				for (Interactable j : ray.w.Interactables) {
					double[] inter = j.findIntersection(shadowRay);
					if (inter != null) {
						double dist = Utilities.mag(Utilities.subtract(inter, intersect));
						if ((minDist == -1 || dist < minDist) && dist > 0.00001) {
							if(dist < 0.000001) System.out.println(dist);
							closest = j;
							minDist = dist;
						}
					}

				}

				if (closest!= null && closest == i) {
					shadow += closest.light(i.findIntersection(shadowRay))*Math.exp(-0.0044*Utilities.mag(Utilities.subtract(intersect, closest.findIntersection(shadowRay))));
					if((int)(intersect[0]*5)%2 == 0) shadow *=0.2;
				}
			}
		}
		return shadow;
	}

}

