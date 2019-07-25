import java.awt.Rectangle;

public class Turret implements GlobalConst {
	private final int FIRE_DELAY = 50;
	
	private int x, y, w, h;
	private int health = 10;
	private boolean isDead;
	private int curFireDelay = 0;
	
	public Turret() {
		w = gameVar.turetImage.getWidth(null);
		h = gameVar.turetImage.getHeight(null);
	}
	
	private void fire() {
		gameVar.turretMissiles.add(new Missile(x + w / 2, y, true));
	}
	
	public void takeDamage(int damage) {
		health -= damage;
		
		isDead = health <= 0;
	}
	
	public boolean getIsDead() {
		return isDead;
	}
	
	public void update() {
		curFireDelay++;
		
		if (curFireDelay > FIRE_DELAY && !gameVar.gameOver)
		{
			fire();
			curFireDelay = 0;
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
	public int getH()
	{
		return h;
	}
}