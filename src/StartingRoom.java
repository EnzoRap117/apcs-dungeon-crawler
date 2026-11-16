import java.io.File;

public class StartingRoom extends Room{

	private Tile[][] room2d;

	public StartingRoom(){
		super(9, 5, 1, true);
		super.addTile(new Chest(new Greatsword()), 2, 5);//new Weapon("Battle Axe", "a dwarf's weapon of choice", 1, "BattleAxeWithArm_1.txt")), 2, 5);
		//super.addTile(new Chest(new Potion()), 3, 5);
		super.addTile(new Chest(new BlessedArmor()), 3, 5);
		super.addTile(new Player(), 2, 3);
		super.addTile(new Sign(new File("StartingRoomSign.txt")), 3, 6);
	}

	public StartingRoom(Item[] itemList){
		super(9, 5, 1, true);

		int ran = (int)(Math.random() * itemList.length);
		System.out.println("\t> "+itemList[ran].getName()+" generated"); //debugg

		Weapon wep = (Weapon)itemList[ran].clone();
		if(ran == 0){
			super.addTile(new Chest((BattleAxe)wep), 2, 5);
			System.out.println("\t\t > "+wep.getName()+" casted to BattleAxe"); //debugg
		}else if(ran == 1){
			super.addTile(new Chest((DustyBlade)wep), 2, 5);
			System.out.println("\t\t > "+wep.getName()+" casted to DustyBlade"); //debugg
		}else if(ran == 2){
			super.addTile(new Chest((Greatsword)wep), 2, 5);
			System.out.println("\t\t > "+wep.getName()+" casted to GreatSword"); //debugg
		}

		super.addTile(new Player(), 2, 3);
		super.addTile(new Sign(new File("StartingRoomSign.txt")), 3, 6);
	}

}