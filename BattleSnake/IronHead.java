import java.awt.Color;
import java.awt.Graphics;

public class IronHead extends Pixel
{
	public IronHead(int xCoor, int yCoor, int tileSize, Color colour)
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
