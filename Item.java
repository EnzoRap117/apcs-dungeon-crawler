import java.io.*;
import java.util.*;

public class Item{

	private char symbol = '?';
	private String name;
	private String description;
	private int type;
	//private static int badCount = 0;

	public Item(){
		symbol = '_';
		name = " ";
		description = " ";
		type = 0;
	}

	/*
	public Item(String n, String d){
		System.out.println("In bad constructor");
		badCount++;
		System.out.println(badCount + " bad items");
		name = n;
		description = d;
	}
	*/


	public Item(String n, String d, int t){
		name = n;
		description = d;
		type = t;
	}

	public String getDescription(){
		return description;
	}

	public String getInformation(){
		return name + " - " + description;
	}

	public String getName(){
		return name;
	}

	public int getType(){
		return type;
	}

	public String toString(){
		return name + " - " + description + " - (item type)" +type;
	}

	public static char[][] transposeText(File text, char[][] array){
		//System.out.println("\t>Inside transposeText"); //debugg
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

	public Item clone(){
		return new Item(name, description, type);
	}


}