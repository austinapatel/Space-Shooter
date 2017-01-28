// Created by Austin Patel on 4/15/16 at 10:55 AM.

import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// Handles the actual playing part of the game
public class GameMode extends JPanel implements MouseHandler, KeyboardHandler {

    private static int CIRCLE_POLYGON_SIDES = 30;

    private boolean isMouseDragging, isDragJustFinished;
    private int mouseStartPositionX, mouseStartPositionY, mouseCurrentDragPositionX, mouseCurrentDragPositionY;
    private ArrayList<Drawable> outputShapes = new ArrayList<>();
    private ArrayList<Planet> planets = new ArrayList<>();
    private int curPlanetId = 0;
    private boolean controlKeyDown = false;

    public GameMode() {
        initializeJPanel();
        initializeInputListeners();
    }

    private void initializeJPanel() {
        this.setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);

        // Update and draw the canvas at a given intervals
        Timer updateTimer = new Timer(Main.UPDATE_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
            }
        });
        updateTimer.start();

        Timer drawTimer = new Timer(Main.REDRAW_INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameMode.this.repaint();
            }
        });
        drawTimer.start();
    }

    private  void initializeInputListeners() {
        MouseInput mouseListener = new MouseInput(this);
        this.addMouseMotionListener(mouseListener);
        this.addMouseListener(mouseListener);
    }

    public void mouseDragged(MouseEvent e) {
        if (controlKeyDown) {
            isMouseDragging = true;
            mouseCurrentDragPositionX = e.getX();
            mouseCurrentDragPositionY = e.getY();
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        mouseStartPositionX = e.getX();
        mouseStartPositionY = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        if (isMouseDragging) isDragJustFinished = true;
        isMouseDragging = false;
        mouseCurrentDragPositionX = e.getX();
        mouseCurrentDragPositionY = e.getY();
    }

    // Adds all the shapes to be drawn to an ArrayList to be later drawn
    private void update() {
        outputShapes.clear(); // Remove all previous shapes to de brawn

        // DRAG TO CREATE PLANET
        if (isMouseDragging || isDragJustFinished) {
            System.out.println("HEy");
            double radius = Math.sqrt(Math.pow(mouseCurrentDragPositionX - mouseStartPositionX, 2) + Math.pow(mouseCurrentDragPositionY - mouseStartPositionY, 2));
            double rotation = Math.atan2(mouseCurrentDragPositionY - mouseStartPositionY, mouseCurrentDragPositionX - mouseStartPositionX);
            Drawable dragPerimeterDrawable = GraphicsEngine.getRegularPolygon(mouseStartPositionX, mouseStartPositionY, CIRCLE_POLYGON_SIDES, radius, rotation);

            if (isMouseDragging) {
                // Line between drag start and current position
                Drawable dragLineDrawable = GraphicsEngine.getLine(mouseStartPositionX, mouseStartPositionY, mouseCurrentDragPositionX, mouseCurrentDragPositionY);
                outputShapes.add(dragLineDrawable);

                // Circle around drag area
                outputShapes.add(dragPerimeterDrawable);
            } else if (isDragJustFinished) {
                // Create a new planet
                curPlanetId++;

                dragPerimeterDrawable.setFill(true);
                dragPerimeterDrawable.setColor(Color.YELLOW);

                Planet newPlanet = new Planet(dragPerimeterDrawable, curPlanetId);
                planets.add(newPlanet);

                isDragJustFinished = false;
            }
        }

        // ADD & UPDATE ALL THE PLANETS TO THE OUTPUT SHAPES
        for (int q = 0; q < planets.size(); q++) {
            Planet planet = planets.get(q);

            Planet[] otherPlanets = new Planet[planets.size() - 1];

            int curOtherPlanetIndex = 0;
            for (int i = 0; i < planets.size(); i++) {
                if (planets.get(i).getId() != planet.getId()) {
                    otherPlanets[curOtherPlanetIndex] = planets.get(i);
                    curOtherPlanetIndex++;
                }
            }

            planet.update(otherPlanets);

            outputShapes.add(planet.getDrawable());
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (Drawable shape : outputShapes) drawDrawable(graphics, shape);
    }

    private void drawDrawable(Graphics graphics, Drawable drawable) {
        graphics.setColor(drawable.getColor());
        if (drawable.getFill())
            graphics.fillPolygon(drawable.getPolygon());
        else
            graphics.drawPolygon(drawable.getPolygon());
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getModifiers() == InputEvent.CTRL_MASK) controlKeyDown = true;
        System.out.println("pressed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            controlKeyDown = false;
            System.out.println("released");
        }
    }
}
