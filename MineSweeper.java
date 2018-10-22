import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.TimerTask;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Group;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.util.TimerTask;
import javafx.scene.layout.BackgroundImage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.Animation;
import javafx.animation.Transition;

public class MineSweeper extends ArcadeApp{
	//initializes variables
	int flagCount = 10;
	int trueFlagsMarked=0;
	JDialog frame = new JDialog();
	JButton [][] buttons = new JButton[8][8];
	Boolean [][] mineArray = new Boolean[8][8];
	String[][] checkArray = new String[8][8];
	int [][] adjArray = new int[8][8];
	int [][] adjArray2 = new int[8][8];
	int [][] recursiveArray = new int[8][8];
	int [][] countArray = new int[8][8];
	Container container = new Container();
	Random rand = new Random();
	JButton reset;
	JButton reset2;
	JButton reset3;
	int seconds=0;
	int mines =10;
	int highScore = 0;
	String initials = "";
	Timer t;
	/**
	 * Gets the high score
	 * @return int high score
	 */
	public int getHighScore() {
		return highScore;
	}
	/**
	 * Gets the initials of user
	 * @return String initals that user input
	 */
	public String getInitials() {
		return initials;
	}
/**
 * Checks if cell clicked on is a mine. If so, changes it to black
 * @param row row of indicated cell
 * @param col column of indicated cell
 */
	public void checkMine(int row, int col) {
		if(mineArray[row][col] == true) {
			for(int i = 0; i<8; i++) {
				for(int k = 0; k<8; k++) {
					if(mineArray[i][k] == true) {
						buttons[i][k].setBackground(Color.BLACK);	//If cell is a mine, make it black
						t.stop();
					}
				}
			}
			reset2.setText("X(");
			JOptionPane.showMessageDialog(frame, "You revealed a mine :(\nGame Over. Please click OK to exit.");		//game over dialog box	
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		
		}
	}
	/**
	 * Checks if games is complete or not
	 * @return boolean true if game is won
	 */
	public boolean completedGame() {
		if(trueFlagsMarked == mines) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks if the cell indicated is in the bounds of the grid
	 * @param row row of indicated cell
	 * @param col col of indicated cell
	 * @return boolean true if it is in bounds, false otherwise
	 */
	private boolean isInBounds(int row, int col) {
		if(row > mineArray.length -1 || col > mineArray[0].length -1 || row < 0 || col < 0)		//checks bounds of array
			return false;
		else
			return true;
	}
	
	/**
	 * Checks for the number of adjacent mines for indicated cell
	 * @param row row of indicated cell
	 * @param col column of indicated cell
	 * @return int the number of adjacent mines
	 */
	public int checkGrid(int row, int col) {
	
		int numAdjMines = 0;
		if(isInBounds(row + 1,col) && mineArray[row+1][col]==true) 
			numAdjMines++;
		if(isInBounds(row + 1,col+1) && mineArray[row+1][col+1]==true)
			numAdjMines++;
		if(isInBounds(row + 1,col-1) && mineArray[row+1][col-1]==true)
			numAdjMines++;
		if(isInBounds(row - 1,col) && mineArray[row-1][col]==true) 
			numAdjMines++;
		if(isInBounds(row - 1,col-1) && mineArray[row-1][col-1]==true) 
			numAdjMines++;
		if(isInBounds(row - 1,col+1) && mineArray[row-1][col+1]==true) 
			numAdjMines++;
		if(isInBounds(row,col+1) && mineArray[row][col+1]==true) 
			numAdjMines++;
		if(isInBounds(row,col-1) && mineArray[row][col-1]==true) 
			numAdjMines++;

		return numAdjMines;
	}
	
	/**
	 * Recursively calls cells around the indicated cell.
	 * Sets the number of adjacent mines to surrounding cells.
	 * 
	 * @param row row of indicated cell
	 * @param col column of indicated cell
	 */
	public void recursive(int row, int col) {
		if(!isInBounds(row,col)) return;
		
		if(adjArray2[row][col] != 0) {		//Base case
			buttons[row][col].setText("" + adjArray[row][col] + "");
			buttons[row][col].setBackground(Color.GRAY);
			
	
			return;
		} else if(adjArray2[row][col] == 0) {
			buttons[row][col].setBackground(Color.GRAY);
			buttons[row][col].setText("" + adjArray[row][col] + "");
			
			adjArray2[row][col] = -1;	//Sets to -1 so it does not get called again
			
			//Checks 8 squares around cell
			recursive(row+1,col-1);
			recursive(row+1,col);
			recursive(row+1,col+1);
			recursive(row-1,col+1);
			recursive(row-1,col-1);
			recursive(row,col+1);
			recursive(row,col-1);
			recursive(row-1,col);
			return;
		} else {
			return;
		}
	}

	/**
	 * Clears and resets the board. 
	 * Also resets the timer and flag count.
	 */
	public void reset() {
		container.removeAll(); //Clears container to set up a new one
		seconds=0;
		trueFlagsMarked = 0 ;
		flagCount = 10;
		setBoard();
		reset3.setText(""+flagCount+"");
		
	}
	/**
	 * Sets up the board and populates the grid with buttons.
	 * Sets the color of all the buttons.
	 */
	public void setBoard() {
		for(int i = 0; i < 8; i ++) {
			for(int j = 0; j <8; j++) {
				int row = i;
				int column = j;
				buttons[i][j] = new JButton();
				buttons[i][j].setBackground(Color.LIGHT_GRAY); 		//Sets color of initial cells
				buttons[i][j].addMouseListener(new MouseAdapter() {	      
					
					/**
					 * Controls what happens when user clicks the mouse
					 * 
					 * @param e MouseEvent that takes in user input from mouse
					 */
					public void mouseClicked(MouseEvent e) {
						if (e.getButton() == MouseEvent.NOBUTTON) {
							//nothing
						} else if (e.getButton() == MouseEvent.BUTTON1) {
							if(mineArray[row][column]==true) {
								buttons[row][column].setBackground(Color.BLACK);		//Changes to black if its a mine
								checkMine(row,column);
							}
							if(buttons[row][column].getBackground() == Color.LIGHT_GRAY || buttons[row][column].getBackground() == Color.YELLOW) {
								checkMine(row,column);
								if(completedGame()) {
									t.stop();
									JOptionPane.showMessageDialog(frame, "You Win. Please click OK to exit.");
									frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
								}
							
								buttons[row][column].setBackground(Color.GRAY);
								recursive(row,column);		//Starts recursive method once a cell is clicked
								
							}

						} else if (e.getButton() == MouseEvent.BUTTON2) {
							//nothing
						} else if (e.getButton() == MouseEvent.BUTTON3) {
							if(buttons[row][column].getBackground()!=Color.GRAY && buttons[row][column].getBackground()!=Color.BLACK) {
								if(countArray[row][column] == 0) {
									flagCount--;
									reset3.setText("" + flagCount + "");
									buttons[row][column].setBackground(Color.RED);  //Changes cell to red if flagged
									buttons[row][column].setText("F");
									if(mineArray[row][column]== true) {
									
										trueFlagsMarked++;
										
										if(completedGame()) {
											t.stop();
												
												if(seconds > highScore) {
													highScore = seconds;
													
												JOptionPane.showMessageDialog(frame, "High Score! Congratulations:");
												initials = JOptionPane.showInputDialog(frame, "Enter initials here:");
												frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
											
											} else {
												JOptionPane.showMessageDialog(frame, "You Win. Please click OK to exit.");
												
												frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
											}
										
										}
									}

									countArray[row][column] = 1;
								} else if(countArray[row][column] == 1) {
									flagCount++;
									reset3.setText("" + flagCount + "");
									buttons[row][column].setBackground(Color.YELLOW);  //Changes cell to yellow if question marked
									buttons[row][column].setText("?");
									countArray[row][column] = 2;
									if(mineArray[row][column]== true) {
										trueFlagsMarked--;
										System.out.println(trueFlagsMarked);
									}
								} else if(countArray[row][column] == 2) {

									buttons[row][column].setBackground(Color.LIGHT_GRAY);  //reverts cell back to original color
									buttons[row][column].setText("");
									countArray[row][column] = 0;
								} 
							}
						}

					}
					
					/**
					 * Called when user holds down the mouse button
					 * 
					 * @param e MouseEvent
					 */
					public void mousePressed(MouseEvent e) {
						reset2.setText(":O");		//Changes face when click is held
					}
					/**
					 * Called when user released mouse button
					 * 
					 * @param e MouseEvent
					 */
					public void mouseReleased(MouseEvent e) {
						reset2.setText(":)");		//Changes face when click is released
					}
					
				});
				
				//Sets all values in 2d arrays
				container.add(buttons[i][j]);	
				mineArray[i][j] = false;
				checkArray[i][j] = "";
				countArray[i][j] = 0;
				adjArray[i][j] = 0;
				adjArray2[i][j] = 0;
				recursiveArray[i][j] = 0;
			
			}
		}
		int i = 0;
		int minesCount=10;
		while(i < minesCount) {
			int r = rand.nextInt(8);
			int c = rand.nextInt(8);
			if(mineArray[r][c] == false) {
				mineArray[r][c] = true;			//randomly assigns mines
				i++;
			} 
		}
		for(int k = 0; k < 8; k++) {
			for (int j = 0; j<8; j++) {
				adjArray[k][j] = checkGrid(k,j);
				adjArray2[k][j] = checkGrid(k,j);		//Sets every cells to the number of adjacent mines
			}
		}
	}
	
	/**
	 * Constructor that sets up frame for Minesweeper
	 * and adds the grid and buttons
	 */
	public MineSweeper() {
		JPanel format = new JPanel();
		JOptionPane.showMessageDialog(frame, "Welcome to Minesweeper!\nHere is how to play:\nYou win by correctly flagging every mine on the board. The number of correct flags has to match the number of mines exactly.\n"
				+ "There are 10 mines that are randomly placed on the board. To Flag a mine right click the space.\nIf you are unsure of that grid, right click it again to question mark the space.\n"
				+ "Another right click will revert the square back to normal. Left click on the square to reveal the location. If you click on a mine, you lose!\n"
				+ "\nThis game is organized by colors. A red square means that it is flagged. A yellow square means that it is question marked. And a black square means that you have revealed a mine.");
	
		format.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		frame.setSize(400,400);
		frame.setLayout(new BorderLayout());
		reset = new JButton("" + seconds + "");
		reset2 = new JButton(":)");
		reset2.addActionListener(e -> {
			reset();		//resets board
		});
		reset3 = new JButton("" + flagCount + "");
		format.add(reset);
		format.add(reset2);
		format.add(reset3);
		frame.add(format,BorderLayout.NORTH);
		container.setLayout(new GridLayout(8,8));


		setBoard();
		frame.add(container, BorderLayout.CENTER);
		frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		int delay = 1000; //milliseconds
		
		ActionListener taskPerformer = new ActionListener() {
			/**
			 * Timer that increases every second
			 * @param evt event from taskPerformer
			 */
			public void actionPerformed(ActionEvent evt) {
				seconds++;

				reset.setText("" + seconds + "");		//Timer that displays seconds
			}
		};
		
		t = new Timer(delay, taskPerformer);
		t.start();
		
		frame.setVisible(true);			//Makes frame visible.
	}
	

	
}




