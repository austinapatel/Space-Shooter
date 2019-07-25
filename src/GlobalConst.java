import java.util.ArrayList;

// This interface provides global content to the entire game; note that
//  the keyword 'final' used below is, technically, not necessary, since
//  all "variables" in an interface are actually static constants (because
//  interfaces cannot be instantiated, so no instances of the "variables"
//  exist to be re-assigned by program code)

// Note, also, that this file is defined as an interface (as opposed to an
//  abstract class) so that the other classes in this program can implement
//  it (as opposed to extending it); this frees up those classes to extend
//  other classes

// Finally, note that even though some of the constants below are used in
//  only one file, having all of the game constants in one place (here)
//  makes them more accessible to anyone viewing or editing the game code

public interface GlobalConst
{
	final int FIELD_WIDTH = 500;
	final int FIELD_HEIGHT = 400;
	
	final float WtoH = 0.8f;

	final int TIMER_SPEED = 10;
	final int TIMER_DELAY = 750;

	final int SHOOTER_SPEED = 2;
	final int ALIEN_SPEED = 1;
	final int MISSILE_SPEED = 2;

	final int NUM_ALIENS = 10;

	// Set up ArrayLists to hold all of the 'Alien' and 'Missile' objects
	//  that will be used throughout the game
	ArrayList<Alien> aliens = new ArrayList<Alien>();

	// Allow global variables (as opposed to global constants) from the
	//  'GlobalVar' class to be accessed and modified from multiple files 
	GlobalVar gameVar = new GlobalVar();
}
