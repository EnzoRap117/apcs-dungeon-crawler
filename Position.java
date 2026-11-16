public class Position{

	private int row;
	private int col;

	public Position(){
		col = 0;
		row = 0;
	}

	public Position(int r, int c){
		row = r;
		col = c;
	}

	public int getRow(){
		return row;
	}

	public int getCol(){
		return col;
	}

	public void setRow(int r){
		row = r;
	}

	public void setCol(int c){
		col = c;
	}

	public void setPosition(int r, int c){
		row = r;
		col = c;
	}

	public String toString(){
		return "Pos: ("+row+", "+col+")";
	}


}