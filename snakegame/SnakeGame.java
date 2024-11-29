package snakegame;

import snakegame.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SnakeGame extends JFrame{
	public SnakeGame(){
		super("Snake Game");//SETTING TITLE
		add(new Board());//ADDING GAME PANEL
		pack();
		setSize(300,300);//SETTING FRAME SIZE
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//CLOSES APPLICATION
        setLocationRelativeTo(null);//CENTRES THE APPLICATION FRAME
		setVisible(true);
	}
	public static void main(String...args){
		SwingUtilities.invokeLater(SnakeGame::new);//INVOKING SNAKEGAME OBJECT IN MAIN METHOD
	}
}