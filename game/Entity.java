package game;

import java.awt.Graphics2D;

public abstract class Entity implements IRenderable, IUpdatable {
    
    private double  x, y,   // position
                    dx, dy, // velocity
                    rotation; // in radians
    private boolean alive;

    public Entity(double x, double y) {
        this(x, y, 0, 0);
    }

    public Entity(double x, double y, double dx, double dy) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.rotation = 0;
        this.alive = true;
    }

    // -- GAME LOGIC --

    @Override public abstract void render(Graphics2D g);
    
    @Override
    public void update(double dt) {
        x += dx * dt;
        y += dy * dt;   
    }

    public void remove() {
        alive = false;
    }

    // -- GETTERS -- 

    public double getX() { return x; }
    public double getY() { return y; }
    public double getDeltaX() { return dx; }
    public double getDeltaY() { return dy; }
    public double getRotation() {return rotation; }
    public boolean isAlive() { return alive; }


    // -- SETTERS --
    
    public void setX(double x) { this.x = x; }
    public void addX(double x) { this.x += x; }
    public void setY(double y) { this.y = y; }
    public void addY(double y) { this.y += y; }
    public void setDeltaX(double dx) { this.dx = dx; }
    public void addDeltaX(double dx) { this.dx += dx; }
    public void setDeltaY(double dy) { this.dy = dy; }
    public void addDeltaY(double dy) { this.dy += dy; }
    public void setRotation(double rotation ) { this.rotation = rotation; }
    public void addRotation(double rotation ) { this.rotation += rotation; }

}
