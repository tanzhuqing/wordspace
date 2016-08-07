package crawler.demo.goodcrawler.sbs.util;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.BitSet;
import java.util.Collection;


public class BloomFilter<E> implements Serializable {

	private static final long serialVersionUID = -2421480352844166626L;
	private BitSet bitSet;
	private int bitSetSize;
	private double bitsPerElement;
	private int expectedNumberOfFilterElements;
	private int numberOfAddedElements;
	private int k;
	
	static final Charset charset = Charset.forName("UTF-8");
	
	static final String hashName = "MD5";
	static final MessageDigest digestFunction;
	
	static{
		MessageDigest tmp;
		try {
			tmp = java.security.MessageDigest.getInstance(hashName);
		} catch (Exception e) {
			tmp = null;
		}
		digestFunction = tmp;
	}

	public BloomFilter(double c, int n, int k){
		this.expectedNumberOfFilterElements = n;
		this.k = k;
		this.bitsPerElement = c;
		this.bitSetSize = (int)Math.ceil(c*n);
		numberOfAddedElements = 0;
		this.bitSet = new BitSet(bitSetSize);
	}
	
	public BloomFilter(int bitSetSize,int expectedNumberOfElements){
		this(bitSetSize/(double)expectedNumberOfElements,
				expectedNumberOfElements,
				(int)Math.round((bitSetSize/(double)expectedNumberOfElements)*Math.log(2.0)));
	}
	
	public BloomFilter(double falsePositiveProbability,int expectedNumberOfElements) {
		this(Math.ceil(-(Math.log(falsePositiveProbability)/Math.log(2)))/Math.log(2),
				expectedNumberOfElements,
				(int)Math.ceil(-(Math.log(falsePositiveProbability)/Math.log(2))));
	}
	
	 public BloomFilter(int bitSetSize,int expectedNumberOfFilterElements,int actualNumerbOfFilterElements,BitSet filterData) {
		this(bitSetSize, expectedNumberOfFilterElements);
		this.bitSet = filterData;
		this.numberOfAddedElements = actualNumerbOfFilterElements;
	}
	 
	 public static int createHash(String val,Charset charset) {
		return createHash(val.getBytes(charset));
	}
	 
	 public static int  createHash(String val) {
		return createHash(val, charset);
	}
	 
	 public static int createHash(byte[] data) {
		return createHashes(data,1)[0];
	}
	 
	 public static int[] createHashes(byte[] data,int hashes) {
		int[] result = new int[hashes];
		
		int k = 0;
		byte salt = 0;
		while(k < hashes){
			byte[] digest;
			synchronized (digestFunction) {
				digestFunction.update(salt);
				salt++;
				digest = digestFunction.digest(data);
			}
			
			for (int i = 0; i < digest.length/4 && k < hashes; i++) {
				int h=0;
				for (int j = (i*4); j < (i*4)+4; j++) {
					h <<= 8;
					h |=((int)digest[j]) & 0xFF;
				}
				result[k] = h;
				k++;
			}
		}
		return result;
	}
	 
	 
	 public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BloomFilter<E> other = (BloomFilter<E>)obj;
		if (this.expectedNumberOfFilterElements != other.expectedNumberOfFilterElements) {
			return false;
		}
		if (this.k != other.k) {
			return false;
		}
		if (this.bitSetSize != other.bitSetSize) {
			return false;
		}
		if (this.bitSet != other.bitSet && (this.bitSet == null || !this.bitSet.equals(other.bitSet))) {
			return false;
		}
		return true;
	}
	 
	 
	 public int hashCode(){
		 int hash = 7;
		 hash = 61 * hash + (this.bitSet != null ? this.bitSet.hashCode():0);
		 hash = 61 * hash + this.expectedNumberOfFilterElements;
		 hash = 61 * hash + this.bitSetSize;
		 hash = 61 * hash + this.k;
		 return hash;
	 }
	 
	 public double expectedFalsePositiveProbability() {
		return getFalsePositiveProbability(expectedNumberOfFilterElements);
	}
	 
	 public double getFalsePositiveProbability(double numberOfElements) {
		return Math.pow(1- Math.exp(-k * (double)numberOfElements/(double)bitSetSize), k);
	}
	 
	 public double getFalsePositiveProbability() {
		return getFalsePositiveProbability(numberOfAddedElements);
	}
	 
	 public int getK(){
		 return k;
	 }
	 
	 public void clear(){
		 bitSet.clear();
		 numberOfAddedElements = 0;
	 }
	 
	 public void add(E element){
		 add(element.toString().getBytes(charset));
	 }
	 
	 public void add(byte[] bytes) {
		int[] hashes = createHashes(bytes, k);
		for (int hash : hashes) {
			bitSet.set(Math.abs(hash % bitSetSize),true);
		}
		numberOfAddedElements ++;
	}
	 
	 public void addAll(Collection<? extends E> c) {
		for (E e : c) {
			add(e);
		}
	}
	 
	 public boolean contains(E element) {
		return contains(element.toString().getBytes(charset));
	}
	 
	 public boolean contains(byte[] bytes) {
		int[] hashes = createHashes(bytes, k);
		for (int i : hashes) {
			if (!bitSet.get(Math.abs(i%bitSetSize))) {
				return false;
			}
		}
		return true;
	}
	 
	 public boolean containsOradd(byte[] bytes){
		 int[] hashes = createHashes(bytes, k);
		 boolean b = true;
		 for (int i : hashes) {
			if (!bitSet.get(Math.abs(i % bitSetSize))) {
				b = false;
				break;
			}
		}
		 if (!b) {
			for (int i : hashes) {
				bitSet.set(Math.abs(i % bitSetSize),true);
			}
			numberOfAddedElements ++;
		}
		 return b;
	 }
	 public boolean containAll(Collection<?extends E> c){
		 for (E e : c) {
			if (!contains(e)) {
				return false;
			}
			
		}
		 return true;
	 }
	 
	 public boolean getBit(int bit){
		 return bitSet.get(bit);
	 }
	 
	 public void setBit(int bit,boolean value) {
		bitSet.set(bit,value);
	}
	 
	 public int size(){
		 return this.bitSetSize;
	 }
	 
	 public int count(){
		 return this.numberOfAddedElements;
	 }
	 
	 public int getExpectedNumberOfElement(){
		 return expectedNumberOfFilterElements;
	 }
	 public double getBitsPerElement(){
		 return this.bitSetSize/(double)numberOfAddedElements;
	 }
	 
	 public static void main(String[] args) {
		 BloomFilter<String> bf = new BloomFilter<String>(0.001, 10000);
	    	System.out.println(bf.contains("abcd"));
	    	bf.add("abcd");
	    	System.out.println(bf.contains("a"));
	    	System.out.println(bf.contains("hello"));
	    	System.out.println(bf.contains("abcd"));
	}
	 
}
