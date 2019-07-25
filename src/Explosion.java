
// Created by Lex Von Klark and Austin Patel at 1:05 PM at 5/3/16

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Explosion {
	private int x, y;
	private Image curFrame;
	private Image allFrames;
	private int xCounter, yCounter = 0;
	private boolean isFin = false;

	public Explosion(int x, int y) {
		this.x = x - 50;
		this.y = y - 50;

		allFrames = new ImageIcon(getClass().getResource("explosion.png")).getImage();
	}

	public void update() {
		xCounter += 100;
		if (xCounter > 800) {
			yCounter += 100;
			xCounter = 0;
		}

		if (yCounter > 800)
			isFin = true;
		else
			curFrame = toBufferedImage(allFrames).getSubimage(xCounter, yCounter, 100, 100);
	}

	public boolean getIsFin() {
		return isFin;
	}

	public Image getCurFrame() {
		return curFrame;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}
}
