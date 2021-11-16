/**
* WordID.java
* @author Zachary Iguelmamene
* @authoer Edgar Aguilar
* @author Truon Phu Vu
* @authoer Tan Dung Dong
* @authoer Jayesh Chhabra
* CIS 22C Fall 2020 Course Project
*/

public class WordID {

	private String word;
	private int id;
	
	private static int tracking = 0;
	
	public WordID(String word, int id) {
		this.word = word;
		this.id = id;
		tracking++;
	}
	

	public WordID(String word) {
		this.word = word;
	}
	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		System.out.println("Word: " + word);
		System.out.println("ID: " + id);
		
		return "";
	}
	
	public static int getTrackingNumber() {
		return tracking;
	}
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof WordID)) {
			return false;
		} else {
			WordID L = (WordID) o;
			if (!(this.word.equals(L.word))) {
				return false;
			} else {
				return true;
			}
		}
	}
	
	public int hashCode() {
		int sum = 0;
		for ( int i = 0; i < word.length(); i++) {
			sum += word.charAt(i);
		}
		return sum;
	}
}
