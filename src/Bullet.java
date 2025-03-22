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
public class Bullet
{
   private double x,y;
   private int width,height;
   private double dx;
   public final double SPEED = 7;
   private Player p;
   private TileMap map;
   private BufferedImage pic;
   private boolean isRight;
   
   public Bullet(Player p, TileMap map)
   {
	  this.map = map;
      this.p = p;
      if(p.isRight())
      {
          x = p.getX()+p.getWidth(); 
          dx = SPEED;
          isRight = true;
      }
      else
      {
         x = p.getX();
         dx = -SPEED;
         isRight = false;
      }
      y = p.getY()+p.getHeight()*(1/2); 
      width = 60;
      height = 30;
      try
      {
    	   pic = ImageIO.read(new File("src/bullet.png"));
      }
      catch (IOException e1) 
      {
		e1.printStackTrace();
      } 
   }
   
   public void update()
   {
      x += dx;
   }
   
   public double getX()
   {
	   return x;
   }
   
   public Rectangle getBounds()
   {
	   return new Rectangle( (int)x, (int)y, width, height );
   }
   public void draw(Graphics2D g2)
   {
	   int tx = map.getx();
       int ty = map.gety();
	   
	  if(isRight)
		  g2.drawImage(pic, (int)x+tx, (int)y+ty, width, height, null);
	  else
		  g2.drawImage(pic, (int)x+tx, (int)y+ty, -width, height, null);
   }
   
   
}
