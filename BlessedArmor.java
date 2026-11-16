import java.io.*;

public class BlessedArmor extends Armor{

	public BlessedArmor(){
		super("Runic Armor", "an imposing set of armor covered in runic symbols", 2);
	}

	public char[][] getArmorArray(){
		//System.out.println("Inside getArmorArray"); //debugg

		char[][] arr = new char[20][40]; //[20][40]
		for(int r = 0; r < arr.length; r++){
			for(int c = 0; c < arr[r].length; c++){
				arr[r][c] = ' ';
			}
		}

		return transposeText(new File("BlessedArmor.txt"), arr);
	}

	public BlessedArmor clone(){
		return new BlessedArmor();
	}

}