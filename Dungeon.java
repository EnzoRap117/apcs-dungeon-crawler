
//import Player.*;

public class Dungeon{

	private int height;
	private int width;
	private Tile[][] dungeon;
	private Room[][] roomLayout;
	private Position currRoom;
	private Position posBossRoom;
	private Player player;
	private boolean tutorialsOn;
	private Enemy[] enemyList;
	private Item[] itemList;
	private Item[] startingWeaponList;


	/* Dungeon generation needs to create a pathway between the
	 * starting and boss room, adding special rooms off the beaten path
	 */
	 /* 1) Create the current blank floor
	  * 2) place the starting room in the middle
	  * 3) pick a random wall and place a room on the side
	  * 3a) make sure that you dont backtrack and place a room on one thats already occupied
	  * 4) keep repeating until you hit the border of the dungeon, make that room a boss room
	  * 5) Add doors, (must implement)
	  */
	public Dungeon(int difficulty, boolean tut, int f){
		tutorialsOn = tut;
		int floor = f;

		enemyList = new Enemy[3];
		enemyList[0] = new Bat(floor+1);
		enemyList[1] = new Rat(floor+1);
		enemyList[2] = new Spider(floor+1);

		itemList = new Item[5];
		itemList[0] = new BattleAxe(f);
		itemList[1] = new DustyBlade(f);
		itemList[2] = new Greatsword(f);
		itemList[3] = new BlessedArmor();
		itemList[4] = new Potion("Large Health Potion", "recovers a large amount of health", "healing", 50);

		startingWeaponList = new Item[3];
		startingWeaponList[0] = new BattleAxe(f);
		startingWeaponList[1] = new DustyBlade(f);
		startingWeaponList[2] = new Greatsword(f);


		int size = 10 + (floor/2 + difficulty-1)*2;
		roomLayout = new Room[size][size];
		System.out.println("Constructing a StartingRoom"); //debugg
		roomLayout[size/2][size/2] = new StartingRoom(startingWeaponList);
		currRoom = new Position(size/2,size/2);
		int dunRow = size/2;
		int dunCol = size/2;
		int roomCap = size*2;
		int prevWall = -1;
		boolean bossRoomExists = false;
		int roomCount = 0;
		while(!bossRoomExists){

			//Map is too large

			int ran = (int)(Math.random() * 4)+1;
			int backWall = (ran+2) % 4;
			if(!(backWall == prevWall)){ //this ran stuff is a bit iffy
				prevWall = ran-1;
				if(ran == 1){
					if(dunRow == 0){
						System.out.println("Constructing a BossRoom..."); //debugg
						roomLayout[dunRow][dunCol] = new BossRoom();
						posBossRoom = new Position(dunRow, dunCol);
						bossRoomExists = true;
					}else if(roomLayout[dunRow-1][dunCol] == null){
						System.out.println("Constructing a room..."); //debugg
						dunRow--;
						roomLayout[dunRow][dunCol] = genRandomRoom(floor, enemyList);
					}
				}else if(ran == 2){
					if(dunCol == roomLayout[0].length-1){
						System.out.println("Constructing a BossRoom..."); //debugg
						roomLayout[dunRow][dunCol] = new BossRoom();
						posBossRoom = new Position(dunRow, dunCol);
						bossRoomExists = true;
					}else if(roomLayout[dunRow][dunCol+1] == null){
						System.out.println("Constructing a room..."); //debugg
						dunCol++;
						roomLayout[dunRow][dunCol] = genRandomRoom(floor, enemyList);
					}
				}else if(ran == 3){
					if(dunRow == roomLayout.length-1){
						System.out.println("Constructing a BossRoom..."); //debugg
						roomLayout[dunRow][dunCol] = new BossRoom();
						posBossRoom = new Position(dunRow, dunCol);
						bossRoomExists = true;
					}else if(roomLayout[dunRow+1][dunCol] == null){
						System.out.println("Constructing a room..."); //debugg
						dunRow++;
						roomLayout[dunRow][dunCol] = genRandomRoom(floor, enemyList);
					}
				}else{
					if(dunCol == 0){
						System.out.println("Constructing a BossRoom..."); //debugg
						roomLayout[dunRow][dunCol] = new BossRoom();
						posBossRoom = new Position(dunRow, dunCol);
						bossRoomExists = true;
					}else if(roomLayout[dunRow][dunCol-1] == null){
						System.out.println("Constructing a room..."); //debugg
						dunCol--;
						roomLayout[dunRow][dunCol] = genRandomRoom(floor, enemyList);
					}
				}
				roomCount++;

				if(roomCount == roomCap-1){
					System.out.println("> Room limit reached"); //debugg
					System.out.println("Constructing a BossRoom..."); //debugg
					roomLayout[dunRow][dunCol] = new BossRoom();
					posBossRoom = new Position(dunRow, dunCol);
					bossRoomExists = true;
				}

			}

		}
		//System.out.println("Outside addDoors");
		roomLayout = addDoors(roomLayout);
		player = new Player();

	}

	public Room getCurrentRoom(){
		return roomLayout[currRoom.getRow()][currRoom.getCol()];
	}

	public Room genRandomRoom(int floor, Enemy[] enemyList){
		//Max room length/width is 20, min is 4 - not true anymore
		int width = (int)(Math.random() * 12) + 8;
		int length = (int)(Math.random() * 8) + 6;
		int numDoors = 2;//(int)(Math.random() * 3) + 2;
		return new Room(width, length, floor, enemyList);//, numDoors);
	}

	public Room[][] addDoors(Room[][] roomLay){
		//System.out.println(">Inside addDoors"); //debugg
		for(int r = 0; r < roomLay.length; r++){
			for(int c = 0; c < roomLay[0].length; c++){
				if(roomLay[r][c] != null){
					for(int s = 0; s < 4; s++){
						if(hasAdjacent(roomLay, r, c, s)){
							//System.out.println("> > > adding a door"); //debugg
							roomLay[r][c].placeDoor(roomLay[r][c].getRoomArray(), s);
						}
					}
				}
			}
		}
		return roomLay;
	}

	public boolean hasAdjacent(Room[][] roomLay, int r, int c, int s){
		//check bounds
		//System.out.println("> > Inside hasAdjacent"); //debugg
		if(s == 1){
			if(r == 0) return false;
			if(roomLayout[r-1][c] != null) return true;
		}else if(s == 2){
			if(c == roomLay[0].length-1) return false;
			if(roomLayout[r][c+1] != null) return true;
		}else if(s == 3){
			if(r == roomLay.length-1) return false;
			if(roomLayout[r+1][c] != null) return true;
		}else{
			if(c == 0) return false;
			if(roomLayout[r][c-1] != null) return true;
		}
		return false;
	}




	public void nextRoom(int side){
		if(side == 1){
			currRoom.setRow(currRoom.getRow()-1);
		}else if(side == 2){
			currRoom.setCol(currRoom.getCol()+1);
		}else if(side == 3){
			currRoom.setRow(currRoom.getRow()+1);
		}else{
			currRoom.setCol(currRoom.getCol()-1);
		}
	}

	/*
	public void prevRoom(){
		currRoom--;
	}
	*/

	public void skipToBoss(Player player){
		//currRoom = roomList.length-1;
		System.out.println("In skipToBoss"); //debugg
		currRoom.setPosition(posBossRoom.getRow(), posBossRoom.getCol());
		Room room = getCurrentRoom();
		Tile[][] roomArr = room.getRoomArray();
		Tile door = room.findDoor();
		int side = door.getSide();
		int dRow = door.getRow();
		int dCol = door.getCol();
		System.out.println(side);
		if(side == 1){
			System.out.println("In side 1"); //debugg
			roomArr[dRow+1][dCol] = player;
			roomArr[dRow][dCol] = new Tile('X', dRow, dCol);
			player.setPosition(dRow+1, dCol);
		}else if(side == 2){
			System.out.println("In side 2"); //debugg
			roomArr[dRow][dCol-1] = player;
			roomArr[dRow][dCol] = new Tile('X', dRow, dCol);
			player.setPosition(dRow, dCol-1);
		}else if(side == 3){
			System.out.println("In side 3"); //debugg
			roomArr[dRow-1][dCol] = player;
			roomArr[dRow][dCol] = new Tile('X', dRow, dCol);
			player.setPosition(dRow-1, dCol);
		}else{
			System.out.println("In side 0/4"); //debugg
			roomArr[dRow][dCol+1] = player;
			roomArr[dRow][dCol] = new Tile('X', dRow, dCol);
			player.setPosition(dRow, dCol+1);
		}
	}

	public Player getPlayer(){
		return player;
	}

	public void drawMap(){
		for(int r = 0; r < roomLayout.length; r++){
			for(int c = 0; c < roomLayout[0].length; c++){
				if(roomLayout[r][c] == null){
					System.out.print(".");
				}else{
					if(roomLayout[r][c].isSpecial()){
						System.out.print("X");
					}else{
						System.out.print("O");
					}
				}
			}
			System.out.println();
		}
	}



}