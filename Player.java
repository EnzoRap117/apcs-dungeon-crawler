
import java.util.Scanner;

public class Player extends Tile{

	private boolean tutorialsOn;
	private int level;
	private double experience;
	private int health;
	private int maxHp;
	private char symbol;
	private Item[] inventory = new Item[5];
	private int invInd = 0;
	private Weapon weapon = new Weapon();
	private Armor armor = new Armor();
	private int statusTicks;
	private boolean guarded;

	//private PlayerInventory pI = new PlayerInventory();
	Scanner input = new Scanner(System.in);


	public Player(){
		super('@');
		level = 1;
		health = 50;
		maxHp = 50;
		guarded = false;
		statusTicks = 0;
		for(int i = 0; i < inventory.length; i++){
			inventory[i] = new Item("_", "", 0);
		}
		//inventory[0] = new Potion();
	}

	public Player(boolean tut){
		super('@');
		level = 1;
		health = 50;
		maxHp = 50;
		guarded = false;
		statusTicks = 0;
		tutorialsOn = tut;
		for(int i = 0; i < inventory.length; i++){
			inventory[i] = new Item("_", "", 0);
		}


	}

	/*
	public void hit(int dam){
		health -= dam;
		if(health < 0){
			System.out.println("Game Over!");
		}
	}
	*/

	public int getHealth(){
		return health;
	}

	public int getMaxHp(){
		return maxHp;
	}

	public Weapon getWeapon(){
		return weapon;
	}

	public Armor getArmor(){
		return armor;
	}

	public void setGuard(boolean b){
		guarded = b;
	}

	public void levelUp(double xp){
		System.out.println("\t"+ xp + " xp collected");
		experience += xp;
		//temp
		double threshold = level * 100;
		if(experience / threshold != 0){
			level += (int)experience/threshold;
		}
	}

	public void usePotion(Potion p, int index){
		int prevHealth = health;
		if(p.getKindOfPotion().equals("healing")){
			if(health == maxHp){
				System.out.println("~~~HP FULL~~~");
			}else{
				health += p.getEffectAmount();
				if(health > maxHp){
					health = 100;
				}
				inventory[index] = new Item("_", "", 0);
				System.out.println(p.getName() + " used, "+Math.abs(prevHealth-health)+" healed");
			}
		}

	}

	//There's something here thats bugged: Once a weapon/armor
	//is swapped from inventory, it can no loner be re-equipped
	public void equip(int index){
		Item item = inventory[index];
		if(item.getType() == 1){
			inventory[index] = weapon;
			weapon = (Weapon)item;
			System.out.println(item.getName()+" equiped");
		}else if(item.getType() == 2){
			inventory[index] = armor;
			armor = (Armor)item;
			System.out.println(item.getName()+" equiped");
		}else if(item.getType() == 3){
			usePotion((Potion)item, index);
			//inventory[index] = new Item("_", "");
		}
	}

	public void drop(int index){
		if(!inventory[index].getName().equals("_")){
			System.out.println(inventory[index].getName()+" discarded.");
			inventory[index] = new Item("_", "", 0);
		}
	}

	//To do, make this more complex
	public double playerAttack(){
		int damage = weapon.getDamage() * level;
		System.out.println("\n!------------------------------!\n   You attack for "+damage+" damage.");
		return damage;
	}

	public void hurt(double d){

		d -= armor.getDefense();
		if(guarded){
			d = d/2;
			guarded = false;
		}
		if(d <= 0){
			System.out.print("\tEnemy attack ineffective ( -0 HP )");
		}else{
			System.out.print(" -"+d+" HP");
			health -= d;
		}
	}


	public void interract(Dungeon dungeon, Tile t){
		int pRow = super.getRow();
		int pCol = super.getCol();
		Scanner input = new Scanner(System.in);
		char symbol = t.getSymbol();
		if(symbol == '='){
			Chest c = (Chest)t;
			Item i = c.getItem();
			itemInteraction(i);
		}else if(symbol == '#' || symbol == 'O'){
			//Removing player from the room he is leaving
			int doorSide = t.getSide();
			Room oldRoom = dungeon.getCurrentRoom();
			Tile[][] oldArr = oldRoom.getRoomArray();
			dungeon.nextRoom(doorSide); //doesn't work with new system
			oldArr[pRow][pCol] = new Tile(' ', pRow, pCol);

			//Moving player into next room
			Room room = dungeon.getCurrentRoom();
			room.setActiveRoom(false);
			movePlayerIntoNextRoom(room, doorSide);
		}else if(symbol == 'X'){
			/*
			//Removing player from the room he is leaving
			Room oldRoom = dungeon.getCurrentRoom();
			Tile[][] oldArr = oldRoom.getRoomArray();
			//System.out.print(t); //debug
			int xRow = t.getRow();
			int xCol = t.getCol();
			int xSide = t.getSide();
			t = new Tile('#', xRow, xCol);
			oldArr[xRow][xCol].setSide(xSide);
			t.setSide(xSide);
			//System.out.println("New Door:" + t); //debug
			//dungeon.prevRoom();
			System.out.println("dungeon.prevRoom() was commented out");
			oldArr[pRow][pCol] = new Tile(' ', pRow, pCol);
			Room room = dungeon.getCurrentRoom();

			movePlayerIntoNextRoom(room, xSide);// - doesn't work, not so simple

			//Moving player into next room
			Tile[][] roomArr = room.getRoomArray();
			Tile door = room.findDoor();
			//System.out.println(door); //debug
			int side = door.getSide();
			int dRow = door.getRow();
			int dCol = door.getCol();


			if(side == 1){
				//System.out.println("ONE"); //debugg
				roomArr[dRow+1][dCol] = this;
				//roomArr[dRow][dCol] = new Tile('#', dRow, dCol);
				this.setPosition(dRow+1, dCol);
			}else if(side == 2){
				//System.out.println("TWO"); //debugg
				roomArr[dRow][dCol-1] = this;
				//roomArr[dRow][dCol] = new Tile('#', dRow, dCol);
				this.setPosition(dRow, dCol-1);

			}else if(side == 3){
				//System.out.println("THREE"); //debugg
				roomArr[dRow-1][dCol] = this;
				//roomArr[dRow][dCol] = new Tile('O', dRow, dCol);
				this.setPosition(dRow-1, dCol);
			}else if(side == 4){
				//System.out.println("FOUR"); //debugg
				roomArr[dRow][dCol+1] = this;
				//roomArr[dRow][dCol] = new Tile('O', dRow, dCol);
				this.setPosition(dRow, dCol+1);
			}
			*/
		}else if(symbol == '?'){
			System.out.println("------------------------------------------------\n\n\""+t.getWords()+"\"\n\n------------------------------------------------");
		}else if(symbol == '@'){
			System.out.println("Time paradox! "+t);
		}else if(symbol == '^'){
			System.out.println("A spike shoots through the ground");
			hurt(5);
		}else{
			enemyEncounter(dungeon, t);
		}


	}

	public void itemInteraction(Item i){
		System.out.println("\tYou found a: " + i.getInformation());
		System.out.print("\t\t> Take item? y/n : ");
		String action = input.nextLine();
		action = action.toLowerCase();
		if(action.equals("y")){
			if(invInd == inventory.length-1){
				System.out.println("Inventory full, the item you hold shatters by some unknown force. Drop an item to pick up another.");
			}else{
				for(int ind = 0; ind < inventory.length; ind++){
					if(inventory[ind].getName().equals("_")){
						inventory[ind] = i;
						ind = inventory.length;
					}
				}

			}
		}else{
			System.out.println("Item discarded");
		}
	}

	public void enemyEncounter(Dungeon d, Tile t){
		Scanner input = new Scanner(System.in);
		System.out.println("Attack!");
		Enemy enemy = (Enemy)t;
		enemy.setBattleActive(true);
		//System.out.println("> Battle is now active"); //debugg
		while(enemy.isBattleActive()){
			enemy.drawEncounter();
			enemy.displayBattleInfo(this);
			this.drawBattleOptions();
			System.out.print("Input: ");
			String action = input.nextLine();
			action = action.toLowerCase();

			if(action.equals("q")){
				weapon.attackOptions(this, enemy);
			}else if(action.equals("i")){
				inventoryBattle();
			}else if(action.indexOf("show item") >= 0){ //debugging
				int ind = Integer.valueOf(action.substring(10));
				System.out.println("[ "+enemy.showItem(ind) + " ]"); //debugg
			}

			if(!enemy.isBattleActive()){
				//System.out.println("> Battle is now over"); //debugg
				levelUp(enemy.dropXp());
				if(Math.random() <= .33){
					Tile[][] arr = d.getCurrentRoom().getRoomArray();
					itemInteraction(enemy.dropLoot(arr));
				}
				//System.out.println("\t\t\t > Code has executed below item drop code"); //debugg
				//System.out.println("> Attempting to remove defeated enemy at ("+enemy.getPosition().getRow()+", "+enemy.getPosition().getRow()+")"); //debugg
				Tile[][] arr = d.getCurrentRoom().getRoomArray();
				arr[enemy.getPosition().getRow()][enemy.getPosition().getCol()] = new Tile();
			}
			//System.out.println("\033[H\033[2J");
			//System.out.flush();
			if(action.equals("r")){
				enemy.setBattleActive(false);
				System.out.println("> Attempting to remove defeated enemy at ("+enemy.getPosition().getRow()+", "+enemy.getPosition().getRow()+")"); //debugg
				Tile[][] arr = d.getCurrentRoom().getRoomArray();
				arr[enemy.getPosition().getRow()][enemy.getPosition().getCol()] = new Tile();
			}
			if(enemy.isBattleActive()) hurt(enemy.attack(this));
		}

	}

	/*
	public void enemyEncounter(Tile t){
		Scanner input = new Scanner(System.in);
		System.out.println("Attack!");
		Enemy enemy = (Enemy)t;
		enemy.setBattleActive(true);
		while(enemy.isBattleActive()){
			enemy.drawEncounter();
			enemy.displayBattleInfo(this);
			this.drawBattleOptions();
			System.out.print("Input: ");
			String action = input.nextLine();
			action = action.toLowerCase();

			if(action.equals("q")){
				weapon.attackOptions(this, enemy);
			}else if(action.equals("i")){
				inventoryBattle();
			//}else if(action.equals("r")){ //causes enemy to give loot when running
				//enemy.setBattleActive(false);
			}else if(action.indexOf("show item") >= 0){ //debugging
				int ind = Integer.valueOf(action.substring(10));
				System.out.println("[ "+enemy.showItem(ind) + " ]"); //debugg
			}

			//if(enemy.isBattleActive()) hurt(enemy.attack(this));
			if(!enemy.isBattleActive()){
				levelUp(enemy.dropXp());
				if(Math.random() <= .20){
					itemInteraction(enemy.dropLoot());
				}

			}
			//System.out.println("\033[H\033[2J");
			//System.out.flush();
			if(action.equals("r")) enemy.setBattleActive(false);
			if(enemy.isBattleActive()) hurt(enemy.attack(this));
		}

	}
	*/

	public void movePlayerIntoNextRoom(Room room, int side){
		Tile[][] roomArr = room.getRoomArray();
		if(side == 1){
			int r = roomArr.length-1;
			for(int c = 0; c < roomArr[0].length; c++){
				if(roomArr[r][c].getSymbol() == '#'){
					roomArr[r-1][c] = this;
					this.setPosition(r-1, c);
				}
			}
		}else if(side == 2){
			int c = 0;
			for(int r = 0; r < roomArr.length; r++){
				if(roomArr[r][c].getSymbol() == '#'){
					roomArr[r][c+1] = this;
					this.setPosition(r, c+1);
				}
			}
		}else if(side == 3){
			int r = 0;
			for(int c = 0; c < roomArr[0].length; c++){
				if(roomArr[r][c].getSymbol() == '#'){
					roomArr[r+1][c] = this;
					this.setPosition(r+1, c);
				}
			}
		}else{
			int c = roomArr[0].length-1;
			for(int r = 0; r < roomArr.length; r++){
				if(roomArr[r][c].getSymbol() == '#'){
					roomArr[r][c-1] = this;
					this.setPosition(r, c-1);
				}
			}
		}
	}




	public void showInventory(){
		System.out.print("Inventory [ ");
		for(Item i : inventory){
			System.out.print(i.getName()+", ");
		}
		System.out.print(" ]");
	}

	public void inventoryBattle(){
		boolean invOpen = true;
		while(invOpen){
			System.out.println("I-----------------------I");
			System.out.println("\t");
			showInventory();
			System.out.print("\n\nI-----------------------I\nInput:");
			Scanner input = new Scanner(System.in);
			String action = input.nextLine();
			if(action.indexOf("use") >= 0){
				int invIndex = Integer.valueOf(action.substring(4));
				if(invIndex < 5 && invIndex > -1){
					this.equip(invIndex);
					invOpen = false;
				}else{
					System.out.println("You attempt to pick up the air, you fail");
				}
			}else if(action.indexOf("drop") >= 0){
				int invIndex = Integer.valueOf(action.substring(5));
				if(invIndex < 5 && invIndex > -1){
					this.drop(invIndex);
				}else{
					System.out.println("You attempt to drop the air, you succeed!");
				}
			}else if(action.indexOf("close") >= 0){
				invOpen = false;
			}
		}
	}

	public void drawBattleOptions(){
		System.out.println("\t(Q) Attack");
		System.out.println("\t(I) Use Item");
		System.out.println("\t(R) Run ");
		System.out.println("Input: ");
	}



	public void showStats(){

		/*
		System.out.println("      _,.");
		System.out.println("    ,` -.)");
		System.out.println("   ( _/-\\\\-._");
		System.out.println("  /,|`--._,-^|            ,");
		System.out.println("  \\_| |`-._/||          ,'|");
		System.out.println("    |  `-, / |         /  /");
		System.out.println("    |     || |        /  /");
		System.out.println("     `r-._||/   __   /  /");
		System.out.println(" __,-<_     )`-/  `./  /");
		System.out.println("'  \\   `---'   \\   /  /");
		System.out.println("    |           |./  /");
		System.out.println("    /           //  /");
		System.out.println("\\_/' \\         |/  /");
		System.out.println(" |    |   _,^-'/  /");
		System.out.println(" |    , ``  (\\/  /");
		System.out.println("  \\,.->._    \\X-=/^");
		System.out.println("  (  /   `-._//^`");
		System.out.println("   `Y-.____(__}");
		System.out.println("    |     {__)");
		System.out.println("          ()");
		*/

		createPlayerImage();
		System.out.println("[ @: HP("+health+"/"+maxHp+") - (LV "+level+") | (Def: "+armor.getDefense()+") Armor: "+armor.getName()+", (ATK: "+weapon.getDamage()+") Weapon: "+weapon.getName()+" ]");

	}

	public void createPlayerImage(){
		char[][] wep = weapon.getWeaponArray();
		char[][] arm = armor.getArmorArray();
		for(int r = 0; r < arm.length; r++){
			for(int c = 0; c < arm[r].length; c++){
				if(wep[r][c] != ' '){
					if(wep[r][c] == '?'){
						arm[r][c] = ' ';
					}else{
						arm[r][c] = wep[r][c];
					}
				}
			}
		}

		for(char[] row : arm){
			for(char col : row){
				System.out.print(col);
			}
			System.out.println();
		}
	}





}