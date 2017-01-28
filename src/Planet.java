// Created by Austin Patel on 4/16/16 at 9:12 PM.

// Acts as a drawable with built in gravity & velocity / other planetary features
public class Planet {

    private final int GRAVITY_SCALE_FACTOR = 1000000;
    private double xVelocity = 0, yVelocity = 0;
    private Drawable drawable;
    private int id;
    private double area;

    public Planet(Drawable drawable, int id) {
        this.drawable = drawable;
        this.id = id;

        this.area = Math.PI * Math.pow(drawable.getRadius(), 2);
    }

    public int getId() {
        return id;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public double getArea() {
        return area;
    }

    public void update(Planet[] otherPlanets) {
        // Move the position based on its velocity
        double[] accelerationsX = new double[otherPlanets.length];
        double[] accelerationsY = new double[otherPlanets.length];

        for (int i = 0; i < accelerationsX.length; i++) {
            Planet otherPlanet = otherPlanets[i];
            Drawable otherDrawable = otherPlanet.getDrawable();
            double deltaX = otherDrawable.getCenterX() - drawable.getCenterX();
            double deltaY = otherDrawable.getCenterY() - drawable.getCenterY();
            double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

            accelerationsX[i] = (otherPlanet.getArea() / GRAVITY_SCALE_FACTOR) * (deltaX / distance);
            accelerationsY[i] = (otherPlanet.getArea() / GRAVITY_SCALE_FACTOR) * (deltaY / distance);
        }

        for (int i = 0; i < accelerationsX.length; i++) {
            xVelocity += accelerationsX[i];
            yVelocity += accelerationsY[i];
        }

        double[] xLocations = drawable.getXLocations();
        double[] yLocations = drawable.getYLocations();

        for (int i = 0; i < xLocations.length; i++) {
            double newX = xLocations[i] + xVelocity;
            double newY = yLocations[i] + yVelocity;

            xLocations[i] = newX;
            yLocations[i] = newY;
        }

        drawable.setXLocations(xLocations);
        drawable.setYLocations(yLocations);

        drawable.setCenterX(drawable.getCenterX() + xVelocity);
        drawable.setCenterY(drawable.getCenterY() + yVelocity);
    }
}
