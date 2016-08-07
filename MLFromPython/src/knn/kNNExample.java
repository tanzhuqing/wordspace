package knn;

public class kNNExample  extends Example{

	private double relativeDistance;
	private double weight;
	private int id;
	private static double[] featureWeights;
		
	public kNNExample(int classLabel, double[] attributes) {
		super(classLabel, attributes);
	}
	
	public void setWeight(int kernel) {
		this.weight = 1.0/Math.exp(kernel * relativeDistance);
	}
	
	public void setrelativeDist(Example example) {
		double sum = 0;
		for (int i = 0; i < attributes.length; i++) {
			sum = sum + featureWeights[i]*(Math.pow(getAttribute(i)-example.getAttribute(i), 2));
		}
		this.relativeDistance = Math.sqrt(sum);
	}

	public boolean equals(kNNExample example) {
		return this.id == example.getId();
	}

	public double getRelativeDistance() {
		return relativeDistance;
	}

	

	public double getWeight() {
		return weight;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static double[] getFeatureWeights() {
		return featureWeights;
	}

	public static void setFeatureWeights(double[] featureWeights) {
		int numWeights = featureWeights.length;
		kNNExample.featureWeights = new double[numWeights];
		for (int i = 0; i < numWeights; i++) {
			kNNExample.featureWeights[i] = featureWeights[i];
		}
		
	}
	
	public static void setFeatureWeights(int attributeNum) {
		kNNExample.featureWeights = new double[attributeNum];
		for (int i = 0; i < attributeNum; i++) {
			kNNExample.featureWeights[i]=1;
		}
	}
	
}
