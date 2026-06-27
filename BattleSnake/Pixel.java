import java.awt.Color;
import java.awt.Graphics;

public abstract class Pixel 
{
	protected int xCoor;
	protected int yCoor;
	protected int width;
	protected int height;
	
	protected Color colour;
	
	public Pixel(int xCoor, int yCoor, int tileSize, Color colour)
	{
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.colour = colour;
		
		width = tileSize;
		height = tileSize;
	}
	
	public abstract void draw(Graphics g);
	
	public int getxCoor(){return xCoor;}
	public void setxCoor(int xCoor){this.xCoor = xCoor;}
	public int getyCoor(){return yCoor;}
	public void setyCoor(int yCoor){this.yCoor = yCoor;}
	
	public Color getColour() {return colour;}
	public void setColour(Color colour) {this.colour = colour;}
}
