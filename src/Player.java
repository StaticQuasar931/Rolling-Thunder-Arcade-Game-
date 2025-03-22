import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


//Bennett Bierman
//Program description:
//May 14, 2018
public class Player extends Character
{
   private int height;
   private TileMap map;
   private boolean yMove;
   private Animation animation;
   private boolean visible;
   private BufferedImage standing;
   private int lives;
   private boolean touchR, touchL;
   public final int TOUCHSTART = 10;
   public final int BOTTOM_Y = -1025; //the Y when I am at the bottom of the tileMap
   
   public Player(int x, int y, TileMap map)
   {
      super(x,y,map);
      setRight(true);
      height = getHeight();
      lives = 10;
      this.map = map;
      yMove = true;
      visible = true;
      touchR = false;
      touchL = false;
      try
      {
    	  	standing = ImageIO.read(new File("src/StandingRight.png"));
      
      } 	
      catch (IOException e1) 
      {
		e1.printStackTrace();
      } 
      animation = new Animation();
   }
   
   @Override
   public void update()
   {  
	   double hereX = getX(); //Use to setX when an object is in the player's way
	   
	   if(visible)
	   {
	   
      if(canFall() || getSlide()==true)
    	  	setFalling(true);
      else
      {
    	  	setDy(0);
    	  	setFalling(false);
      }
      
	   if(isJumping() && getDy()==0)
      { 
         setFalling(true);
         setDy(getJUMPSTART());
         setJumping(false);
      }
	   
	   if(isLeaping() && getDy()==0 && isLeapable())  //A jump is a small jump, a leap is to get to the other story
	   {
		   setFalling(true);
	       setDy(getLEAPSTART());
	       setLeaping(false);
	   }
	   
	   
	   if(onGround())
		   setSlide(false);
	   
      if(isFalling())
         setDy(getDy()+getGRAVITY());
      
      if(isMoving() && canLeftRight())
      {
         if(isRight())
            setDx(getDx()+1);
         else
            setDx(getDx()-1);
      }
      else
      {
    	  	setDx(0);
    	  	setX(hereX);
      }
      
      if(getDx()>getMAX_X())
         setDx(getMAX_X());
      
      if(getDx()<-getMAX_X())
        setDx(-getMAX_X());
      
      if(touchR)  //touchR and touchL are when an enemy touches you
      {
    	  	  setDx(TOUCHSTART);
		  lives--;
		  touchR = false;
      }
      
      if(touchL)
      {
    	  	  setDx(-TOUCHSTART);
		  lives--;
		  touchL = false;
      }
      
      if(canLeftRight())
    	  	setX(getX()+getDx());
      setY(getY()+getDy()); 
      
      jumpStillMove();
      
      map.setx((int)(Runner.PREF_W/2-getX()));
      if(yMove)
    	  	map.sety((int)(Runner.PREF_H-(getY()+getHeight()+10)));
      else
    	  	map.sety(BOTTOM_Y);
	   }
	   
   }
    	  	
   
   

   public void draw(Graphics2D g2)
   {
	   int tx = map.getx();
       int ty = map.gety();
     
      if(visible)
      {
    	  	if(isRight())
    	  		g2.drawImage(standing, (int)getX()+tx,  (int)getY()+ty, getWidth(), getHeight(), null);
    	  	else
    	  		g2.drawImage(standing, (int)getX()+tx+getWidth(),  (int)getY()+ty, -getWidth(), getHeight(), null);
      }
      
    	  
   } 
   
   public boolean atDoor()
   {
	   if(map.getTile(map.getColTile((int)getY()+getHeight()/2),map.getRowTile((int)getX()+getWidth()/2)) == 4 ||
		  map.getTile(map.getColTile((int)getY()+getHeight()/2),map.getRowTile((int)getX()+getWidth()/2)) == 5||
		  map.getTile(map.getColTile((int)getY()+getHeight()/2),map.getRowTile((int)getX()+getWidth()/2)) == 7)
		   return true;
	   return false;
	   
   }
   
   
   public boolean gameWin()
   {
	   if(map.getTile(map.getColTile((int)getY()+getHeight()/2),map.getRowTile((int)getX()+getWidth()/2)) == 30)
		   return true;
	   return false;
   }
   
   public boolean getTouchR()
   {
	   return touchR;
   }
   
   public void setTouchR(boolean b)
   {
	   touchR = b;
   }
   
   public boolean getTouchL()
   {
	   return touchL;
   }
   
   public void setTouchL(boolean b)
   {
	   touchL = b;
   }
   
  
   public void jumpStillMove()
   {
	  if((map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()+getWidth()))==20))
		   yMove = false;	    
   }
   
   public boolean getVisible()
   {
	   return visible;
   }
   
   public void setVisible(boolean b)
   {
	   visible = b;
   }
   
   public int getLives()
	{
		return lives;
	}

    
   
}
