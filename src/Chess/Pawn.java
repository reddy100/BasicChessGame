package Chess;

import Chess.Board;

public class Pawn extends Piece {


	public Pawn(boolean color, char type, int xCoord, int yCoord)
	{
		super(color, type, xCoord, yCoord);
	}

	//checks for user input before calling the actual move function
		//ADD OTHER CHOICES HERE
		//ONE ISSUE IS IT DOESN'T PRINT AN ILLEGAL MOVE STATEMENT IF A SIDEWAYS KILL RESULTS IN ENCOUNTERING A FRIENDLY PIECE. TAKE CARE OF THAT LATER
		/**
		 * @param user_input
		 * @param piece
		 * @return
		 */
		public Piece moveThis(String user_input)
		{
			
			Piece temp_piece=new Pawn(this.getColor(), this.getType(), this.getxCoord(), this.getyCoord());
			char user=user_input.charAt(0);
			char user2=' ';
			if(user_input.length()>1)
			{
				user2=user_input.charAt(1);
			}
			int x=this.getxCoord();
			int y=this.getyCoord();
			Piece diagonal_left=null;
			Piece diagonal_right=null;
			//variables holding the pieces(if they exist else null) diagonally 
			//to the left and right of currently moving pawn
			if((x+1)<=7 && (y-1)>=0)
			{
				diagonal_left=Board.board[x+1][y-1].getPiece();
			}
			if((x+1)<=7 && (y+1)<=7)
			{
				diagonal_right=Board.board[x+1][y+1].getPiece();
			}
			Piece moved=null;
			
			if(x==1)
			{
				if(user_input.length()>1 && user=='s' && user2=='2')
				{
					moved = Board.movePiece(this.getxCoord(), this.getyCoord(), this.getxCoord()+2, this.getyCoord());
				}
				else if(user=='s')
				{
					moved = Board.movePiece(this.getxCoord(), this.getyCoord(), this.getxCoord()+1, this.getyCoord());
				}
			}
			
			//regular forward movement
			else if(user=='s')
			{
				moved = Board.movePiece(this.getxCoord(), this.getyCoord(), this.getxCoord()+1, this.getyCoord());
			}
			if(x==6)
			{
				if(user_input.length()>1 && user=='w' && user2=='2')
				{
					moved = Board.movePiece(this.getxCoord(), this.getyCoord(), this.getxCoord()-2, this.getyCoord());
				}
				else if(user=='w')
				{
					moved = Board.movePiece(this.getxCoord(), this.getyCoord(), this.getxCoord()-1, this.getyCoord());
				}
			}
			//Check if the diagonal squares are filled with enemy pieces only then are these movements allowed
			else if(diagonal_left!=null && diagonal_left.getColor()==!this.getColor() && user=='z')
			{
				moved = Board.movePiece(this.getxCoord(), this.getyCoord(), this.getxCoord(), this.getyCoord()-1);
			}
			else if(diagonal_right!=null && diagonal_right.getColor()==!this.getColor() && user=='x')
			{
				moved = Board.movePiece(this.getxCoord(), this.getyCoord(), this.getxCoord()+1, this.getyCoord()+1);
			}
			
			//If the piece returned after calling movePiece is the same as temp_piece then we know the king has been killed
			if(moved!=null && temp_piece.getColor()==moved.getColor() && temp_piece.getType()==moved.getType() && temp_piece.getxCoord()==moved.getxCoord() && temp_piece.getyCoord()==moved.getyCoord())
			{
				System.out.println("dafuq");
				Board.game_over=true;
			}
			return moved;
		}
}