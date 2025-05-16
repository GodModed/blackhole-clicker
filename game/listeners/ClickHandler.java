package game.listeners;

import java.awt.event.MouseListener;
import java.util.ArrayList;

import game.Game;
import game.entity.Entity;

import java.awt.event.MouseEvent;

public class ClickHandler implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        ArrayList<Entity> allEntities = Game.INSTANCE.getAllEntities();
        for (int i = 0; i < allEntities.size(); i++) {
            allEntities.get(i).click(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
