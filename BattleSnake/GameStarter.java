
public class GameStarter implements Runnable
{
	private Screen currentScreen;
	
	public GameStarter(Screen currentScreen)
	{
		this.currentScreen = currentScreen;
	}
	
	@Override
	public void run() 
	{
		currentScreen.start();
	}	
}
