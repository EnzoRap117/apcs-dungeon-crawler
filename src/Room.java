import java.util.ArrayList;

public class Room{

	private int height;
	private int width;
	private int depth;
	private boolean exists = false;
	private boolean special;
	private boolean containsPlayer = false;
	private Tile[][] room2d;
	private Enemy[] enemyList;
	private boolean activeRoom;

	/*
	public Room(int w, int h){
		height = h;
		width = w;
		room2d = createRoom2d();//1); //this probably need to be changed
		exists = true;
		special = false;
	}
	*/

	public Room(int w, int h, int floor, Enemy[] enemyList){
		height = h;
		width = w;
		room2d = createRoom2d(enemyList);
		exists = true;
		special = false;
		activeRoom = false;

	}


	public Room(int w, int h, int nD, boolean spec){
		height = h;
		width = w;
		room2d = createRoom2d();//nD); //broken till place doors is complete
		exists = true;
		special = spec;
		activeRoom = false;
	}


	/* Precondition: room is at least 5*5
		Should look something like this:
				+----------------+
				|                |
				#                |
				|                |
				+----------------+
	*/
	private Tile[][] createRoom2d(){//int numDoors){
		/*
		*/
		Tile[][] room = new Tile[height][width];
		for(int r = 0; r < room.length; r++){
			for(int c = 0; c < room[r].length; c++){
				if(r == 0 || r == room.length-1){
					if(c == 0 || c == room[r].length-1){
						room[r][c] = new Tile('+');
						room[r][c].setPosition(r, c);
					}else{
						room[r][c] = new Tile('_');
						room[r][c].setPosition(r, c);
					}
				}else{
					if(c == 0 || c == room[r].length-1){
						room[r][c] = new Tile('|');
						room[r][c].setPosition(r, c);
					}else{
						room[r][c] = new Tile(' ');
						room[r][c].setPosition(r, c);
					}
				}
			}
		}

		return room;
	}

	private Tile[][] createRoom2d(Enemy[] enemyList){//int numDoors){
		/*
		*/
		Tile[][] room = new Tile[height][width];
		for(int r = 0; r < room.length; r++){
			for(int c = 0; c < room[r].length; c++){
				if(r == 0 || r == room.length-1){
					if(c == 0 || c == room[r].length-1){
						room[r][c] = new Tile('+');
						room[r][c].setPosition(r, c);
					}else{
						room[r][c] = new Tile('_');
						room[r][c].setPosition(r, c);
					}
				}else{
					if(c == 0 || c == room[r].length-1){
						room[r][c] = new Tile('|');
						room[r][c].setPosition(r, c);
					}else{
						room[r][c] = new Tile(' ');
						room[r][c].setPosition(r, c);
					}
				}
			}
		}

		for(int r = 2; r < room.length-2; r++){
			for(int c = 2; c < room[r].length-2; c++){
				double ran = Math.random();
				if(ran <= .01){
					int ranEnemy = (int)(Math.random() * enemyList.length);
					Enemy uncastedEnemy = enemyList[ranEnemy].clone();
					System.out.println("\t\t> "+enemyList[ranEnemy].getName() +" cloned"); //debugg
					castTheCorrectEnemy(room, r, c, uncastedEnemy);
					//uncastedEnemy;
				}
				if(ran >= .80){
					room[r][c].setSymbol('^');
				}
			}
		}
		//room = placeDoors(room, numDoors);

		return room;
	}

	private void castTheCorrectEnemy(Tile[][] room, int r, int c, Enemy e){
		String name = e.getName();
		System.out.println("\t\t\t> Attempting to cast "+name+" to correct type..."); //debugg
		if(name.equals("Bat")){
			room[r][c] = (Bat)e;
		}else if(name.equals("Rat")){
			room[r][c] = (Rat)e;
		}else if(name.equals("Spider")){
			room[r][c] = (Spider)e;
		}else{
			System.out.println(">Enemy Cast Failure<");
		}
		e.setPosition(r, c);
	}

	public void moveEnemies(Player player, Dungeon d){

		if(activeRoom){
			//System.out.println("\t> In moveEnemies"); //debugg
			ArrayList<Enemy> enemyArrayList = new ArrayList<Enemy>();
			for(int r = 0; r < room2d.length; r++){
				for(int c = 0; c < room2d[r].length; c++){
					if(room2d[r][c].isEnemy()){
						room2d[r][c].setPosition(r, c);
						enemyArrayList.add((Enemy)room2d[r][c]);
						//System.out.println("\t\t> Enemy found at: "+"("+ r + ", " + c +")"); //debugg
						/*
						Enemy e = (Enemy)room2d[r][c];
						e.moveEnemy(room2d);
						*/
					}

				}
			}

			for(Enemy e : enemyArrayList){
				//System.out.println("\t\t\t> attempting to move: "+e.getName());; //debugg
				e.moveEnemy(room2d, d, player);
			}
		}/*else{
			System.out.println("> > Guard against early enemy movement triggered"); //debugg
		}*/

	}

	public void drawRoom2d(){
		for(int r = 0; r < room2d.length; r++){
			for(int c = 0; c < room2d[r].length; c++){
				System.out.print(room2d[r][c].getSymbol());
			}
			System.out.println();
		}
	}

	/* needs to be changed to "placeDoors" */
	// 1) Look at rooms adjacent to current room
	// 2) If there is a room, place a door
	// - changed header "Dungeon dungeon"
	private Tile[][] placeDoors(Tile[][] room2d, int numDoors){
		boolean[] hasDoor = new boolean[5];
		if(numDoors > 4) numDoors = 4;
		while(numDoors != 0){
			int random = (int)(Math.random() * 4)+1;
			if(hasDoor[random]){
				numDoors++;
			}else{
				room2d = placeDoor(room2d, random);
				hasDoor[random] = true;
			}
			numDoors--;
		}
		return room2d;
	}

	/* Preconditon, side is int 1-4 */
	//changed side assignment of side 4 to side 0
	public Tile[][] placeDoor(Tile[][] room2d, int side){
		//System.out.println(">In placeDoor"); //debugg
		int length = room2d.length;
		int width = room2d[0].length;
		if(side == 1){
			int c = width/2-1;
			int r = 0;
			room2d[r][c] = new Tile('#');
			room2d[r][c].setPosition(r, c);
			room2d[r][c].setSide(1);
		}else if(side == 2){
			int c = width-1;
			int r = length/2-1;
			room2d[r][c] = new Tile('#');
			room2d[r][c].setPosition(r, c);
			room2d[r][c].setSide(2);
		}else if(side == 3){
			int c = width/2-1;
			int r = length-1;
			room2d[r][c] = new Tile('#');
			room2d[r][c].setPosition(r, c);
			room2d[r][c].setSide(3);
		}else{
			int c = 0;
			int r = length/2-1;
			room2d[r][c] = new Tile('#');
			room2d[r][c].setPosition(r, c);
			room2d[r][c].setSide(0);
		}
		return room2d;
	}

	public void addTile(Tile t){
		int col = (int)(Math.random() * (room2d[0].length-2))+1;
		int row = (int)(Math.random() * (room2d.length-2))+1;
		if(!room2d[row][col].isWalkable()){
			addTile(t);
		}
		room2d[row][col] = t;
		t.setPosition(row, col);
		//System.out.println("Tile: "+t.getSymbol()+"Pos: ("+row+", "+col+")");
		System.out.println(t);
	}

	public void addTile(Tile t, int r, int c){
		room2d[r][c] = t;
		room2d[r][c].setPosition(r, c);
	}

	public Tile[][] getRoomArray(){
		return room2d;
	}

	public Tile findDoor(){
		Tile t =  new Tile('?');
		for(int r = 0; r < room2d.length; r++){
			for(int c = 0; c < room2d[r].length; c++){
				if(room2d[r][c].getSymbol() == '#'){
					t = room2d[r][c];
					return t;
				}

			}
		}
		return t;

	}

	public boolean doesExist(){
		return exists;
	}

	public boolean isSpecial(){
		return special;
	}

	public boolean isActiveRoom(){
		return activeRoom;
	}

	public void setActiveRoom(boolean b){
		activeRoom = b;
	}


}