//Bennett Bierman
//Program description:
//May 16, 2018

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Runner extends JPanel implements KeyListener
{
 private static final long serialVersionUID = 1L;
 public static final int PREF_W = 1000;
 public static final int PREF_H = 600;
 private static final int DELAY = 10;
 private RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 private TileMap map;
 private Player player;
 private BulletManager bullets;
 private EnemyManager enemies;
 private String message1,message2;
 private int counter;

 public Runner() 
 {
  
    addKeyListener(this);
    setFocusable(true);

    map = new TileMap("src/FirstMap", (PREF_H/6)-5);
    player = new Player(100,416,map);
    bullets = new BulletManager(player,map);
    enemies = new EnemyManager(player,map,bullets);
    message2 = "";
    message1 = "Lives: "+player.getLives()+"\nBullets: "+bullets.getCounter();
    counter = 1;

    new Timer(DELAY, new ActionListener() 
    {
       @Override
       public void actionPerformed(ActionEvent e) 
       {
          update();
          repaint();
       }
    }).start();
 }

 @Override
 protected void paintComponent(Graphics g) 
 {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHints(hints);
    g2.setColor(Color.WHITE);
    g2.fillRect(0, 0, PREF_W, PREF_H);
    
    if(gameWin())
    {
    		g2.setColor(Color.GREEN);
    		message2 = "Good Win";
    }
    else if(gameOver())
    {
    	 	g2.setColor(Color.RED);
    	 	message2 = "Game Over";
    }
    g2.drawString(message2, PREF_W/2, PREF_H/2);
    if(!gameOver() && !gameWin())
    {
    		map.draw(g2);
    		player.draw(g2);
    		enemies.draw(g2);
    		bullets.draw(g2);
    		 g2.setColor(Color.RED);
    		g2.drawString(message1, 100, 100);
    }
    
 }
 
 public void update()
 {
	if(!gameOver() && !gameWin())
	{
		player.update();
		bullets.update();
		enemies.update(counter);
		bulletCollides();
		enemyCollides();
		counter++;
		message1 = "Lives: "+player.getLives()+"\nBullets: "+bullets.getCounter();
	}
    
 }
 
 public void bulletCollides()
 {
		 for(int e = enemies.size()-1; e>=0; e--)
			 for(int b=bullets.size()-1; b>=0; b--)
		 {
			 if(bullets.size()>0)
				 if(bullets.getBullet(b).getBounds().intersects(enemies.getEnemy(e).getBounds()))
				 {
					 System.out.println(e);
					 enemies.getEnemy(e).setShot(true);
					 
					 bullets.remove(b);				 
				 }
		 }
 }
 
 public void enemyCollides()
 {
	for(int i=enemies.size()-1; i>=0; i--)
		if(player.getBounds().intersects(enemies.getEnemy(i).getBounds()))
		{
			if(enemies.getEnemy(i).getDx()>0)
				player.setTouchR(true);
			else
				player.setTouchL(true);
			 //enemies.getEnemy(i).setShot(true);
			
		}
 }
 
 public boolean gameOver()
 {
	 if(player.getLives()<1)
		 return true;
	 return false;
 }
 

 public boolean gameWin()
 {
	 return player.gameWin();
 }
 
 
 public void scoreUp()
 {
    
 }
 
 public void reset()
 {
   
 }

 @Override
 public void keyPressed(KeyEvent e)
 {
    int key = e.getKeyCode();
    if(key == KeyEvent.VK_RIGHT)
    {
       player.setRight(true);
       player.setLeft(false);
       player.setMoving(true);
       
    }
       
    if(key == KeyEvent.VK_LEFT)
    {
       player.setLeft(true);
       player.setRight(false);
       player.setMoving(true);
    }
    
    if(key == KeyEvent.VK_SPACE)
       bullets.add();
    if(key == KeyEvent.VK_F)
       player.setJumping(true);
    if(key == KeyEvent.VK_R)
        player.setLeaping(true);
    if(key == KeyEvent.VK_G)
		player.setSlide(true);
    if(key == KeyEvent.VK_UP && player.atDoor())
    		player.setVisible(false);
    
 }

 @Override
 public void keyReleased(KeyEvent e)
 {
    int key = e.getKeyCode();
    if(key == KeyEvent.VK_RIGHT)
       player.setMoving(false);
    if(key == KeyEvent.VK_LEFT)
       player.setMoving(false);
    if(key == KeyEvent.VK_R)
        player.setLeaping(false);
    if(key == KeyEvent.VK_F)
        player.setJumping(false);
    if(key == KeyEvent.VK_UP)
		player.setVisible(true);

    
 }

 @Override
 public void keyTyped(KeyEvent arg0)
 {
    
 }

 private static void createAndShowGUI() 
 {
    Runner gamePanel = new Runner();

    JFrame frame = new JFrame("Rolling Thunder");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(gamePanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
 }

 public Dimension getPreferredSize() 
 {
    return new Dimension(PREF_W, PREF_H);
 }

 public static void main(String[] args) 
 {
    SwingUtilities.invokeLater(new Runnable() {
       public void run() {
          createAndShowGUI();
       }
    });
 }
}