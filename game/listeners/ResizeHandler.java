package game.listeners;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import game.Game;
import game.entity.Entity;

public class ResizeHandler implements ComponentListener {

    @Override
    public void componentResized(ComponentEvent e) {
        Game.WIDTH = e.getComponent().getWidth();  // resize the game when the window is resized
        Game.HEIGHT = e.getComponent().getHeight();

        for (Entity entity : Game.INSTANCE.getEntities()) {
            entity.resize();
        }

        for (Entity entity : Game.INSTANCE.getGuiEntities()) {
            entity.resize();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
    
}
