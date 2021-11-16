/**
* SongTest.java
* @author Zachary Iguelmamene
* @authoer Edgar Aguilar
* @author Truon Phu Vu
* @authoer Tan Dung Dong
* @authoer Jayesh Chhabra
* CIS 22C Fall 2020 Course Project
*/

import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SongTest {
	final int NUM_SONGS = 15;
	public static void main(String[] args) {
		final int NUM_SONGS = 15;
		/*String title, artist, album;
		int year;*/
		HashTable<Song> song = new HashTable<>(NUM_SONGS * 3);
		ArrayList<BST<Song>> searchResult = new ArrayList<>();
		HashTable<WordID> IDList = new HashTable<>(4500); 
		HashTable<WordID> commonWord = new HashTable<>(30); 
		
		try {
			File file1 = new File("song.txt");
			
			Scanner input1 = new Scanner(file1);
			Scanner input2 = new Scanner(System.in);
						
			readFile(song, commonWord);
			searchEngine(song, searchResult, commonWord, IDList);
					
			/******************************************************************************************/
			//User Input Begin
			/******************************************************************************************/
			
			System.out.println("WELCOME!!!\n");
			System.out.println("What would you like to do?");
			printMenu();
			System.out.print("Please enter your choice (A, B, C, D, E, or X): ");
			String choice = input2.nextLine();
			
			while(!choice.equalsIgnoreCase("X")) {
				String songChoice;
				String artist2;
				if(choice.equalsIgnoreCase("A")) {
					System.out.print("\nEnter the name of the record you wish to upload: ");
					songChoice = input2.nextLine();
					System.out.println("\nNew Record is: " + songChoice);
					File file2 = new File(songChoice);
					input1 = new Scanner(file2);
					uploadFile(input1, song, IDList, searchResult ,commonWord);
					
				} else if (choice.equalsIgnoreCase("B")) {
					System.out.print("\nEnter the name of the song you wish to delete: ");
					songChoice = input2.nextLine();
					System.out.print("\nEnter the name of the artist: ");
					artist2 = input2.nextLine();
					System.out.println("\nYou Want to delete: " + songChoice + " by " + artist2);					
					Song temp = new Song(songChoice, artist2);
					//temp = song.get(temp);
					
					if (song.contains(temp)) {
						System.out.println("\nFOUND IT!!!");
						temp = song.get(temp);
						updateSearch(temp, false, IDList, searchResult, commonWord, WordID.getTrackingNumber());
						deleteRecord(temp, song);					
						System.out.println("\n" + songChoice + " by " + artist2 + " has been deleted.\n");
					} else {
						System.out.println("\nRecord not found.");
					}
					
				} else if (choice.equalsIgnoreCase("C")) {
					System.out.print("\nSearch By: \n\nA. Primary Key\nB. KeyWord\n\nEnter your choice (A or B): ");
					
					String selection = input2.nextLine();
					//input2.nextLine();

					if (selection.equalsIgnoreCase("A")) {
						// Find and display one record using the primary key
						System.out.print("\nEnter the name of the song you're looking for: ");
						songChoice = input2.nextLine();
						System.out.print("\nEnter the name of the artist: ");
						artist2 = input2.nextLine();
						System.out.println("\nYou're searching for: " + songChoice + " by " + artist2);
						Song temp = new Song(songChoice, artist2);	
						
						if (song.contains(temp)) {
							System.out.println("\nFOUND IT!!!");
							searchRecord(temp, song);
						} else {
							System.out.println("\nRecord not found.");
						}
						
					} else if (selection.equalsIgnoreCase("B")) {
						
						boolean frag = true;
						while (frag == true) {
							System.out.print("\nEnter the key word: ");
							String keyWord = input2.next();
							System.out.println();
							keyWord = keyWord.toLowerCase();
							WordID tempID = new WordID(keyWord);
							tempID = IDList.get(tempID);
							if (commonWordCheck(commonWord, keyWord) == true) {
								System.out.println("Please find another word which is more unique!");
								//System.out.println();
							} else if (tempID == null) {
								System.out.println("Please insert again and try to remember the key word in the song!");
								//System.out.println();
							} else {
								if (searchResult.get(tempID.getId()).getSize() == 0) {
									System.out.println("There are no songs containing this key word!");
									frag = false;
									input2.nextLine();
							} else {
								//searchResult.get(tempID.getId()).inOrderPrint();
								System.out.println("\nThe following songs contain the word \"" + keyWord + "\":"); // Added by EA						
								ArrayList<Song> songList = new ArrayList<>();				// TTEST CODE
								searchResult.get(tempID.getId()).createSongList(songList);	// TEST CODE							
								printTitles(songList);										// TEST CODE
								System.out.println();
								frag = false;
								input2.nextLine();	// CLEAR THE BUFFER/
								System.out.print("What would you like to do next? \n\nA. View more information about one of these songs\nB. Main menu\n\nEnter your choice (A or B): ");
								String nextStep = input2.nextLine();
								
								if (nextStep.equalsIgnoreCase("A")) {
									System.out.print("\nEnter the song title: ");
									songChoice = input2.nextLine();
									System.out.print("\nEnter the artist: ");
									artist2 = input2.nextLine();
									Song temp = new Song(songChoice, artist2);							
									if (song.contains(temp)) {
										System.out.println("\nFOUND IT!!!");
										searchRecord(temp, song);
									} else {
										System.out.println("\nRecord not found.");
									}
								}
								
								System.out.println();
								}
							}
						}

						
					} else {
						System.out.println("\nInvalid Choice!");
					}
					
				} else if (choice.equalsIgnoreCase("D")) {
					System.out.print("\nEnter the title of the song you want to modify: ");
					songChoice = input2.nextLine();
					System.out.print("\nEnter the name of the artist: ");
					artist2 = input2.nextLine();
					Song temp = new Song(songChoice, artist2);
					if (song.contains(temp)) {
						System.out.println("\nFound it!");
						System.out.print("\nEnter the new title of the song: ");
						String newTitle = input2.nextLine();
						modifyRecord(temp, song, newTitle, artist2, IDList, searchResult, commonWord);
					} else {
						System.out.println("\nRecord not found.");
					}					
										
				} else if (choice.equalsIgnoreCase("E")) {
					System.out.println("\nSTATISTICS:\n");
					int size = NUM_SONGS * 3;
					calcStats(song, size);
					
				} else {
					System.out.println("\nInvalid option!  Please try again :)");
				}
				
				System.out.println("\nWhat would you like to do?");
				printMenu();
				System.out.print("Please enter your choice (A, B, C, D, E, or X): ");
				choice = input2.nextLine();
			}
			
			System.out.print("\nPlease enter the output filename: ");
			String filename = input2.nextLine();
			writeSongsFile(song, filename);
						
			input1.close();
			input2.close();
					
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage()); 
		}

		System.out.println("\nGoodbye!\n");

	}
	
	/******************************************************************************************/
	//Additional Methods
	/******************************************************************************************/
	
	public static void printMenu() {
		System.out.println(
				"\nA. Upload a new song\nB. Delete a song\nC. Search for a song\nD. Modify a song title\nE. Statistics\nX. Exit\n");
	}
	
	public static void uploadFile(Scanner input, HashTable<Song> songs, HashTable<WordID> IDList, ArrayList<BST<Song>> searchResult ,HashTable<WordID> commonWord) {
		while (input.hasNextLine()) {
			String title = input.nextLine();
			String artist = input.nextLine();
			String album = input.nextLine();
			int year = input.nextInt();				
			input.nextLine();	// Clear the buffer
			
			List<String> words = new List<>();
			String lyrics = new String();
			while (input.hasNext()) {
				String temp = input.nextLine(); //Switched back to nextLine() from next()
				if(!temp.equals("")) {
					lyrics = temp + "\n";
					words.addLast(lyrics);
				} else {
					break;
				}
			}
			Song s = new Song(title, artist, album, year, words);
			//input.nextLine();				
			songs.put(s);
			
			updateSearch(s, true, IDList, searchResult, commonWord, WordID.getTrackingNumber());
		}
	}
	
	public static void deleteRecord(Song s, HashTable<Song> songs) {
		songs.remove(s);
	}
	
	public static void searchRecord(Song s, HashTable<Song> songs) {
		System.out.println();
		//System.out.println(songs.get(s));
		System.out.println("Title: " + songs.get(s).getName());
		System.out.println("Artist: " + songs.get(s).getArtist());
		System.out.println("Album: " + songs.get(s).getAlbum());
		System.out.println("Year: " + songs.get(s).getYear());
		System.out.println("\n" + songs.get(s).getLyrics());
	}
	
	public static void calcStats(HashTable<Song> songs, int size) {
		int totalWords = 0;
		int numSongs = 0;
		// Step through the HashTable ArrayList (Table)
		for (int i = 0; i < size; i++) {
			songs.getBucket(i).placeIterator();
			// Step through the linked list of songs at each HashTable index
			for (int j = 0; j < songs.getBucket(i).getLength(); j++) {
				System.out.println(songs.getBucket(i).getIterator().getName());
				songs.getBucket(i).getIterator().getLyrics().placeIterator(); // You are now at the first line of lyrics of this song
				int songWords = 0;
				// Step through the linked list of lines of lyrics
				for (int k = 0; k < songs.getBucket(i).getIterator().getLyrics().getLength(); k++) {
					String str = songs.getBucket(i).getIterator().getLyrics().getIterator();
						//System.out.println(str);
					String[] arr = str.split(" ", -2);	//Parse the line into an array of Strings
					
					int numWords = arr.length;
					totalWords += numWords;
					songWords += numWords;
						//System.out.println(numWords);					
					songs.getBucket(i).getIterator().getLyrics().advanceIterator();
				}							
				songs.getBucket(i).advanceIterator();
				System.out.println("This song has: " + songWords + " words\n");
				numSongs++;
			}
		}
		System.out.println("TOTAL WORDS: " + totalWords + " words\n");
		System.out.println("Number of songs on file: " + numSongs);
		System.out.println("\nAverage number of words per song: " + (totalWords / numSongs));
	}
	
	public static void modifyRecord(Song s, HashTable<Song> songs, String newTitle, String artist, HashTable<WordID> IDList, ArrayList<BST<Song>> searchResult ,HashTable<WordID> commonWord) {
		String tempAlbum = songs.get(s).getAlbum();
		int tempYear = songs.get(s).getYear();
		List <String> tempWords = songs.get(s).getLyrics();
		s = songs.get(s);
		Song tempSong = new Song(newTitle, artist, tempAlbum, tempYear, tempWords);
		songs.put(tempSong);
		
		updateSearch(tempSong, true, IDList, searchResult, commonWord, WordID.getTrackingNumber());
		updateSearch(s, false, IDList, searchResult, commonWord, WordID.getTrackingNumber());
		songs.remove(s);
	}
	
	public static void printTitles(ArrayList<Song> titles) {
		for (int i = 0; i < titles.size(); i++) {
			System.out.println("\n" + (i + 1) + ". " + titles.get(i).getName() + " by " + titles.get(i).getArtist());
		}
	}
	
	public static void writeSongsFile(HashTable<Song> songs, String filename) {
		System.out.println("\nThe output file is " + filename);	// This is a test print statement
		// PRINTWRITER CODE GOES HERE
		try {
			PrintWriter out = new PrintWriter(filename);
			out.write(songs.toString());
			out.close();
		} catch (FileNotFoundException e) {
			System.out.println("Sorry, I am unable to output to that filename: " + filename);
		}
	}
	
	/******************************************************************************************/
	//Update Search
	/******************************************************************************************/
	
	public static void updateSearch(Song song, boolean update, HashTable<WordID> IDList,
			ArrayList<BST<Song>> searchResult, HashTable<WordID> commonWord, int id) {
		//when upload a new song
		if (update == true) {
			List<String> lyrics = song.getLyrics();

			lyrics.placeIterator();

			// Loop through all data in each bucket
			while (!lyrics.offEnd()) {

				// First word
				int pos1 = lyrics.getIterator().indexOf(" ");

				// Handle lines with only 1 word
				if (pos1 == -1) {
					// lyrics.getIterator() is now a single word
					
					int pos2 = lyrics.getIterator().indexOf("\n");
					String subString = lyrics.getIterator();
					subString = lyrics.getIterator().substring(0,pos2);
					subString = subString.toLowerCase();

					// Check if it's a common word
					// Y: don't do anything with this word
					// N: continue to another checking

					// Check if it's already had the ID
					// Y: get the ID out
					// N: Create an ID for it

					// Don't insert a song twice for one index
					if (!commonWordCheck(commonWord, subString)) {
						if (!existedWordCheck(IDList, subString)) {
							WordID tempID = new WordID(lyrics.getIterator(), id);
							id++;
							IDList.put(tempID);
							BST<Song> tempBST = new BST<>();
							tempBST.insert(song, new NameComparator());
							searchResult.add(tempBST);
						} else {
							WordID tempID = new WordID(subString);
							tempID = IDList.get(tempID);

							if (searchResult.get(tempID.getId()).search(song, false,
									new NameComparator()) == null)
								searchResult.get(tempID.getId()).insert(song,
										new NameComparator());
						}
					}
					
				} else {

					// Lines more than 1 word
					// First word of each line
					String subString = lyrics.getIterator().substring(0, pos1);
					subString = subString.toLowerCase();

					// Check if it's a common word
					// Y: don't do anything with this word
					// N: continue to another checking

					// Check if it's already had the ID
					// Y: get the ID out
					// N: Create an ID for it

					// Don't insert a song twice for one index

					if (!commonWordCheck(commonWord, subString)) {
						if (!existedWordCheck(IDList, subString)) {
							WordID tempID = new WordID(subString, id);
							id++;
							IDList.put(tempID);
							BST<Song> tempBST = new BST<>();
							tempBST.insert(song, new NameComparator());
							searchResult.add(tempBST);
						} else {
							WordID tempID = new WordID(subString);
							tempID = IDList.get(tempID);

							if (searchResult.get(tempID.getId()).search(song, false,
									new NameComparator()) == null)
								searchResult.get(tempID.getId()).insert(song,
										new NameComparator());
						}
					}

					// The rest of the String after 1st word taken out
					String subString2 = lyrics.getIterator().substring(pos1 + 1);

					// Run a loop to cut the String until cannot file " " anymore, means that
					// it is last word of the line
					// If it is the last word, then break the loop manually

					while (subString2 != "\n") {
						pos1 = subString2.indexOf(" "); // -1
						if (pos1 != -1) {

							// Single Word
							String tempString = subString2;
							subString = subString2.substring(0, pos1);
							subString = subString.toLowerCase();

							// Check if it's a common word
							// Y: don't do anything with this word
							// N: continue to another checking

							// Check if it's already had the ID
							// Y: get the ID out
							// N: Create an ID for it
							// The rest of the String after a word is taken out
							if (commonWordCheck(commonWord, subString) == false) {
								if (!existedWordCheck(IDList, subString)) {
									WordID tempID = new WordID(subString, id);
									id++;
									IDList.put(tempID);
									BST<Song> tempBST = new BST<>();
									tempBST.insert(song, new NameComparator());
									searchResult.add(tempBST);
								} else {
									WordID tempID = new WordID(subString);
									tempID = IDList.get(tempID);

									if (searchResult.get(tempID.getId()).search(song,
											false, new NameComparator()) == null)
										searchResult.get(tempID.getId()).insert(song,
												new NameComparator());
								}
							}

							subString2 = tempString.substring(pos1 + 1);

						} else { // handle the last word in the line: stored in subString2
							
							int pos2 = subString2.indexOf("\n");
							subString2 = subString2.substring(0,pos2); 
							subString2 = subString2.toLowerCase();
							
							//case there is a capitol word
							

							// Check if it's a common word
							// Y: don't do anything with this word
							// N: continue to another checking

							// Check if it's already had the ID
							// Y: get the ID out
							// N: Create an ID for it

							if (!commonWordCheck(commonWord, subString2)) {
								if (!existedWordCheck(IDList, subString2)) {
									WordID tempID = new WordID(subString2, id);
									id++;
									IDList.put(tempID);
									BST<Song> tempBST = new BST<>();
									tempBST.insert(song, new NameComparator());
									searchResult.add(tempBST);
								} else {
									WordID tempID = new WordID(subString2);
									tempID = IDList.get(tempID);

									if (searchResult.get(tempID.getId()).search(song,
											false, new NameComparator()) == null)
										searchResult.get(tempID.getId()).insert(song,
												new NameComparator());
								}
							}

							break;
						}

					}

				}

				lyrics.advanceIterator();
			}
		} else { //when delete a song
			List<String> lyrics = song.getLyrics();

			lyrics.placeIterator();

			// Loop through all data in each bucket
			while (!lyrics.offEnd()) {

				// First word
				int pos1 = lyrics.getIterator().indexOf(" ");

				// Handle lines with only 1 word
				if (pos1 == -1) {
					// lyrics.getIterator() is now a single word
					
					int pos2 = lyrics.getIterator().indexOf("\n");
					String subString = lyrics.getIterator();
					subString = lyrics.getIterator().substring(0,pos2);
					subString = subString.toLowerCase();

					// Check if it's a common word
					// Y: don't do anything with this word
					// N: continue to another checking

					// Check if it's already had the ID
					// Y: get the ID out
					// N: Create an ID for it

					// Don't insert a song twice for one index
					if (!commonWordCheck(commonWord, subString)) {
						WordID tempId = new WordID(subString);
						tempId = IDList.get(tempId);
						int ID = tempId.getId();
						if (searchResult.get(ID).search(song, false, new NameComparator()) != null) {
							searchResult.get(ID).remove(song, new NameComparator());
						}	
					}
					
				} else {

					// Lines more than 1 word
					// First word of each line
					String subString = lyrics.getIterator().substring(0, pos1);
					subString = subString.toLowerCase();

					// Check if it's a common word
					// Y: don't do anything with this word
					// N: continue to another checking

					// Check if it's already had the ID
					// Y: get the ID out
					// N: Create an ID for it

					// Don't insert a song twice for one index

					if (!commonWordCheck(commonWord, subString)) {
						WordID tempId = new WordID(subString);
						tempId = IDList.get(tempId);
						int ID = tempId.getId();
						if (searchResult.get(ID).search(song, false, new NameComparator()) != null) {
							searchResult.get(ID).remove(song, new NameComparator());
						}	
					}

					// The rest of the String after 1st word taken out
					String subString2 = lyrics.getIterator().substring(pos1 + 1);

					// Run a loop to cut the String until cannot file " " anymore, means that
					// it is last word of the line
					// If it is the last word, then break the loop manually

					while (subString2 != "\n") {
						pos1 = subString2.indexOf(" "); // -1
						if (pos1 != -1) {

							// Single Word
							String tempString = subString2;
							subString = subString2.substring(0, pos1);
							subString = subString.toLowerCase();

							// Check if it's a common word
							// Y: don't do anything with this word
							// N: continue to another checking

							// Check if it's already had the ID
							// Y: get the ID out
							// N: Create an ID for it
							// The rest of the String after a word is taken out
							if (commonWordCheck(commonWord, subString) == false) {
								WordID tempId = new WordID(subString);
								tempId = IDList.get(tempId);
								int ID = tempId.getId();
								if (searchResult.get(ID).search(song, false, new NameComparator()) != null) {
									searchResult.get(ID).remove(song, new NameComparator());
								}	
							}

							subString2 = tempString.substring(pos1 + 1);

						} else { // handle the last word in the line: stored in subString2
							
							int pos2 = subString2.indexOf("\n");
							subString2 = subString2.substring(0,pos2); 
							subString2 = subString2.toLowerCase();
							
							//case there is a capitol word
							

							// Check if it's a common word
							// Y: don't do anything with this word
							// N: continue to another checking

							// Check if it's already had the ID
							// Y: get the ID out
							// N: Create an ID for it

							if (!commonWordCheck(commonWord, subString2)) {
								WordID tempId = new WordID(subString2);
								tempId = IDList.get(tempId);
								int ID = tempId.getId();
								if (searchResult.get(ID).search(song, false, new NameComparator()) != null) {
									searchResult.get(ID).remove(song, new NameComparator());
								}	
							}

							break;
						}

					}

				}

				lyrics.advanceIterator();
			}
		}
	}
	
	/******************************************************************************************/
	//Search Engine
	/******************************************************************************************/
	public static void readFile(HashTable<Song> song, HashTable<WordID> commonWord) {
		File file = new File("song.txt");
		try {

			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String name = scanner.nextLine();
				String artist = scanner.nextLine();
				String album = scanner.nextLine();
				int year = scanner.nextInt();
				scanner.nextLine();

				List<String> lyricsList = new List<String>();

				String lyrics = new String();
				while (scanner.hasNext()) {
					String tempString = scanner.nextLine();
					if (!tempString.equals("")) {
						lyrics = tempString + "\n";
						lyricsList.addLast(lyrics);
					} else {
						break;
					}
				}
				Song tempSong = new Song(name, artist, album, year, lyricsList);

				song.put(tempSong);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Read in common words
		file = new File("commonWords.txt");

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNext()) {
				String tempString = scanner.nextLine();
				WordID tempID = new WordID(tempString);
				commonWord.put(tempID);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static boolean commonWordCheck(HashTable<WordID> list, String A) {
		WordID tempID = new WordID(A);
		return list.contains(tempID);
	}

	public static boolean existedWordCheck(HashTable<WordID> list, String A) {
		WordID tempID = new WordID(A);
		return list.contains(tempID);
	}

	public static void searchEngine(HashTable<Song> song, ArrayList<BST<Song>> searchResult,
			HashTable<WordID> commonWord, HashTable<WordID> IDList) {
		// Create an integer variable to keep track and create WordID object
		int id = 0;
		int i = 0;
		// Loop through all lyrics of songs in data base

		// Loop through all the HashTable<Song>

		while (i < song.getTableSize()) {

			// If length != 0 means there are Song stored at that bucket
			// If length == 0 means nothing in the bucket so we simply skip that bucket
			if (song.getBucket(i).getLength() != 0) {

				// System.out.println(song.getBucket(i).toString());
				song.getBucket(i).placeIterator();

				while (!song.getBucket(i).offEnd()) {

					List<String> lyrics = song.getBucket(i).getIterator().getLyrics(); 	//Modified getWords from getLyrics

					lyrics.placeIterator();

					// Loop through all data in each bucket
					while (!lyrics.offEnd()) {

						// First word
						int pos1 = lyrics.getIterator().indexOf(" ");

						// Handle lines with only 1 word
						if (pos1 == -1) {
							// lyrics.getIterator() is now a single word
							
							int pos2 = lyrics.getIterator().indexOf("\n");
							String subString = lyrics.getIterator();
							subString = lyrics.getIterator().substring(0,pos2);
							subString = subString.toLowerCase();
							// Check if it's a common word
							// Y: don't do anything with this word
							// N: continue to another checking

							// Check if it's already had the ID
							// Y: get the ID out
							// N: Create an ID for it

							// Don't insert a song twice for one index
							if (!commonWordCheck(commonWord, subString)) {
								if (!existedWordCheck(IDList, subString)) {
									WordID tempID = new WordID(subString, id);
									id++;
									IDList.put(tempID);
									BST<Song> tempBST = new BST<>();
									tempBST.insert(song.getBucket(i).getIterator(), new NameComparator());
									searchResult.add(tempBST);
								} else {
									WordID tempID = new WordID(subString);
									tempID = IDList.get(tempID);

									if (searchResult.get(tempID.getId()).search(song.getBucket(i).getIterator(), false,
											new NameComparator()) == null)
										searchResult.get(tempID.getId()).insert(song.getBucket(i).getIterator(),
												new NameComparator());
								}
							}
							
						} else {

							// Lines more than 1 word
							// First word of each line
							String subString = lyrics.getIterator().substring(0, pos1);
							subString = subString.toLowerCase();

							// Check if it's a common word
							// Y: don't do anything with this word
							// N: continue to another checking

							// Check if it's already had the ID
							// Y: get the ID out
							// N: Create an ID for it

							// Don't insert a song twice for one index

							if (!commonWordCheck(commonWord, subString)) {
								if (!existedWordCheck(IDList, subString)) {
									WordID tempID = new WordID(subString, id);
									id++;
									IDList.put(tempID);
									BST<Song> tempBST = new BST<>();
									tempBST.insert(song.getBucket(i).getIterator(), new NameComparator());
									searchResult.add(tempBST);
								} else {
									WordID tempID = new WordID(subString);
									tempID = IDList.get(tempID);

									if (searchResult.get(tempID.getId()).search(song.getBucket(i).getIterator(), false,
											new NameComparator()) == null)
										searchResult.get(tempID.getId()).insert(song.getBucket(i).getIterator(),
												new NameComparator());
								}
							}

							// The rest of the String after 1st word taken out
							String subString2 = lyrics.getIterator().substring(pos1 + 1);

							// Run a loop to cut the String until cannot file " " anymore, means that
							// it is last word of the line
							// If it is the last word, then break the loop manually

							while (subString2 != "\n") {
								pos1 = subString2.indexOf(" "); // -1
								if (pos1 != -1) {

									// Single Word
									String tempString = subString2;
									subString = subString2.substring(0, pos1);
									subString = subString.toLowerCase();

									// Check if it's a common word
									// Y: don't do anything with this word
									// N: continue to another checking

									// Check if it's already had the ID
									// Y: get the ID out
									// N: Create an ID for it
									// The rest of the String after a word is taken out
									if (commonWordCheck(commonWord, subString) == false) {
										if (!existedWordCheck(IDList, subString)) {
											WordID tempID = new WordID(subString, id);
											id++;
											IDList.put(tempID);
											BST<Song> tempBST = new BST<>();
											tempBST.insert(song.getBucket(i).getIterator(), new NameComparator());
											searchResult.add(tempBST);
										} else {
											WordID tempID = new WordID(subString);
											tempID = IDList.get(tempID);

											if (searchResult.get(tempID.getId()).search(song.getBucket(i).getIterator(),
													false, new NameComparator()) == null)
												searchResult.get(tempID.getId()).insert(song.getBucket(i).getIterator(),
														new NameComparator());
										}
									}

									subString2 = tempString.substring(pos1 + 1);

								} else { // handle the last word in the line: stored in subString2
									int pos2 = subString2.indexOf("\n");
									
									subString2 = subString2.substring(0,pos2);
									subString2 = subString2.toLowerCase();

									// System.out.println(subString2);

									// Check if it's a common word
									// Y: don't do anything with this word
									// N: continue to another checking

									// Check if it's already had the ID
									// Y: get the ID out
									// N: Create an ID for it

									if (!commonWordCheck(commonWord, subString2)) {
										if (!existedWordCheck(IDList, subString2)) {
											WordID tempID = new WordID(subString2, id);
											id++;
											IDList.put(tempID);
											BST<Song> tempBST = new BST<>();
											tempBST.insert(song.getBucket(i).getIterator(), new NameComparator());
											searchResult.add(tempBST);
										} else {
											WordID tempID = new WordID(subString2);
											tempID = IDList.get(tempID);

											if (searchResult.get(tempID.getId()).search(song.getBucket(i).getIterator(),
													false, new NameComparator()) == null)
												searchResult.get(tempID.getId()).insert(song.getBucket(i).getIterator(),
														new NameComparator());
										}
									}

									break;
								}

							}

						}

						lyrics.advanceIterator();
					}

					song.getBucket(i).advanceIterator();
				}

			}
			i++;
		}

		// Create WordID for a word:
		// Skip word if it in a common words list
		// Check if HashTable B contains that word
		// Yes: insert Song into ArrayList using getID from WordID class
		// No: Create a new WordID, insert that word to HashTable B to keep track
		// existed words,
		// Add Song to ArrayList<BST>
		// Every time new WordID is created, increment id

		// Make sure there aren't any duplicates of Song in each index of ArrayList
	}
	
	public static ArrayList<BST<Song>> updateSearch(Song A, ArrayList<BST<Song>> searchResult, HashTable<WordID> IDList, HashTable<WordID> commonWords){
		
		return searchResult;
	}

} 
