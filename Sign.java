import java.io.File;
import java.util.Scanner;

public class Sign extends Tile{

	private Item item;
	private String text;

	public Sign(File file){
		super('?');
		text = "";
		try{
			Scanner fromFile = new Scanner(file);
			while(fromFile.hasNext()){
				text += "\t";
				text += fromFile.nextLine();
				text += "\n";
			}
		}catch(Exception e){
			System.out.println("> > Sign .txt file read error");
		}
	}

	public String getWords(){
		return text;
	}

}