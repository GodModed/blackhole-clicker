package game.listeners;

import java.awt.event.MouseListener;

import game.entities.BlackholeEntity;

import java.awt.event.MouseEvent;

public class ClickHandler implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        BlackholeEntity.INSTANCE.click(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}
