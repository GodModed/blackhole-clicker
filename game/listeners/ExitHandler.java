package game.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import game.Game;

public class ExitHandler implements WindowListener {

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        Game.INSTANCE.stop(); // stop the game when the window is closed. Graceful shutdown
    }

    @Override
    public void windowClosed(WindowEvent e) {}

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) {}

    @Override
    public void windowDeactivated(WindowEvent e) {}
    
}
