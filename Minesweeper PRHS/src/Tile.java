import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Tile extends JButton implements MouseListener {

	public static final int BLANK = 0;
	public static final int BOMB = -1;
	public static final int FLAG = 1;
	public static final int ISSHOWN = -1;
	public static final int NOTABOMB = -2;
	public static final int BOMBCLICKED = -3;
	public static final int SHOWNBOMB = -4;
	public static final int QUESTIONMARK = 2;

	private static ImageIcon imgShownBomb = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Shown Mine.png");
	private static ImageIcon imgBlank = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Blank.png");
	private static ImageIcon imgFlag = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Flagged.png");
	private static ImageIcon imgQuestionMark = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Question Mark.png");
	private static ImageIcon imgUnclicked = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Unclicked.png");
	private static ImageIcon imgNotABomb = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Not A Bomb.png");
	private static ImageIcon imgBombClicked = new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\Mine Clicked.png");
	private static ImageIcon imgNumbers[] = {
		imgBlank,
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\1.png"),
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\2.png"),
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\3.png"),
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\4.png"),
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\5.png"),
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\6.png"),
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\7.png"),
		new ImageIcon("T:\\Java\\Minesweeper 2014\\Image Files\\8.png"),
	};

	private int overValue;

	private int underValue;

	private int row;
	private int column;

	private int over;

	private Grid mineGrid;

	public Tile(int row,int column, Grid mineGrid){
		this.row=row;
		this.column=column;
		//help was used to code mouse listener http://stackoverflow.com/questions/3616761/addmouselistener-or-addactionlistener-or-jbutton
		this.addMouseListener(this);
	}

	public void setNumber(int newUnderValue){
		if (underValue != -1 && !(newUnderValue < -1 || newUnderValue > 8)) {
			this.underValue = newUnderValue;
		}
	}
	private int cycleOver(){

			////////////////////////////
			// Done by Chris    REV 1 //
			// Initialize a variable  //
			// called over at the top //
			////////////////////////////

			if (over <= 1)
				over ++;
			else if (over == 2)
				over = 0;
			return over;
	}
	private void updateCaption() {
			if(getOverValue() == BLANK) {
				this.setIcon(imgUnclicked);//imgUnclicked
			}
			if(this.getOverValue() == ISSHOWN) {
				if(this.getUnderValue() > -1) {
					this.setIcon(imgNumbers[underValue]);
				} else {
					this.setIcon(imgBombClicked);
				}
				this.setBorderPainted(false);
			}
			if(this.getOverValue() == FLAG) {
				this.setIcon(imgFlag);
			}
			if(this.getOverValue() == QUESTIONMARK) {
				this.setIcon(imgQuestionMark);
			}
			if(this.getOverValue() == NOTABOMB) { //For Matt to show bombs after game over
				this.setIcon(imgNotABomb);
			}
			if(this.getOverValue() == Tile.SHOWNBOMB) {
				this.setIcon(imgShownBomb);
			}
			if(this.getOverValue() == BOMBCLICKED) {
				this.setIcon(imgBombClicked);
			}
	}
	public int getRow(){
		return row;
	}
	public int getColumn(){
		return column;
	}
	public boolean isBomb(){
		if (underValue == -1){
		  return true;
		}
		else{
		  return false;
		}
	}
	public void mousePressed(MouseEvent arg0){}

	public void mouseExited(MouseEvent arg0){}

	public void mouseEntered(MouseEvent arg0){}

	public void mouseClicked(MouseEvent arg0){}

	public void mouseReleased(MouseEvent arg0) {
		if (SwingUtilities.isLeftMouseButton(arg0) && overValue != FLAG){
			if (underValue == BOMB){
				System.out.println("isBomb");
				mineGrid.showLoss(row, column);
			}
			else{
				if (underValue != ISSHOWN){
					mineGrid.expansion(row, column);
				}
			}
		}
		else if (SwingUtilities.isRightMouseButton(arg0)){
			cycleOver();
		}
	}

	public void setBomb(){
		underValue = BOMB;
	}

	public void setOver(int over){
		overValue = over;
		updateCaption();
	}

	public int getUnderValue(){
		return this.underValue;
	}

	//MADE BY NATHAN
	public int getOverValue() {
		return this.overValue;
	}
}