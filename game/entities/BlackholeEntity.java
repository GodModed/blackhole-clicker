package game.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import game.Entity;
import game.Game;
import game.ResourceManager;
import game.UpgradeManager;
import game.upgrades.ClickUpgrade;

public class BlackholeEntity extends Entity {

    public static BlackholeEntity INSTANCE;
    private final static BufferedImage IMAGE = ResourceManager.BLACKHOLE_IMAGE;
    private final static double MAX_SCALE = 1.1;

    private double holeScale = 1;
    private AnimationState state = AnimationState.NONE;
    

    public BlackholeEntity() {
        super(Game.WIDTH / 2, Game.HEIGHT / 2);
        if (INSTANCE != null) throw new RuntimeException("Blackhole already exists.");
        INSTANCE = this;
    }

    @Override
    public void render(Graphics2D g) {
        // AffineTransform oldTransform = g.getTransform();
        // g.rotate(getRotation(), Game.WIDTH / 2, Game.HEIGHT / 2);

        AffineTransform oldTransform = g.getTransform();
        g.scale(holeScale, holeScale);
        g.drawImage(IMAGE, (int) ((getX() - IMAGE.getWidth() * holeScale / 2) / holeScale),
                           (int) ((getY() - IMAGE.getHeight() * holeScale / 2) / holeScale), Game.INSTANCE);
        g.setTransform(oldTransform);        g.setTransform(oldTransform);
    }

    @Override
    public void update(double dt) {

        setX(Game.WIDTH / 2);
        setY(Game.HEIGHT / 2);
        // addRotation(dt / 2);
        switch (state) {
            case INCREASING:
                holeScale += dt / holeScale;
                break;
            case DECREASING:
                holeScale -=  dt / holeScale;
                break;
            case NONE:
                return;
        }

        if (state == AnimationState.INCREASING && holeScale >= MAX_SCALE) {
            holeScale = MAX_SCALE;
            state = AnimationState.DECREASING;
        } else if (state == AnimationState.DECREASING && holeScale <= 1) {
            holeScale = 1;
            state = AnimationState.NONE;
        }
    }

    public void click(MouseEvent e) {

        double distX = Math.abs(e.getX() - Game.WIDTH / 2);
        double distY = Math.abs(e.getY() - Game.HEIGHT / 2);

        if (Math.pow(distX, 2) + Math.pow(distY, 2) >= Math.pow(128, 2)) return;

        ResourceManager.CLICK_SOUND.play();


        long cash = UpgradeManager.getUpgrade(ClickUpgrade.class).getCurrentLevel();
        Game.INSTANCE.getEntities().add(
            new CashEntity((double) e.getX(), (double) e.getY(), cash, 3)
        );
        Game.INSTANCE.addCash(cash);

        state = AnimationState.INCREASING;
    }

    private enum AnimationState {
        NONE,
        INCREASING,
        DECREASING;
    }
    
}