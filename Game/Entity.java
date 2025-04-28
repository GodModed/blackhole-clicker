package game;

import java.awt.Graphics2D;
import java.util.ArrayList;

public abstract class Entity implements IRenderable, IUpdatable {

    private static ArrayList<Entity> entities = new ArrayList<>();
    
    private double  x, y,   // position
                    dx, dy; // velocity

    public Entity(double x, double y) {
        this(x, y, 0, 0);
    }

    public Entity(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        entities.add(this);
    }

    // -- GAME LOGIC --

    @Override public abstract void render(Graphics2D g);
    @Override public abstract void update(double dt);

    // -- GETTERS -- 
    
    public static ArrayList<Entity> getEntities() { return entities; }

    public double getX() { return x; }
    public double getY() { return y; }
    public double getDeltaX() { return dx; }
    public double getDeltaY() { return dy; }

    // -- SETTERS --
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setDeltaX(double dx) { this.dx = dx; }
    public void addDeltaX(double dx) { this.dx += dx; }
    public void setDeltaY(double dy) { this.dy = dy; }
    public void addDeltaY(double dy) { this.dy += dy; }

}
