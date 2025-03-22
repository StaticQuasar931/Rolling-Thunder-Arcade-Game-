import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

//Bennett Bierman
//Program description:
//May 17, 2018
public abstract class Character //Parent class to Player and Enemy
{
   private double x, y, dx, dy;
   private int width, height;
   private boolean jumping,leaping, falling, moving,left, right;
   public final double MAX_X = 5; 
   public final double GRAVITY = 0.2;
   public final double JUMPSTART = -8;
   public final double LEAPSTART = -11;
   private boolean slide;
   public TileMap map;
     
   public Character(int x, int y, TileMap map)
   {
      this.x = x;
      this.y = y;
      width = 60;
      height = 160;
      dx = 0;
      dy = 0;
      slide = false;
      moving = false;
      jumping = false;
      falling = false;
      leaping = false;
      this.map = map;
      
   }
   
   public abstract void update();
   
   public abstract void draw(Graphics2D g2);
   
   public boolean canFall()  //Checks to see if Character can fall through certain types of tiles
   {	   
	    if(((map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2)) == 1 ) || 
	    		(map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2))==2 &&
	    		(map.getTile(map.getColTile((int)getY()+getHeight()-getHeight()/10),map.getRowTile((int)getX()+getWidth()/2))==3 || 
	    		 map.getTile(map.getColTile((int)getY()+getHeight()-getHeight()/10),map.getRowTile((int)getX()+getWidth()/2))==7 ||
	    		 map.getTile(map.getColTile((int)getY()+getHeight()-getHeight()/10),map.getRowTile((int)getX()+getWidth()/2))==5))||
	    		map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2)) == 9 ||
	    		map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2)) == 10 ||
	    		map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2)) == 13 ||
	    		map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2)) == 15||
	    		map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2)) == 17 ||
	    		map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2))==11) 
	    		&& getDy()>=0 )
	    		return false;
	    return true;
   }	   
   
   public boolean onGround()
   {
	   if(map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2)) == 1 ||
	     map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2))==10 ||
	     map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()+getWidth()/2))==13)
		   return true;
	   return false;
   }
   
   public boolean canLeftRight() //Checks to see if Character can move horizontally through tiles
   {
	  if(getDx()>0)
	  {
			if((map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()+getWidth()))==10 ||
		    map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()+getWidth()))==12 ||
		    map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()+getWidth()))==13||
		     map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()+getWidth()))==15||
					   map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()+getWidth()))==17 ||
					   map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()+getWidth()))==11))
				   return false;
		   }
		   else if(getDx()<0)
		   {
			   if((map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()))==10 ||
					   map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()))==12||
					   map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()))==13||
					   map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()))==15||
					   map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()))==17 ||
					   map.getTile(map.getColTile((int)getY()+getHeight()-30),map.getRowTile((int)getX()))==11))
				   return false;
		   }
	   return true;
   }
   
   public boolean isLeapable() //Checks to see if the second story is above the Character
   {
	   boolean above = false;
	   boolean notStanding = true;
	   for(int i=0; i<Runner.PREF_H/map.getTileSize(); i++)
	   {
		   if(((map.getTile(i,map.getRowTile((int)getX()))==2 ||((map.getTile(i,map.getRowTile((int)getX()))==9)))))
			   above = true;
	   }
	   if(map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()))==2 
		  ||map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX())) == 9)
		   notStanding = false;
	 return(above && notStanding);
		   
   }
   
   public boolean onLedge() //Checks to see if the character is on the second story
   {
	   if(map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX()))==2 
	    		||map.getTile(map.getColTile((int)getY()+getHeight()),map.getRowTile((int)getX())) == 9)
			return true;
	  return false;
   }
   
   public void setSlide(boolean slide)
   {
	   this.slide = slide;
   }
   
   public boolean getSlide()
   {
	   return slide;
   }
   
   public Rectangle getBounds()
   {
      return new Rectangle((int)x,(int)y, width, height);
   }
   
   public double getMAX_X()
   {
      return MAX_X;
   }

   public double getGRAVITY()
   {
      return GRAVITY;
   }

   public double getJUMPSTART()
   {
      return JUMPSTART;
   }
   
   public double getLEAPSTART()
   {
      return LEAPSTART;
   }

   public boolean isMoving()
   {
     return moving;
   }

   public void setMoving(boolean moving)
   {
      this.moving = moving;
   }

   public double getX()
   {
      return x;
   }

  public void setX(double x)
  {
     this.x = x;
  }

  public double getY()
  {
     return y;
  }

  public void setY(double y)
  {
     this.y = y;
  }

  public double getDx()
  {
     return dx;
  }

  public void setDx(double dx)
  {
     this.dx = dx;
  }

  public double getDy()
  {
     return dy;
  }

  public void setDy(double dy)
  {
      this.dy = dy;
  }

  public int getWidth()
  {
     return width;
  }

  public void setWidth(int width)
  {
     this.width = width;
   }

  public int getHeight()
  {
     return height;
  }

  public void setHeight(int height)
  {
     this.height = height;
  }

  public boolean isLeft()
  {
     return left;
  }

  public void setLeft(boolean left)
  {
     this.left = left;
  }

  public boolean isRight()
  {
     return right;
  }

  public void setRight(boolean right)
  {
     this.right = right;
  }

  public boolean isJumping()
  {
     return jumping;
  }

  public void setLeaping(boolean leaping)
  {
     this.leaping = leaping;
  }
  
  public boolean isLeaping()
  {
     return leaping;
  }

  public void setJumping(boolean jumping)
  {
     this.jumping = jumping;
  }


  public boolean isFalling()
  {
     return falling;
  }

  public void setFalling(boolean falling)
  {
     this.falling = falling;
  }
     
     
}
