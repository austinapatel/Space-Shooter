
// Created by Austin Patel and Lex Von Klark at 10:48 AM on 4/27/16

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Shooter implements KeyHandler, GlobalConst {
	public static final int SHOOTER_SPEED = 2;

	private int x, y, w, h;
	private boolean leftPressed, rightPressed, upPressed, downPressed;

	private int playerNumber;
	private Image image, leftImage, rightImage;
	private int score = 0;
	private final int SHOOT_DELAY = 30;
	private int curShootDelay = 0;
	private int lives = 3;
	private int spawnX, spawnY;
	private int curFreeTime = 0;
	private final int FREE_TIME_TOTAL = 100;
	private boolean isInvinsible = false;

	private boolean isDead;

	private ArrayList<Missile> missiles = new ArrayList<Missile>();

	public Shooter(int playerNumber, int spawnX, int spawnY) {
		image = new ImageIcon(getClass().getResource("shooter" + playerNumber + ".png")).getImage();
		leftImage = new ImageIcon(getClass().getResource("shooter" + playerNumber + "Left.png")).getImage();
		rightImage = new ImageIcon(getClass().getResource("shooter" + playerNumber + "Right.png")).getImage();

		this.playerNumber = playerNumber;
		this.w = image.getWidth(null);
		this.h = image.getHeight(null);
		
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		
		x = spawnX;
		y = spawnY;
	}

	@Override
	public void keyPressed(KeyEvent event) {
		if (playerNumber == 1) {
			switch (event.getKeyChar()) {
			case 'w': // Up
				upPressed = true;
				break;

			case 's': // Down
				downPressed = true;
				break;

			case 'a': // Left
				leftPressed = true;
				break;

			case 'd': // Right
				rightPressed = true;
				break;
			}
		} else if (playerNumber == 2) {
			switch (event.getKeyCode()) {
			case 38: // Up
				upPressed = true;
				break;

			case 40: // Down
				downPressed = true;
				break;

			case 37: // Left
				leftPressed = true;
				break;

			case 39: // Right
				rightPressed = true;
				break;
			}
		}
	}
	
	public void update() {
		curShootDelay++;
		fireMissile();
		
		curFreeTime--;
		if (curFreeTime == 0)
			isInvinsible = false;
	}
	
	public boolean getIsInvinsible()
	{
		return isInvinsible;
	}
	
	public void loseLife() {
		lives -= 1;
		if (lives == 0) isDead = true;
		
		x = spawnX;
		y = spawnY;
		
		isInvinsible = true;
		curFreeTime = FREE_TIME_TOTAL;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if (playerNumber == 1) {
			switch (event.getKeyChar()) {
			case 'w': // Up
				upPressed = false;
				break;

			case 's': // Down
				downPressed = false;
				break;

			case 'a': // Left
				leftPressed = false;
				break;

			case 'd': // Right
				rightPressed = false;
				break;
			}
		} else if (playerNumber == 2) {
			switch (event.getKeyCode()) {
			case 38: // Up
				upPressed = false;
				break;

			case 40: // Down
				downPressed = false;
				break;

			case 37: // Left
				leftPressed = false;
				break;

			case 39: // Right
				rightPressed = false;
				break;
			}
		}
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}

	private void fireMissile() {
		if (isDead) return;
		
		if (curShootDelay < SHOOT_DELAY) return;
		curShootDelay = 0;
		
		// Determine the width and height of the missile being launched
		Missile tempMissile = new Missile(0, 0, true);
		int missileWidth = tempMissile.getWidth();

		// Set the starting position of the missile being launched
		int x = this.x - missileWidth / 2;
		int y = this.y;

		// Create a new 'Missile' object and add it to the 'missiles'
		// ArrayList
		missiles.add(new Missile(x, y, true));
		missiles.add(new Missile(x + w - missileWidth, y, true));
	}

	public boolean getIsDead() {
		return isDead;
	}
	
	public int getLives() {
		return lives;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void addScore(int a) {
		score += a;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public int getScore() {
		return score;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean getLeftPressed() {
		return leftPressed;
	}

	public boolean getRightPressed() {
		return rightPressed;
	}

	public boolean getUpPressed() {
		return upPressed;
	}

	public boolean getDownPressed() {
		return downPressed;
	}

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	public Image getLeftImage() {
		return leftImage;
	}

	public Image getRightImage() {
		return rightImage;
	}
}
