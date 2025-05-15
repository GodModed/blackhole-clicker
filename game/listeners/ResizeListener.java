package game.listeners;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import game.Game;

public class ResizeListener implements ComponentListener {

    @Override
    public void componentResized(ComponentEvent e) {
        Game.WIDTH = e.getComponent().getWidth();
        Game.HEIGHT = e.getComponent().getHeight();
    }

    @Override
    public void componentMoved(ComponentEvent e) {}

    @Override
    public void componentShown(ComponentEvent e) {}

    @Override
    public void componentHidden(ComponentEvent e) {}
    
}
