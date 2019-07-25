import java.awt.Image;
import java.awt.Rectangle;

public class Missile implements GlobalConst {
	private int missileWidth;
	private int missileHeight;
	private int missileXPos;
	private int missileYPos;
	private boolean up;

	// Constructor
	public Missile(int xPos, int yPos, boolean up) {
		missileWidth = gameVar.missileImage.getWidth(null);
		missileHeight = gameVar.missileImage.getHeight(null);
		missileXPos = xPos;
		missileYPos = yPos;
		this.up = up;
	}

	// Move the missile 'MISSILE_SPEED' pixels up the playing field
	public void moveMissile() {
		if (up)
			missileYPos -= MISSILE_SPEED;
		else
			missileYPos += MISSILE_SPEED;
	}
	
	public void setDirection(boolean up) {
		this.up = up;
	}

	public Rectangle getRect() {
		return new Rectangle(missileXPos, missileYPos, missileWidth, missileHeight);
	}

	public Image getMissileImage() {
		return gameVar.missileImage;
	}

	public int getWidth() {
		return missileWidth;
	}

	public int getHeight() {
		return missileHeight;
	}

	public int getX() {
		return missileXPos;
	}

	public int getY() {
		return missileYPos;
	}
}
