import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;

public class DualScreen extends Screen
{
	/**
	 * by Tihhoo(Ken Chen)
	 */
	private static final long serialVersionUID = 3143610392679825511L;
	
	private Pixel poison;
	private boolean s1toxic;
	private boolean s2toxic;
	private int s1poisonCounter;
	private int s2poisonCounter;
	
	private ArrayList<Food> foods;
	private int foodGeneration;
	
	private ArrayList<SnakeBody> snake2;
	private int sizeS2;
	private boolean s2right, s2left, s2up, s2down;
	private int xCoorS2, yCoorS2;
	
	private ArrayList<Food> garbages;
	
	private Pixel ironHead;
	private boolean s1ih;
	private boolean s2ih;
	private long ihTimer1;
	private long ihTimer2;
	
	protected KeyPressedListener keyListener;
	
	public DualScreen(JLabel lblMessage)
	{
		super(lblMessage);
		keyListener = new KeyPressedListener();
		addKeyListener(keyListener);
		start();
	}

	@Override
	public void initialiseGame() {
		message.setText("");
		foodGeneration = 0;
		ironHead = null;
		ihTimer1 = 0;
		ihTimer2 = 0;
		s1ih = false;
		s2ih = false;
		
		sizeS2 = 3;
		size = 3;
		ticks = 0;
		
		xCoorS2 = 10;
		yCoorS2 = 10;
		s2right = true;
		s2left = false;
		s2up = false;
		s2down = false;
		
		xCoor = 40;
		yCoor = 40;
		left = true;
		running = false;
		right = false;
		up = false;
		down = false;
		
		random = new Random();
		
		snake = new ArrayList<SnakeBody>();
		snake.add(new SnakeBody(40,40,10,Color.BLUE));
		
		snake2 = new ArrayList<SnakeBody>();
		snake2.add(new SnakeBody(10,10,10,Color.RED));
		
		foods = new ArrayList<Food>();
		foods.add(new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW));
		foods.add(new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW));
		foods.add(new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW));
		
		garbages = new ArrayList<Food>();
		
		poison = new Poison(random.nextInt(49),random.nextInt(49),10,Color.GREEN);
		s1toxic = false;
		s2toxic = false;
		s1poisonCounter = 0;
		s2poisonCounter = 0;
	}

	@Override
	public void tick() 
	{
		foodHandler();
		poisonHandler();
		
		if(ironHead!= null && xCoor == ironHead.getxCoor() && yCoor == ironHead.getyCoor())
		{
			ihTimer1 = System.currentTimeMillis();
			s1ih = true;
			ironHead = null;
		}
		
		if(ironHead!= null && xCoorS2 == ironHead.getxCoor() && yCoorS2 == ironHead.getxCoor())
		{
			ihTimer2 = System.currentTimeMillis();
			s2ih = true;
			ironHead = null;
		}
		
		if(xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49) 
			stopProcessor("Red","Blue"," hits a wall! Press 'R' to rematch!");
		
		if(xCoorS2 < 0 || xCoorS2 > 49 || yCoorS2 < 0 || yCoorS2 > 49)
			stopProcessor("Blue","Red"," hits a wall! Press 'R' to rematch!");
		
		snakeHandler();

		if(s1ih && (System.currentTimeMillis()-ihTimer1)/1000 == 12)
		{
			s1ih = false;
			ihTimer1 = 0;
		}
		
		if(s2ih && (System.currentTimeMillis()-ihTimer2)/1000 == 12)
		{
			s2ih = false;
			ihTimer2 = 0;
		}
		
		if(right) xCoor++;
		if(left) xCoor--;
		if(up) yCoor--;
		if(down) yCoor++;
		
		if(s2right) xCoorS2++;
		if(s2left) xCoorS2--;
		if(s2up) yCoorS2--;
		if(s2down) yCoorS2++;
		
		snake.add(new SnakeBody(xCoor,yCoor,10,Color.BLUE));
		snake2.add(new SnakeBody(xCoorS2,yCoorS2,10,Color.RED));
		
		while(snake.size() > size)
			snake.remove(0);
		
		while(snake2.size() > sizeS2)
			snake2.remove(0);
	}

	@Override
	public void paint(Graphics g) 
	{
		for(Food f: foods)
			f.draw(g);
		
		poison.draw(g);
		
		if(ironHead != null)
			ironHead.draw(g);
		
		for(int k = 0; k < snake.size()-1; k++)
		{
			if(k%2==0 && s1toxic)
				snake.get(k).drawToxicBody(g);
			else
				snake.get(k).draw(g);
		}
		
		for(int l = 0; l < snake2.size()-1; l++)
		{
			if(l%2==0 && s2toxic)
				snake2.get(l).drawToxicBody(g);
			else
				snake2.get(l).draw(g);
		}
		
		if(s1ih)
			snake.get(snake.size()-1).drawIronHead(g);
		else
			snake.get(snake.size()-1).drawHead(g);
		
		if(s2ih)
			snake2.get(snake2.size()-1).drawIronHead(g);
		else
			snake2.get(snake2.size()-1).drawHead(g);
	}

	@Override
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void foodHandler()
	{
		for(Food f: foods)
		{
			if(xCoor == f.getxCoor() && yCoor == f.getyCoor())
			{
				if(s1toxic)
				{
					if(size > 6)
						size-=6;
					else if(size == 1)
						stopProcessor("Red","Blue"," is poisoned to death! Press 'R' to rematch!");
					else
						s1toxic = false;
				}
				else
					size++;
				garbages.add(f);
			}
			
			if(xCoorS2 == f.getxCoor() && yCoorS2 == f.getyCoor())
			{
				if(s2toxic)
				{
					if(sizeS2 > 6)
						sizeS2-=6;
					else if(sizeS2 == 1)
						stopProcessor("Blue","Red"," is poisoned to death! Press 'R' to rematch!");
					else
						s2toxic = false;
				}
				else
					sizeS2++;
				garbages.add(f);
			}
		}
		
		for(Food g: garbages)
			foods.remove(g);
		
		if(foods.isEmpty())
		{
			foods.add(new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW));
			foods.add(new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW));
			
			if(++foodGeneration % 4 == 0)
			{
				ironHead = new IronHead(random.nextInt(49),random.nextInt(49),10,Color.WHITE);
			}
			else
				foods.add(new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW));
		}
	}
	
	private void poisonHandler() 
	{
		if(xCoor == poison.getxCoor() && yCoor == poison.getyCoor())
		{
			if(s1toxic)
				size+=3;
			else if(!s1toxic && ++s1poisonCounter == 6)
			{
				s1toxic = true;
				size+=15;
			}
			else if(size > 6)
				size-=6;
			else if(size == 1)
				stopProcessor("Red","Blue"," can't handle healthy food! Press 'R' to rematch!");
			else
				size = 1;
			poison = new Poison(random.nextInt(49),random.nextInt(49),10,Color.GREEN);
		}
		
		if(xCoorS2 == poison.getxCoor() && yCoorS2 == poison.getyCoor())
		{
			if(s2toxic)
				sizeS2+=3;
			else if(!s2toxic && ++s2poisonCounter == 6)
			{
				s2toxic = true;
				sizeS2+=15;
			}
			else if(sizeS2 > 6)
				sizeS2-=6;
			else if(sizeS2 == 1)
				stopProcessor("Blue","Red"," can't handle healthy food! Press 'R' to rematch!");
			else
				sizeS2 = 1;
			poison = new Poison(random.nextInt(49),random.nextInt(49),10,Color.GREEN);
		}
	}
	
	private void snakeHandler()
	{
		for(int i = 0; i < snake.size(); i++)
		{
//			if(xCoor == snake.get(i).getxCoor() &&yCoor == snake.get(i).getyCoor() && i != snake.size()-1)
//				stopProcessor("Red","Blue"," commits a suicide! Press 'R' to rematch!");
			
			if(xCoorS2 == snake.get(i).getxCoor() && yCoorS2 == snake.get(i).getyCoor())
			{
				if(i == snake.size()-1)
				{
					if(s1ih && !s2ih)
						stopProcessor("Blue","Red"," is smashed by Blue! Press 'R' to rematch!");
					else if(s2ih && !s1ih)
						stopProcessor("Red","Blue"," is smashed by Red! Press 'R' to rematch!");
					else
						stopProcessor("No one","","Both dead, it's a draw! Rematch press 'R'!");
				}
				
				else
				{
					if(s2ih)
						size-=(i+1);
					else
						stopProcessor("Blue","Red"," is consumed! Press 'R' to rematch!");
				}
			}
		}
		
		for(int j = 0; j < snake2.size(); j++)
		{
//			if(xCoorS2 == snake2.get(j).getxCoor() && yCoorS2 == snake2.get(j).getyCoor() && j != snake2.size()-1)
//				stopProcessor("Blue","Red"," commits a suicide! Press 'R' to rematch!");
			
			if(xCoor == snake2.get(j).getxCoor() && yCoor == snake2.get(j).getyCoor())
			{
				if(j == snake2.size()-1)
				{
					if(s1ih && !s2ih)
						stopProcessor("Blue","Red"," is smashed by Blue! Press 'R' to rematch!");
					else if(s2ih && !s1ih)
						stopProcessor("Red","Blue"," is smashed by Red! Press 'R' to rematch!");
					else
						stopProcessor("No one","","none alive, it's a draw! Rematch press 'R'!");
				}
				else
				{
					if(s1ih)
						sizeS2-=(j+1);
					else
						stopProcessor("Red","Blue"," is consumed! Press 'R' to rematch!");
				}
			}
		}
	}
	
	private void stopProcessor(String winner, String loser, String condition)
	{
		message.setText("Game Over!"+" "+winner +" wins, because "+loser+condition);
		stop();
	}
	
	protected class KeyPressedListener implements KeyListener
	{
		@Override
		public void keyPressed(KeyEvent e) 
		{	
			int key = e.getKeyCode();	
			if(key == KeyEvent.VK_RIGHT && !left)
			{
				up = false;
				down = false;
				right = true;
			}
			
			if(key == KeyEvent.VK_LEFT && !right)
			{
				up = false;
				down = false;
				left = true;
			}
			
			if(key == KeyEvent.VK_UP && !down)
			{
				left = false;
				right = false;
				up = true;
			}
			
			if(key == KeyEvent.VK_DOWN && !up)
			{
				left = false;
				right = false;
				down = true;
			}
			
			//player2
			if(key == KeyEvent.VK_D && !s2left)
			{
				s2up = false;
				s2down = false;
				s2right = true;
			}
			
			if(key == KeyEvent.VK_A && !s2right)
			{
				s2up = false;
				s2down = false;
				s2left = true;
			}
			
			if(key == KeyEvent.VK_W && !s2down)
			{
				s2left = false;
				s2right = false;
				s2up = true;
			}
			
			if(key == KeyEvent.VK_S && !s2up)
			{
				s2left = false;
				s2right = false;
				s2down = true;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) 
		{
			if(!running && e.getKeyCode() == KeyEvent.VK_R)
			{
				initialiseGame();
				start();
			}
		}
	}
}


