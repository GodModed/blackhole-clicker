package game.listeners;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import game.Game;
import game.entity.Entity;

public class ResizeHandler implements ComponentListener {

    @Override
    public void componentResized(ComponentEvent e) {
        Game.WIDTH = e.getComponent().getWidth();  // resize the game when the window is resized
        Game.HEIGHT = e.getComponent().getHeight();

        ArrayList<Entity> allEntities = Game.INSTANCE.getAllEntities();
        for (int i = 0; i < allEntities.size(); i++) {
            allEntities.get(i).resize();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
    
}
