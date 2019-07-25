import java.awt.Image;
import java.util.ArrayList;

import javax.swing.*;

// This class, with a default (empty) constructor, sets up global variables
//  (as opposed to global constants, which are defined in a different file)
//  that can be accessed (and modified) from multiple files; the variables
//  are accessed through an instance of this class, which is created in the
//  'GlobalConst' interface 

// Allowing direct access to variables in this class may not be the best
//  programming practice; some will argue (perhaps correctly) that the variables
//  should all be made private and then accessed through public accessor and
//  modifier methods (to maintain the programming concept of information hiding)

// Regardless, programmatically this approach is almost certainly better than
//  defining the variables in the 'Game' class and making them all static,
//  since doing so would associate the variables with the classes themselves,
//  not with instances of the classes (which can cause unintended side effects)
public class GlobalVar {
	Shooter[] shooters;
	ArrayList<Explosion> explosions = new ArrayList<>();
	ArrayList<Missile> alienMissiles = new ArrayList<>(), turretMissiles = new ArrayList<>();
	ArrayList<Turret> turrets = new ArrayList<>();
	Earth[] earths = new Earth[5];
	Mirror[] mirrors = new Mirror[earths.length];
	boolean gameOver = false;

	int earthHealthMax = 100;
	int earthHealth = earthHealthMax;

	int resizedWidth = 1300;
	int resizedHeight = (int) (resizedWidth * GlobalConst.WtoH);
	int frameX = 0;
	int frameY = 0;

	int numberOfPlayers = 0;

	int money = 0;

	int panelWidth = resizedWidth;
	int panelHeight = resizedHeight;

	// The Images below are, technically, constants, since their values are not
	// changed (new graphics are not loaded from disk) once they have been
	// initialized, but they are defined in this class (as opposed to the
	// interface that contains global constants) because the 'getClass' method
	// below cannot be used in an interface; while it is true that the Images
	// could be loaded without the use of the 'getClass' (and 'getResource')
	// methods, using those two methods allows all of the files that make up
	// this
	// program (the .CLASS files and the graphics files) to be put into a single
	// .JAR file and then loaded and run directly from that (executable) file;
	// also, a benefit of using the 'ImageIcon' class is that, unlike some of
	// the
	// other file-loading classes, the 'ImageIcon' class fully loads the Image
	// when the object is created, making it possible to immediately determine
	// and use the dimensions of the Image
	Image backgroundImage = new ImageIcon(getClass().getResource("spacebg.png")).getImage();
	Image shooterImage = new ImageIcon(getClass().getResource("shooter1.png")).getImage();
	Image alienImage1 = new ImageIcon(getClass().getResource("alien.png")).getImage();
	Image alienImage2 = new ImageIcon(getClass().getResource("alien2.png")).getImage();
	Image missileImage = new ImageIcon(getClass().getResource("missile.png")).getImage();
	Image earthImage = new ImageIcon(getClass().getResource("earth.png")).getImage();
	Image turetImage = new ImageIcon(getClass().getResource("turet.png")).getImage();
	Image mirrorImage = new ImageIcon(getClass().getResource("mirror.png")).getImage();
}
