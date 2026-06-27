import java.awt.Color;
import java.awt.Graphics;

public class SnakeBody extends Pixel
{	
	public SnakeBody(int xCoor, int yCoor, int tileSize, Color colour)
	{
		super(xCoor, yCoor, tileSize, colour);
	}

	@Override
	public void draw(Graphics g) 
	{
		g.setColor(colour);
		g.fillRect(xCoor * width+2, yCoor * height+2, width-2, height-2);
	}
	
	public void drawHead(Graphics g)
	{
		g.setColor(colour);
		g.fillRect(xCoor * width, yCoor*height, width, height);
	}
	
	public void drawIronHead(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.fillRect(xCoor * width, yCoor*height, width, height);
	}
	
	public void drawToxicBody(Graphics g)
	{
		g.setColor(Color.GREEN);
		g.fillRect(xCoor * width+2, yCoor * height+2, width-2, height-2);
	}
}
