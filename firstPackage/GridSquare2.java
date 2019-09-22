package firstPackage;

import java.awt.Color;
import javax.swing.*;

/*
 *  a simple extension of JComponent which allows the background colour to be set and switched back and forth with ease
 *  
 *  there are other ways of doing this, but it's a neat way to demonstrate how to create your own gui components
 *  (as well as how to use ternary operators)
 */
public class GridSquare2 extends JPanel
{
private int xcoord, ycoord;			// not used in this demo, but might be helpful in future...
private int legal;

	// constructor takes the x and y coordinates of this square
	public GridSquare2( int xcoord, int ycoord)
	{
		super();
		this.setSize(50,50);
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		this.legal = 0;
	}
	
	// if the decider is even, it chooses black, otherwise white (for 'column+row' will allow a chequerboard effect)
	public void setColor( int decider)
	{
		if (decider == 0) {
			this.setBackground( Color.black);
		} else if (decider == 1) {
			this.setBackground( Color.white);
		} else if (decider == 3) {
			this.setBackground( Color.pink);
		}
	}
	
	// if the square is black it becomes white, and vice-versa
	public void switchColor()
	{
		Color colour = (getBackground() == Color.black) ? Color.white: Color.black;
		this.setBackground( colour);
	}
	
	public int getXcoord() {
		return this.xcoord;
	}
	
	public int getYcoord() {
		return this.ycoord;
	}
	
	public int getLegal() {
		return this.legal;
	}
	
	public void setLegal(int x) {
		this.legal = x;
	}
}
