import java.awt.Graphics2D;
import java.util.ArrayList;

//Bennett Bierman
//Program description:
//May 15, 2018
public class BulletManager
{
   private ArrayList<Bullet> bullets;
   public static int bulletCounter = 40;
   private Player p;
   private TileMap map;
   
   public BulletManager(Player p, TileMap map)
   {
       bullets = new ArrayList<Bullet>();
       this.p =p;
       this.map = map;
   }
   
   public int getCounter()
   {
	   return bulletCounter;
   }
   
   public void update()
   {
      for(Bullet b: bullets)
         b.update();
   }
   
   public void add()
   {  
      if(bulletCounter>0)
      {
    	  	bullets.add(new Bullet(p, map));
    	  	bulletCounter--;
      }
      System.out.println(bulletCounter);
   }
   
   public void remove(int i)
   {
	   if(i<bullets.size() && i>=0)
		   bullets.remove(i);
   }
   
   public void reload(int num)
   {
	   bulletCounter+=num;
   }
   
   public void draw(Graphics2D g2)
   {
      for(Bullet b: bullets)
         b.draw(g2);
   }
   
   public int size()
   {
	   return bullets.size();
   }
   
   public Bullet getBullet(int i)
   {
	   if(i<size() && i>=0)
		   return bullets.get(i);
	   return null;
   }
  
   
}
