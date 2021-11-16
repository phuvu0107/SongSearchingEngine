/**
* Song.java
* @author Zachary Iguelmamene
* @authoer Edgar Aguilar
* @author Truon Phu Vu
* @authoer Tan Dung Dong
* @authoer Jayesh Chhabra
* CIS 22C Fall 2020 Course Project
*/

import java.util.Comparator;

public class Song {

	private String name;
	private String artist;
	private String album;
	private int year;
	private List<String> lyrics;
	
	public Song (String name, String artist, String album, int year, List<String> lyrics) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
		this.lyrics = lyrics;
	}

	public Song (String name) {
		this.name = name;
	}
	public Song (String name, String artist) {
		this.name = name;
		this.artist = artist;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public String getAlbum() {
		return album;
	}

	public int getYear() {
		return year;
	}

	public List<String> getLyrics() {
		return lyrics;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int hashCode() {
		int sum = 0;
		String key = name + artist;
		for ( int i = 0; i < key.length(); i++) {
			sum += (int) key.charAt(i);
		}
		return sum;
	}
	
	@Override public String toString() {
		return "\nTitle: " + name + "\nArtist: " + artist + "\nAlbum: " + album + "Year: " + year + "\n" + lyrics;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		} else if (!(o instanceof Song)) {
			return false;
		} else {
			Song L = (Song) o;
			if (!(this.name.equals(L.name))) {
				return false;
			} else {
				return true;
			}
		}
	}
	
}
class NameComparator implements Comparator<Song> {
    /**
   * Compares the song by name of the song
   * uses the String compareTo method to make the comparison
   * @param song1 the first Song
   * @param song2 the second Song
   */
   @Override public int compare(Song song1, Song song2) {
	   return song1.getName().compareTo(song2.getName());
   }
}  //end class NameComparator