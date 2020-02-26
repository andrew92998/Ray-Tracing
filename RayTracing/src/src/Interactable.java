package src;

public interface Interactable {
	public double[] findIntersection(Ray ray);
	public double[] center();
	public double[] getNorm(double[] pos);
	public double rIndex(double[] pos);
	public double light(double[] pos);
	public double reflection(Ray ray);
	public double refraction(Ray ray);
	public double shadow(Ray ray);

}
