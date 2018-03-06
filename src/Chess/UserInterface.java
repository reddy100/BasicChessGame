package Chess;
 
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
 

import java.util.Scanner;

import javax.swing.*;
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
    static int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize=32;
    public List<Piece> deadWhitePieces = new ArrayList<Piece>();
    public static List<Piece> deadBlackPieces = new ArrayList<Piece>();
    public boolean king_in_check=false;
    public Piece [][] History=new Piece[8][8];
    boolean validity=false;
   
    
    
     
     
    public boolean gameOver=false;              //decides if the game is over or not
    boolean turn=true;                          //decides who's turn it is
     
     
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.yellow);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0;i<64;i+=2) {
            g.setColor(new Color(255,200,100));
            g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
            g.setColor(new Color(150,50,30));
            g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
        }
        Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("ChessPieces.png").getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            switch (Board.chessBoard[i/8][i%8]) {
                case "P": j=5; k=0;
                    break;
                case "p": j=5; k=1;
                    break;
                case "R": j=2; k=0;
                    break;
                case "r": j=2; k=1;
                    break;
                case "K": j=4; k=0;
                    break;
                case "k": j=4; k=1;
                    break;
                case "B": j=3; k=0;
                    break;
                case "b": j=3; k=1;
                    break;
                case "Q": j=1; k=0;
                    break;
                case "q": j=1; k=1;
                    break;
                case "A": j=0; k=0;
                    break;
                case "a": j=0; k=1;
                    break;
            }
            if (j!=-1 && k!=-1) {
                g.drawImage(chessPiecesImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*64, k*64, (j+1)*64, (k+1)*64, this);
            }
        }
        
        //Undo button
        g.setColor(Color.black);
        g.drawString("Undo", 350, 100);
        g.fillRect(350, 110, squareSize, squareSize);
        
        
        g.setColor(Color.black);
        g.drawString("Player:"+Board.convert_color(turn)+"'s Turn", 200, 400);
         //if the king is in check this paints the warning sign
        if(king_in_check==true)
        {
        	g.setColor(Color.red);
       	 g.drawString("Player "+Board.convert_color(turn)+", your King is in check. Move him dammit", 100, 350);
        }
        //if the game is over this paints the end message
        if(gameOver==true)
        {
            g.setColor(Color.blue);
            g.drawString("GAME OVER", 400, 400);
        }
    }
     
    @Override
    public void mouseMoved(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) 
    {
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) 
        {
            //if inside the board
            mouseX=e.getX();
            mouseY=e.getY();
            repaint();
            
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) 
    {
    	
        if (e.getX()<8*squareSize &&e.getY()<8*squareSize) 
        {
            //if inside the board
            newMouseX=e.getX();
            newMouseY=e.getY();
            if (e.getButton()==MouseEvent.BUTTON1) 
            {
                Piece piece=null;
                Piece currentPiece=Board.board[mouseY/squareSize][mouseX/squareSize].getPiece();                
                
                    if(Board.board[mouseY/squareSize][mouseX/squareSize].getPiece()!=null && 
                            Board.board[mouseY/squareSize][mouseX/squareSize].getPiece().getColor()==turn)
                    {
                    	
                        //this checks if the move made is valid or not
                        validity=Board.allowedMove(mouseY/squareSize, mouseX/squareSize, newMouseY/squareSize, newMouseX/squareSize);
                        //if a valid move was made then the successive steps are taken
                        if (validity==true) 
                        {
                        	
                            if(Board.board[newMouseY/squareSize][newMouseX/squareSize].getPiece()!=null && 
                                    Board.board[newMouseY/squareSize][newMouseX/squareSize].getPiece().getColor()==!turn)
                            {
                            	//adds any killed pieces to these lists
                                if(turn==true)
                                {
                                    deadBlackPieces.add(Board.board[newMouseY/squareSize][newMouseX/squareSize].getPiece());
                                }
                                else
                                {
                                    deadWhitePieces.add(Board.board[newMouseY/squareSize][newMouseX/squareSize].getPiece());
                                }
                            }
                            
                            //the actual move is made and all data structures are updated here 
                            piece=Board.moveThis(mouseY/squareSize, mouseX/squareSize, newMouseY/squareSize, newMouseX/squareSize);
                            
                        }
                        if(piece!=null)
                        {
                            //Game winning conditions
                            if(piece.getType()=='W')
                            {
                                System.out.println("THE KING IS DEAD");
                                gameOver=true;
                            }
                        }
                        //this is done to update the moves list to check for any checks
                        Board.allowedMove(newMouseY/squareSize, newMouseX/squareSize, -1, -1);
                        king_in_check=Board.kingInCheck(Board.moves, currentPiece);
                        Board.moves.clear();
                        
                        //checks if a valid move was made only then does it switch turns
                        if(validity==true)
                        {
                            turn=!turn;
                            
                        }
 
                    }
                    else
                    {
                        return;
                    }
                    
            }
            repaint();
            
            
            Board.printBoard(Board.board);
            /*
           Scanner reader = new Scanner(System.in);
            String s = reader.next();
            if(s=="undo")
            {
            	gameOver=false;
            	king_in_check=false;
            	if(Board.board[newMouseY/squareSize][newMouseX/squareSize].getPiece()!=null && 
                        Board.board[newMouseY/squareSize][newMouseX/squareSize].getPiece().getColor()==!turn)
                {
                    if(turn==true)
                    {
                        deadBlackPieces.remove(deadBlackPieces.size());
                    }
                    else
                    {
                        deadWhitePieces.remove(deadWhitePieces.size());
                    }
                }
            	turn=!turn;
            	Board.board=History;
            	repaint();
            	
            }
            */
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {
    	if (e.getButton()==MouseEvent.BUTTON1 && validity==true)
    	{
    		int x=e.getX();
    		int y=e.getY();
    		
    		if (x>=350 && x<=350+squareSize && y>=110 && y<=110+squareSize)
    		{
    			//System.out.println("Tried an undo");
    			gameOver=false;
            	king_in_check=false;
            	Piece piece =Board.board[mouseY/squareSize][mouseX/squareSize].getPiece();
            	
            	//the state of the board before the move was made replaces the current state
            	for(int i=0;i<Board.board.length;i++)
            	{
            		for(int j=0;j<Board.board[0].length;j++)
            		{
            			//if this piece has a participation value of 1 ie.it made a move before the undo button was pressed
            			if(Board.board[i][j].getPiece()!=null && Board.board[i][j].getPiece().getParticipation()==1)
            			{
            				//this piece is moved back to its original position before the move
            				Piece movedPiece=Board.board[i][j].getPiece();
            				Board.board[movedPiece.getxCoord()][movedPiece.getyCoord()].setPiece(null);
            				movedPiece.moveTo(movedPiece.getPrevxCoord(), movedPiece.getPrevyCoord());
            				Board.board[movedPiece.getPrevxCoord()][movedPiece.getPrevyCoord()].setPiece(movedPiece);
            				
            				
            			}
            			//if the deadPieces list is not empty ie.a piece was killed in this spot 
            			if(Board.board[i][j].getDeadPieces().isEmpty()==false)
            			{
            				//the dead piece is popped from the list and placed in the position it was before it was killed
            				Piece killedPiece=Board.board[i][j].getDeadPieces().get(Board.board[i][j].getDeadPieces().size()-1);
            				for(int k=0;k<Board.board[i][j].getDeadPieces().size();k++)
            				{
            					if(Board.board[i][j].getDeadPieces().get(k).getParticipation()==-1)
            					{
            						killedPiece=Board.board[i][j].getDeadPieces().get(k);
            						//System.out.println("killed:"+killedPiece.getxCoord()+","+killedPiece.getyCoord());
            					}
            				}
            				Piece moveKillerPiece=Board.board[killedPiece.getPrevxCoord()][killedPiece.getPrevyCoord()].getPiece();
            				
            				killedPiece.moveTo(killedPiece.getPrevxCoord(), killedPiece.getPrevyCoord());
            				Board.board[killedPiece.getPrevxCoord()][killedPiece.getPrevyCoord()].setPiece(killedPiece);
            				
            				
            				if(Board.board[i][j]==null)
            				{
            					moveKillerPiece.moveTo(i, j);
            					Board.board[i][j].setPiece(moveKillerPiece);
            				}
            				
            			}
            			
            		}
            	}
            	
            	//the board is updated to the state before the move was made
            	Board.update();
            	//the turn is reset so that the player who pressed undo gets his/her turn again
            	if(piece!=null && piece.getColor()==true)
            	{
            		turn=true;
            	}
            	else if(piece!=null && piece.getColor()==false)
            		turn=false;
            	
            	repaint();
    		}
    	}
    }
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) 
    { 
    	//System.out.print("exit");	
    }
    
     
     
}