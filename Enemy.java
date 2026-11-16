class Enemy extends Tile{

	private String name;
	private int health;
	private int level;
	private String status;
	private int maxHp;
	private boolean battleActive;
	private Item[] lootPool;// = new Item[5];
	private String type;
	private boolean heavy;
	private boolean aggressive;
	private Position ePosition;
	private Tile storage;
	private boolean justSteppedOnASpike;

	public Enemy(){
		super('!', true);
		name = "default";
		health = 1;
		status = "alive";
		level = 1;
		heavy = false;
		aggressive = false;
		//Messing around with item drops
		lootPool = new Item[1];
		setLootPoolAtInd(new Sword("Wooden Sword", "a blunt wooden blade", 1), 0);
		storage = new Tile(' ', 0,0);
		justSteppedOnASpike = false;
	}

	public Enemy(char s, String n, double h, int l, boolean hev, boolean a, int pool){//, String t){
		super(s, true);
		name = n;
		health = (int)h;
		maxHp = (int)h;
		status = "alive";
		level = l;
		heavy = hev;
		aggressive = a;
		lootPool = new Item[pool];
		//type = t;
		ePosition = new Position(1, 1);
		storage = new Tile(' ', 0,0);
		justSteppedOnASpike = false;
	}

	public Enemy(char s, String n, double h, int l, boolean hev, boolean a, int pool, Position p){//, String t){
		super(s, true);
		name = n;
		health = (int)h;
		maxHp = (int)h;
		status = "alive";
		level = l;
		heavy = hev;
		aggressive = a;
		ePosition = p;
		lootPool = new Item[pool];
		//type = t;
		storage = new Tile(' ', 0,0);
		justSteppedOnASpike = false;
	}

	public Enemy(char s, String n, double h, int l, boolean hev, boolean a, Item[] pool){
		super(s, true);
		name = n;
		health = (int)h;
		maxHp = (int)h;
		status = "alive";
		level = l;
		heavy = hev;
		aggressive = a;
		lootPool = pool;
		ePosition = new Position(1, 1);
		storage = new Tile(' ', 0,0);
		justSteppedOnASpike = false;
	}

	public Enemy(char s, String n, double h, int l, boolean hev, boolean a, Item[] pool, Position p){
		super(s, true);
		name = n;
		health = (int)h;
		maxHp = (int)h;
		status = "alive";
		level = l;
		heavy = hev;
		aggressive = a;
		lootPool = pool;
		ePosition = p;
		storage = new Tile(' ', 0,0);
		justSteppedOnASpike = false;
	}

	public boolean isEnemy(){
		return true;
	}

	public void hurt(double d){
		if(d == 0){
			System.out.println("\tYour attack misses the "+name+"!");
		}else{
			System.out.println("\t"+name+" hit for "+(int)d+" damage!");
		}
		health -= d;
		//setHealth =
		if(health < 0){
			status = "defeated";
			setBattleActive(false);
		}

	}

	public int attack(Player p){
		//int missChance =
		int damage = level * (int)(Math.random() * 6);
		if(damage == 0){
			System.out.println(name+" Misses!");
			return 0;
		}else{
			System.out.println("\t"+name + " hit you for "+damage+" damage!");
			return damage;
		}

	}


	private void moveEnemyInDirection(Tile[][] roomArr, Dungeon d, Player player, int eRow, int eCol, int rowAdj, int colAdj){
		Tile adjacent = roomArr[eRow+rowAdj][eCol+colAdj];
		if(adjacent.getSymbol() == ' '){
			//System.out.println("\t\t\t\t\t> Attempting to move "+name+" to: "+"("+ (eRow+rowAdj) + ", " + (eCol+colAdj) +")"); //debugg
			roomArr[eRow+rowAdj][eCol+colAdj] = this;
			roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);;
			setPosition(eRow+rowAdj, eCol+colAdj);
		}else if(adjacent.getSymbol() == '^'){
			 //Causes hella bugs
			/*if(!heavy){
				if(justSteppedOnASpike){
					justSteppedOnASpike = false;
					roomArr[eRow+rowAdj][eCol+colAdj] = this;
					roomArr[eRow][eCol] = storage;
					storage = new Tile();
				}else{
					justSteppedOnASpike = true;
					storage = roomArr[eRow+rowAdj][eCol+colAdj];
					roomArr[eRow+rowAdj][eCol+colAdj] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);
				}
			}else{*/
				//System.out.println("\t\t\t\t\t> Attempting to move "+name+" to: "+"("+ (eRow+rowAdj) + ", " + (eCol+colAdj) +")"); //debugg
				roomArr[eRow+rowAdj][eCol+colAdj] = this;
				roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);
				setPosition(eRow+rowAdj, eCol+colAdj);
				hurt(3);
			//}
		}else if(adjacent.getSymbol() == '@'){
			player.enemyEncounter(d, this);
		}
	}



	public void moveEnemy(Tile[][] roomArr, Dungeon d, Player player){

		//System.out.println("\t\t\t\t> In moveEnemy for the "+name); //debugg

		int eRow = ePosition.getRow();
		int eCol = ePosition.getCol();

		//System.out.println("\t\t\t\t\t> Position of enemy was saved as: ("+ eRow + ", " + eCol +")"); //debugg

		if(aggressive){
			int pRow = player.getRow();
			int pCol = player.getCol();

			if(Math.random() < .5){ //So it doesn't always try to match your row/col first
				if(pRow > eRow){
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, 1, 0);
				}else if(pRow < eRow){
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, -1, 0);
				}else if(pCol > eCol){
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, 0, 1);
				}else{
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, 0, -1);
				}
			}else{
				if(pCol > eCol){
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, 0, 1);
				}else if(pCol < eCol){
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, 0, -1);
				}else if(pRow > eRow){
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, 1, 0);
				}else{
					moveEnemyInDirection(roomArr, d, player, eRow, eCol, -1, 0);
				}
			}

		}else{
			int ran = (int)(Math.random()*4 + 1);
			if(ran == 1){
				moveEnemyInDirection(roomArr, d, player, eRow, eCol, -1, 0);
			}else if(ran == 2){
				moveEnemyInDirection(roomArr, d, player, eRow, eCol, 0, 1);
			}else if(ran == 3){
				moveEnemyInDirection(roomArr, d, player, eRow, eCol, 1, 0);
			}else{
				moveEnemyInDirection(roomArr, d, player, eRow, eCol, 0, -1);
			}

			/*
			if(ran == 1){ // eRow -1 (moving up)
				Tile adjacent = roomArr[eRow-1][eCol];
				if(adjacent.getSymbol() == ' '){
					roomArr[eRow-1][eCol] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);;
					setPosition(eRow-1, eCol);
				}else if(adjacent.getSymbol() == '^'){
					roomArr[eRow-1][eCol] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);
					setPosition(eRow-1, eCol);
					hurt(3);
				}
			}else if(ran == 2){ // eCol +1 (moving right)
				Tile adjacent = roomArr[eRow][eCol+1];
				if(adjacent.getSymbol() == ' '){
					roomArr[eRow][eCol+1] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);;
					setPosition(eRow, eCol+1);
				}else if(adjacent.getSymbol() == '^'){
					roomArr[eRow][eCol+1] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);
					setPosition(eRow, eCol+1);
					hurt(3);
				}
			}else if(ran == 3){ // eRow +1 (moving down)
				Tile adjacent = roomArr[eRow+1][eCol];
				if(adjacent.getSymbol() == ' '){
					roomArr[eRow+1][eCol] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);;
					setPosition(eRow+1, eCol);
				}else if(adjacent.getSymbol() == '^'){
					roomArr[eRow+1][eCol] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);
					setPosition(eRow+1, eCol);
					hurt(3);
				}
			}else{ // eCol -1 (moving left)
				Tile adjacent = roomArr[eRow][eCol-1];
				if(adjacent.getSymbol() == ' '){
					roomArr[eRow][eCol-1] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);;
					setPosition(eRow, eCol-1);
				}else if(adjacent.getSymbol() == '^'){
					roomArr[eRow][eCol-1] = this;
					roomArr[eRow][eCol] = new Tile(' ', eRow, eCol);
					setPosition(eRow, eCol-1);
					hurt(3);
				}
			}
			*/

		}

	}


	public String status(){
		if(health > 0){
			return name + " - Level: "+level+ " - HP: "+health;
		}else{
			return name + " has been defeated.";
		}
	}

	public Item itemDrop(){
		//unfinished
		Item weapon = new BattleAxe();
		return weapon;
	}

	public double xpDrop(){
		double experience = health * level;
		return experience;
	}

	public char getSymbol(){
		return super.getSymbol();
	}

	public int getHealth(){
		return health;
	}

	public int getLevel(){
		return level;
	}

	public String getName(){
		return name;
	}

	public String getType(){
		return type;
	}

	public Position getPosition(){
		return ePosition;
	}

	/*
	public boolean isWalkable(){
		return true;
	}
	*/

	public boolean isBattleActive(){
		if(health <= 0) return false;
		return battleActive;
	}

	public void setPosition(int r, int c){
		ePosition.setPosition(r, c);
	}

	public void setBattleActive(boolean s){
		battleActive = s;
	}

	public void drawEncounter(){
		System.out.println(" ________  ________  ________   ______   ________ ");
		System.out.println(" _         __    __  __    __  ___  ___  __    __ ");
		System.out.println(" ________  ________  ________  __    __  ________ ");
		System.out.println(" _         __ ___    ___ __    ___  ___  ___ __   ");
		System.out.println(" ________  __    __  __    __   ______   __    __ ");
	}

	public void displayBattleInfo(Player p){
		System.out.println("!------------------------------!");
		System.out.println("  "+name+" ("+getHealth()+"/"+maxHp+")");
		System.out.println("  (You)  ("+p.getHealth()+"/"+p.getMaxHp()+")"); //changed here
		System.out.println("                               ");
		System.out.println("!------------------------------!");


	}

	public Item dropLoot(Tile[][] arr){

		int ran = (int)(Math.random() * 5);
		arr[getPosition().getRow()][getPosition().getCol()] = new Tile();
		return lootPool[ran];

	}

	public double dropXp(){
		return (double)maxHp * level;
	}

	public Item showItem(int index){
		return lootPool[index];
	}

	public void setLootPool(Item i0, Item i1, Item i2, Item i3, Item i4){
		lootPool[0] = i0;
		lootPool[1] = i1;
		lootPool[2] = i2;
		lootPool[3] = i3;
		lootPool[4] = i4;
	}

	public void setLootPoolAtInd(Item item, int index){
		lootPool[index] = item;
	}


	public Enemy clone(){
		return new Enemy(this.getSymbol(), name, health, level, heavy, aggressive, lootPool, ePosition);
	}





}