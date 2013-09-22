/*Shahzaib Bhatti, Saad Asif
  January 18, 2010
  Final assignmnet
  The purpose of this program is to make a game using applets in ready to program java

S and S Productions present Tetris, a remake

    The objective of tetris is to move and rotate shapes to create a line at the bottom
of the screen which will go away when created and add to the users point. Pieces are
moves with the arrow keys and spun with the space bar. By destroying 1 line, the user
gains one thousand points and a second line in a combo gives an additional two thousand
points. However a third line in a combo will give the user one thousand points, but the
forth will give the user 3000. This is to premote the user to go for a 4 part combo instead
of settling for a threee part one. The user will gain a level every 10000 points.
Like classic tetris, the next piece that the user will be getting will be displayed.
Also like classic tetris a tumbling effect will not be in play and a row will move
down when the previous row was destroyed exacly as it was. Like new tetris hoever, the user
can hold a piece to the side by pressing the 'c' key, the user can only do once per piece
and switch out to it at these times. Like classic tetris an Inifinite spin trick comes
into play, to give the user time to think, by continously rotating (holding the space bar)
the users piece will not move down. Like tetris, when it is inconvenient for a piece to
rotate, it will not. Press Enter to start game when ready.

Notes to teacher:
Sometimes when a piece should appear it does not and that is a graphics error, it should appear
after moving down one point.
Pictures were declares properly however continously recieved errors, because of this pieces
were contructed block by block for next and held piece locations

Job Distribution:

Shahzaib:
Next Piece
Held Piece

Saad:
Picture creating
Picture Editing

The rest and most of the program was cooperative effort and equally shared work
*/

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

import java.awt.*;
import java.awt.font.*;
import java.applet.*;
import java.util.*;
import java.lang.*;
// These classes are for Url's.
import java.net.*;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class Tetris extends Applet implements Runnable
{

    int appletsize_x = 386;
    int appletsize_y = 382;

    // Your image name;
    Image background;
    Image backgroundPlay;
    Image gameOverScreen;
    Image blue;
    Image cyan;
    Image green;
    Image magenta;
    Image orange;
    Image red;
    Image yellow;

    // The applet base URL
    URL base;

    // This object will allow you to control loading
    MediaTracker mt;

    // declare two instance variables at the head of the program
    private Image dbImage;
    private Graphics dbg;

    public static Random generator = new Random ();

    //Sets variables for grid
    final public static int COLUMN_NUMBER = 10;
    final public static int ROW_NUMBER = 18;
    final public static boolean[] LINE_COMPLETE = new boolean [ROW_NUMBER - 4];
    final public static boolean[] [] TETRIS_GRID = new boolean [ROW_NUMBER] [COLUMN_NUMBER];
    final public static boolean[] [] CURRENT_PIECE_GRID_0 = new boolean [ROW_NUMBER] [COLUMN_NUMBER];
    final public static boolean[] [] CURRENT_PIECE_GRID_90 = new boolean [ROW_NUMBER] [COLUMN_NUMBER];
    final public static boolean[] [] CURRENT_PIECE_GRID_180 = new boolean [ROW_NUMBER] [COLUMN_NUMBER];
    final public static boolean[] [] CURRENT_PIECE_GRID_270 = new boolean [ROW_NUMBER] [COLUMN_NUMBER];

    public static int score = 0;
    public static int level = 1;
    final public static int POINTS = 1000;
    final public static int LEVELER = 10000;


    //Colours

    final public static int NUMBER_OF_COLOURS = 7;
    final public static int BLUE_BLOCK = 0;
    final public static int CYAN_BLOCK = 1;
    final public static int MAGENTA_BLOCK = 2;
    final public static int GREEN_BLOCK = 3;
    final public static int YELLOW_BLOCK = 4;
    final public static int ORANGE_BLOCK = 5;
    final public static int RED_BLOCK = 6;
    final public static int[] [] CURRENT_BLOCK_COLOUR = new int [ROW_NUMBER] [COLUMN_NUMBER];
    final public static int[] [] TETRIS_BLOCK_COLOUR = new int [ROW_NUMBER] [COLUMN_NUMBER];

    //Numbered coordinates for Row's Xs
    final public static int FIRST_X = 11;
    final public static int PIECE_X_DIFFERENCE = 20;

    //Position X for all rows
    //Equation is X position equals the first X Position plus the piece width times the number of pieces before it
    final public static int[] COLUMN_X = new int [COLUMN_NUMBER];

    //Numbered coordinates for Columns's ys
    final public static int FIRST_Y = 351;
    final public static int PIECE_Y_DIFFERENCE = 20;

    //Position X for all rows
    //Equation is X position equals the first X Position plus the piece width times the number of pieces before it
    final public static int[] ROW_Y = new int [ROW_NUMBER];

    //Rotation variable
    final public static int ORIGINAL_ROTATION = 0;
    final public static int NINTY_ROTATION = 1;
    final public static int ONE_EIGHTY_ROTATION = 2;
    final public static int TWO_SEVENTY_ROTATION = 3;

    //Piece type
    final public static int NUMBER_OF_PIECE_TYPES = 7;
    final public static int T_PIECE = 0;
    final public static int L_PIECE = 1;
    final public static int J_PIECE = 2;
    final public static int I_PIECE = 3;
    final public static int O_PIECE = 4;
    final public static int Z_PIECE = 5;
    final public static int S_PIECE = 6;

    //Stored piece type indicator
    public static int CURRENT_PIECE_TYPE;
    public static int NEXT_PIECE_TYPE;
    public static int HELD_PIECE_TYPE = -1;
    public static int SWITCH_PIECE_HOLDER;

    //Pause times in game, numbers in milliseconds
    final public static int ORIGINAL_PAUSE_TIME = 1000;
    public static int pausedTime = 1000;
    final public static int PAUSE_DECREASE = 50;
    final public static int SMALL_PAUSE_TIME = 10;
    final public static int MOVEMENT_PAUSE_TIME = 100;
    public static int PAUSE_START = 0;

    //Piece References for contructing the held and next piece
    final public static int NEXT_PIECE_Y_REFERENCE = 40;
    final public static int HELD_PIECE_Y_REFERENCE = 135;
    final public static int BOTH_PIECE_X_REFERENCE = 280;

    //Keeps track of current rotation type
    public static int CURRENT_ROTATION = 0;

    //results from keyboard inputs to do certain tasks
    final public static int NO_INPUT = 0;
    final public static int FORCE_DROP = 1;
    final public static int MOVE_DOWN = 2;
    final public static int MOVE_LEFT = 3;
    final public static int MOVE_RIGHT = 4;
    final public static int ROTATE_PIECE = 5;
    final public static int STORE_PIECE = 6;
    final public static int START_GAME = 7;

    //Keyboard Character values
    final public static int UP_ARROW = 1004;
    final public static int DOWN_ARROW = 1005;
    final public static int RIGHT_ARROW = 1006;
    final public static int LEFT_ARROW = 1007;
    final public static int SPACE_BAR = 32;
    final public static int C_KEY = 99;
    final public static int ENTER_KEY = 10;

    //Lock variables for locking at bottom or on piece and exit variable
    public static boolean lock0 = false;
    public static boolean lock1 = false;
    public static boolean lock2 = false;
    public static boolean lock3 = false;
    public static boolean exit = false;

    //keeps track of keyboard input
    public static int keyboardCount = 0;

    //sees if force drop was activated
    public static boolean forceDropCheck = false;

    //Sees if user subbed a piece out
    public static boolean subPieceOut = false;

    //build on from before, preventing user from subbing more than once a piece
    public static boolean holdPieceLimit = false;

    //tracks if user loses
    public static boolean gameOver = false;

    //prevent the piece from going too far left or right for rotation 1
    public static boolean leftLock0 = false;
    public static boolean movedLeft0 = false;
    public static boolean rightLock0 = false;
    public static boolean movedRight0 = false;

    //prevent the piece from going too far left or right for rotation 2
    public static boolean leftLock1 = false;
    public static boolean movedLeft1 = false;
    public static boolean rightLock1 = false;
    public static boolean movedRight1 = false;

    //prevent the piece from going too far left or right for rotation 3
    public static boolean leftLock2 = false;
    public static boolean movedLeft2 = false;
    public static boolean rightLock2 = false;
    public static boolean movedRight2 = false;

    //prevent the piece from going too far left or right for rotation 4
    public static boolean leftLock3 = false;
    public static boolean movedLeft3 = false;
    public static boolean rightLock3 = false;
    public static boolean movedRight3 = false;

    //Checks if the piece rotates
    public static boolean rotateCheck = false;

    //prevent rotation in sticky situations
    public static boolean preventRotation0 = false;
    public static boolean preventRotation1 = false;
    public static boolean preventRotation2 = false;
    public static boolean preventRotation3 = false;

    //Checks when game starts
    public static boolean gameStart = false;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void init ()
    {

	//sets font
	Font f3 = new Font ("Bauhaus 93", Font.PLAIN, 24);
	setFont (f3);

	AudioClip gong = getAudioClip (getDocumentBase (), "Resources/Classic+VGM+65-+Tetris+-+Theme+A.wav");
	gong.loop ();


	// initialize the MediaTracker
	mt = new MediaTracker (this);

	// The try-catch is necassary when the URL isn't valid
	// Ofcourse this one is valid, since it is generated by
	// Java itself.

	try
	{
	    // getDocumentbase gets the applet path.
	    base = getDocumentBase ();
	}
	catch (Exception e)
	{
	}

	// Here we load the images.
	background = getImage (base, "Resources/background.png");
	backgroundPlay = getImage (base, "Resources/background_play.png");
	gameOverScreen = getImage (base, "Resources/game_over.png");
	blue = getImage (base, "Resources/blue.png");
	cyan = getImage (base, "Resources/cyan.png");
	green = getImage (base, "Resources/green.png");
	magenta = getImage (base, "Resources/magenta.png");
	orange = getImage (base, "Resources/orange.png");
	red = getImage (base, "Resources/red.png");
	yellow = getImage (base, "Resources/yellow.png");

	// tell the MediaTracker to kep an eye on this image, and give it ID 1;
	mt.addImage (background, 1);

	//initialize the random generator to choose which piece


	// now tell the mediaTracker to stop the applet execution
	// (in this example don't paint) until the images are fully loaded.
	// must be in a try catch block.

	try
	{
	    mt.waitForAll ();
	}
	catch (InterruptedException e)
	{
	}

	// when the applet gets here then the images is loaded.

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void start ()
    {

	// define a new thread
	Thread th = new Thread (this);
	// start this thread
	th.start ();

	//Sets the varaible for completed lines to false
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    LINE_COMPLETE [i] = false;
	}

	//Sets all grid variables to false
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		TETRIS_GRID [j] [i] = false;
	    }
	}

	//Sets all current grid variables to false
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		CURRENT_PIECE_GRID_0 [j] [i] = false;
	    }
	}


	//greats values for all our imaginary grid locations
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    COLUMN_X [i] = FIRST_X + PIECE_X_DIFFERENCE * i;
	}
	for (int i = 0 ; i < ROW_NUMBER ; i++)
	{
	    ROW_Y [i] = FIRST_Y - PIECE_Y_DIFFERENCE * i;
	}

	NEXT_PIECE_TYPE = generator.nextInt (NUMBER_OF_PIECE_TYPES);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void update (Graphics g)
    {

	// initialize buffer
	if (dbImage == null)
	{
	    dbImage = createImage (this.getSize ().width, this.getSize ().height);
	    dbg = dbImage.getGraphics ();
	}

	// clear screen in backgroundStart
	dbg.setColor (getBackground ());
	dbg.fillRect (0, 0, this.getSize ().width, this.getSize ().height);

	// draw elements in backgroundStart
	dbg.setColor (getForeground ());
	paint (dbg);

	// draw image on the screen
	g.drawImage (dbImage, 0, 0, this);

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //whats gets updated
    public void run ()
    {
	while (true)
	{
	    //starts game when enter pressed
	    if (keyboardCount == START_GAME)
	    {
		gameStart = true;
	    }
	    //actually game
	    while (gameStart == true)
	    {
		while (true)
		{
		    //finds out when user gets  agame over
		    for (int i = 14 ; i < ROW_NUMBER ; i++)
		    {
			for (int j = 0 ; j < COLUMN_NUMBER ; j++)
			{
			    if (TETRIS_GRID [i] [j] == true)
			    {
				gameOver = true;
			    }
			}
		    }
		    //ends game
		    if (gameOver == true)
		    {
			repaint ();
			break;
		    }
		    //destroys complete line
		    lineDestroyer ();
		    //spawns piece at top
		    spawnRandomPiece ();
		    //updates screen
		    repaint ();
		    //resets variables
		    exit = false;
		    lock0 = false;
		    lock1 = false;
		    lock2 = false;
		    lock3 = false;
		    subPieceOut = false;
		    //checks if piece should go down only if piece did not move left or right to prevent glitch
		    while (exit == false && subPieceOut == false)
		    {
			pausePhase ();
			if (movedLeft0 == false && movedRight0 == false && movedLeft1 == false && movedRight1 == false && movedLeft2 == false && movedRight2 == false && movedLeft3 == false && movedRight3 == false && rotateCheck == false)
			{
			    fallCheck ();
			}
			repaint ();
			//creates a pause so piece is not shot left or right when pressed
			if (movedLeft0 == true || movedRight0 == true || movedLeft1 == true || movedRight1 == true || movedLeft2 == true || movedRight2 == true || movedLeft3 == true || movedRight3 == true || rotateCheck == true)
			{
			    try
			    {
				Thread.sleep (MOVEMENT_PAUSE_TIME);
			    }
			    catch (Exception e)
			    {
			    }
			}
			//resets more variables
			movedLeft0 = false;
			movedRight0 = false;
			movedLeft1 = false;
			movedRight1 = false;
			movedLeft2 = false;
			movedRight2 = false;
			movedLeft3 = false;
			movedRight3 = false;
			rotateCheck = false;
		    }
		}
	    }
	}
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //draws things to screen
    public void paint (Graphics g)
    {
	//Draws background
	if (gameStart == false)
	{
	    g.drawImage (background, 0, 0, this);
	}
	else
	{
	    g.drawImage (backgroundPlay, 0, 0, this);
	}

	//fills grid with pieces if they locked in
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		if (TETRIS_GRID [j] [i] == true)
		{
		    if (TETRIS_BLOCK_COLOUR [j] [i] == BLUE_BLOCK)
		    {
			g.drawImage (blue, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (TETRIS_BLOCK_COLOUR [j] [i] == CYAN_BLOCK)
		    {
			g.drawImage (cyan, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (TETRIS_BLOCK_COLOUR [j] [i] == MAGENTA_BLOCK)
		    {
			g.drawImage (magenta, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (TETRIS_BLOCK_COLOUR [j] [i] == GREEN_BLOCK)
		    {
			g.drawImage (green, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (TETRIS_BLOCK_COLOUR [j] [i] == YELLOW_BLOCK)
		    {
			g.drawImage (yellow, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (TETRIS_BLOCK_COLOUR [j] [i] == ORANGE_BLOCK)
		    {
			g.drawImage (orange, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (TETRIS_BLOCK_COLOUR [j] [i] == RED_BLOCK)
		    {
			g.drawImage (red, COLUMN_X [i], ROW_Y [j], this);
		    }
		}
	    }
	}


	//filles with pieces for current moving piece depending on rotation
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		if (CURRENT_PIECE_GRID_0 [j] [i] == true && CURRENT_ROTATION == ORIGINAL_ROTATION)
		{
		    if (CURRENT_BLOCK_COLOUR [j] [i] == BLUE_BLOCK)
		    {
			g.drawImage (blue, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == CYAN_BLOCK)
		    {
			g.drawImage (cyan, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == MAGENTA_BLOCK)
		    {
			g.drawImage (magenta, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == GREEN_BLOCK)
		    {
			g.drawImage (green, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == YELLOW_BLOCK)
		    {
			g.drawImage (yellow, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == ORANGE_BLOCK)
		    {
			g.drawImage (orange, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == RED_BLOCK)
		    {
			g.drawImage (red, COLUMN_X [i], ROW_Y [j], this);
		    }
		}
	    }
	}


	//filles with pieces for current moving piece depending on rotation
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		if (CURRENT_PIECE_GRID_90 [j] [i] == true && CURRENT_ROTATION == NINTY_ROTATION)
		{
		    if (CURRENT_BLOCK_COLOUR [j] [i] == BLUE_BLOCK)
		    {
			g.drawImage (blue, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == CYAN_BLOCK)
		    {
			g.drawImage (cyan, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == MAGENTA_BLOCK)
		    {
			g.drawImage (magenta, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == GREEN_BLOCK)
		    {
			g.drawImage (green, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == YELLOW_BLOCK)
		    {
			g.drawImage (yellow, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == ORANGE_BLOCK)
		    {
			g.drawImage (orange, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == RED_BLOCK)
		    {
			g.drawImage (red, COLUMN_X [i], ROW_Y [j], this);
		    }
		}
	    }
	}



	//filles with pieces for current moving piece depending on rotation
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		if (CURRENT_PIECE_GRID_180 [j] [i] == true && CURRENT_ROTATION == ONE_EIGHTY_ROTATION)
		{
		    if (CURRENT_BLOCK_COLOUR [j] [i] == BLUE_BLOCK)
		    {
			g.drawImage (blue, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == CYAN_BLOCK)
		    {
			g.drawImage (cyan, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == MAGENTA_BLOCK)
		    {
			g.drawImage (magenta, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == GREEN_BLOCK)
		    {
			g.drawImage (green, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == YELLOW_BLOCK)
		    {
			g.drawImage (yellow, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == ORANGE_BLOCK)
		    {
			g.drawImage (orange, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == RED_BLOCK)
		    {
			g.drawImage (red, COLUMN_X [i], ROW_Y [j], this);
		    }
		}
	    }
	}


	//filles with pieces for current moving piece depending on rotation
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		if (CURRENT_PIECE_GRID_270 [j] [i] == true && CURRENT_ROTATION == TWO_SEVENTY_ROTATION)
		{
		    if (CURRENT_BLOCK_COLOUR [j] [i] == BLUE_BLOCK)
		    {
			g.drawImage (blue, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == CYAN_BLOCK)
		    {
			g.drawImage (cyan, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == MAGENTA_BLOCK)
		    {
			g.drawImage (magenta, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == GREEN_BLOCK)
		    {
			g.drawImage (green, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == YELLOW_BLOCK)
		    {
			g.drawImage (yellow, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == ORANGE_BLOCK)
		    {
			g.drawImage (orange, COLUMN_X [i], ROW_Y [j], this);
		    }
		    else if (CURRENT_BLOCK_COLOUR [j] [i] == RED_BLOCK)
		    {
			g.drawImage (red, COLUMN_X [i], ROW_Y [j], this);
		    }
		}
	    }
	}


	// this draws the next peice, accorind to the one generated
	if (NEXT_PIECE_TYPE == T_PIECE)
	{
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	}
	else if (NEXT_PIECE_TYPE == L_PIECE)
	{
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	}
	else if (NEXT_PIECE_TYPE == J_PIECE)
	{
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);

	}
	else if (NEXT_PIECE_TYPE == I_PIECE)
	{
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE * 2, NEXT_PIECE_Y_REFERENCE, this);
	}
	else if (NEXT_PIECE_TYPE == O_PIECE)
	{
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	}
	else if (NEXT_PIECE_TYPE == Z_PIECE)
	{
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);

	}
	else if (NEXT_PIECE_TYPE == S_PIECE)
	{
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE, this);
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, NEXT_PIECE_Y_REFERENCE, this);

	}

	//this draws the help peice if the user has one selected
	if (HELD_PIECE_TYPE == T_PIECE)
	{
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (magenta, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	}
	else if (HELD_PIECE_TYPE == L_PIECE)
	{
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (orange, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	}
	else if (HELD_PIECE_TYPE == J_PIECE)
	{
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE + PIECE_Y_DIFFERENCE, this);
	    g.drawImage (blue, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);

	}
	else if (HELD_PIECE_TYPE == I_PIECE)
	{
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (cyan, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE * 2, HELD_PIECE_Y_REFERENCE, this);
	}
	else if (HELD_PIECE_TYPE == O_PIECE)
	{
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (yellow, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	}
	else if (HELD_PIECE_TYPE == Z_PIECE)
	{
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (red, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);

	}
	else if (HELD_PIECE_TYPE == S_PIECE)
	{
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE, this);
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE + PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE - PIECE_Y_DIFFERENCE, this);
	    g.drawImage (green, BOTH_PIECE_X_REFERENCE - PIECE_X_DIFFERENCE, HELD_PIECE_Y_REFERENCE, this);

	}

	g.drawString (Integer.toString (score), 280, 262);
	g.drawString (Integer.toString (level), 280, 229);

	//overwrites all drawings if user loses with the game over screen
	if (gameOver == true)
	{
	    g.drawImage (gameOverScreen, 0, 0, this);
	}

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void lineDestroyer ()
    {
	//Sets the varaible for completed lines to true
	for (int q = 0 ; q < 4 ; q++)
	{
	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		LINE_COMPLETE [i] = true;
	    }
	    //checks if the line wasnt actually completed
	    for (int i = 0 ; i < ROW_NUMBER - 4 ; i++)
	    {
		for (int j = 0 ; j < COLUMN_NUMBER ; j++)
		{
		    if (TETRIS_GRID [i] [j] == false)
		    {
			LINE_COMPLETE [i] = false;
		    }
		}
	    }
	    // destroys the completed lines (if any) then moves the lines above the completed line down
	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		if (LINE_COMPLETE [i] == true)
		{
		    score = score + POINTS;
		    for (int a = i ; a < ROW_NUMBER - 1 ; a++)
		    {
			for (int p = 0 ; p < COLUMN_NUMBER ; p++)
			{
			    TETRIS_GRID [a] [p] = false;
			    TETRIS_BLOCK_COLOUR [a] [p] = TETRIS_BLOCK_COLOUR [a + 1] [p];
			    TETRIS_GRID [a] [p] = TETRIS_GRID [a + 1] [p];
			}
		    }
		}
	    }
	}
	//re-evaluates the score and the delay if needed
	level = (score / LEVELER) + 1;
	if (pausedTime > 50)
	{
	    pausedTime = ORIGINAL_PAUSE_TIME - (PAUSE_DECREASE * (level - 1));
	}
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void spawnRandomPiece ()
    {
	// resets variables
	CURRENT_ROTATION = 0;
	preventRotation0 = false;
	preventRotation1 = false;
	preventRotation2 = false;
	preventRotation3 = false;

	// switches selected piece to the next peice if the user uses the switrch function for the first time
	if (subPieceOut == false)
	{
	    CURRENT_PIECE_TYPE = NEXT_PIECE_TYPE;
	    NEXT_PIECE_TYPE = generator.nextInt (NUMBER_OF_PIECE_TYPES);
	}
	// switches out pieces if the user uses the held function
	else if (subPieceOut == true)
	{
	    SWITCH_PIECE_HOLDER = CURRENT_PIECE_TYPE;
	    if (HELD_PIECE_TYPE != -1)
	    {
		CURRENT_PIECE_TYPE = HELD_PIECE_TYPE;
	    }
	    //changes peices after one is placed
	    else
	    {
		CURRENT_PIECE_TYPE = NEXT_PIECE_TYPE;
		NEXT_PIECE_TYPE = generator.nextInt (NUMBER_OF_PIECE_TYPES);
	    }
	    HELD_PIECE_TYPE = SWITCH_PIECE_HOLDER;
	}

	//the following sets colors and coordiantes for peices based upon the random one generated
	if (CURRENT_PIECE_TYPE == T_PIECE)
	{

	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		for (int j = 0 ; j < ROW_NUMBER ; j++)
		{
		    CURRENT_BLOCK_COLOUR [j] [i] = MAGENTA_BLOCK;
		}
	    }

	    CURRENT_PIECE_GRID_0 [17] [4] = true;
	    CURRENT_PIECE_GRID_0 [17] [5] = true;
	    CURRENT_PIECE_GRID_0 [17] [6] = true;
	    CURRENT_PIECE_GRID_0 [16] [5] = true;

	    CURRENT_PIECE_GRID_90 [17] [5] = true;
	    CURRENT_PIECE_GRID_90 [16] [5] = true;
	    CURRENT_PIECE_GRID_90 [16] [4] = true;
	    CURRENT_PIECE_GRID_90 [15] [5] = true;

	    CURRENT_PIECE_GRID_180 [17] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [4] = true;
	    CURRENT_PIECE_GRID_180 [16] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [6] = true;

	    CURRENT_PIECE_GRID_270 [17] [5] = true;
	    CURRENT_PIECE_GRID_270 [16] [5] = true;
	    CURRENT_PIECE_GRID_270 [16] [6] = true;
	    CURRENT_PIECE_GRID_270 [15] [5] = true;


	}


	else if (CURRENT_PIECE_TYPE == L_PIECE)
	{

	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		for (int j = 0 ; j < ROW_NUMBER ; j++)
		{
		    CURRENT_BLOCK_COLOUR [j] [i] = ORANGE_BLOCK;
		}
	    }

	    CURRENT_PIECE_GRID_0 [17] [4] = true;
	    CURRENT_PIECE_GRID_0 [16] [4] = true;
	    CURRENT_PIECE_GRID_0 [15] [4] = true;
	    CURRENT_PIECE_GRID_0 [15] [5] = true;

	    CURRENT_PIECE_GRID_90 [17] [4] = true;
	    CURRENT_PIECE_GRID_90 [17] [5] = true;
	    CURRENT_PIECE_GRID_90 [17] [6] = true;
	    CURRENT_PIECE_GRID_90 [16] [4] = true;

	    CURRENT_PIECE_GRID_180 [17] [4] = true;
	    CURRENT_PIECE_GRID_180 [17] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [5] = true;
	    CURRENT_PIECE_GRID_180 [15] [5] = true;

	    CURRENT_PIECE_GRID_270 [16] [4] = true;
	    CURRENT_PIECE_GRID_270 [16] [5] = true;
	    CURRENT_PIECE_GRID_270 [16] [6] = true;
	    CURRENT_PIECE_GRID_270 [17] [6] = true;

	}


	else if (CURRENT_PIECE_TYPE == J_PIECE)
	{

	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		for (int j = 0 ; j < ROW_NUMBER ; j++)
		{
		    CURRENT_BLOCK_COLOUR [j] [i] = BLUE_BLOCK;
		}
	    }

	    CURRENT_PIECE_GRID_0 [17] [5] = true;
	    CURRENT_PIECE_GRID_0 [16] [5] = true;
	    CURRENT_PIECE_GRID_0 [15] [5] = true;
	    CURRENT_PIECE_GRID_0 [15] [4] = true;

	    CURRENT_PIECE_GRID_90 [17] [4] = true;
	    CURRENT_PIECE_GRID_90 [16] [4] = true;
	    CURRENT_PIECE_GRID_90 [16] [5] = true;
	    CURRENT_PIECE_GRID_90 [16] [6] = true;

	    CURRENT_PIECE_GRID_180 [17] [4] = true;
	    CURRENT_PIECE_GRID_180 [17] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [4] = true;
	    CURRENT_PIECE_GRID_180 [15] [4] = true;

	    CURRENT_PIECE_GRID_270 [17] [4] = true;
	    CURRENT_PIECE_GRID_270 [17] [5] = true;
	    CURRENT_PIECE_GRID_270 [17] [6] = true;
	    CURRENT_PIECE_GRID_270 [16] [6] = true;

	}


	else if (CURRENT_PIECE_TYPE == I_PIECE)
	{

	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		for (int j = 0 ; j < ROW_NUMBER ; j++)
		{
		    CURRENT_BLOCK_COLOUR [j] [i] = CYAN_BLOCK;
		}
	    }

	    CURRENT_PIECE_GRID_0 [17] [4] = true;
	    CURRENT_PIECE_GRID_0 [16] [4] = true;
	    CURRENT_PIECE_GRID_0 [15] [4] = true;
	    CURRENT_PIECE_GRID_0 [14] [4] = true;

	    CURRENT_PIECE_GRID_90 [14] [3] = true;
	    CURRENT_PIECE_GRID_90 [14] [4] = true;
	    CURRENT_PIECE_GRID_90 [14] [5] = true;
	    CURRENT_PIECE_GRID_90 [14] [6] = true;

	    CURRENT_PIECE_GRID_180 [17] [4] = true;
	    CURRENT_PIECE_GRID_180 [16] [4] = true;
	    CURRENT_PIECE_GRID_180 [15] [4] = true;
	    CURRENT_PIECE_GRID_180 [14] [4] = true;

	    CURRENT_PIECE_GRID_270 [14] [3] = true;
	    CURRENT_PIECE_GRID_270 [14] [4] = true;
	    CURRENT_PIECE_GRID_270 [14] [5] = true;
	    CURRENT_PIECE_GRID_270 [14] [6] = true;

	}


	else if (CURRENT_PIECE_TYPE == O_PIECE)
	{

	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		for (int j = 0 ; j < ROW_NUMBER ; j++)
		{
		    CURRENT_BLOCK_COLOUR [j] [i] = YELLOW_BLOCK;
		}
	    }

	    CURRENT_PIECE_GRID_0 [17] [4] = true;
	    CURRENT_PIECE_GRID_0 [17] [5] = true;
	    CURRENT_PIECE_GRID_0 [16] [4] = true;
	    CURRENT_PIECE_GRID_0 [16] [5] = true;

	    CURRENT_PIECE_GRID_90 [17] [4] = true;
	    CURRENT_PIECE_GRID_90 [17] [5] = true;
	    CURRENT_PIECE_GRID_90 [16] [4] = true;
	    CURRENT_PIECE_GRID_90 [16] [5] = true;

	    CURRENT_PIECE_GRID_180 [17] [4] = true;
	    CURRENT_PIECE_GRID_180 [17] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [4] = true;
	    CURRENT_PIECE_GRID_180 [16] [5] = true;

	    CURRENT_PIECE_GRID_270 [17] [4] = true;
	    CURRENT_PIECE_GRID_270 [17] [5] = true;
	    CURRENT_PIECE_GRID_270 [16] [4] = true;
	    CURRENT_PIECE_GRID_270 [16] [5] = true;

	}


	else if (CURRENT_PIECE_TYPE == Z_PIECE)
	{
	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		for (int j = 0 ; j < ROW_NUMBER ; j++)
		{
		    CURRENT_BLOCK_COLOUR [j] [i] = RED_BLOCK;
		}
	    }

	    CURRENT_PIECE_GRID_0 [17] [4] = true;
	    CURRENT_PIECE_GRID_0 [17] [5] = true;
	    CURRENT_PIECE_GRID_0 [16] [5] = true;
	    CURRENT_PIECE_GRID_0 [16] [6] = true;

	    CURRENT_PIECE_GRID_90 [17] [5] = true;
	    CURRENT_PIECE_GRID_90 [16] [5] = true;
	    CURRENT_PIECE_GRID_90 [16] [4] = true;
	    CURRENT_PIECE_GRID_90 [15] [4] = true;

	    CURRENT_PIECE_GRID_180 [17] [4] = true;
	    CURRENT_PIECE_GRID_180 [17] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [6] = true;

	    CURRENT_PIECE_GRID_270 [17] [5] = true;
	    CURRENT_PIECE_GRID_270 [16] [5] = true;
	    CURRENT_PIECE_GRID_270 [16] [4] = true;
	    CURRENT_PIECE_GRID_270 [15] [4] = true;
	}


	else if (CURRENT_PIECE_TYPE == S_PIECE)
	{

	    for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	    {
		for (int j = 0 ; j < ROW_NUMBER ; j++)
		{
		    CURRENT_BLOCK_COLOUR [j] [i] = GREEN_BLOCK;
		}
	    }

	    CURRENT_PIECE_GRID_0 [17] [4] = true;
	    CURRENT_PIECE_GRID_0 [17] [5] = true;
	    CURRENT_PIECE_GRID_0 [16] [4] = true;
	    CURRENT_PIECE_GRID_0 [16] [3] = true;

	    CURRENT_PIECE_GRID_90 [17] [4] = true;
	    CURRENT_PIECE_GRID_90 [16] [4] = true;
	    CURRENT_PIECE_GRID_90 [16] [5] = true;
	    CURRENT_PIECE_GRID_90 [15] [5] = true;

	    CURRENT_PIECE_GRID_180 [17] [4] = true;
	    CURRENT_PIECE_GRID_180 [17] [5] = true;
	    CURRENT_PIECE_GRID_180 [16] [4] = true;
	    CURRENT_PIECE_GRID_180 [16] [3] = true;

	    CURRENT_PIECE_GRID_270 [17] [4] = true;
	    CURRENT_PIECE_GRID_270 [16] [4] = true;
	    CURRENT_PIECE_GRID_270 [16] [5] = true;
	    CURRENT_PIECE_GRID_270 [15] [5] = true;


	}
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void fallCheck ()
    {
	//for each of the 4 pieces, discovers if the piece should lock based on how low it is
	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    if (CURRENT_PIECE_GRID_0 [0] [i] == true)
	    {
		lock0 = true;
	    }
	}

	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    if (CURRENT_PIECE_GRID_90 [0] [i] == true)
	    {
		lock1 = true;
	    }
	}

	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    if (CURRENT_PIECE_GRID_180 [0] [i] == true)
	    {
		lock2 = true;
	    }
	}

	for (int i = 0 ; i < COLUMN_NUMBER ; i++)
	{
	    if (CURRENT_PIECE_GRID_270 [0] [i] == true)
	    {
		lock3 = true;
	    }
	}


	//Discovers if the piece should lock based on locked pieces below it for the 4 made grids
	if (lock0 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_0 [j] [i] == true)
		    {
			if (TETRIS_GRID [j - 1] [i] == true)
			{
			    lock0 = true;
			}
		    }
		}
	    }
	}

	if (lock1 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_90 [j] [i] == true)
		    {
			if (TETRIS_GRID [j - 1] [i] == true)
			{
			    lock1 = true;
			}
		    }
		}
	    }
	}


	if (lock2 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_180 [j] [i] == true)
		    {
			if (TETRIS_GRID [j - 1] [i] == true)
			{
			    lock2 = true;
			}
		    }
		}
	    }
	}


	if (lock3 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_270 [j] [i] == true)
		    {
			if (TETRIS_GRID [j - 1] [i] == true)
			{
			    lock3 = true;
			}
		    }
		}
	    }
	}

	//If nothing was locked, the piece will move down for all 4 grids of rotations
	if (lock0 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_0 [j] [i] == true)
		    {
			if (CURRENT_ROTATION == ORIGINAL_ROTATION)
			{
			    CURRENT_BLOCK_COLOUR [j - 1] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			}
			CURRENT_PIECE_GRID_0 [j - 1] [i] = true;
			CURRENT_PIECE_GRID_0 [j] [i] = false;
		    }
		}
	    }
	}

	if (lock1 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_90 [j] [i] == true)
		    {
			if (CURRENT_ROTATION == NINTY_ROTATION)
			{
			    CURRENT_BLOCK_COLOUR [j - 1] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			}
			CURRENT_PIECE_GRID_90 [j - 1] [i] = true;
			CURRENT_PIECE_GRID_90 [j] [i] = false;
		    }
		}
	    }
	}

	if (lock2 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_180 [j] [i] == true)
		    {
			if (CURRENT_ROTATION == ONE_EIGHTY_ROTATION)
			{
			    CURRENT_BLOCK_COLOUR [j - 1] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			}
			CURRENT_PIECE_GRID_180 [j - 1] [i] = true;
			CURRENT_PIECE_GRID_180 [j] [i] = false;
		    }
		}
	    }
	}

	if (lock3 == false)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_270 [j] [i] == true)
		    {
			if (CURRENT_ROTATION == TWO_SEVENTY_ROTATION)
			{
			    CURRENT_BLOCK_COLOUR [j - 1] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			}
			CURRENT_PIECE_GRID_270 [j - 1] [i] = true;
			CURRENT_PIECE_GRID_270 [j] [i] = false;
		    }
		}
	    }
	}

	//Locks the game if piece should be locked and using the current rotation based on the rotation you are on
	if (lock0 == true && CURRENT_ROTATION == ORIGINAL_ROTATION)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_0 [j] [i] == true)
		    {
			CURRENT_PIECE_GRID_0 [j] [i] = false;
			TETRIS_BLOCK_COLOUR [j] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			TETRIS_GRID [j] [i] = true;
			exit = true;
			holdPieceLimit = false;
			forceDropCheck = false;
			try
			{
			    Thread.sleep (MOVEMENT_PAUSE_TIME);
			}
			catch (Exception e)
			{
			}
		    }
		    CURRENT_PIECE_GRID_90 [j] [i] = false;
		    CURRENT_PIECE_GRID_180 [j] [i] = false;
		    CURRENT_PIECE_GRID_270 [j] [i] = false;
		}
	    }
	}


	if (lock1 == true && CURRENT_ROTATION == NINTY_ROTATION)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_90 [j] [i] == true)
		    {
			CURRENT_PIECE_GRID_90 [j] [i] = false;
			TETRIS_BLOCK_COLOUR [j] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			TETRIS_GRID [j] [i] = true;
			exit = true;
			holdPieceLimit = false;
			forceDropCheck = false;
			try
			{
			    Thread.sleep (MOVEMENT_PAUSE_TIME);
			}
			catch (Exception e)
			{
			}
		    }
		    CURRENT_PIECE_GRID_0 [j] [i] = false;
		    CURRENT_PIECE_GRID_180 [j] [i] = false;
		    CURRENT_PIECE_GRID_270 [j] [i] = false;
		}
	    }
	}


	if (lock2 == true && CURRENT_ROTATION == ONE_EIGHTY_ROTATION)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_180 [j] [i] == true)
		    {
			CURRENT_PIECE_GRID_180 [j] [i] = false;
			TETRIS_BLOCK_COLOUR [j] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			TETRIS_GRID [j] [i] = true;
			exit = true;
			holdPieceLimit = false;
			forceDropCheck = false;
			try
			{
			    Thread.sleep (MOVEMENT_PAUSE_TIME);
			}
			catch (Exception e)
			{
			}
		    }
		    CURRENT_PIECE_GRID_0 [j] [i] = false;
		    CURRENT_PIECE_GRID_90 [j] [i] = false;
		    CURRENT_PIECE_GRID_270 [j] [i] = false;
		}
	    }
	}


	if (lock3 == true && CURRENT_ROTATION == TWO_SEVENTY_ROTATION)
	{
	    for (int j = 0 ; j < ROW_NUMBER ; j++)
	    {
		for (int i = 0 ; i < COLUMN_NUMBER ; i++)
		{
		    if (CURRENT_PIECE_GRID_270 [j] [i] == true)
		    {
			CURRENT_PIECE_GRID_270 [j] [i] = false;
			TETRIS_BLOCK_COLOUR [j] [i] = CURRENT_BLOCK_COLOUR [j] [i];
			TETRIS_GRID [j] [i] = true;
			exit = true;
			holdPieceLimit = false;
			forceDropCheck = false;
			try
			{
			    Thread.sleep (MOVEMENT_PAUSE_TIME);
			}
			catch (Exception e)
			{
			}
		    }
		    CURRENT_PIECE_GRID_0 [j] [i] = false;
		    CURRENT_PIECE_GRID_90 [j] [i] = false;
		    CURRENT_PIECE_GRID_180 [j] [i] = false;
		}
	    }
	}



    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //During the gap between a piece moving down, the events that can happen
    public static void pausePhase ()
    {
	//decides how long the pause should be based on the pause length and the small pause gaps between actions
	for (int i = 0 ; i < pausedTime / SMALL_PAUSE_TIME ; i++)
	{
	    i = PAUSE_START;
	    PAUSE_START = PAUSE_START + 1;

	    //Drops piece to bottom
	    if (forceDropCheck == false)
	    {
		try
		{
		    Thread.sleep (SMALL_PAUSE_TIME);
		}
		catch (Exception e)
		{
		}

		if (keyboardCount == FORCE_DROP)
		{
		    //Turns forcedrop on to complete piece drop
		    forceDropCheck = true;
		}
		else if (keyboardCount == MOVE_DOWN)
		{
		    //exits pause to automatically move peice down
		    break;
		}
		//Moves piece left
		else if (keyboardCount == MOVE_LEFT)
		{
		    //Checks to see if peice can move left based on how far left it is
		    for (int n = 0 ; n < ROW_NUMBER ; n++)
		    {
			if (CURRENT_PIECE_GRID_0 [n] [0] == true)
			{
			    leftLock0 = true;
			}
			if (CURRENT_PIECE_GRID_90 [n] [0] == true)
			{
			    leftLock1 = true;

			}
			if (CURRENT_PIECE_GRID_180 [n] [0] == true)
			{
			    leftLock2 = true;

			}
			if (CURRENT_PIECE_GRID_270 [n] [0] == true)
			{
			    leftLock3 = true;

			}
		    }

		    //Checks to see if the piece can go left based on the pieces to the left of it
		    if (leftLock0 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_0 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n - 1] == true)
				    {
					leftLock0 = true;
					preventRotation0 = true;
				    }
				}
			    }
			}
		    }

		    if (leftLock1 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_90 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n - 1] == true)
				    {
					leftLock1 = true;
					preventRotation1 = true;
				    }
				}
			    }
			}
		    }

		    if (leftLock2 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_180 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n - 1] == true)
				    {
					leftLock2 = true;
					preventRotation2 = true;
				    }
				}
			    }
			}
		    }

		    if (leftLock3 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_270 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n - 1] == true)
				    {
					leftLock3 = true;
					preventRotation3 = true;
				    }
				}
			    }
			}
		    }
		    //Moves piece left is allowed
		    if (leftLock0 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_0 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == ORIGINAL_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n - 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_0 [j] [n - 1] = true;
				    CURRENT_PIECE_GRID_0 [j] [n] = false;
				    movedLeft0 = true;
				}
			    }
			}
		    }

		    if (leftLock1 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_90 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == NINTY_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n - 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_90 [j] [n - 1] = true;
				    CURRENT_PIECE_GRID_90 [j] [n] = false;
				    movedLeft1 = true;
				}
			    }
			}
		    }

		    if (leftLock2 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_180 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == ONE_EIGHTY_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n - 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_180 [j] [n - 1] = true;
				    CURRENT_PIECE_GRID_180 [j] [n] = false;
				    movedLeft2 = true;
				}
			    }
			}
		    }

		    if (leftLock3 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			    {
				if (CURRENT_PIECE_GRID_270 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == TWO_SEVENTY_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n - 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_270 [j] [n - 1] = true;
				    CURRENT_PIECE_GRID_270 [j] [n] = false;
				    movedLeft3 = true;
				}
			    }
			}
		    }

		    //Resets variables
		    leftLock0 = false;
		    leftLock1 = false;
		    leftLock2 = false;
		    leftLock3 = false;

		}
		//Moves piece right
		else if (keyboardCount == MOVE_RIGHT)
		{
		    //Checks to see if the piece can actually go right anymore
		    for (int n = 0 ; n < ROW_NUMBER ; n++)
		    {
			if (CURRENT_PIECE_GRID_0 [n] [COLUMN_NUMBER - 1] == true)
			{
			    rightLock0 = true;
			}
			if (CURRENT_PIECE_GRID_90 [n] [COLUMN_NUMBER - 1] == true)
			{
			    rightLock1 = true;
			}
			if (CURRENT_PIECE_GRID_180 [n] [COLUMN_NUMBER - 1] == true)
			{
			    rightLock2 = true;
			}
			if (CURRENT_PIECE_GRID_270 [n] [COLUMN_NUMBER - 1] == true)
			{
			    rightLock3 = true;
			}
		    }
		    //Prevent piece from going right if there is a piece to the right of it
		    if (rightLock0 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > 0 ; n--)
			    {
				if (CURRENT_PIECE_GRID_0 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n + 1] == true)
				    {
					rightLock0 = true;
					preventRotation0 = true;
				    }
				}
			    }
			}
		    }

		    if (rightLock1 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > 0 ; n--)
			    {
				if (CURRENT_PIECE_GRID_90 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n + 1] == true)
				    {
					rightLock1 = true;
					preventRotation1 = true;
				    }
				}
			    }
			}
		    }

		    if (rightLock2 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > 0 ; n--)
			    {
				if (CURRENT_PIECE_GRID_180 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n + 1] == true)
				    {
					rightLock2 = true;
					preventRotation2 = true;
				    }
				}
			    }
			}
		    }

		    if (rightLock3 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > 0 ; n--)
			    {
				if (CURRENT_PIECE_GRID_270 [j] [n] == true)
				{
				    if (TETRIS_GRID [j] [n + 1] == true)
				    {
					rightLock3 = true;
					preventRotation3 = true;
				    }
				}
			    }
			}
		    }

		    //Moves piece right if conditions are met
		    if (rightLock0 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > -1 ; n--)
			    {
				if (CURRENT_PIECE_GRID_0 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == ORIGINAL_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n + 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_0 [j] [n + 1] = true;
				    CURRENT_PIECE_GRID_0 [j] [n] = false;
				    movedRight0 = true;
				}
			    }
			}
		    }

		    if (rightLock1 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > -1 ; n--)
			    {
				if (CURRENT_PIECE_GRID_90 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == NINTY_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n + 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_90 [j] [n + 1] = true;
				    CURRENT_PIECE_GRID_90 [j] [n] = false;
				    movedRight1 = true;
				}
			    }
			}
		    }

		    if (rightLock2 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > -1 ; n--)
			    {
				if (CURRENT_PIECE_GRID_180 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == ONE_EIGHTY_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n + 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_180 [j] [n + 1] = true;
				    CURRENT_PIECE_GRID_180 [j] [n] = false;
				    movedRight2 = true;
				}
			    }
			}
		    }

		    if (rightLock3 == false)
		    {
			for (int j = 0 ; j < ROW_NUMBER ; j++)
			{
			    for (int n = COLUMN_NUMBER - 1 ; n > -1 ; n--)
			    {
				if (CURRENT_PIECE_GRID_270 [j] [n] == true)
				{
				    if (CURRENT_ROTATION == TWO_SEVENTY_ROTATION)
				    {
					CURRENT_BLOCK_COLOUR [j] [n + 1] = CURRENT_BLOCK_COLOUR [j] [n];
				    }
				    CURRENT_PIECE_GRID_270 [j] [n + 1] = true;
				    CURRENT_PIECE_GRID_270 [j] [n] = false;
				    movedRight3 = true;
				}
			    }
			}
		    }
		    //resets variables
		    rightLock0 = false;
		    rightLock1 = false;
		    rightLock2 = false;
		    rightLock3 = false;
		}
		//Rotates the piece depending on if a rotation is allowed and what the current rotation is
		else if (keyboardCount == ROTATE_PIECE)
		{
		    if (CURRENT_ROTATION == ORIGINAL_ROTATION && preventRotation1 == false)
		    {
			CURRENT_ROTATION = NINTY_ROTATION;
			rotateCheck = true;
		    }
		    else if (CURRENT_ROTATION == NINTY_ROTATION && preventRotation2 == false)
		    {
			CURRENT_ROTATION = ONE_EIGHTY_ROTATION;
			rotateCheck = true;
		    }
		    else if (CURRENT_ROTATION == ONE_EIGHTY_ROTATION && preventRotation3 == false)
		    {
			CURRENT_ROTATION = TWO_SEVENTY_ROTATION;
			rotateCheck = true;
		    }
		    else if (CURRENT_ROTATION == TWO_SEVENTY_ROTATION && preventRotation0 == false)
		    {
			CURRENT_ROTATION = ORIGINAL_ROTATION;
			rotateCheck = true;
		    }
		}

		//Stores the piece to the side
		else if (keyboardCount == STORE_PIECE)
		{
		    if (holdPieceLimit == false)
		    {
			//only allows one storage per piece
			subPieceOut = true;
			holdPieceLimit = true;

			for (int n = 0 ; n < COLUMN_NUMBER ; n++)
			{
			    for (int j = 0 ; j < ROW_NUMBER ; j++)
			    {
				//Clears all the piece off screen
				CURRENT_PIECE_GRID_0 [j] [n] = false;
				CURRENT_PIECE_GRID_90 [j] [n] = false;
				CURRENT_PIECE_GRID_180 [j] [n] = false;
				CURRENT_PIECE_GRID_270 [j] [n] = false;
			    }
			}
			break;
		    }
		}
	    }
	    //Tells the program to break when moving left so the pause time will still go down
	    if (movedLeft0 == true || movedRight0 == true || movedLeft1 == true || movedRight1 == true || movedLeft2 == true || movedRight2 == true || movedLeft3 == true || movedRight3 == true || rotateCheck == true)
	    {
		break;
	    }
	}

	//Resets pause time if piece fif not move left or right so it will take the required time to move down again
	if (movedLeft0 == false && movedRight0 == false && movedLeft1 == false && movedRight1 == false && movedLeft2 == false && movedRight2 == false && movedLeft3 == false && movedRight3 == false)
	{
	    PAUSE_START = 0;
	}
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method to handle key - down events
    public boolean keyDown (Event e, int key)
    {

	// user presses up arrow
	if (key == UP_ARROW)
	{
	    keyboardCount = FORCE_DROP;
	}

	// user presses down arrow
	else if (key == DOWN_ARROW)
	{
	    keyboardCount = MOVE_DOWN;
	}


	// user presses left arrow
	else if (key == RIGHT_ARROW)
	{
	    keyboardCount = MOVE_LEFT;
	}


	// user presses down arrow
	else if (key == LEFT_ARROW)
	{
	    keyboardCount = MOVE_RIGHT;
	}


	// user presses space bar
	else if (key == SPACE_BAR)
	{
	    keyboardCount = ROTATE_PIECE;
	}


	// user presses c
	else if (key == C_KEY)
	{
	    keyboardCount = STORE_PIECE;
	}
	//Enter key presses
	else if (key == ENTER_KEY)
	{
	    keyboardCount = START_GAME;
	}


	// DON'T FORGET (although it has no meaning here)
	return true;

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // method to handle key - down events
    public boolean keyUp (Event e, int key)
    {

	// user lets go of any of the active buttons
	if (key == UP_ARROW || key == DOWN_ARROW || key == LEFT_ARROW || key == RIGHT_ARROW || key == SPACE_BAR || key == C_KEY)
	{
	    keyboardCount = NO_INPUT;
	}


	// DON'T FORGET (although it has no meaning here)
	return true;

    }
}


