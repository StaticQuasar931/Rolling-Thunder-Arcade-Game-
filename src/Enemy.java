import java.awt.Color;
import java.awt.Graphics2D;


//Bennett Bierman
//Program description:
//May 18, 2018
public class Enemy extends Character
{
	private Player p;
	private BulletManager b;
	private int lives;
	private boolean shot;
	public final int SHOTSTART = 50; //the amount an enemy is moved back when they are shot
	

	public Enemy(int x, int y, Player p, TileMap map, BulletManager b) 
	{
		super(x, y, map);
		this.p = p;
		this.b = b;
		lives = 2;
		shot = false;
	}

	@Override
	public void update() 
	{
		// TODO Auto-generated method stub
		
	     
	      if(canFall() || getSlide())
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
		   
		   if(isLeaping() && getDy()==0 && isLeapable())
		   {
			   setFalling(true);
		       setDy(getLEAPSTART());
		       setLeaping(false);
		   }
		   
	      if(isFalling())
	         setDy(getDy()+getGRAVITY());
	      
	      if(!canLeftRight())
	    	  	setJumping(true);
	      
	      if(p.getX()<getX())
				 setDx(getDx()-1);
	       else
			setDx(getDx()+1);
			
	      if(getDx()>getMAX_X()/3)
		      setDx(getMAX_X()/3);
		      
		   if(getDx()<-getMAX_X()/3)
		     setDx(-getMAX_X()/3);
		   
		   
		   if(shot)
		   {
			   if(getDx()>0)
				   setDx(-SHOTSTART);
			   else 	
				   setDx(SHOTSTART);
			   lives--;
			   shot = false;
				   
		   }
		   if(onGround())
			   setSlide(false);
			
		  if(p.onLedge() && this.isLeapable())
			  setLeaping(true);
		  
		  if(p.onGround() && this.onLedge())
			  setSlide(true);
		   if(p.getVisible())
			   setX(getX()+getDx());
		   setY(getY()+getDy());
	      
	}

	@Override
	public void draw(Graphics2D g2) 
	{
		// TODO Auto-generated method stub
		int tx = map.getx();
	    int ty = map.gety();
		g2.setColor(Color.RED);
		g2.fillRect((int)getX()+tx,  (int)getY()+ty, getWidth(), getHeight());
		
	}
	
	public boolean getShot()
	{
		return shot;
	}
	
	public void setShot(boolean b)
	{
		shot = b;
	}
	
	public int getLives()
	{
		return lives;
	}


}
