package Chess;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Abishek
 *
 */
public class Square {
	private int xCoord;				//coordinates of square
	private int yCoord;
	private boolean isEmpty;		//private variable that tells if the square is empty
	private boolean square_color;			//It it true for white and false for black
	private Piece piece;
	private List<Piece> deadPieces = new ArrayList<Piece>();
	
	//Square constructor
	/**
	 * @param xCoord
	 * @param yCoord
	 * @param square_color
	 * @param isEmpty
	 * @param piece
	 */
	public Square(int xCoord, int yCoord, boolean square_color, boolean isEmpty, Piece piece)
	{
		this.xCoord=xCoord;
		this.yCoord=yCoord;
		this.square_color=square_color;
		this.isEmpty=isEmpty;
		this.piece=piece;
	}
	//default constructor
	/**
	 * 
	 */
	public Square()
	{
		this.xCoord=0;
		this.yCoord=0;
		this.square_color=false;
		this.isEmpty=false;
		this.piece=null;
	}
	//copy constructor
	public Square(Square s)
	{
		this.xCoord=s.xCoord;
		this.yCoord=s.yCoord;
		this.square_color=s.square_color;
		this.isEmpty=s.isEmpty;
		this.piece=s.piece;
	}
	
	//Getters and setters
	/**
	 * @param xCoord
	 */
	public void setxCoord(int xCoord) {
		this.xCoord = xCoord;
	}

	/**
	 * @param yCoord
	 */
	public void setyCoord(int yCoord) {
		this.yCoord = yCoord;
	}

	/**
	 * @param isEmpty
	 */
	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	/**
	 * @param square_color
	 */
	public void setSquare_color(boolean square_color) {
		this.square_color = square_color;
	}
	/**
	 * @param piece
	 */
	public void setPiece(Piece piece)
	{
		this.piece=piece;
	}
	/**
	 * @return
	 */
	public boolean getSquare_color()
	{
		return square_color;
	}
	/**
	 * @return
	 */
	public int getxCoord()
	{
		return xCoord;
	}
	/**
	 * @return
	 */
	public int getyCoord()
	{
		return yCoord;
	}
	/**
	 * @return
	 */
	public Piece getPiece()
	{
		return piece;
	}
	
	
	/**
	 * @return
	 */
	public List<Piece> getDeadPieces() {
		return deadPieces;
	}
	/**
	 * @param deadPieces
	 */
	public void setDeadPieces(List<Piece> deadPieces) {
		this.deadPieces = deadPieces;
	}
	
	public void emptyList()
	{
		this.deadPieces.clear();
	}
	
	public void addToList(Piece piece)
	{
		this.deadPieces.add(piece);
	}
	//kills or removes a piece from the current square. This is used for 
	//both killing opponent pieces or when we move a piece from a square to a new square
	/**
	 * 
	 */
	public void killPiece()
	{
		deadPieces.add(this.piece);
		this.piece=null;
		this.isEmpty=false;
	}


}
