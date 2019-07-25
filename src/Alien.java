import java.awt.Image;
import java.awt.Rectangle;

public class Alien implements GlobalConst {
	private int alienWidth;
	private int alienHeight;
	private int alienXPos;
	private int alienYPos;
	private int type;
	private int alienMoveDirection = 1; // 1 = move right, 2 = move left
	private Image image;

	// Constructor
	public Alien(int xPos, int yPos, int type) {
		if (type == 0) // Regular can't fire alien
		{
			image = gameVar.alienImage1;
		} else // Shooter aliens
		{
			image = gameVar.alienImage2;
		}

		alienWidth = image.getWidth(null);
		alienHeight = image.getHeight(null);
		alienXPos = xPos;
		alienYPos = yPos;
		this.type = type;
	}

	public void shoot() {
		if (type == 0) return;
		
		gameVar.alienMissiles.add(new Missile(alienXPos, alienYPos, false));
		gameVar.alienMissiles.add(new Missile(alienXPos + alienWidth, alienYPos, false));
	}

	public Rectangle getRect() {
		return new Rectangle(alienXPos, alienYPos, alienWidth, alienHeight);
	}

	// Move the alien 'ALIEN_SPEED' pixels in the appropriate direction; if the
	// alien has reached the left or right edge of the field, reverse the
	// direction of the alien
	public void moveAlien() {
		if ((alienXPos <= 0) || (alienXPos + alienWidth + 6 >= FIELD_WIDTH))
			alienMoveDirection = 3 - alienMoveDirection;

		if (alienMoveDirection == 1)
			alienXPos += ALIEN_SPEED;
		else
			alienXPos -= ALIEN_SPEED;
	}

	public Image getAlienImage() {
		return image;
	}

	public int getWidth() {
		return alienWidth;
	}

	public int getHeight() {
		return alienHeight;
	}

	public int getX() {
		return alienXPos;
	}

	public int getY() {
		return alienYPos;
	}
}
