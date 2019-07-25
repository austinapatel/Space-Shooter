import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Game implements GlobalConst, ActionListener, KeyHandler {
	private JFrame gameFrame;
	private GamePanel spacePanel;
	private Timer timer;
	private JButton players1, players2, addTurret, addMirror, addHeal;
	private JLabel title;
	private int numberOfPlayers = 0;
	private JLabel background;
	private Random random = new Random();
	private int alienRate = 50;
	private final int RATE_INCREASE_TIME = 120;
	private int curAlienIncrease = 0;

	public static void main(String[] args) {
		new Game();
	}

	// Constructor
	@SuppressWarnings("unchecked")
	public Game() {
		gameFrame = new JFrame();
		spacePanel = new GamePanel();
		spacePanel.setLocation(0, 0);
		spacePanel.setSize(gameVar.resizedWidth, gameVar.resizedHeight);

		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.setSize(gameVar.resizedWidth, gameVar.resizedHeight);
		gameFrame.setTitle("Definitely Not Galaga");
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setResizable(true);
		gameFrame.setFocusable(true);
		
		KeyListener listener = new KeyListener(this);
		gameFrame.addKeyListener(listener);
		
		gameFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				resizeFrame(gameFrame.getWidth(), gameFrame.getHeight());
			}

			public void componentMoved(ComponentEvent evt) {
				frameMoved();
			}
		});

		gameFrame.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent arg0) {
				windowMaximized();
			}
		});

		title = new JLabel("Galaga", SwingConstants.CENTER);

		try {
			Font font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("sciFiFont.TTF"));
			font = font.deriveFont(Font.TRUETYPE_FONT, 200);
			@SuppressWarnings("rawtypes")
			Map attributes = font.getAttributes();
			attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);

			title.setFont(new Font(attributes));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		title.setForeground(Color.RED);
		title.setLocation(0, 20);
		gameFrame.add(title);
		int iW = gameVar.shooterImage.getWidth(gameFrame);
		int iH = gameVar.shooterImage.getHeight(gameFrame);
		gameFrame.setIconImage(gameVar.shooterImage.getScaledInstance(iW * 4, iH * 4, Image.SCALE_SMOOTH));

		players1 = new JButton("One Player");
		players2 = new JButton("Two Player");

		players1.addActionListener(this);
		players2.addActionListener(this);

		gameFrame.add(players1);
		gameFrame.add(players2);

		gameFrame.getContentPane().setBackground(Color.BLACK);

		background = new JLabel();
		background.setLocation(0, 0);

		gameFrame.add(background);

		gameFrame.setLayout(null);

		gameFrame.setVisible(true);
	}

	private void windowMaximized() {
		resizeFrame(gameFrame.getWidth(), gameFrame.getHeight());
	}

	private void frameMoved() {
		gameVar.frameX = gameFrame.getX();
		gameVar.frameY = gameFrame.getY();
	}

	private void resizeFrame(int w, int h) {
		gameVar.resizedWidth = w;
		gameVar.resizedHeight = h;

		title.setSize(gameVar.resizedWidth, 300);

		background.setSize(gameVar.resizedWidth, gameVar.resizedHeight);
		background.setIcon(new ImageIcon(gameVar.backgroundImage.getScaledInstance(w, h, Image.SCALE_FAST)));

		spacePanel.setSize(w, h);

		if (w * GlobalConst.WtoH > h)
			w = (int) (h / GlobalConst.WtoH);
		else
			h = (int) (w * GlobalConst.WtoH);

		int buttonMargin = 20;
		int buttonWidth = (w - buttonMargin * 2) / 3;

		int shiftX = (gameVar.resizedWidth - w) / 2;
		
		if (numberOfPlayers != 0)
		{
			addTurret.setLocation(shiftX, h - 100 - 73);
			addTurret.setSize(buttonWidth, 100);

			addMirror.setLocation(shiftX + buttonWidth + buttonMargin, h - 100 - 73);
			addMirror.setSize(buttonWidth, 100);

			addHeal.setLocation(shiftX + (buttonWidth + buttonMargin) * 2, h - 100 - 73);
			addHeal.setSize(buttonWidth, 100);	
		}

		gameVar.panelWidth = w;
		gameVar.panelHeight = h;

		players1.setSize(gameVar.resizedWidth / 4, gameVar.resizedHeight / 5);
		players2.setSize(gameVar.resizedWidth / 4, gameVar.resizedHeight / 5);

		players2.setLocation(gameVar.resizedWidth / 2 + buttonMargin,
				(gameVar.resizedHeight / 2) - players2.getHeight() / 2);
		players1.setLocation(gameVar.resizedWidth / 2 - buttonMargin - players1.getWidth(),
				(gameVar.resizedHeight / 2) - players1.getHeight() / 2);
	}

	public void startGame() {
		gameVar.numberOfPlayers = numberOfPlayers;
		players1.setVisible(false);
		players2.setVisible(false);
		background.setVisible(false);
		title.setVisible(false);

		// Add JStuff related to player3

		addTurret = new JButton("Turret ($1000) [1]");
		addTurret.addActionListener(this);
		addTurret.setActionCommand("Turret");
		addTurret.setFocusable(false);
		gameFrame.add(addTurret);

		addMirror = new JButton("Mirror ($2000) [2]");

		addMirror.addActionListener(this);
		addMirror.setActionCommand("Mirror");
		addMirror.setFocusable(false);
		gameFrame.add(addMirror);

		addHeal = new JButton("Heal ($500) [3]");
		addHeal.addActionListener(this);
		addHeal.setActionCommand("Heal");
		addHeal.setFocusable(false);
		gameFrame.add(addHeal);

		// Forces the positions of objects to be recalculated
		resizeFrame(gameVar.resizedWidth, gameVar.resizedHeight);


		gameFrame.getContentPane().add(spacePanel);
		setUpShooters();
		setUpAliens();
		setUpEarth();

		// Set (and start) a new Swing Timer to fire every 'TIMER_SPEED'
		// milliseconds,
		// after an initial delay of 'TIMER_DELAY' milliseconds; this Timer,
		// along
		// with the distance (number of pixels) that the aliens, missiles, and
		// shooter
		// move with each cycle, controls how fast the objects move on the
		// playing
		// field; note that if adding a "pause/unpause" feature to this game,
		// the
		// value of the 'TIMER_DELAY' constant should probably be set to zero
		timer = new Timer(TIMER_SPEED, this);
		timer.setInitialDelay(TIMER_DELAY);
		timer.setActionCommand("Timer");
		timer.start();
	}

	// Sets up the earth
	private void setUpEarth() {
		int earthWidth = gameVar.earthImage.getWidth(null);
		int earthHeight = gameVar.earthImage.getHeight(null);

		for (int i = 0; i < gameVar.earths.length; i++)
			gameVar.earths[i] = new Earth(i * earthWidth, FIELD_HEIGHT - earthHeight);
	}

	// Set the starting position of the player's shooter
	public void setUpShooters() {

		if (numberOfPlayers == 1) {
			gameVar.shooters = new Shooter[1];
			int shooter1X = (FIELD_WIDTH / 2) - (gameVar.shooterImage.getWidth(null) / 2);
			int shooter1Y = FIELD_HEIGHT - gameVar.shooterImage.getHeight(null) - 50;
			gameVar.shooters[0] = new Shooter(1, shooter1X, shooter1Y);
			KeyListener listener1 = new KeyListener(gameVar.shooters[0]);
			gameFrame.addKeyListener(listener1);
		} else if (numberOfPlayers == 2 || numberOfPlayers == 3) {
			gameVar.shooters = new Shooter[2];
			int shooter1X = (FIELD_WIDTH / 2) - (gameVar.shooterImage.getWidth(null) / 2) - 50;
			int shooter1Y = FIELD_HEIGHT - gameVar.shooterImage.getHeight(null) - 50;
			gameVar.shooters[0] = new Shooter(1, shooter1X, shooter1Y);
			KeyListener listener1 = new KeyListener(gameVar.shooters[0]);
			gameFrame.addKeyListener(listener1);

			int shooter2X = 50 + (FIELD_WIDTH / 2) - (gameVar.shooterImage.getWidth(null) / 2);
			int shooter2Y = FIELD_HEIGHT - gameVar.shooterImage.getHeight(null) - 50;
			gameVar.shooters[1] = new Shooter(2, shooter2X, shooter2Y);
			KeyListener listener2 = new KeyListener(gameVar.shooters[1]);
			gameFrame.addKeyListener(listener2);
		}
	}

	// Create and randomly place the appropriate number of aliens on the playing
	// field
	public void setUpAliens() {
		// Determine the width and height of each alien being placed
		for (int i = 0; i < NUM_ALIENS; i++)
			addAlien();
	}

	private void addAlien() {
		Alien tempAlien = new Alien(0, 0, 0);
		int alienWidth = tempAlien.getWidth();
		int alienHeight = tempAlien.getHeight();

		int x = (int) (Math.random() * (FIELD_WIDTH - alienWidth - 7) + 1);
		int y = (int) (Math.random() * (FIELD_HEIGHT - alienHeight - 26 - gameVar.shooterImage.getHeight(null) - 80));

		int type = random.nextInt(2);
		
		Rectangle alienRect = new Rectangle(x, y, alienWidth * 2, alienHeight * 2);
		
		for (Shooter shooter : gameVar.shooters)
			if (!shooter.getRect().intersects(alienRect))
				aliens.add(new Alien(x, y, type));
	}

	// This method will be called whenever the user presses a key; it is also
	// called automatically whenever the Timer fires
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();

		// Respond to the # player button presses
		if (c.equals("One Player")) {
			numberOfPlayers = 1;
			startGame();
		} else if (c.equals("Two Player")) {
			numberOfPlayers = 2;
			startGame();
		} else if (c.equals("Three Player")) {
			numberOfPlayers = 3;
			startGame();
		} else if (c.equals("Turret")) {
			gameVar.money -= 1000;
			Turret turret = new Turret();
			gameVar.turrets.add(turret);
			turret.setY(FIELD_HEIGHT - turret.getH());

			updateTurretPositions();
		} else if (c.equals("Mirror")) {
			int lowestHeight = 0;
			int lowestHeightIndex = 0; // Index of the lowest planet spot
			for (int f = 0; f < gameVar.earths.length; f++) {
				if (gameVar.mirrors[f] != null)
					continue;

				Earth earth = gameVar.earths[f];

				if (earth.getY() > lowestHeight) {
					lowestHeight = earth.getY();
					lowestHeightIndex = f;
				}
			}

			if (gameVar.mirrors[lowestHeightIndex] == null) {
				int x = gameVar.earths[lowestHeightIndex].getX();
				gameVar.mirrors[lowestHeightIndex] = new Mirror(x, FIELD_HEIGHT - 40);

				gameVar.money -= 2000;
			}
		} else if (c.equals("Heal")) {
			int lowestHeight = 0;
			int lowestHeightIndex = 0; // Index of the lowest planet spot
			for (int f = 0; f < gameVar.earths.length; f++) {
				if (gameVar.mirrors[f] != null)
					continue;

				Earth earth = gameVar.earths[f];

				if (earth.getY() > lowestHeight) {
					lowestHeight = earth.getY();
					lowestHeightIndex = f;
				}
			}
			
			gameVar.earths[lowestHeightIndex].heal();
			gameVar.money -= 500;
		} else if (c.equals("Timer")) {

			boolean gameDone = true;
			for (Shooter shooter : gameVar.shooters)
				if (!shooter.getIsDead())
					gameDone = false;
			
			if (gameDone)
				gameVar.gameOver = true;

			for (int i = 0; i < gameVar.turrets.size(); i++)
				if (gameVar.turrets.get(i).getIsDead()) {
					gameVar.turrets.remove(i);
					updateTurretPositions();
				}
			
			curAlienIncrease++;
			if (curAlienIncrease > RATE_INCREASE_TIME)
			{
				curAlienIncrease = 0;
				alienRate--;
			}
			
			if (alienRate < 5) alienRate = 5;
			
			// Update the ships
			for (int i = 0; i < gameVar.shooters.length; i++) {
				Shooter shooter = gameVar.shooters[i];
				shooter.update();

				// Missile movement
				for (int m = 0; m < shooter.getMissiles().size(); m++)
					shooter.getMissiles().get(m).moveMissile();

				if (shooter.getIsDead())
					continue;

				// Movement
				if (shooter.getRightPressed() && shooter.getX() < FIELD_WIDTH - gameFrame.getInsets().right * 2 - 10)
					shooter.setX(shooter.getX() + Shooter.SHOOTER_SPEED);
				if (shooter.getLeftPressed() && shooter.getX() > 3)
					shooter.setX(shooter.getX() - Shooter.SHOOTER_SPEED);
				if (shooter.getUpPressed() && shooter.getY() > 3)
					shooter.setY(shooter.getY() - Shooter.SHOOTER_SPEED);
				if (shooter.getDownPressed() && shooter.getY() < FIELD_HEIGHT - 50)
					shooter.setY(shooter.getY() + Shooter.SHOOTER_SPEED);
			}

			// Move the remaining aliens across the playing field
			for (int i = 0; i < aliens.size(); i++) {
				Alien alien = aliens.get(i);
				alien.moveAlien();
			}

			// Spawn aliens
			if (random.nextInt(alienRate / numberOfPlayers) == 0 && !gameVar.gameOver)
				addAlien();

			// Update the turrets
			for (Turret turret : gameVar.turrets)
				turret.update();

			// Update the turrets' missiles
			for (Missile missile : gameVar.turretMissiles)
				missile.moveMissile();

			// Have a random change for an alien to shoot a missile
			for (Alien alien : aliens)
				if (random.nextInt(500) == 0 && !gameVar.gameOver)
					alien.shoot();

			for (Missile missile : gameVar.alienMissiles)
				missile.moveMissile();

			// Only enable the buttons to purchase items if there is enough
			// money
			addHeal.setEnabled(gameVar.money >= 500 && !gameVar.gameOver);
			addTurret.setEnabled(gameVar.money >= 1000 && !gameVar.gameOver);
			addMirror.setEnabled(gameVar.money >= 2000 && !gameVar.gameOver);

			// Redraw/Update the playing field
			spacePanel.repaint();
			gameFrame.repaint();

			checkCollisions();
		}
	}

	private void updateTurretPositions() {
		int move = FIELD_WIDTH / (gameVar.turrets.size() + 1);

		for (int i = 0; i < gameVar.turrets.size(); i++)
			gameVar.turrets.get(i).setX(move * (i + 1));
	}

	// For every alien and missile currently on the playing field, create a
	// "rectangle" around both the alien and the missile, and then check to
	// see if the two rectangles intersect each other
	private void checkCollisions() {
		// The 'try-catch' exception trapping is needed to prevent an error from
		// occurring when an element is removed from the 'aliens' and 'missiles'
		// ArrayLists, causing the 'for' loops to end prematurely
		for (int i = 0; i < aliens.size(); i++) {
			for (int j = 0; j < gameVar.shooters.length; j++) {
				if (i > aliens.size() - 1)
					continue;
				Alien alien = aliens.get(i);
				Shooter shooter = gameVar.shooters[j];
				ArrayList<Missile> missiles = shooter.getMissiles();

				Rectangle rShooter = shooter.getRect();
				try {
					Rectangle rAlien = alien.getRect();

					if (rShooter.intersects(rAlien) && !shooter.getIsDead()) {

						if (!shooter.getIsInvinsible())
						{
							Explosion explosion = new Explosion(shooter.getX(), shooter.getY());
							gameVar.explosions.add(explosion);
							aliens.remove(i);
							
							shooter.loseLife();							
						}
					}
				} catch (Exception e) {
				}

				for (int m = 0; m < missiles.size(); m++) {

					Missile missile = missiles.get(m);
					try {
						Rectangle rAlien = alien.getRect();
						Rectangle rMissile = missile.getRect();

						// If an alien and a missile intersect each other,
						// remove both
						// of them from the playing field
						if (rAlien.intersects(rMissile)) {

							// Show the explosions
							Explosion explosion = new Explosion(aliens.get(i).getX(), aliens.get(i).getY());
							gameVar.explosions.add(explosion);
							aliens.remove(i);
							missiles.remove(m);
							shooter.addScore(1);
							gameVar.money += 50;
						}

						if (missile.getY() < 0 || missile.getY() > FIELD_HEIGHT)
							missiles.remove(m);
					} catch (Exception error) {
					}
				}
			}
		}

		// Remove alien missiles if they are off the screen
		for (int z = 0; z < gameVar.alienMissiles.size(); z++) {
			Missile missile = gameVar.alienMissiles.get(z);
			if (missile.getY() > FIELD_HEIGHT) {
				gameVar.alienMissiles.remove(z);
				gameVar.earthHealth -= 2;
				if (gameVar.earthHealth < 0)
					gameVar.earthHealth = 0;
			}
		}

		// Destroy aliens who get hit by the explosion
		for (Explosion explosion : gameVar.explosions) {
			Rectangle explosionR = new Rectangle(explosion.getX() + 25, explosion.getY() + 25, 50, 50);
			for (int i = 0; i < aliens.size(); i++) {
				Alien alien = aliens.get(i);
				Rectangle alienR = new Rectangle(alien.getX(), alien.getY(), alien.getWidth(), alien.getHeight());
				if (explosionR.intersects(alienR))
					aliens.remove(i);
			}
		}

		// Turret missile hit detection
		for (int m = 0; m < gameVar.turretMissiles.size(); m++) {
			Missile missile = gameVar.turretMissiles.get(m);
			for (int i = 0; i < aliens.size(); i++) {
				Alien alien = aliens.get(i);
				if (alien.getRect().intersects(missile.getRect())) {
					aliens.remove(i);
					gameVar.explosions.add(new Explosion(missile.getX(), missile.getY()));
					gameVar.turretMissiles.remove(m);
					gameVar.money += 50;
				}
			}
		}

		// Collision detection for alien missile and player / earth / turrets /
		// mirror
		for (int m = 0; m < gameVar.alienMissiles.size(); m++) {
			Missile missile = gameVar.alienMissiles.get(m);
			boolean hit = false;

			// Player collision detection with alien missile
			for (Shooter shooter : gameVar.shooters)
				if (missile.getRect().intersects(shooter.getRect()) && !shooter.getIsDead()) {
					if (!shooter.getIsInvinsible())
					{
						Explosion explosion = new Explosion(shooter.getX(), shooter.getY());
						gameVar.explosions.add(explosion);
						gameVar.alienMissiles.remove(m);

						shooter.loseLife();
						hit = true;
					}
				}

			if (hit)
				continue;

			// Earth collision detection with alien missile
			for (int i = 0; i < gameVar.earths.length; i++)
				if (gameVar.earths[i].getRect().intersects(missile.getRect()) && !gameVar.earths[i].getIsDead()) {
					gameVar.earths[i].takeDamage(2);
					gameVar.alienMissiles.remove(m);
					hit = true;
				}

			if (hit)
				continue;

			// Turret collision detection with alien missile
			for (Turret turret : gameVar.turrets)
				if (turret.getRect().intersects(missile.getRect())) {
					turret.takeDamage(2);
					if (m >= gameVar.alienMissiles.size()) {
						gameVar.alienMissiles.remove(m);
						hit = true;
					}
				}

			if (hit)
				continue;

			for (int j = 0; j < gameVar.mirrors.length; j++) {
				Mirror mirror = gameVar.mirrors[j];
				
				if (mirror == null) continue;
					
				if (mirror.getRect().intersects(missile.getRect())) {
					missile.setDirection(true);
					gameVar.turretMissiles.add(missile);
					gameVar.alienMissiles.remove(m);
				}
			}
		}

		// If all of the aliens have been destroyed, the game is over, so stop
		// the Timer and remove any remaining missiles from the playing field
		if (gameVar.earthHealth <= 0) {
			for (int s = 0; s < gameVar.shooters.length; s++) {
				Shooter shooter = gameVar.shooters[s];
				ArrayList<Missile> missiles = shooter.getMissiles();
				missiles.removeAll(missiles);
			}

			gameVar.gameOver = true;
		}
	}

	@Override
	public void keyPressed(KeyEvent event) {
		char k = event.getKeyChar();
		
		if (numberOfPlayers != 0) // If the game has started
		{
			if (k == '1')
				addTurret.doClick();
			else if (k == '2')
				addMirror.doClick();
			else if (k == '3')
				addHeal.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent event) {
		
	}
}