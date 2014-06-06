

import javax.swing.*;

import java.awt.Dimension;
import java.awt.GridLayout;

public class Grid extends JPanel{
	private static final long serialVersionUID = 1L;
	Tile[][] tiles;
	GameFrame mineFrame;
	int numBombs;
	int unclickedTiles;
	private boolean beenClicked;

	/** Made by Nathan Knight
	 * Instantiates the tile array (Tile tiles[][])
	 * Populates array
	 * Adds actionlisteners to buttons
	 * Sets bounds itself (jpanel)
	 * Sets up the gridlayout of itself
	 * */
	public Grid(GameFrame frame) {
		tiles = new Tile[9][9];
		unclickedTiles = 9*9;
		GridLayout g = new GridLayout(9,9);
		setLayout(g);
		final int SIZE = 18;
		setPreferredSize(new Dimension(9 * SIZE, 9 * SIZE));
		for(int x = 0; x < 9; x++) {
			for(int y = 0; y < 9; y++) {
				tiles[x][y] = new Tile(x, y, this);
			}
		}
		numBombs = 10;
		mineFrame = frame;
	}

	/** Made by Nathan Knight
	 * Instantiates the tile array (Tile tiles[][])
	 * Populates array
	 * Adds actionlisteners to buttons
	 * Sets bounds itself (jpanel)
	 * Sets up the gridlayout of itself
	 * */
	public Grid(GameFrame frame, int width, int height, int bombs) {
		tiles = new Tile[width][height];
		unclickedTiles = width * height;
		GridLayout g = new GridLayout(width,height);
		setLayout(g);
		final int SIZE = 17;
		setPreferredSize(new Dimension(height * SIZE, width * SIZE));
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				tiles[x][y] = new Tile(x, y, this);
			}
		}
		numBombs = bombs;
		mineFrame = frame;
	}
	
	public boolean calcWin(){
		return numBombs == unclickedTiles;
	}
	/**
	 * By Leejay
	 * @param firstRow
	 * @param firstColumn
	 */
	public void makeBombs(int firstRow, int firstColumn){
		int numBomb=0;
		int totalBombs=10;
		int counter=0;
		Integer randRow[]=new Integer[totalBombs];
		Integer randColumn[]=new Integer[totalBombs];
		java.util.Random rand=new java.util.Random();
		do{
			randRow[numBomb]=rand.nextInt(9);
			randColumn[numBomb]=rand.nextInt(9);
			while(counter<numBomb){
				if(((randRow[numBomb]==randRow[counter]) && (randColumn[numBomb]==randColumn[counter])) && (numBomb!=counter)){
					System.out.println("Hey, there was a bomb over a bomb. "+randRow[numBomb]+" "+randColumn[numBomb]);
					randRow[numBomb]=rand.nextInt(9);
					randColumn[numBomb]=rand.nextInt(9);
					System.out.println("I moved it to "+ randRow[numBomb]+" "+randColumn[numBomb]);
					counter=0;
				}else{
					counter++;
				}
			}
			counter=0;
			if(!(randRow[numBomb]==firstRow && randColumn[numBomb]==firstColumn)){
				tiles[randRow[numBomb]][randColumn[numBomb]].setBomb();
				incrementBombsTouching(randRow[numBomb], randColumn[numBomb]);
				numBomb++;
			}
		}while(numBomb<totalBombs);
	}

	/**
	 * By Stephen Buttolph
	 * @param row
	 * @param column
	 */
	public void incrementBombsTouching(int row, int column){
		Tile[] neighbors = getNeighbors(row, column);
		for (Tile tile : neighbors){
			if (!tile.isBomb()){
				tile.setNumber(tile.getUnderValue() + 1);
			}
		}
		getTile(row, column).setBomb();
	}
	/**
	 * By Stephen Buttolph
	 * @param row
	 * @param column
	 * @return
	 */
	private Tile getTile(int row, int column){
		return tiles[row][column];
	}
	/**
	 * By Stephen Buttolph
	 * @param inputTile
	 * @return
	 */
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
	/**
	 * By Stephen Buttolph
	 * @param row
	 * @param column
	 */
	private void firstClick(int row, int column){
		
		if (!beenClicked){
			GameFrame.timer.start();
			beenClicked = true;
			makeBombs(row, column);
		}
	}
	/**
	 * By Stephen Buttolph
	 * @param row
	 * @param column
	 * @return
	 */
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
	for(int x=0;x<=8;x++){
		for(int y=0;y<=8;y++){
			if(tiles[x][y].getOverValue()!=-1){
				tiles[x][y].setOver(Tile.FLAG);
			}
			GameFrame.timer.stop();
			tiles[x][y].removeMouseListener(tiles[x][y]);
		}
	}
	JOptionPane.showMessageDialog(GameFrame.mineFrame,"Congratulations! You have completed Minesweeper in " + GameFrame.seconds + " seconds!");
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