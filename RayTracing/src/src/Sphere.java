package src;

public class Sphere implements Interactable {
	private double[] center;
	private double radius;
	private double rIndex;
	private double light;
	public World w;

	public Sphere(double[] center, double radius, double rIndex, double light, World w) {
		this.center = center;
		this.radius = radius;
		this.rIndex = rIndex;
		this.light = light;
		this.w = w;
	}

	public Sphere(double x, double y, double z, double r, double rIndex, double light, World w) {
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
		return light;
	}

	@Override
	public double reflection(Ray ray) {
		double[] norm = getNorm(findIntersection(ray));
		double newIndex = 1.333;
		if (Utilities.dot(norm, ray.slope) <= 0) {
			newIndex = rIndex;
		}
		double[] reSlope = Utilities.reflected(ray.slope, getNorm(findIntersection(ray)));
		Ray reflection = new Ray(this.findIntersection(ray), reSlope, newIndex, ray.numSpawn - 1, w);
		return reflection.getColor();
	}

	@Override
	public double refraction(Ray ray) {
		double[] norm = getNorm(findIntersection(ray));
		double newIndex = rIndex;
		if (Utilities.dot(norm, ray.slope) <= 0) {
			newIndex = 1.333;
		}
		double cos1 = Utilities.dot(norm, ray.slope);
		double[] reNorm = Utilities.multiply(norm, cos1);
		double[] diff = Utilities.subtract(reNorm, ray.slope);

		double[] reSlope = Utilities.add(Utilities.multiply(reNorm, 1),
				Utilities.multiply(diff, -(ray.rIndex / newIndex)));
		Ray refraction = new Ray(findIntersection(ray), reSlope, newIndex, ray.numSpawn - 1, w);
		return refraction.getColor();
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
					shadow += closest.light(closest.findIntersection(shadowRay))*Math.exp(-0.0044*Utilities.mag(Utilities.subtract(intersect, closest.findIntersection(shadowRay))));
				}
			}
		}
		return shadow;
	}

}
