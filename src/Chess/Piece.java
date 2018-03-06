package Chess;

/**
 * @author Abishek
 *
 */
public class Piece {

	private boolean color;
	private char type;
	private int xCoord;
	private int yCoord;
	private int prevxCoord;
	private int prevyCoord;
	private int participation;			//if a piece has jsut moved its participation is set to 1, if it has just been killed it is -1 and 0 otherwise
	
	

	//default constructor
	/**
	 * 
	 */
	public Piece()
	{
		this.color=false;
		this.type=' ';
		this.xCoord=0;
		this.yCoord=0;
		this.participation=0;
	}
	
	public Piece(boolean color, char type)
	{
		this.color=color;
		this.type=type;
		this.xCoord=0;
		this.yCoord=0;
	}
	//Piece constructor
	/**
	 * @param color
	 * @param type
	 * @param xCoord
	 * @param yCoord
	 */
	public Piece(boolean color,char type, int xCoord, int yCoord)
	{
		this.color=color;
		this.type=type;
		this.xCoord=xCoord;
		this.yCoord=yCoord;
	}
	//copy constructor
	/**
	 * @param p
	 */
	public Piece(Piece p)
	{
		this.color=p.color;
		this.type=p.type;
		this.xCoord=p.xCoord;
		this.yCoord=p.yCoord;
	}
	
	public Piece moveThis(String user_input)
	{
		System.out.println("Default piece tried to move");
		return null;
	}
	
	//moves a piece to the specified coordinates
		/**
		 * @param x
		 * @param y
		 */
		public void moveTo(int x, int y)
		{
			this.xCoord=x;
			this.yCoord=y;
		}
		
		/**
		 * @param p1
		 * @param p2
		 * @return
		 */
		public boolean is_Equal(Piece p1, Piece p2)
		{
			if(p1.getColor()==p2.getColor() && p1.getType()==p2.getType())
				return true;
			
			else
				return false;
			
		}

	//Setters and Getters
	/**
	 * @return
	 */
	public char getType()
	{
		return this.type;
	}
	/**
	 * @return
	 */
	public boolean getColor() 
	{
		return color;
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
	 * @param type
	 */
	public void setType(char type)
	{
		this.type=type;
	}
	/**
	 * @param color
	 */
	public void setColor(boolean color) {
		this.color=color;
	}
	/**
	 * @param xCoord
	 */
	public void setxCoord(int xCoord) {
		this.xCoord=xCoord;
	}

	/**
	 * @param yCoord
	 */
	public void setyCoord(int yCoord) {
		this.yCoord=yCoord;
	}
	
	/**
	 * @return
	 */
	public int getPrevxCoord() {
		return prevxCoord;
	}

	/**
	 * @param prevxCoord
	 */
	public void setPrevxCoord(int prevxCoord) {
		this.prevxCoord = prevxCoord;
	}

	/**
	 * @return
	 */
	public int getPrevyCoord() {
		return prevyCoord;
	}

	public void setPrevyCoord(int prevyCoord) {
		this.prevyCoord = prevyCoord;
	}

	/**
	 * @return
	 */
	public int getParticipation() {
		return participation;
	}

	/**
	 * @param participation
	 */
	public void setParticipation(int participation) {
		this.participation = participation;
	}
	
}
