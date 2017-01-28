// Created by Austin Patel on 4/15/16 at 11:19 AM.

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

// Handles mouse input for the game
public class MouseInput extends MouseInputAdapter implements MouseMotionListener {

    private MouseHandler callingClass;

    public MouseInput(GameMode callingClass) {
        this.callingClass = callingClass;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        callingClass.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        callingClass.mouseMoved(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        callingClass.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        callingClass.mouseReleased(e);
    }
}