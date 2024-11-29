package snakegame;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Board extends JPanel implements ActionListener{
	
	private boolean inGame = true;
	
	public Image red;//snake head
	public Image green;//snake body parts
	public Image frog;//prey Image
	
	private Timer timer;//Timer for delaying paint Updation 
	private int dots=3;
	
	private boolean left=false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
	
	private int frog_x;
	private int frog_y;
	
	private final int ALL_DOTS=900;//MAX SIZE OF SNAKE
	private final int DOT_SIZE=10;//SIZE OF DOT
	
	//X and Y coordinates snake dots(ie. treating dots as single entity)
	private int x[]=new int[ALL_DOTS];private int y[]=new int[ALL_DOTS];
	//Initialize timer
	{
		timer=new Timer(140,this);//(delay,Listener)
	}
	public Board(){
		addKeyListener(new KeystrokeAdapter());
		setBackground(Color.black);
		setFocusable(true);
		loadImages();
		initgame();
	}
	
	public void initgame(){
		dots=3;inGame=true;
		for(int i=0;i<dots;i++){
			y[i]=50;
			x[i]=50-i*DOT_SIZE;
		}
		randomPrey();
		//timer=new Timer(140,this);//(delay,Listener)
		timer.restart();
		left=false;right=true;up=false;down=false;//seting movements
	}
	
	public void randomPrey(){
		//Prey position generated in 270x270 grid space
		
		int r=(int)(Math.random()*270);
		frog_x=r;
		r=(int)(Math.random()*270);//updating random generation..
		frog_y=r;
	}
	public void loadImages(){
		ImageIcon i1=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/red.png"));
		ImageIcon i2=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/green.png"));
		ImageIcon i3=new ImageIcon(ClassLoader.getSystemResource("snakegame/icons/frog.png"));//prey is frog
		red=i1.getImage();green=i2.getImage();frog=i3.getImage();
		
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		draw(g);
	}
	
	public void gameOver(Graphics g) {
        String msg = "Game Over!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.RED);//setting color for gameOver banner
        g.setFont(font);
        g.drawString(msg,(300 - metrices.stringWidth(msg)) / 2, 300/2);
    }
	
	public void scoreBannner(Graphics g) {
        String msg = "Score: "+(dots-3);
        Font font = new Font("SAN_SERIF", Font.BOLD, 15);
        FontMetrics metrices = getFontMetrics(font);
        
        g.setColor(Color.GREEN);//setting color for gameOver banner
        g.setFont(font);
        g.drawString(msg,200,20);
    }
	
	
	public void draw(Graphics g){
		if(inGame){
		/*g.setColor(Color.GREEN);//setting color of score banner
		g.drawString("Score: "+(dots-3),200,10);*/
		g.drawImage(frog,frog_x,frog_y,this);scoreBannner(g);
		for(int i=0;i<dots;i++){
			if(i==0){
				g.drawImage(red,x[i],y[i],this);
			}else{
				g.drawImage(green,x[i],y[i],this);
			}
		}
		//Toolkit.getDefaultToolkit().sync();//needed for awt not for swing
	
		}else{
			gameOver(g);
			timer.stop();//timer is stopped
		}
	}
	
	public void checkPrey(){
		
        if ((x[0] >= frog_x - 10) && (x[0] <= frog_x + 10) && 
        (y[0] >= frog_y - 10) && (y[0] <= frog_y + 10)){
            dots++;   //Snake grows when prey gets eaten
            randomPrey();//generation of new prey
        }
    }
	
	public void move() {
        for (int i = dots ; i > 0 ; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        
        if (left) {
            x[0] = x[0] - DOT_SIZE;
        }
        if (right) {
            x[0] = x[0] + DOT_SIZE;
        }
        if (up) {
            y[0] = y[0] - DOT_SIZE;
        }
        if (down) {
            y[0] = y[0] + DOT_SIZE;
        }
    }
	
	public void checkCollision(){
			//Self-collision	
		for(int i = dots; i > 0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])){
                inGame = false;
            }
        }
		//Boundary colllision
		if (y[0] >= 300) {
            inGame=false;
        }
        if (x[0]>=300) {
            inGame=false;
        }
        if (y[0]<0) {
            inGame=false;
        }
        if (x[0]<0) {
            inGame=false;
        }
	}
	@Override
	public void actionPerformed(ActionEvent e){
		if(inGame){
			move();
			checkPrey();
			checkCollision();
		}
		repaint();
	}
	//KeyAdapter innerclass for controls
	class KeystrokeAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			int key_pressed=e.getKeyCode();
			
			if(key_pressed==KeyEvent.VK_LEFT && !right){
				left=true;right=false;up=false;down=false;
			}
			if(key_pressed==KeyEvent.VK_RIGHT && !left){
				left=false;right=true;up=false;down=false;
			}
			if(key_pressed==KeyEvent.VK_UP && !down){
				left=false;right=false;up=true;down=false;
			}
			if(key_pressed==KeyEvent.VK_DOWN && !up){
				left=false;right=false;up=false;down=true;
			}
			
			if(key_pressed==KeyEvent.VK_ENTER && !inGame){// PRESS ENTER TO RESTART GAME
				initgame();//GOES TO INITIAL PHASE
				repaint();//UPDATING PAINTED COMPONENTS
			}
		}
	}
}
