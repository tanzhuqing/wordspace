package knn;

import java.io.Serializable;

public class Example implements Serializable {

	private int classLabel;
	protected double[] attributes;
	
	
	public Example(int classLabel,double[] attributes) {
		this.classLabel = classLabel;
		this.attributes = new double[attributes.length];
		for (int i = 0; i < attributes.length; i++) {
			this.attributes[i] = attributes[i];
		}
	}


	public int getClassLabel() {
		return classLabel;
	}

	public double getAttribute(int index) {
		return attributes[index];
	}

	
	
	
}
