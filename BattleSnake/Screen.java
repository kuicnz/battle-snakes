import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class Screen extends JPanel implements Runnable
{
	
	protected static final long serialVersionUID = 1L;
	
	protected Thread thread;
	protected boolean running;
	
	protected ArrayList<SnakeBody> snake;
	protected Food food;
	protected Random random;
	
	protected int xCoor, yCoor;
	protected int size;
	
	protected boolean right, left, up, down;
	protected int ticks;
	
	protected JLabel message;
	
	public Screen(JLabel lblMessage)
	{
		message = lblMessage;
		setFocusable(true);
		initialiseGame();
	}
	
	public abstract void initialiseGame();
	public abstract void tick();
	public abstract void paint(Graphics g);
	
	public void start()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public abstract void stop();
	
	public void run()
	{
		while(running)
		{
			ticks++;
			if(ticks > 500000)
			{
				ticks = 0;
				tick();
				repaint();
				
				try {
					Thread.sleep(75);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
