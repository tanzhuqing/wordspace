package knn;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.StringTokenizer;

public class DataSet implements Serializable {

	private int size;
	private int attributeNum;
	Example examples[];
	
	
	public DataSet(String filename) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(filename));
			String newLine = bf.readLine();
			StringTokenizer st = new StringTokenizer(newLine);
			size = Integer.parseInt(st.nextToken());
			attributeNum = Integer.parseInt(st.nextToken());
			examples = new Example[size];
			
			int classLabel;
			double attributes[] = new double[attributeNum];
			
			
			for (int i = 0; i <size; i++) {
				newLine = bf.readLine();
				st = new StringTokenizer(newLine);
				classLabel = Integer.parseInt(st.nextToken());
				for (int j = 0; j < attributeNum; j++) {
					attributes[j] = Double.parseDouble(st.nextToken()); 
				}
				examples[i] = new Example(classLabel, attributes);
			}
			
			
		} catch (IOException e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private int size(){
		return size;
	}
	
	public Example getExample(int index) {
		return examples[index];
	}
	
	public int getClassLabel(int index) {
		return examples[index].getClassLabel();
	}
	
	public int getAttributeNum() {
		return attributeNum;
	}
	
	public double getAttribute(int i, int j) {
		return examples[i].getAttribute(j);
	}
}
