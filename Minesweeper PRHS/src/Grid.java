import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.awt.Dimension;
import java.awt.GridLayout;

public class Grid extends JPanel{

 Tile[][] tiles;
 GameFrame mineFrame;
 int numBombs;
 int unclickedTiles;
 private boolean beenClicked;

/* Made by Nathan Knight
 * Instantiates the tile array (Tile tiles[][])
 * Populates array
 * Adds actionlisteners to buttons
 * Sets bounds itself (jpanel)
 * Sets up the gridlayout of itself
 */

 public Grid(GameFrame frame) {
   tiles = new Tile[9][9];
   unclickedTiles = 9*9;
   GridLayout g = new GridLayout(9,9);
   this.setLayout(g);
   int size = 18;
   this.setPreferredSize(new Dimension(9 * size, 9 * size));
   for(int x = 0; x < 9; x++) {
    for(int y = 0; y < 9; y++) {
      tiles[x][y] = new Tile(x, y, this);
    }
   }
   numBombs = 10;
   this.mineFrame = frame;
 }

 	public void showLoss(int clickedRow, int clickedColumn){
	 		for(int row = 0; row < 9; row++){
	 			for(int column = 0; column < 9; column ++){
	 				int underValue = tiles[row][column].getUnderValue();
	 				int overValue = tiles[row][column].getOverValue();
	 				if(underValue == -1 && overValue != 1){
	 					tiles[row][column].setOver(-4);
	 				}
	 				else if(underValue != -1 && overValue == 1){
	 					tiles[row][column].setOver(-2);
	 				}
					tiles[row][column].removeMouseListener(tiles[row][column]);
	 			}
	 		}
	 		tiles[clickedRow][clickedColumn].setOver(-3);
	 		GameFrame.timer.stop();
	}
	public void showWin(){
		for(int x=0;x<=9;x++){
			for(int y=0;y<=9;y++){
				if(tiles[x][y].getOverValue()!=-1){
					tiles[x][y].setOver(Tile.FLAG);
				}
				this.mineFrame.timer.stop();
				this.mineFrame.lblBombs.setText("000");
				tiles[x][y].removeMouseListener(tiles[x][y]);
			}
		}
	}


 public Grid(GameFrame frame, int width, int height, int bombs) {
  tiles = new Tile[width][height];
  unclickedTiles = width * height;
  GridLayout g = new GridLayout(width,height);
  this.setLayout(g);
  for(int x = 0; x < width; x++) {
   for(int y = 0; y < height; y++) {
     tiles[x][y] = new Tile(x, y, this);
   }
  }
  numBombs = bombs;
  this.mineFrame = frame;
 }

 public boolean calcWin(){
  if (numBombs == unclickedTiles)
	  return true;
  else
	return false;
 }
 	public void makeBombs(int firstR, int firstC){
java.util.Random rand=new java.util.Random();
		int x=0;
		while(x<10){
			int randR=rand.nextInt(9);
			int randC=rand.nextInt(9);
			if(!(randR==firstR && randC==firstC) && !(tiles[randR][randC].isBomb())){
				tiles[randR][randC].setBomb();
				x++;
			}
		}
	}
	/* 
	Stephen: this shouldn't be static, right? (incrementBombsTouching)
	*/
	public void incrementBombsTouching(int posRow, int posColumn){
	
		for(int newRow = posRow -1; newRow < tiles.length && newRow < posRow+2; newRow++){
			if (newRow < 0){
				newRow = 0;
			}
			for(int newColumn = posColumn -1; newColumn < tiles[5].length && newColumn < posColumn+2; newColumn++){
				if (newColumn < 0){
					newColumn = 0;
				}
				if(newColumn != posColumn || newRow != posRow){//doesnt increment input tile
					tiles[newRow][newColumn].setNumber(tiles[newRow][newColumn].getUnderValue() +1);
					
				}
			}
		
		}	

	}

	private Tile getTile(int row, int column){
		return tiles[row][column];
	}
	private boolean tileIsBlank(Tile inputTile){
		return inputTile.getUnderValue() == 0;
	}
	/**
	 * By Stephen Buttolph
	 * @param inputTile
	 */
	private void reveal(Tile inputTile){
		inputTile.setOver(Tile.ISSHOWN);
	}
	/**
	 * By Stephen Buttolph
	 * @param inputTile
	 * @return
	 */
	private boolean isRevealed(Tile inputTile){
		return inputTile.getOverValue() == Tile.ISSHOWN;
	}
	/**
	 * By Stephen Buttolph
	 * @param inputTile
	 * @return
	 */
	private boolean isFlagged(Tile inputTile){
		return inputTile.getOverValue() == Tile.FLAG;
	}
	private void firstClick(int row, int column){
		if (!beenClicked){
			beenClicked = true;
			makeBombs(row, column);
		}
	}
	private int calcNumNeighbors(int row, int column){
		int startNum = 8;
		if (row == 0 || row == tiles.length - 1){
			startNum -= 3;
			if (column == 0 || column == tiles[0].length - 1){
				startNum -= 2;
			}
			return startNum;
		}
		else if (column == 0 || column == tiles[0].length - 1){
			startNum -= 3;
		}
		return startNum;
	}
	private Tile[] getNeighbors(int clickedRow, int clickedColumn){
		int places = calcNumNeighbors(clickedRow, clickedColumn);
		int index = 0;
		Tile[] adjacentLocations = new Tile[places];
		for (int changeInRow = clickedRow > 0 ? -1 : 0; changeInRow + clickedRow < tiles.length && changeInRow <= 1; changeInRow++){ //iterates through all adjacent rows
			for (int changeInColumn = clickedColumn > 0 ? -1 : 0; changeInColumn + clickedColumn < tiles[0].length && changeInColumn <= 1; changeInColumn++){ //iterates through all adjacent columns (in the row)
				if (changeInColumn != 0 || changeInRow != 0){ //makes sure it doesn't call on the same tile.
					int newRow = changeInRow + clickedRow; //finds the new position (row)
					int newColumn = changeInColumn + clickedColumn; //finds the new position (column)
					Tile newTile = getTile(newRow, newColumn); //gets the new tile
					adjacentLocations[index] = newTile;
					index++;
				}
			}
		}
		return adjacentLocations;
	}
	/**
	 * By Stephen Buttolph
	 * @param clickedRow
	 * @param clickedColumn
	 */
	public void expansion(int clickedRow, int clickedColumn){
		firstClick(clickedRow, clickedColumn); //places bombs if this is the first click.
		Tile currentTile = getTile(clickedRow, clickedColumn);
		if (!isRevealed(currentTile)){
			reveal(currentTile); //shows the tiles undervalue if it isn't already showed.
			unclickedTiles--;
		}
		if (tileIsBlank(currentTile)){ //tileIsBlank returns true if the undervalue of the tile is 0.
			for (Tile adjacentTile : getNeighbors(clickedRow, clickedColumn)){
				if (!isRevealed(adjacentTile) && !isFlagged(adjacentTile)){ //tile isn't already revealed, and isn't flagged
					expansion(adjacentTile.getRow(), adjacentTile.getColumn()); //recursive call to "expansion"
				}
			}
		}
		if (calcWin()){
			showWin();
		}
	}
}