package game.entity.entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

import game.Game;
import game.ResourceManager;
import game.entity.AnimationState;
import game.entity.Entity;
import game.upgrade.UpgradeManager;
import game.upgrade.upgrades.ClickUpgrade;

public class BlackholeEntity extends Entity {

    public static BlackholeEntity INSTANCE;
    private final static BufferedImage IMAGE = ResourceManager.RESOURCE_MAP.get("blackhole.png");
    private final static double MAX_SCALE = 1.1;

    private double holeScale = 1;
    private AnimationState state = AnimationState.NONE; // keep state to know if the blackhole is animating currently
    

    public BlackholeEntity() {
        super(Game.WIDTH / 2, Game.HEIGHT / 2); // put in the center of the screen
        if (INSTANCE != null) throw new RuntimeException("Blackhole already exists.");
        INSTANCE = this;
    }

    @Override
    public void render(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();
        g.scale(holeScale, holeScale); // scale up the black hole
        g.drawImage(IMAGE, (int) ((getX() - IMAGE.getWidth() * holeScale / 2) / holeScale),
                           (int) ((getY() - IMAGE.getHeight() * holeScale / 2) / holeScale), Game.INSTANCE);
        g.setTransform(oldTransform); // undo scale
    }

    @Override
    public void update(double dt) {
        switch (state) { // add to the hole scale depending on whether it is increasing or decreasing
            case INCREASING:
                holeScale += dt / holeScale;
                break;
            case DECREASING:
                holeScale -=  dt / holeScale;
                break;
            case NONE:
                return;
        }

        if (state == AnimationState.INCREASING && holeScale >= MAX_SCALE) { // switch states when reaching max
            holeScale = MAX_SCALE;
            state = AnimationState.DECREASING;
        } else if (state == AnimationState.DECREASING && holeScale <= 1) {
            holeScale = 1;
            state = AnimationState.NONE;
        }
    }

    @Override
    public void click(MouseEvent e) {

        double distX = Math.abs(e.getX() - Game.WIDTH / 2);
        double distY = Math.abs(e.getY() - Game.HEIGHT / 2);

        // check if the difference between click and blackhole is less than radius of blackhole
        if (Math.pow(distX, 2) + Math.pow(distY, 2) >= Math.pow(128, 2)) return;

        ResourceManager.CLICK_SOUND.play(); // play click sound when clicked


        long cash = UpgradeManager.getUpgrade(ClickUpgrade.class).getCurrentLevel(); // get cash from click upgrade
        Game.INSTANCE.addEntity(
            new CashEntity((double) e.getX(), (double) e.getY(), cash, 3)
        );
        Game.INSTANCE.addCash(cash); // add click upgrade cash

        state = AnimationState.INCREASING; // make blackhole animate
    }

    @Override
    public void resize() {
        setX(Game.WIDTH / 2);
        setY(Game.HEIGHT / 2);
    }
    
}