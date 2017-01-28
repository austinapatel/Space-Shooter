// Created by Austin Patel on 4/16/16 at 6:24 PM.

import java.awt.*;

// Holds the data for a specific shape that is going to be drawn
public class Drawable {

    private Color color = Color.WHITE;
    private boolean fill;
    private Polygon polygon;
    private double radius;
    private double centerX, centerY;
    private double[] xLocations, yLocations;

    public Drawable(double[] xLocations, double[] yLocations) {
        this.xLocations = xLocations;
        this.yLocations = yLocations;

        createPolygon();
    }

    private void createPolygon() {
        // Create a Polygon given the data
        polygon = new Polygon();
        for (int i = 0; i < xLocations.length; i++) polygon.addPoint((int) Math.round(xLocations[i]), (int) Math.round(yLocations[i]));
    }

    public double[] getXLocations() {
        return xLocations;
    }

    public double[] getYLocations() {
        return yLocations;
    }

    public void setXLocations(double[] xLocations) {
        this.xLocations = xLocations;
        createPolygon();
    }

    public void setYLocations(double[] yLocations) {
        this.yLocations = yLocations;
        createPolygon();
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean getFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }
}
