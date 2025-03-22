import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.Timer;

public class EnemyManager
{
	private ArrayList<Enemy> enemies;
	private Player p;
	private TileMap map;
	private BulletManager b;
	
	public EnemyManager(Player p, TileMap map, BulletManager b)
	{
		enemies = new ArrayList<Enemy>();
		this.p = p;
		this.map = map;
		this.b = b;
		firstAdd();
	}
	
	public void firstAdd() //Adds enemies when the game starts
	{
		enemies.add(new Enemy(600,416,p,map,b));
		enemies.add(new Enemy(800,50,p,map,b));
		enemies.add(new Enemy(1000,416,p,map,b));
	}
	
	public void add() //Adds enemies throughout the game
	{
		if(p.getX()>Runner.PREF_W/2+60  && p.getX()<(map.mapWidth*map.getTileSize()-(Runner.PREF_W/2+60)))
		{
			enemies.add(new Enemy((int)p.getX()-(Runner.PREF_W/2+60),(int)p.getY(),p,map, b));
			enemies.add(new Enemy((int)p.getX()+(Runner.PREF_W/2+60),(int)p.getY(),p,map, b));
			enemies.add(new Enemy(10,400,p,map, b));
		}
		
	}
	
	public void Remove(int i)
	{
		if(i<size() && i>=0)
			enemies.remove(i);	
	}
	
	public void draw(Graphics2D g2)
	{
		for(int i=0; i<enemies.size(); i++)
			enemies.get(i).draw(g2);
	}
	
	public void update(int j)
	{
		for(int i=enemies.size()-1; i>=0; i--)
			enemies.get(i).update();
		remove();
		if(j%300==0) //Adds enemies after every 300 ticks of the timer
			add();	
	}
	
	public void remove()
	{
		for(int i=enemies.size()-1; i>=0; i--)
			if(enemies.get(i).getLives() < 1)
				enemies.remove(i);
		
	}
	
	public int size()
	{
		return enemies.size();
	}
	
	public Enemy getEnemy(int i)
	{
		if(i<size() && i>=0)
			return enemies.get(i);
		return null;
					
	}	

}
