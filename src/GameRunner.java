import java.util.Scanner;

/* This is broken until dungeon and room generation is refactored */

public class GameRunner{

	public static void main(String[] args){
		int gameState = 0;
		int playerRow = 0;
		int playerCol = 0;
		boolean tutorialsOn = false;
		Scanner input = new Scanner(System.in);

		System.out.println("---Controls (Press enter after each input)---\nBring Up Info Screen: H\nMovement: WASD\nInteract: Move into object\nMap: M\nInventory: I\n\t> Use: \"use \" + index\n\t> Drop: \"drop \" + index\nStats: T\n-------------------------------------------------");
		System.out.print("How dangerous is the dungeon you wish to enter(easy)1-3+(hard)?: ");
		int size = input.nextInt();
		System.out.print("Do you want to enable tutorials (1 = yes): ");
		int tut = input.nextInt();
		if(tut == 1){
			tutorialsOn = true;
			System.out.println("Tutorials enabled...");
		}



		System.out.println("Constructing Dungeon..."); //debugg
		Dungeon dungeon = new Dungeon(size, tutorialsOn, 0);
		Player player = dungeon.getPlayer();



		System.out.println("\n\n\n\n\n\n\n\n\n");

		while(gameState != 1){

			System.out.println("\n\n\n");
			//System.out.println("Attempting to draw current room..."); //debugg
			Tile[][] roomArr = dungeon.getCurrentRoom().getRoomArray();
			for(int r = 0; r < roomArr.length; r++){
				for(int c = 0; c < roomArr[0].length; c++){
					if(roomArr[r][c].getSymbol() == '@'){
						player = (Player)roomArr[r][c];

					}
				}
			}
			dungeon.getCurrentRoom().moveEnemies(player, dungeon);
			dungeon.getCurrentRoom().drawRoom2d();
			System.out.print("Input: ");
			String action = input.nextLine();
			inputHandler(dungeon, roomArr, player, action, tutorialsOn);
			System.out.println("\n\n");

			if(player.getHealth() <= 0) gameState++;
		}
	}

	private static void inputHandler(Dungeon dungeon, Tile[][] room, Player player, String input, boolean tut){
		System.out.println("----V------V----V-------V------V------V------V-------");
		if(tut) System.out.println("(Input H for info and controls)\n\n");
		//System.out.println();
		/*
		if(!dungeon.getCurrentRoom().isActiveRoom()){
			System.out.println("> Room is now active");
			dungeon.getCurrentRoom().setActiveRoom(true);
		}
		*/

		int row = player.getRow();
		int col = player.getCol();
		input = input.toLowerCase();
		if(input.equals("w")){
			if(room[row-1][col].isWalkable()){
				if(!dungeon.getCurrentRoom().isActiveRoom()){
					//System.out.println("> Room is now active"); //debugg
					dungeon.getCurrentRoom().setActiveRoom(true);
				}
				player.setPosition(row-1, col);
				room[row-1][col] = player;
				room[row][col] = new Tile(' ', row, col);
			}else if(!(room[row-1][col].isWall())){
				wInteract(dungeon, room, player, row, col);
			}
		}else if(input.equals("a")){
			if(room[row][col-1].isWalkable()){
				if(!dungeon.getCurrentRoom().isActiveRoom()){
					//System.out.println("> Room is now active"); //debugg
					dungeon.getCurrentRoom().setActiveRoom(true);
				}
				player.setPosition(row, col-1);
				room[row][col-1] = player;
				room[row][col] = new Tile(' ', row, col);
			}else if(!(room[row][col-1].isWall())){
				aInteract(dungeon, room, player, row, col);
			}
		}else if(input.equals("s")){
			if(room[row+1][col].isWalkable()){
				if(!dungeon.getCurrentRoom().isActiveRoom()){
					//System.out.println("> Room is now active"); //debugg
					dungeon.getCurrentRoom().setActiveRoom(true);
				}
				player.setPosition(row+1, col);
				room[row+1][col] = player;
				room[row][col] = new Tile(' ', row, col);
			}else if(!(room[row+1][col].isWall())){
				sInteract(dungeon, room, player, row, col);
			}
		}else if(input.equals("d")){
			if(room[row][col+1].isWalkable()){
				if(!dungeon.getCurrentRoom().isActiveRoom()){
					//System.out.println("> Room is now active"); //debugg
					dungeon.getCurrentRoom().setActiveRoom(true);
				}
				player.setPosition(row, col+1);
				room[row][col+1] = player;
				room[row][col] = new Tile(' ', row, col);
			}else if(!(room[row][col+1].isWall())){
				dInteract(dungeon, room, player, row, col);
			}
		}else if(input.equals("i")){
			player.showInventory();
		}else if(input.equals("t")){
			player.showStats();
		}else if(input.equals("m")){
			dungeon.drawMap();
		}else if(input.equals("h")){
			System.out.println("---Controls (Press enter after each input)---\nInfo: H\nMovement: WASD\nInteract: Move into object\nMap: M\nInventory: I\n\t> Use: \"use \" + index\n\t> Drop: \"drop \" + index\nStats: T\n-------------------------------------------------");
		}else if(input.indexOf("use") >= 0){
			int invIndex = Integer.valueOf(input.substring(4));
			if(invIndex < 5 && invIndex > -1){
				player.equip(invIndex);
			}else{
				System.out.println("You attempt to pick up the air, you fail");
			}
		}else if(input.indexOf("drop") >= 0){
			int invIndex = Integer.valueOf(input.substring(5));
			if(invIndex < 5 && invIndex > -1){
				player.drop(invIndex);
			}else{
				System.out.println("You attempt to drop the air, you fail");
			}
		}else if(input.equals("debug nextroom")){
			//dungeon.nextRoom();
		}else if(input.equals("debug prevroom")){
			//dungeon.prevRoom();
		}else if(input.equals("debug bossroom")){
			dungeon.skipToBoss(player);
		}else if(input.equals("debug playerpos")){
			System.out.println(player);
		}else if(input.equals("debug clearscreen")){
			System.out.println("\033[H\033[2J");
			System.out.flush();
		}else if(input.equals("debug map")){
			dungeon.drawMap();
		}else if(input.equals("debug weapon")){
			System.out.println(player.getWeapon().getInformation());
		}
		//System.out.println("\033[H\033[2J");
		//System.out.flush();


	}

	private static void wInteract(Dungeon d, Tile[][] room, Player player, int row, int col){
		player.interract(d, room[row-1][col]);
		//room[row-1][col] = new Tile(' ', row-1, col);
		/*
		if(room[row-1][col].getSymbol() != '#'){
			room[row-1][col] = new Tile(' ', row-1, col);
		}
		*/
		if(room[row-1][col].getSymbol() >= 97 && room[row-1][col].getSymbol() <= 122){
			System.out.println("Enemy check happened"); //debug
		}else if(!(room[row-1][col].getSymbol() == '#'
		|| room[row-1][col].getSymbol() == 'O'
		|| room[row-1][col].getSymbol() == 'X')){
			room[row-1][col] = new Tile(' ', row-1, col);
		}else if(room[row-1][col].getSymbol() == 'X'){
			room[row-1][col] = new Tile('#', row-1, col, room[row-1][col].getSide());
		}

	}

	private static void aInteract(Dungeon d, Tile[][] room, Player player, int row, int col){
		player.interract(d, room[row][col-1]);

		if(!(room[row][col-1].getSymbol() == '#'
		|| room[row][col-1].getSymbol() == 'O'
		|| room[row][col-1].getSymbol() == 'X')){
			room[row][col-1] = new Tile(' ', row, col-1);
		}else if(room[row][col-1].getSymbol() == 'X'){
			room[row][col-1] = new Tile('#', row, col-1, room[row][col-1].getSide());
		}
	}

	private static void sInteract(Dungeon d, Tile[][] room, Player player, int row, int col){
		player.interract(d, room[row+1][col]);
		if(!(room[row+1][col].getSymbol() == '#'
		|| room[row+1][col].getSymbol() == 'O'
		|| room[row+1][col].getSymbol() == 'X')){
			room[row+1][col] = new Tile(' ', row+1, col);
		}else if(room[row+1][col].getSymbol() == 'X'){
			room[row+1][col] = new Tile('#', row+1, col, room[row+1][col].getSide());
		}
	}

	private static void dInteract(Dungeon d, Tile[][] room, Player player, int row, int col){
		player.interract(d, room[row][col+1]);
		if(!(room[row][col+1].getSymbol() == '#'
		|| room[row][col+1].getSymbol() == 'O'
		|| room[row][col+1].getSymbol() == 'X')){
			room[row][col+1] = new Tile(' ', row, col+1);
		}else if(room[row][col+1].getSymbol() == 'X'){
			room[row][col+1] = new Tile('#', row, col+1, room[row][col+1].getSide());
		}
	}

}