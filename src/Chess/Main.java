package Chess;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;




public class Main {
	
	static Board board=new Board();
	static Square[][] map=board.getBoard();
	public static void main(String [] args)
	{


		JFrame f=new JFrame("Chess Tutorial");
		

	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    UserInterface ui=new UserInterface();
	    f.add(ui);
	    f.setSize(500, 500);
	    f.setVisible(true);
	    
		board.initialize();
		//board.show();
		//board.move();
		//board.show();

	}
	
	
}
