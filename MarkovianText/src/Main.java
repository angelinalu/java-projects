import readerwriter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import probability.*;
public class Main {
	public static Reader reader;
	public static Writer writer;
	public static ProbModel model;
	public static void main(String[] args){
		model = new ProbModel();
		
		Scanner sc = new Scanner(System.in);
		System.out.println("enter filename");
		String filename = sc.nextLine();
		System.out.println("input k for k Markovian process");
		int kval = sc.nextInt();
		System.out.println("enter text length");
		int textLength = sc.nextInt();
//		int kval = 2;
//		String filename = "text copy3.txt";
		ArrayList <String> text = Reader.read(filename, kval);
		

		model.createWordMapKGeneral(text, kval);


		List<String> seeds = new ArrayList<String>();
		/**
		 * create seed array, containing first k words
		 */
		for(int i = 0; i < kval; i++){
			seeds.add(text.get(i));
		}

		for(int i = 0; i < textLength; i++){
			System.out.print(text.get(i)+ " ");
		}
		System.out.println(" ");
//		System.out.println(text);
//		System.out.println(model.wordMapKGeneral);
		String next = model.wordReturnerK(seeds);
		/**
		 * create new text
		 */
		String newText = ""; 
		for(int i = 0; i < kval; i++){
			newText = newText + seeds.get(i) + " ";
		}
		/**
		 * create next words, step through until reach textLength
		 */

		for(int i = 0; i < textLength; i++){
			seeds.remove(0);
			seeds.add(next);

			newText = newText+next + " ";

			next = model.wordReturnerK(seeds);
			//			if(next == System.getProperty("line.seperator")){
			//				System.out.println("\n");
			//			}
		}
		System.out.print(newText);

		sc.close();
	}
}
