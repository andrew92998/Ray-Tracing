package src;

public class Plane implements Interactable {
	private double[] center;
	private double[] normal;
	private double rIndex;
	private double light;
	private double[] p1;
	private double[] p2;
	private double[] p3;
	private World w;
	public Plane(double[] center, double[] normal, double rIndex, double light, World w){
		this.center=center;
		this.normal = Utilities.normal(normal);
		this.rIndex = rIndex;
		this.light = light;
		this.w = w;
	}
	public Plane(double[] p1, double[] p2, double[] p3, double rIndex, double light, World w){
		this.center = p1;
		this.normal = new double[3];
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.rIndex = rIndex;
		this.light = light;
		this.w = w;
		normal = Utilities.cross(Utilities.subtract(p1, p2), Utilities.subtract(p1, p3));
	}
	@Override
	public double[] findIntersection(Ray ray) {
		double d =  Utilities.dot(Utilities.subtract(center, ray.startPos), normal)/(Utilities.dot(ray.slope, normal));
		if(d < 0.01) return null;
		double[] inter = Utilities.add(Utilities.multiply(ray.slope, d), ray.startPos);
		double[] temp1 = Utilities.subtract(inter, p1);
		double[] temp2 = Utilities.subtract(p2, p1);
		double[] temp3 = Utilities.subtract(p3, p1);
		double temp4 = Utilities.dot(Utilities.normal(temp2), temp1);
		double temp5 = Utilities.dot(Utilities.normal(temp3), temp1);
		if(temp4 < 0 || temp4 > Utilities.mag(temp2) || temp5 < 0 || temp5 > Utilities.mag(temp3)){
			return null;
		}
		return inter;
	}

	@Override
	public double[] center() {
		return center;
	}

	@Override
	public double[] getNorm(double[] pos) {
		return normal;
	}

	@Override
	public double rIndex(double[] pos) {
		return rIndex;
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
