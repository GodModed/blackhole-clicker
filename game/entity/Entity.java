package game.entity;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import game.interfaces.IClickable;
import game.interfaces.IRenderable;
import game.interfaces.IResizable;
import game.interfaces.IUpdatable;

public abstract class Entity implements IRenderable, IUpdatable, IClickable, IResizable {
    
    private double  x, y,   // position
                    dx, dy, // velocity
                    rotation; // in radians
    private boolean alive;

    public Entity(double x, double y) {
        this(x, y, 0, 0); // start entitiy with no velocity
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

    @Override public void click(MouseEvent e) {} // entities can override to get these events :)
    @Override public void resize() {} // ^

    public void remove() {
        alive = false; // mark as deleted so it won't be rendered and it will be deleted in the next frame
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
