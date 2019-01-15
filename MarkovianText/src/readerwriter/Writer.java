package readerwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Writer {
	public static void toFile(byte[] data, File destination){
		//convert bytes to file
		try (FileOutputStream fos = new FileOutputStream(destination);){
			fos.write(data);
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void toOutput (String[] data){
		//convert string array to string output
		String str = "";
		for(int i = 0; i < data.length; i++){
			str = str + data[i];
		}
		System.out.println(str);
	}

	public static void main(String[] args){
		String text = "i am what i am and that"
				+ " is all i am. i am what i am and that"
				+ " is all what i am and that is all i am.";
		String[] data = text.split("");
		byte[] data2;
		try {
			data2 = text.getBytes("");
			File destination = new File("text.txt");
			toFile(data2, destination);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		toOutput(data);
	}
}
