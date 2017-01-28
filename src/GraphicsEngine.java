// Created by Austin Patel on 4/15/16 at 12:02 PM.

// Creates Drawables for different geometric items
public class GraphicsEngine {

    public static Drawable getRegularPolygon(int centerX, int centerY, int numSides, double radius, double rotation)
    {
        double angleDelta = (Math.PI * 2) / numSides;

        double[] xLocations = new double[numSides];
        double[] yLocations = new double[numSides];

        // Calculate the x, y coordinates for each point on the shape
        for (int i = 0; i < numSides; i++) {
            xLocations[i] = centerX + (Math.cos(angleDelta * i + rotation) * radius);
            yLocations[i] = centerY + (Math.sin(angleDelta * i + rotation) * radius);
        }

        Drawable shape = new Drawable(xLocations, yLocations);
        shape.setRadius(radius);
        shape.setCenterX(centerX);
        shape.setCenterY(centerY);

        return shape;
    }

    public static Drawable getLine(int x1, int y1, int x2, int y2) {
        double[] xLocations = new double[2];
        double[] yLocations = new double[2];

        xLocations[0] = x1;
        xLocations[1] = x2;

        yLocations[0] = y1;
        yLocations[1] = y2;

        return new Drawable(xLocations, yLocations);
    }
}
