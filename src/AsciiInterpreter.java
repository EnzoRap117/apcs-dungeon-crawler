import java.io.*;
import java.util.*;


public class AsciiInterpreter{


	public static void main(String[] args){

		char[][] arr = new char[20][30];
		for(int r = 0; r < arr.length; r++){
			for(int c = 0; c < arr[r].length; c++){
				arr[r][c] = ' ';
			}
		}

		arr = transposeText(new File("DefaultKnight.txt"), arr);

		for(char[] row : arr){
			for(char col : row){
				System.out.print(col);
			}
			System.out.println();
		}

	}

	public static char[][] transposeText(File text, char[][] array){
		int row = 0;
		int col = 0;
		try{
			Scanner fromFile = new Scanner(text);
			while (fromFile.hasNext()){
				String line = fromFile.nextLine();
				while(line.length() > 0){
					array[row][col] = line.charAt(0);
					col++;
					line = line.substring(1);
				}
				row++;
				col = 0;
			}
		}catch (Exception e){
			System.out.println("File read error");
		}
		return array;

	}

}