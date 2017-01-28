// Created by Austin Patel on 4/17/16 at 7:39 PM.

import java.awt.event.KeyEvent;

public interface KeyboardHandler {
    void keyTyped(KeyEvent e);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);
}
