import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

// This class, with a default (empty) constructor, creates a JPanel,
//  which is added to the JFrame in the 'Game' class, thereby enabling
//  a background Image (as opposed to just a single-color background)
//  to be placed under the graphics objects (shooter, aliens, missiles)
public class GamePanel extends JPanel implements GlobalConst {
	private static final long serialVersionUID = 1L;
	
	private BufferedImage draw = new BufferedImage(FIELD_WIDTH, FIELD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
	Graphics g = draw.getGraphics();
	private Font font = new Font("Helvetica", Font.BOLD, 24);
	private FontMetrics metrics = this.getFontMetrics(font);
	Font startFont = g.getFont();

	// This method handles the painting/drawing of all Images for this
	// program; the Images are drawn onto a JPanel, which is displayed
	// on a JFrame; note that an Image is "removed" from the JPanel by
	// simply not having it redrawn by this method
	public void paintComponent(Graphics panelG) {
		super.paintComponent(panelG);
		g.setFont(startFont);

		// This line causes graphics and text to be rendered with anti-aliasing
		// turned on, making the overall display look smoother and cleaner
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw the background for the JPanel
		g.drawImage(gameVar.backgroundImage, 0, 0, null);
		
		// Draw the explosions
		for (int e = 0; e < gameVar.explosions.size(); e++) {
			Explosion explosion = gameVar.explosions.get(e);
			explosion.update();
			Image image = explosion.getCurFrame();
			g.drawImage(image, explosion.getX(), explosion.getY(), this);
			if (explosion.getIsFin()) {
				gameVar.explosions.remove(e);
			}
		}
		
		// Draw the aliens
		for (int i = 0; i < aliens.size(); i++) {
			Alien alien = aliens.get(i);
			g.drawImage(alien.getAlienImage(), alien.getX(), alien.getY(), this);
		}
		
		// Draw the earth health remaining
		g.drawString("Earth Health: " + gameVar.earthHealth, FIELD_WIDTH / 2, 20);
		
		// Draw the money
		g.setColor(Color.GREEN);
		g.drawString("Money: $" + gameVar.money, FIELD_WIDTH / 2 - 100, 20);
		

		// Draw the missiles
		for (int s = 0; s < gameVar.shooters.length; s++) {
			Shooter shooter = gameVar.shooters[s];
			ArrayList<Missile> missiles = shooter.getMissiles();

			for (int i = 0; i < missiles.size(); i++) {
				Missile missile = missiles.get(i);
				g.drawImage(missile.getMissileImage(), missile.getX(), missile.getY(), this);
			}
		}
		
		

		for (Missile missile : gameVar.alienMissiles)
			g.drawImage(missile.getMissileImage(), missile.getX(), missile.getY(), this);

		// Draw stuff related to the shooter
		for (Shooter shooter : gameVar.shooters) {
			// Draw the shooter
			Image image2Draw;
			if (shooter.getLeftPressed())
				image2Draw = shooter.getLeftImage();
			else if (shooter.getRightPressed())
				image2Draw = shooter.getRightImage();
			else
				image2Draw = shooter.getImage();
			if (!shooter.getIsDead())
				g.drawImage(image2Draw, shooter.getX(), shooter.getY(), this);

			// Draw the score
			int score = shooter.getScore();
			int playerNumber = shooter.getPlayerNumber();

			if (playerNumber == 1) {
				g.setColor(Color.RED);
				g.drawString("Player 1: ", 10, 20);
				g.setColor(Color.YELLOW);
				g.drawString(String.valueOf(score), 60, 20);
			}

			if (playerNumber == 2) {
				g.setColor(Color.RED);
				g.drawString("Player 2: ", FIELD_WIDTH - 80, 20);
				g.setColor(Color.YELLOW);
				g.drawString(String.valueOf(score), FIELD_WIDTH - 30, 20);
			}

			// Draw the lives left for the shooters
			for (int l = 0; l < shooter.getLives(); l++) {
				if (playerNumber == 1)
					g.drawImage(shooter.getImage(), 85 + l * (shooter.getImage().getWidth(this) + 5), 10, this);

				if (playerNumber == 2)
					g.drawImage(shooter.getImage(), FIELD_WIDTH - 140 + l * (shooter.getImage().getWidth(this) + 5), 10,
							this);
			}
		}

		// Draw all of the earth tiles
		for (int i = 0; i < gameVar.earths.length; i++)
			g.drawImage(gameVar.earthImage, gameVar.earths[i].getX(), gameVar.earths[i].getY(), this);
		
		// Draw the turrets
		for (Turret turret : gameVar.turrets)
			g.drawImage(gameVar.turetImage, turret.getX(), turret.getY(), this);
		
		// Draw the turret missiles
		for (Missile missile : gameVar.turretMissiles)
			g.drawImage(gameVar.missileImage, missile.getX(), missile.getY(), this);
		
		// Draw the mirrors
		for (int i = 0; i < gameVar.mirrors.length; i++)
		{
			Mirror mirror = gameVar.mirrors[i];
			
			if (mirror == null) continue;
			g.drawImage(gameVar.mirrorImage, mirror.getX(), mirror.getY(), this);
		}

		// If the game is over, display a "game over" message
		if (gameVar.gameOver) {
			String message = "Game Over!";
			g.setColor(Color.YELLOW);
			g.setFont(font);
			g.drawString(message, (FIELD_WIDTH - metrics.stringWidth(message)) / 2, FIELD_HEIGHT / 2);
		}

		int xShift = (gameVar.resizedWidth - gameVar.panelWidth) / 2;
		int yShift = 0;
		int width = gameVar.panelWidth;
		int height = gameVar.panelHeight - 75 - 100;

		this.setBackground(Color.BLACK);

		panelG.drawImage(draw, xShift, yShift, width, height, this);

		// This line synchronizes the graphics state by flushing buffers
		// containing
		// graphics events and forcing the frame drawing to happen now;
		// otherwise,
		// it can sometimes take a few extra milliseconds for the drawing to
		// take
		// place, which can result in jerky graphics movement; this line ensures
		// that the display is up-to-date; it is useful for animation, since it
		// reduces or eliminates flickering
		Toolkit.getDefaultToolkit().sync();
	}
}
