
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class TileMap
{
   private int x;
   private int y;
   
   private int tileSize;
   private int[][] map;
   public int mapWidth;
   public int mapHeight;
   
   private boolean blocked[][];
   
   private int minx;
   private int miny;
   private int maxx = 0;
   private int maxy = 0;
   private BufferedImage wall, topDoor, bottomDoor, rail, underRail, bottomWall, 
   						fenceWall,floor,hold,topHold, redJumpOver, grayWall, stairs,
   						topStairs, darkWall, railing, sand, tires;
   
   public TileMap(String s, int tileSize) 
   {
      this.tileSize = tileSize;
      try 
      {
		wall = ImageIO.read(new File("src/coolWall.png"));
		topDoor = ImageIO.read(new File("src/topDoor.png"));
		bottomDoor = ImageIO.read(new File("src/bottomDoor.png"));
		rail = ImageIO.read(new File("src/coolRail.png"));
		underRail = ImageIO.read(new File("src/coolUnderRail.png"));
		bottomWall = ImageIO.read(new File("src/new6.png"));
		fenceWall = ImageIO.read(new File("src/newCoolBottomDoor.png"));
		floor = ImageIO.read(new File("src/floor.jpg"));
		hold = ImageIO.read(new File("src/theHold.png"));
		topHold = ImageIO.read(new File("src/p.png"));
		redJumpOver = ImageIO.read(new File("src/redJumpOver.png"));
		grayWall = ImageIO.read(new File("src/floor.jpg"));
		stairs =  ImageIO.read(new File("src/stairs.png"));
		topStairs = ImageIO.read(new File("src/TopStairs.png"));
		darkWall = ImageIO.read(new File("src/inWall.png"));
		railing = ImageIO.read(new File("src/Rename.png"));
		sand = ImageIO.read(new File("src/sand.png"));
		tires = ImageIO.read(new File("src/Tires.png"));
		
      } 	
      catch (IOException e1) 
      {
		// TODO Auto-generated catch block
		e1.printStackTrace();
      } 
      
      try 
      {
         
         BufferedReader br = new BufferedReader(new FileReader(s));
         
         mapHeight = Integer.parseInt(br.readLine());
         mapWidth = Integer.parseInt(br.readLine());
         map = new int[mapHeight][mapWidth];
         System.out.println("Map: " + mapWidth + " " + mapHeight);
         
         minx = Runner.PREF_W - mapWidth * tileSize;
         miny = Runner.PREF_H - mapHeight * tileSize;
         
         System.out.println("Min/Max: " + minx + " " + miny);
         String delimiters = "\\s+";
         for(int row = 0; row < mapHeight; row++)
         {
            String line = br.readLine();
            String[] tokens = line.split(delimiters);
            for(int col = 0; col < mapWidth; col++)
            {
               map[row][col] = Integer.parseInt(tokens[col]);
            }
         }
         
      }
      catch(Exception e) {}
      
      blocked = new boolean[mapHeight][mapWidth];
      setBlocked();
   }
   
   public void loadTiles(String s)
   {  
   }
   
   public void setBlocked()
   {
      for(int row = 0; row < mapHeight; row++) 
      {
         for(int col = 0; col < mapWidth; col++) 
         {
            
            if(map[row][col] == 1)
               blocked[row][col] = true;
            else
               blocked[row][col] = false;
         }
      }
   }
   
   public int getx() 
   {
      return x;
   }
   
   public int gety() 
   {
      return y; 
   }
   
   public int getColTile(int x)
   {
       double r = (x+0.0)/tileSize;
	   return (int)r;
   }
   public int getRowTile(int y) 
   {
      return y / tileSize;
   }
   public int getTile(int row, int col)
   {
      
	   return map[row][col];
   }
   
   public int getBlockX(int num)
   {
	   for(int row = 0; row < mapHeight; row++) 
	         for(int col = 0; col < mapWidth; col++)
	         {
	        	 	if(getTile(row,col)==num)
	        	 		return col*tileSize;
	         }
	   return -1;
	        	 	
   }
   
   public int getTileSize() 
   {
      return tileSize;
   }
   
   //Since I removed the Tile connection, I created a boolean array for tracking blocked tiles
//   public boolean isBlocked(int row, int col) {
//      int rc = map[row][col];
//      int r = rc / tiles[0].length;
//      int c = rc % tiles[0].length;
//      return tiles[r][c].isBlocked();
//   }
   
   public boolean isBlocked(int row, int col)
   {
      return blocked[row][col];
   }
   
   public void setx(int i)
   {
      x = i;
      if(x < minx)
      {
         x = minx;
      }
      if(x > maxx)
      {
         x = maxx;
      }
   }
   public void sety(int i) 
   {
      y = i;
      if(y < miny)
      {
         y = miny;
      }
      if(y > maxy) 
      {
         y = maxy;
      }
   }
   
   
   public void draw(Graphics2D g2) 
   {
     
      for(int row = 0; row < mapHeight; row++) 
      {
         for(int col = 0; col < mapWidth; col++)
         { 
        	 	if(map[row][col] == 0)
        	 		g2.drawImage(wall, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);
                        
            if(map[row][col] == 1)
            {
            	  g2.setColor(Color.GRAY);
               g2.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
            }
             
            if(map[row][col] == 2)
               g2.drawImage(underRail, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);
            
            if(map[row][col] == 3)
               g2.drawImage(rail, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            if(map[row][col] == 4)
               g2.drawImage(topDoor, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);
            
            if(map[row][col] == 5)
               g2.drawImage(bottomDoor, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);
            
            
            if(map[row][col] == 6)
               g2.drawImage(bottomWall, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);
            
            
            if(map[row][col] == 7)
               g2.drawImage(fenceWall, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);
            
            
            if(map[row][col] == 8)
               g2.drawImage(hold, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 9)
               g2.drawImage(topHold, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

               
            if(map[row][col] == 10)
               g2.drawImage(redJumpOver, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

                         
            if(map[row][col] == 11)
               g2.drawImage(grayWall, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 12)
               g2.drawImage(stairs, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 13)
               g2.drawImage(topStairs, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 14 || map[row][col]==20)
               g2.drawImage(darkWall, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 15)
               g2.drawImage(sand, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 16)
               g2.drawImage(railing, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 17)
               g2.drawImage(tires, x + col * tileSize, y + row * tileSize, tileSize, tileSize, null);

            
            if(map[row][col] == 18)
            {
               g2.setColor(Color.BLACK);
               g2.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
              
            }
            
            if(map[row][col] == 30)
            {
            	  g2.setColor(Color.GREEN);
               g2.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
            }
                
            
         }
      }
      
   }
   
}