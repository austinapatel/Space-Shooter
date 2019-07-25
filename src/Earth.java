import java.awt.Rectangle;

public class Earth {
	private boolean isDead;
	private int width = 100;
	private int height = 30;
	private int health = height;
	private int maxHealth = height;
	private int x;
	private int y;
	private int startY;
	
	public Earth(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		startY = y;
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		
		if (health <= 0)
		{
			isDead = true;
		}
	}
	
	public void heal() {
		this.health = maxHealth;
		isDead = false;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, getY(), width, height);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y + (30 - health);
	}
}
