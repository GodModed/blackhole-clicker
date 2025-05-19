package game.listeners;

import java.awt.event.MouseListener;

import game.Game;
import game.entity.Entity;

import java.awt.event.MouseEvent;

public class ClickHandler implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        for (Entity entity : Game.INSTANCE.getEntities()) {
            entity.click(e);
        }

        for (Entity entity : Game.INSTANCE.getGuiEntities()) {
            entity.click(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
