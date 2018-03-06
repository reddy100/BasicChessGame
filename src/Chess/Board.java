package Chess;
  
//LEARN HOW TO CLEAR CONSOLE IN JAVA BEFORE PRINTING THE MOVE
//Add conditions for checking if the user input is allowed ie. if he is moving his own piece
//INCLUDE CHECK MATE CONDITIONS
//MAKE SURE THAT A PIECE DOES NOT GO OVER ANOTHER PIECE.ie. IT DOES NOT PASS THROUGH AN ENEMY OR FRIENDLY PIECE
  
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
 




import java.util.Stack;

import javax.swing.JFrame;
  
/**
 * @author Abishek
 *
 */
public class Board 
{
    public boolean turn=false;          //when this is false it is Player1's turn and when true it is Player2's turn
    public boolean king_in_check=false;
    
    public static boolean game_over=false;
    public static List<Piece> moves = new ArrayList<Piece>();
    
     
    static Random random=new Random();
    public static Square[][] board=new Square[8][8]; 
    static String chessBoard[][]={
        {"r","k","b","q","a","b","k","r"},
        {"p","p","p","p","p","p","p","p"},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {" "," "," "," "," "," "," "," "},
        {"P","P","P","P","P","P","P","P"},
        {"R","K","B","Q","A","B","K","R"}};
      
      
      
    //Board constructor
    /**
     * 
     */
    public Board()
    {
        boolean black_or_white=false;       //if it is false it is black else white
        int rand=random.nextInt(1);     //random number between 0 and 1
        if(rand==1)
        {
            black_or_white=true;
        }
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++)
            {
                board[i][j]=new Square();
                board[i][j].setSquare_color(black_or_white);
                black_or_white=!black_or_white;
            }
            black_or_white=!black_or_white;
        }
    }
    //function which pints the board onto the console
    public static void show()
    {
        for(int i=0;i<board.length;i++)
        {
            for(int j=0;j<board[0].length;j++)
            {
                if(board[i][j].getPiece()!=null)
                {
                    System.out.print(board[i][j].getPiece().getType());
                }
                else
                    System.out.print(" ");
                      
            }
            System.out.println();
        }
    }
    //Getter
    /**
     * @return
     */
    public Square[][] getBoard()
    {
        return board;
    }
    //initializes the board. 
    /**
     * 
     */
   
    public void initialize()
    {
        for(int i=0;i<chessBoard.length;i++)
        {
            for(int j=0;j<chessBoard[0].length;j++)
            {
                if(chessBoard[i][j]!=" ")
                {
                    if(isUpper(chessBoard[i][j]))
                    {
                        Piece pawn=new Piece(true, chessBoard[i][j].charAt(0),i,j);
                        board[pawn.getxCoord()][pawn.getyCoord()].setPiece(pawn);
                    }
                    else
                    {
                        Piece pawn=new Piece(false, chessBoard[i][j].charAt(0),i,j);
                        board[pawn.getxCoord()][pawn.getyCoord()].setPiece(pawn);
                    }
                }
                      
            }
              
        }       
    }
          
    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    //This function checks if the king has been killed and if not it calls the MovePiece function 
    //which inturn makes the actual move. It also updates the chessBoard 
    public static Piece moveThis(int fromX, int fromY, int toX, int toY)
    {
        Piece current_piece=board[fromX][fromY].getPiece();
        Piece temp_piece=movePiece(fromX, fromY, toX, toY);
         
        //Game winning condition
        if(game_over==true)
        {
            Piece kingPiece= new Piece(true, 'W', 0, 0);
            update();
            return kingPiece;
        }  
          
        
        update();
        return temp_piece;
    }
     
    //function to move Piece. Moves the piece with coordinates 
    //(fromX, fromY) to (toX, toY) and returns the Piece with its new location updated
    //FIGURE OUT WHAT TO RETURN IN CASE OF AN ILLEGAL MOVE
    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    public static Piece movePiece(int fromX, int fromY, int toX, int toY)
    {
    	
    	//initialising all the participaion to 0 and emptying out every deadPiece list
    	List<Piece> empty = new ArrayList<Piece>();
        for(int i=0;i<Board.board.length;i++)
    	{
    		for(int j=0;j<Board.board[0].length;j++)
    		{
    			if(Board.board[i][j].getPiece()!=null)
    			{
    				Board.board[i][j].getPiece().setParticipation(0);
    				Board.board[i][j].emptyList();   
    			}
    		}
    	}
    	
    	
        //checks if there is a piece that you want to move
        if(board[fromX][fromY].getPiece()!=null)
        {
            //check if the next coords are within bounds. We don't need to check for this later as we wont allow illegal moves
            if(toX<=7 && toX>=0 && toY<=7 && toY>=0)
            {
                Square current=board[fromX][fromY];
                Square next=board[toX][toY];
  
                Piece temp=board[fromX][fromY].getPiece();
                //if there is already a piece in the spot the piece is going to move to 
                if(next.getPiece()!=null)
                {
                    //if that piece belongs to the opponent then we kill that enemy piece and move there
                    //add line which deletes piece form player's stack of pieces
                    if(next.getPiece().getColor()==!temp.getColor())
                    {
                    	//removing it from original place
                        board[fromX][fromY].setPiece(null);
                        //setting participation and previous values
                        temp.setParticipation(1);
                        temp.setPrevxCoord(fromX);
                        temp.setPrevyCoord(fromY);
                        //Winning condition. If the king has been killed then the game has been won. Returns the original piece
                        if(next.getPiece().getType()=='a'||next.getPiece().getType()=='A')
                        {
                            game_over=true;
                            System.out.println("The KING IS DEAD, LONG LIVE THE KING. Game over");
                            
                        }
                      //setting participation and previous values for killed piece
                        board[toX][toY].getPiece().setParticipation(-1);
                        board[toX][toY].getPiece().setPrevxCoord(toX);
                        board[toX][toY].getPiece().setPrevyCoord(toY);
                        //adding dead pieces to the list
                        board[toX][toY].addToList(board[toX][toY].getPiece());		//CHECK
                        board[toX][toY].killPiece();
                        
                        
                        
                        temp.moveTo(toX, toY);
                        board[toX][toY].setPiece(temp);
                        
                          
                        System.out.println("Killed em");
                        return temp;
                    }
                    //if that piece belongs to us then the current move is not possible
                    else
                    {
                        System.out.println("cannot move there. There is already your piece there");
                        return null;
                    }
                }
                //if the spot is empty we just move the piece there
                else
                {
                    board[fromX][fromY].setPiece(null);
                  //setting participation and previous values
                    temp.setParticipation(1);
                    temp.setPrevxCoord(fromX);
                    temp.setPrevyCoord(fromY);
                    
                    temp.moveTo(toX, toY);
                    board[toX][toY].setPiece(temp);
                    return temp;
                }   
            }
            //If the attempted move is out of bounds then this loop is entered
            else
            {   
                System.out.println("Out of bounds");
                return null;
            }
        }
        else
            System.out.println("there is no piece here");
            return null;
    }
    //converts the booleans we use to denote color into strings 
    /**
     * @param color
     * @return
     */
    public static String convert_color(boolean color)
    {
        if(color==true)
        {
            return "White";
        }
          
        else
        {
            return "Black";
        }
    }
    //converts a char variable to an int
    /**
     * @param a
     * @return
     */
    public static int convertInt(char a)
  
    {
        if(a=='1')
        {
            return 1;
        }
        else if(a=='2')
        {
            return 2;
        }
        else if(a=='3')
        {
            return 3;
        }
        else if(a=='4')
        {
            return 4;
        }
        else if(a=='5')
        {
            return 5;
        }
        else if(a=='6')
        {
            return 6;
        }
        else if(a=='7')
        {
            return 7;
        }
        else
        {
            return 8;
        }
          
    }
      
    /**
     * @param s
     * @return
     */
    //returns true if a string is uppercase and false otherwise
    public static boolean isUpper(String s)
    {
        for(char c : s.toCharArray())
        {
            if(! Character.isUpperCase(c))
                return false;
        }
  
        return true;
    }
     
    /**
     * 
     */
    //this function updates the chessBoard array according to the current state of the board array
    //this is done as the UI class uses the chessBoard array to draw the board onto the applet
    public static void update()
    {
        for(int i=0;i<chessBoard.length;i++)
        {
            for(int j=0;j<chessBoard[0].length;j++)
            {
                if(board[i][j].getPiece()!=null)
                {
                    chessBoard[i][j]=String.valueOf(board[i][j].getPiece().getType());
                }
                else
                    chessBoard[i][j]=" ";
            }
              
        }
    }
     

     

    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    //this function takes the current position of a piece and creates a list of
    //all possible moves that can be taken by that particular piece 
    //The checkList function then takes this list and returns either true or false 
    //depending on whether a move can be made
    public static boolean allowedMove(int fromX,int fromY, int toX, int toY)
    {
    	moves.clear();
    	//if a piece exists in the current position
        if(board[fromX][fromY].getPiece()!=null)
        {
	        Piece currentPiece=board[fromX][fromY].getPiece();
	        int x=currentPiece.getxCoord();
	        int y=currentPiece.getyCoord();
	        //System.out.println("("+x+","+y+")");
	        boolean color=currentPiece.getColor();
	        char type=currentPiece.getType();
	        
	        //Checks if a move is within bounds and then creates a piece and moves it to that spot
	        //if that spot already has a piece then the move is made only if that piece belongs to teh enemy
	        //then this piece is added to the moves list
	        
	        //adds all the valid moves for a pawn piece
	        if(type=='p'||type=='P')
	        {
	            if(type=='p')
	            {   
	                if(x==1)
	                {
	                    if(board[x+1][y].getPiece()==null && board[x+2][y].getPiece()==null)
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x+2, y);
	                        moves.add(newPiece);
	                    }
	                }
	                    if((x+1)<8 && board[x+1][y].getPiece()==null)
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type,x+1, y);
	                        moves.add(newPiece);
	                    }
	                    //Killing moves
	                    if((x+1)<8 && (y-1)>=0 && (board[x+1][y-1].getPiece()!=null && board[x+1][y-1].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type,x+1, y-1);
	                        moves.add(newPiece);
	                    }
	                    if((x+1)<8 && (y+1)<8 && (board[x+1][y+1].getPiece()!=null && board[x+1][y+1].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type,x+1, y+1);
	                        moves.add(newPiece);
	                    }
	                     
	                     
	 
	            }
	            if(type=='P')
	            {   
	                if(x==6)
	                {
	                    if(board[x-1][y].getPiece()==null && board[x-2][y].getPiece()==null)
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-2, y);
	                        moves.add(newPiece);
	                    }
	                }
	                    if((x-1)>=0 && board[x-1][y].getPiece()==null)
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-1, y);
	                        moves.add(newPiece);
	                    }
	                    //Killing moves
	                    if((x-1)>=0 && (y-1)>=0 && (board[x-1][y-1].getPiece()!=null && board[x-1][y-1].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type,x-1, y-1);
	                        moves.add(newPiece);
	                    }
	                    if((x-1)>=0 && (y+1)<8 && (board[x-1][y+1].getPiece()!=null && board[x-1][y+1].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type,x-1, y+1);
	                        moves.add(newPiece);
	                    }
	            }
	        }
	        //The booleans in the next few loops are used to specify if a particular move made a piece either kill an enemy
	        //or didn't happen as a friendly piece was present in that direction. In that case the respective boolean for that
	        //direction is switched to true which then doesn't allow any further movement in that direction
	        
	        //adds all the valid moves for a bishop
	        else if(type=='b'||type=='B')
	        {
	            boolean upLeft=true;
	            boolean upRight=true;
	            boolean downLeft=true;
	            boolean downRight=true;
	            for(int i=1;i<8;i++)
	            {
	                if((x-i)>=0 && (y-i)>=0)
	                {
	                    if(upLeft==true && (board[x-i][y-i].getPiece()==null || board[x-i][y-i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-i, y-i);
	                        moves.add(newPiece);
	                        if(board[x-i][y-i].getPiece()!=null && board[x-i][y-i].getPiece().getColor()==!color)
	                        {
	                        	upLeft=false;
	                        }
	                    }
	                    else
	                        upLeft=false;
	                }
	                if((x-i)>=0 && (y+i)<8)
	                {
	                    if(upRight==true && (board[x-i][y+i].getPiece()==null|| board[x-i][y+i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-i, y+i);
	                        moves.add(newPiece);
	                        if(board[x-i][y+i].getPiece()!=null && board[x-i][y+i].getPiece().getColor()==!color)
	                        {
	                        	upRight=false;
	                        }
	                    }
	                    else
	                        upRight=false;
	                }
	                if((x+i)<8 && (y+i)<8)
	                {
	                    if(downRight==true && (board[x+i][y+i].getPiece()==null|| board[x+i][y+i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x+i, y+i);
	                        moves.add(newPiece);
	                        if(board[x+i][y+i].getPiece()!=null && board[x+i][y+i].getPiece().getColor()==!color)
	                        {
	                        	downRight=false;
	                        }
	                    }
	                    else
	                        downRight=false;
	                }
	                if((x+i)<8 && (y-i)>=0)
	                {
	                    if(downLeft==true && (board[x+i][y-i].getPiece()==null|| board[x+i][y-i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x+i, y-i);
	                        moves.add(newPiece);
	                        if(board[x+i][y-i].getPiece()!=null && board[x+i][y-i].getPiece().getColor()==!color)
	                        {
	                        	downLeft=false;
	                        }
	                    }
	                    else
	                        downLeft=false;
	                }
	            }
	        }
	        //adds all the valid moves for a rook
	        else if(type=='r'||type=='R')
	        {
	            boolean up=true;
	            boolean down=true;
	            boolean left=true;
	            boolean right=true;
	             
	            for(int i=1;i<8;i++)
	            {
	                if((x+i)<8)
	                {
	 
	                    if(down==true && (board[x+i][y].getPiece()==null|| board[x+i][y].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x+i, y);
	                        moves.add(newPiece);
	                        if(board[x+i][y].getPiece()!=null && board[x+i][y].getPiece().getColor()==!color)
	                        {
	                        	down=false;
	                        }
	                    }
	                    else
	                        down=false;
	                }
	                if((x-i)>=0)
	                {
	 
	                    if(up==true && (board[x-i][y].getPiece()==null|| board[x-i][y].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-i, y);
	                        moves.add(newPiece);
	                        if(board[x-i][y].getPiece()!=null && board[x-i][y].getPiece().getColor()==!color)
	                        {
	                        	up=false;
	                        }
	                    }
	                    else
	                        up=false;
	                }
	                if((y+i)<8)
	                {
	 
	                    if(right==true && (board[x][y+i].getPiece()==null|| board[x][y+i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x, y+i);
	                        moves.add(newPiece);
	                        if(board[x][y+i].getPiece()!=null && board[x][y+i].getPiece().getColor()==!color)
	                        {
	                        	right=false;
	                        }
	                    }
	                    else
	                        right=false;
	                }
	                if((y-i)>=0)
	                {
	 
	                    if(left==true && (board[x][y-i].getPiece()==null|| board[x][y-i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x, y-i);
	                        moves.add(newPiece);
	                        if(board[x][y-i].getPiece()!=null && board[x][y-i].getPiece().getColor()==!color)
	                        {
	                        	left=false;
	                        }
	                    }
	                    else
	                        left=false;
	                }
	            }
	        }
	        //adds all the valid moves for a queen
	        else if(type=='q'||type=='Q')
	        {
	            boolean upLeft=true;
	            boolean upRight=true;
	            boolean downLeft=true;
	            boolean downRight=true;
	            for(int i=1;i<8;i++)
	            {
	                if((x-i)>=0 && (y-i)>=0)
	                {
	                    if(upLeft==true && (board[x-i][y-i].getPiece()==null|| board[x-i][y-i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-i, y-i);
	                        moves.add(newPiece);
	                        if(board[x-i][y-i].getPiece()!=null && board[x-i][y-i].getPiece().getColor()==!color)
	                        {
	                        	upLeft=false;
	                        }
	                    }
	                    else
	                        upLeft=false;
	                }
	                if((x-i)>=0 && (y+i)<8)
	                {
	                    if(upRight==true && (board[x-i][y+i].getPiece()==null|| board[x-i][y+i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-i, y+i);
	                        moves.add(newPiece);
	                        if(board[x-i][y+i].getPiece()!=null && board[x-i][y+i].getPiece().getColor()==!color)
	                        {
	                        	upRight=false;
	                        }
	                    }
	                    else
	                        upRight=false;
	                }
	                if((x+i)<8 && (y+i)<8)
	                {
	                    if(downRight==true && (board[x+i][y+i].getPiece()==null|| board[x+i][y+i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x+i, y+i);
	                        moves.add(newPiece);
	                        if(board[x+i][y+i].getPiece()!=null && board[x+i][y+i].getPiece().getColor()==!color)
	                        {
	                        	downRight=false;
	                        }
	                    }
	                    else
	                        downRight=false;
	                }
	                if((x+i)<8 && (y-i)>=0)
	                {
	                    if(downLeft==true && (board[x+i][y-i].getPiece()==null|| board[x+i][y-i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x+i, y-i);
	                        moves.add(newPiece);
	                        if(board[x+i][y-i].getPiece()!=null && board[x+i][y-i].getPiece().getColor()==!color)
	                        {
	                        	downLeft=false;
	                        }
	                    }
	                    else
	                        downLeft=false;
	                }
	            }
	            boolean up=true;
	            boolean down=true;
	            boolean left=true;
	            boolean right=true;
	             
	            for(int i=1;i<8;i++)
	            {
	                if((x+i)<8)
	                {
	 
	                    if(down==true && (board[x+i][y].getPiece()==null|| board[x+i][y].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x+i, y);
	                        moves.add(newPiece);
	                        if(board[x+i][y].getPiece()!=null && board[x+i][y].getPiece().getColor()==!color)
	                        {
	                        	down=false;
	                        }
	                    }
	                    else
	                        down=false;
	                }
	                if((x-i)>=0)
	                {
	 
	                    if(up==true && (board[x-i][y].getPiece()==null|| board[x-i][y].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x-i, y);
	                        moves.add(newPiece);
	                        if(board[x-i][y].getPiece()!=null && board[x-i][y].getPiece().getColor()==!color)
	                        {
	                        	up=false;
	                        }
	                    }
	                    else
	                        up=false;
	                }
	                if((y+i)<8)
	                {
	 
	                    if(right==true && (board[x][y+i].getPiece()==null|| board[x][y+i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x, y+i);
	                        moves.add(newPiece);
	                        if(board[x][y+i].getPiece()!=null && board[x][y+i].getPiece().getColor()==!color)
	                        {
	                        	right=false;
	                        }
	                    }
	                    else
	                        right=false;
	                }
	                if((y-i)>=0)
	                {
	 
	                    if(left==true && (board[x][y-i].getPiece()==null|| board[x][y-i].getPiece().getColor()==!color))
	                    {
	                        Piece newPiece=new Piece(currentPiece.getColor(), type, x, y-i);
	                        moves.add(newPiece);
	                        if(board[x][y-i].getPiece()!=null && board[x][y-i].getPiece().getColor()==!color)
	                        {
	                        	left=false;
	                        }
	                    }
	                    else
	                        left=false;
	                }
	            }
	        }
	        //adds all the valid moves for a king
	        else if(type=='a'||type=='A')
	        {
	            if((x+1)<8 && (board[x+1][y].getPiece()==null|| board[x+1][y].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x+1, y);
	                moves.add(newPiece);
	            }
	            if((x-1)>=0 && (board[x-1][y].getPiece()==null|| board[x-1][y].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x-1, y);
	                moves.add(newPiece);
	            }
	            if((y+1)<8 && (board[x][y+1].getPiece()==null|| board[x][y+1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x, y+1);
	                moves.add(newPiece);
	            }
	            if((y-1)>=0 && (board[x][y-1].getPiece()==null|| board[x][y-1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x, y-1);
	                moves.add(newPiece);
	            }
	            if((x+1)<8 && (y+1)<8 && (board[x+1][y+1].getPiece()==null|| board[x+1][y+1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x+1, y+1);
	                moves.add(newPiece);
	            }
	            if((x+1)<8 && (y-1)>=0 && (board[x+1][y-1].getPiece()==null|| board[x+1][y-1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x+1, y-1);
	                moves.add(newPiece);
	            }
	            if((x-1)>=0 && (y+1)<8  && (board[x-1][y+1].getPiece()==null|| board[x-1][y+1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x-1, y+1);
	                moves.add(newPiece);
	            }
	            if((x-1)>=0 && (y-1)>=0 && (board[x-1][y-1].getPiece()==null|| board[x-1][y-1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x-1, y-1);
	                moves.add(newPiece);
	            }
	        }
	        else if(type=='k'||type=='K')
	        {
	            //Down
	            if((x+2)<8 && (y+1)<8 && (board[x+2][y+1].getPiece()==null|| board[x+2][y+1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x+2, y+1);
	                moves.add(newPiece);
	            }
	            if((x+2)<8 && (y-1)>=0 && (board[x+2][y-1].getPiece()==null|| board[x+2][y-1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x+2, y-1);
	                moves.add(newPiece);
	            }
	            //Up
	            if((x-2)>=0 && (y+1)<8 && (board[x-2][y+1].getPiece()==null|| board[x-2][y+1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x-2, y+1);
	                moves.add(newPiece);
	            }
	            if((x-2)>=0 && (y-1)>=0 && (board[x-2][y-1].getPiece()==null|| board[x-2][y-1].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x-2, y-1);
	                moves.add(newPiece);
	            }
	            //Left
	            if((x+1)<8 && (y+2)<8 && (board[x+1][y+2].getPiece()==null|| board[x+1][y+2].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x+1, y+2);
	                moves.add(newPiece);
	            }
	            if((x-1)>=0 && (y+2)<8 && (board[x-1][y+2].getPiece()==null|| board[x-1][y+2].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x-1, y+2);
	                moves.add(newPiece);
	            }
	            //Right
	            if((x+1)<8 && (y-2)>=0 && (board[x+1][y-2].getPiece()==null|| board[x+1][y-2].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x+1, y-2);
	                moves.add(newPiece);
	            }
	            if((x-1)>=0 && (y-2)>=0 && (board[x-1][y-2].getPiece()==null|| board[x-1][y-2].getPiece().getColor()==!color))
	            {
	                Piece newPiece=new Piece(currentPiece.getColor(), type, x-1, y-2);
	                moves.add(newPiece);
	            }
	        }
	
	        
	        if(toX==-1 && toY==-1)
	        {
	        	return false;
	        }
	        		
	        	return checkList(moves, currentPiece, toX, toY, type);
	        }
        else
        {
            return false;
        }
    }
     
    /**
     * @param list
     * @param currentPiece
     * @param toX
     * @param toY
     * @param type
     * @return
     */
    //This function takes in the list passed on from the allowedMove function and checks if the move 
    //made by the piece exists in that list. If it does it returns true, otherwise it returns false
    public static boolean checkList(List<Piece> list,Piece currentPiece, int toX,int toY,char type)
    {
         
        boolean checker=false;
       // String[] print=new String [64];
        for(int i=0;i<list.size();i++)
        {
            Piece temp=list.get(i);
            
            if(temp!=null)
            {
            	System.out.println(" Current Piece: <"+currentPiece.getxCoord()+","+currentPiece.getyCoord()+">");
            	System.out.println("<"+temp.getxCoord()+","+temp.getyCoord()+">");
            }
            
             
            if(temp.getType()==type && temp.getxCoord()==toX && temp.getyCoord()==toY)
            {
                checker=true;
                 
            }
        }
        System.out.println("###############");
        
        return checker;
    }
    /**
     * @param list
     * @param currentPiece
     * @return
     */
    //This function takes in the list from earlier functions and checks if an enemy king exists in it. If it does
    //it returns true else false
    public static boolean kingInCheck(List<Piece> list,Piece currentPiece)
    {
 	   
 	   boolean check=false;
 	   if(list.size()!=0)
 	   {
 		   for(int i=0;i<list.size();i++)
 		   {
 			   Piece pieceOnBoard=board[list.get(i).getxCoord()][list.get(i).getyCoord()].getPiece();
 			   //if a king exists in the list of possible moves and it belongs to the enemy
 			   if(pieceOnBoard!=null)
 			   {
	 			   if(((pieceOnBoard.getColor()==true && pieceOnBoard.getType()=='A') || (pieceOnBoard.getColor()==false 
	 					   && pieceOnBoard.getType()=='a')) 
	 					   && pieceOnBoard.getColor()==!currentPiece.getColor())
	 			   {
	 				   return true;
	 			   }
 			   }
 		   }
 	   }
 	   else 
 	   {
 		   return false;
 	   }
 	   return check;
    }
    
    /**
     * @param chess
     */
    public static void printBoard(Square[][] chess)
    {
    	for(int i=0;i<chess.length;i++)
    	{
    		for(int j=0;j<chess[0].length;j++)
    		{
    			if(chess[i][j].getPiece()==null)
    				System.out.print(" ,");
    			else
    				System.out.print(chess[i][j].getPiece().getType()+",");
    		}
    		System.out.println();
    	}
    }
    
    public void aiMove()
    {
    	Stack <Piece> compMove = new Stack<Piece>();
    	for(int i=0;i<16-UserInterface.deadBlackPieces.size();i++)
    	{
    		
    	}
    }
}