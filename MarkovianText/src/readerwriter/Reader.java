package readerwriter;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader {

	public static ArrayList<String> read(String filename, int kval) {
		Scanner input = null;
		ArrayList<String> words = new ArrayList<String>();
		String line = null;
		/**
		 * get file, add words
		 */
		try {
			input = new Scanner(new File(filename));
			while(input.hasNextLine()){
				line = input.nextLine();
				String[] temp = line.split(" ");
				for(int i = 0; i < line.split(" ").length; i++)
					words.add(temp[i]);

			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			input.close();
		}
		/**
		 * so the last k words in the text are also the 1st k words,
		 * and the program acts like the file wraps
		 */
		for (int i = 0; i < kval; i++){
			words.add(words.get(i));
		}
		return words;
	}
}

