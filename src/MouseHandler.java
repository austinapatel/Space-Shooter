// Created by Austin Patel on 4/17/16 at 7:30 PM.

import java.awt.event.MouseEvent;

// Bridges between class that wants the mouse data and the class that gets the mouse data (MouseInput)
public interface MouseHandler {
    void mouseDragged(MouseEvent e);

    void mouseMoved(MouseEvent e);

    void mousePressed(MouseEvent e);

    void mouseReleased(MouseEvent e);
}
