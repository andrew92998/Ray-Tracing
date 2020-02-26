package src;

public class Ray {
	double startPos[], slope[];
	double rIndex;
	World w;
	int numSpawn;
	
	public Ray(double[] startPos, double slope[], double rIndex, int numSpawn, World w){
		this.numSpawn = numSpawn;
		this.startPos = startPos;
		this.slope = Utilities.normal(slope);
		this.rIndex = rIndex;
		this.w = w;
	}
	
	public double getColor(){
		double color = 0;
		double minDist = -1;
		Interactable closest = w.Interactables.get(0); //dummy interactable
		for(Interactable i:w.Interactables){
			double[] inter = i.findIntersection(this);
			if(inter != null){
				double dist = Utilities.mag(Utilities.subtract(inter, startPos));
				if((minDist == -1 || dist < minDist )){
					closest = i;
					minDist = dist;
				}
			}
	
		}
		if (minDist > 0 && numSpawn > 0){
			double r = reflectance(closest);
			double t = 1-r;
			color += r*closest.reflection(this);
			color += t*closest.refraction(this);
			color += closest.shadow(this);
			color += closest.light(closest.findIntersection(this));
			if(color > 0.999){
				color = 0.999;
			}
			double length = Utilities.mag(Utilities.subtract(startPos, closest.findIntersection(this)));
			double atten = Math.exp(-length*0.0044);
		//	System.out.println(atten);
			color = color*atten;
		}
		return color;
	}
	public double reflectance(Interactable i){
		double irIndex = i.rIndex(i.findIntersection(this));
		double n1cos = Math.abs(this.rIndex*Utilities.dot(slope, i.getNorm(i.findIntersection(this))));
		double n2cos = Math.abs(irIndex*Math.sqrt(1-(this.rIndex/irIndex)*(this.rIndex/irIndex)));
		double n1cos2 = Math.abs(this.rIndex*Math.sqrt(1-(this.rIndex/irIndex)*(this.rIndex/irIndex)));
		double n2cos2 = Math.abs(irIndex*Utilities.dot(slope, i.getNorm(i.findIntersection(this))));
		double rs = Math.abs((n1cos-n2cos)/(n1cos+n2cos))*Math.abs((n1cos-n2cos)/(n1cos+n2cos));
		double rp = Math.abs((n1cos2-n2cos2)/(n1cos2+n2cos2))*Math.abs((n1cos2-n2cos2)/(n1cos2+n2cos2));
		double r = 0.5*(rs+rp);
		return r;
	}
	
}
