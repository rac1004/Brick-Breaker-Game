package brickBreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javafx.scene.text.Font;

import javax.swing.Timer;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener,ActionListener {

	Random rand=new Random();
	int randNumber=rand.nextInt(600-1)+1;
	private boolean play=false;
	private int score=0;
	private int totalBriks=21;
	private Timer timer;
	private int delay=5;
	private int playerX= randNumber;
	private int ballposX= 40+randNumber;
	private int ballposY=525;
	private int ballXdir=-1;
	private int ballYdir=-2;
	private int level=1;
	
	private MapGenerator map;
	
	public Gameplay() {
		// TODO Auto-generated constructor stub
		map=new MapGenerator(3, 7);
		
		
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer=new Timer(delay,this);
		timer.start();
	}
	public void paint(Graphics g){
		//background
		
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//drawing mP
		map.draw((Graphics2D)g);
		//border
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//scores
		g.setColor(Color.white);
		g.setFont(getFont());
		g.drawString("Level:"+level, 90, 30);
		
		g.drawString(""+score, 590, 30);
		
		
		
		//padle
		g.setColor(Color.green);
		g.fillRect(playerX,550 ,100, 8);
		
		//the ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX,ballposY ,20, 20);
		
		if(totalBriks<=0){
			
			
			
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.RED);
			g.setFont(getFont());
			g.drawString("You Won ", 190, 300);
			
			g.setFont(getFont());
			
			
			g.drawString("Press Enter to Restart : ", 230, 350);
		}
		
		if(ballposY>570){
			
			play=false;
			ballXdir=0;
			ballYdir=0;
			g.setColor(Color.RED);
			g.setFont(getFont());
			g.drawString("Game Over, Score : "+score, 190, 300);
			
			g.setFont(getFont());
			g.drawString("Press Enter to Restart : ", 230, 350);
			
		}
		
		
		g.dispose();
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		timer.start();
		
		
		if(play){
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
				ballYdir=-ballYdir;
			}
			
			A: for(int i=0;i<map.map.length;i++){
				for(int j=0;j<map.map[0].length;j++){
					if(map.map[i][j]>0){
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickWidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
						Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						
						if(ballRect.intersects(brickRect)){
							map.setBrickValue(0, i, j);
							totalBriks--;
							score+=5;
							
							if(ballposX+19<=brickRect.x || ballposX+1>=brickRect.x+brickRect.width){
								ballXdir=-ballXdir;
							}else{
								ballYdir=-ballYdir;
							}
							
							break A;
						}
						
						
						
					}
				}
			}
			
			
			
			
			ballposX+=ballXdir;
			ballposY+=ballYdir;
			if(ballposX<0){
				ballXdir=-ballXdir;
			}
			if(ballposY<0){
				ballYdir=-ballYdir;
			}
			if(ballposX>670){
				ballXdir=-ballXdir;
			}
		}
		
		
		repaint();
		
	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(playerX>=600){
				playerX=600;
			}
			else
			{
				moveRight();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			if(playerX<10){
				playerX=10;
			}
			else
			{
				moveLeft();
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			
			if(!play){
				if(totalBriks<=0){
					level++;
					
					if(level>=8)
						level=1;
					
				}
				
				if(ballposY>570){
					level--;
					if(level<=1)
						level=1;
								}
				 randNumber=rand.nextInt(600-1)+1;
				play=true;
				playerX= randNumber;
				ballposX= 40+randNumber;
				ballposY=525;
				
				ballXdir=-1;
				ballYdir=-2;
				score=0;
				totalBriks=21;
				map=new MapGenerator(3,7);
				
				repaint();
			}
		}
	}
	
	
	private void moveLeft() {
		play=true;
		playerX-=20;
		
		
	}
	private void moveRight() {
		play=true;
		playerX+=20;
		
	}

	
	
}
