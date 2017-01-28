// Created by Austin Patel on 4/17/16 at 7:38 PM.

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {

    private KeyboardHandler callingClass;

    public KeyboardInput(KeyboardHandler callingClass) {
        this.callingClass = callingClass;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        callingClass.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        callingClass.keyReleased(e);
    }
}
