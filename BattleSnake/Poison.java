import java.awt.Color;
import java.awt.Graphics;

public class Poison extends Pixel
{
	public Poison(int xCoor,int yCoor, int tileSize, Color colour)
	{
		super(xCoor, yCoor, tileSize, colour);
	}
	
	@Override
	public void draw(Graphics g) 
	{
		g.setColor(colour);
		g.fillRect(xCoor * width, yCoor*height, width, height);	
	}
}

	
