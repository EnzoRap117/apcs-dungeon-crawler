public class BossRoom extends Room{

	private Tile[][] room2d;

	public BossRoom(){
		super(9, 5, 1, true);
		super.addTile(new RatKing(1, 2, 4), 2, 4);
	}

}