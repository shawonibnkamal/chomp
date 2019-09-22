package firstPackage;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/*
 *  the main window of the gui
 *  notice that it extends JFrame - so we can add our own components
 *  notice that it implements ActionListener - so we can handle user input
 */
public class WindowDemo extends JFrame implements ActionListener, MouseListener
{
	// gui components that are contained in this frame:
	private JPanel topPanel, bottomPanel, infoPanel;	// top and bottom panels in the main window
	private JLabel topLabel, info;				// a text label to appear in the top panel
	private JButton topButton;				// a 'reset' button to appear in the top panel
	private GridSquare2 [][] gridSquares;	// squares to appear in grid formation in the bottom panel
	private int x,y;						// the size of the grid
	private ArrayList<int[]> legalCoordinates = new ArrayList<>();
	private int[] tempCoor = new int[10];
	
	/*
	 *  constructor method takes as input how many rows and columns of gridsquares to create
	 *  it then creates the panels, their subcomponents and puts them all together in the main frame
	 *  it makes sure that action listeners are added to selectable items
	 *  it makes sure that the gui will be visible
	 */
	public WindowDemo(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.setSize(600,600);
		
		// first create the panels
		topPanel = new JPanel();
		topPanel.setLayout( new FlowLayout());
		
		bottomPanel = new JPanel();
		bottomPanel.setLayout( new GridLayout( x, y, 2, 2));
		bottomPanel.setSize(500,500);
		bottomPanel.setBackground( Color.white );
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout());
		// then create the components for each panel and add them to it
		
		// for the top panel:
		topLabel = new JLabel("Click the Buttons!");
		topButton = new JButton("Reset");
		topButton.addActionListener( this);			// IMPORTANT! Without this, clicking the button does nothing.
		
		topPanel.add( topLabel);
		topPanel.add ( topButton);
		
		info = new JLabel("Your turn!");
		infoPanel.add(info);
	
		// for the bottom panel:	
		// create the buttons and add them to the grid
		gridSquares = new GridSquare2 [x][y];
		for ( int column = 0; column < x; column ++)
		{
			for ( int row = 0; row < y; row ++)
			{
				tempCoor[0] = column;
				tempCoor[1] = row;
				legalCoordinates.add(tempCoor);
				gridSquares [column][row] = new GridSquare2( column,row);
				gridSquares [column][row].setSize( 20, 20);
				gridSquares [column][row].setColor(0);
				
				gridSquares [column][row].addMouseListener( this);		// AGAIN, don't forget this line!
				
				bottomPanel.add( gridSquares [column][row]);
				
			}
		}
		gridSquares [0][0].setColor(3);
		gridSquares [0][0].setLegal(2);
		
		// now add the top and bottom panels to the main frame
		getContentPane().setLayout( new BorderLayout());
		getContentPane().add( topPanel, BorderLayout.NORTH);
		getContentPane().add( bottomPanel, BorderLayout.CENTER);		// needs to be center or will draw too small
		getContentPane().add( infoPanel, BorderLayout.PAGE_END);
		// housekeeping : behaviour
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		setResizable( false);
		setVisible( true);
	}
	
	
	/*
	 *  handles actions performed in the gui
	 *  this method must be present to correctly implement the ActionListener interface
	 */
	public void actionPerformed (ActionEvent aevt)
	{
		// get the object that was selected in the gui
		Object selected = aevt.getSource();
				
		// if resetting the squares' colours is requested then do so
		if ( selected.equals( topButton) )
		{
			for ( int column = 0; column < x; column ++)
			{
				for ( int row = 0; row < y; row ++)
				{
					gridSquares [column][row].setColor(0);
					gridSquares [column][row].setLegal(0);
				}
			}
			gridSquares [0][0].setColor(3);
			gridSquares [0][0].setLegal(2);
		}
	}


	// Mouse Listener events
	public void mouseClicked(MouseEvent mevt)
	{
		// get the object that was selected in the gui
		Object selected = mevt.getSource();
		
		
		//System.out.println(legalMove(xcoord, ycoord));
		/*
		 * I'm using instanceof here so that I can easily cover the selection of any of the gridsquares
		 * with just one piece of code.
		 * In a real system you'll probably have one piece of action code per selectable item.
		 * Later in the course we'll see that the Command Holder pattern is a much smarter way to handle actions.
		 */
		
		// if a gridsquare is selected then switch its color
		if ( selected instanceof GridSquare2)
		{	
			GridSquare2 select = (GridSquare2) selected;
			int xcoord = select.getXcoord();
			int ycoord = select.getYcoord();
			if (select.getLegal() == 0) {
				afterClicked(xcoord, ycoord);
				info.setText("Computer Move!");
				Timer timer;
				ActionListener action = new ActionListener() {

			        @Override
			        public void actionPerformed(ActionEvent mevt) {
			        	computerMove();
			        	info.setText("Your Move!");
			        }
			      };      
			       timer = new Timer(2000, action);
			       timer.start();
			       timer.setRepeats(false);
			       
			} else if (select.getLegal() == 2) {
				JOptionPane.showMessageDialog(null, "You lose!", "Chomp Game", JOptionPane.INFORMATION_MESSAGE);
			} else {
				info.setText("Illegal Move!"); 
				Timer timer;
				ActionListener action = new ActionListener() {

			        @Override
			        public void actionPerformed(ActionEvent mevt) {
			        	info.setText("Your turn"); 
			        }
			      };      
			       timer = new Timer(1000, action);
			       timer.start();
			       timer.setRepeats(false);
			}
			
		}	
	}
	
	public void afterClicked(int xcoord, int ycoord) {
		for ( int column = xcoord; column < x; column ++)
		{
			for ( int row = ycoord; row < y; row ++)
			{
				gridSquares [column][row].setColor(1);
				gridSquares [column][row].setLegal(1);
			}
		}
	}
	
	public void computerMove() {
		if (gridSquares [1][0].getLegal() == 1 && gridSquares [0][1].getLegal() == 1) {
			JOptionPane.showMessageDialog(null, "You win!", "Chomp Game", JOptionPane.INFORMATION_MESSAGE);
		} else {
			Random randx = new Random();
			int x = randx.nextInt(this.x);
			int y = randx.nextInt(this.y);
			while (gridSquares [x][y].getLegal() == 1 || gridSquares [x][y].getLegal() == 2) {
				x = randx.nextInt(this.x);
				y = randx.nextInt(this.y);
			}
			afterClicked(x, y);
		}
	}
	
	// not used but must be present to fulfil MouseListener contract
	public void mouseEntered(MouseEvent arg0){}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
}
