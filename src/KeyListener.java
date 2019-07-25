import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Created by Austin Patel and Lex Von Klark at 10:55 AM on 4/27/16

public class KeyListener extends KeyAdapter
{
	private KeyHandler callingClass;
	
	public KeyListener(KeyHandler callingClass)
	{
		this.callingClass = callingClass;
	}

	@Override
	public void keyPressed(KeyEvent event)
	{
		callingClass.keyPressed(event);
	}
	
	@Override
	public void keyReleased(KeyEvent event)
	{
		callingClass.keyReleased(event);
	}
}
