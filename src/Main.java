// Created by Austin Patel on 4/15/16 at 10:58 AM.

import javax.swing.*;

// Starts the program and basis of program
public class Main extends JFrame
{
    public static final int SCREEN_WIDTH = 1600, SCREEN_HEIGHT = 1200, REDRAW_INTERVAL = 30, UPDATE_INTERVAL = 10;

    public static void main(String[] arguments)
    {
        new Main();
    }

    public Main() {
        initializeJFrame();

        GameMode gameMode = new GameMode();
        KeyboardInput keyboardListener = new KeyboardInput(gameMode);

        this.add(gameMode); // Use the GameMode as a JPanel to draw on the frame
        this.addKeyListener(keyboardListener); // Add a keyboard key listener to the frame
    }

    private void initializeJFrame() {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
