import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;

public class SoloScreen extends Screen
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7571897993052911557L;

	private int score;
	protected KeyPressedListener keyListener;
	
	public SoloScreen(JLabel lblMessage)
	{
		super(lblMessage);
		keyListener = new KeyPressedListener();
		addKeyListener(keyListener);
		start();
	}

	@Override
	public void initialiseGame() {
		message.setText("");
		ticks = 0;
		score = 0;
		xCoor = 10;
		yCoor = 10;
		size = 3;
		running = false;
		right = true;
		left = false;
		up = false;
		down = false;
		random = new Random();
		snake = new ArrayList<SnakeBody>();
		snake.add(new SnakeBody(10,10,10,Color.RED));
		food = new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW);
	}

	@Override
	public void tick() 
	{
		if(xCoor == food.getxCoor() && yCoor == food.getyCoor())
		{
			size++;
			score++;
			food = new Food(random.nextInt(49),random.nextInt(49),10,Color.YELLOW);
		}
		
		if(xCoor < 0 || xCoor > 49 || yCoor < 0 || yCoor > 49)
			stop();
		
		for(int i = 0; i < snake.size(); i++)
		{
			if(xCoor == snake.get(i).getxCoor() &&yCoor == snake.get(i).getyCoor() && i != snake.size()-1)
				stop();
		}

		if(right) xCoor++;
		if(left) xCoor--;
		if(up) yCoor--;
		if(down) yCoor++;
		
		snake.add(new SnakeBody(xCoor,yCoor,10,Color.RED));
		
		if(snake.size() > size)
			snake.remove(0);
	}

	@Override
	public void paint(Graphics g) 
	{
		food.draw(g);
		
		for(int k = 0; k < snake.size()-1; k++)
			snake.get(k).draw(g);
		snake.get(snake.size()-1).drawHead(g);
	}

	@Override
	public void stop() 
	{
		running = false;
		message.setText("Game Over! You scored "+score+". Press 'R' to restart the game!");
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
				left = false;
			}
			
			if(key == KeyEvent.VK_LEFT && !right)
			{
				up = false;
				down = false;
				left = true;
				right = false;
			}
			
			if(key == KeyEvent.VK_UP && !down)
			{
				left = false;
				right = false;
				up = true;
				down = false;
			}
			
			if(key == KeyEvent.VK_DOWN && !up)
			{
				left = false;
				right = false;
				down = true;
				up = false;
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
