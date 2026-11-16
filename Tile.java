public class Tile{

	private char symbol;
	private int row;
	private int col;
	private int side = -1;
	private Position position = new Position();
	private String words;
	private boolean isEnemy;

	public Tile(){
		symbol = ' ';
		position = new Position(0,0);
		row = 0;
		col = 0;
	}

	public Tile(char s){
		symbol = s;
		position = new Position();
		row = 0;
		col = 0;
	}

	public Tile(char s, boolean e){
		symbol = s;
		isEnemy = e;
		position = new Position();
		row = 0;
		col = 0;
	}

	public Tile(char s, int r, int c){
		symbol = s;
		setPosition(r, c);
		row = r;
		col = c;
	}

	public Tile(char s, int r, int c, int sD){
		symbol = s;
		setPosition(r, c);
		row = r;
		col = c;
		side = sD;
	}

	public Tile(char s, String str){
		symbol = s;
		words = str;
		position = new Position();
		row = 0;
		col = 0;
	}

	public char getSymbol(){
		return symbol;
	}

	public boolean isWalkable(){
		if(symbol == ' '){
			return true;
		}
		return false;
	}

	public boolean isWall(){
		if(symbol == '_'
		|| symbol == '|') return true;
		return false;
	}

	public boolean isEnemy(){
		return isEnemy;
	}

	public boolean isChest(){
		return false;
	}

	public boolean isDoor(){
		if(symbol == '#') return true;
		return false;
	}

	public void setPosition(int r, int c){
		position.setRow(r);
		position.setCol(c);
		row = r;
		col = c;
	}

	public Position getPosition(){
		return position;
	}

	public int getRow(){
		return row;
	}

	public int getCol(){
		return col;
	}

	public int getSide(){
		return side;
	}

	public String getWords(){
		return words;
	}

	public void setRow(int r){
		row = r;
	}

	public void setCol(int c){
		col = c;
	}

	public void setSide(int s){
		side = s;
	}

	public void setSymbol(char s){
		symbol = s;
	}

	public String toString(){
		return "Tile: "+symbol+" Pos: ("+row+", "+col+") Side: "+side;
	}
}