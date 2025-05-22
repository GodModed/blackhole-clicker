package game.entity.entities;

import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.AlphaComposite;

import game.Game;
import game.NumberFormatter;
import game.ResourceManager;
import game.entity.AnimationState;
import game.entity.Entity;
import game.upgrade.Upgrade;
import game.upgrade.UpgradeManager;

public class ShopEntity extends Entity {

    public static ShopEntity INSTANCE;

    private boolean open = false;

    private Bound upgradeBounds;
    private Bound nextBounds;
    private AnimationState state = AnimationState.NONE; // keep aniamtion state

    
    private int selectedUpgrade = 0;
    private float alpha = 0f;
    private static float MAX_ALPHA = 1f;
    
    public ShopEntity() {
        super(0, Game.HEIGHT - 256 - 20); // draw on the bottom
        if (INSTANCE != null) throw new RuntimeException("Shop already exists.");
        INSTANCE = this;
    }


    @Override
    public void render(Graphics2D g) {
        if (alpha == 0) return;

        Composite oldComposite = g.getComposite(); // save old composites
        AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha); // fade out due to animation
        g.setComposite(newComposite); // set new compsite
        
        Upgrade upgrade = UpgradeManager.getUpgrades().get(selectedUpgrade);
        g.drawImage(upgrade.getIcon(), (int) getX(), (int) getY(), Game.INSTANCE); // draw upgrade icon
        g.setColor(Color.WHITE);

        String upgradeName = upgrade.getName();
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(upgradeName, g); // get size of name

        // draw name, cost, and level
        g.drawString(upgrade.getName(), (int) (getX() + upgrade.getIcon().getWidth()), (int) (getY() + upgrade.getIcon().getHeight() / 2 - bounds.getHeight()));
        g.drawString("Cost:  " +  ((upgrade.getCost() == -1) ? "MAXED" : NumberFormatter.format(upgrade.getCost())), (int) (getX() + upgrade.getIcon().getWidth()), (int) (getY() + upgrade.getIcon().getHeight() / 2));
        g.drawString("Level: " + upgrade.getCurrentLevel(), (int) (getX() + upgrade.getIcon().getWidth()), (int) (getY() + upgrade.getIcon().getHeight() / 2 + bounds.getHeight()));
        Font oldFont = g.getFont(); // save old font
        g.setFont(oldFont.deriveFont(50f)); // make buttons big with bigger font

        int xOffset = (int) (getX() + upgrade.getIcon().getWidth() + bounds.getWidth() + 50); // offset the buttons from the other text and images
        int height = g.getFontMetrics().getHeight();
        int width = g.getFontMetrics().stringWidth("A");

        nextBounds = new Bound(xOffset, (int) (getY() + upgrade.getIcon().getHeight() / 2 - height / 2), width, height); // get bounds for the next button
        g.drawString(">", nextBounds.x, nextBounds.y); // draw next button

        upgradeBounds = new Bound(xOffset, (int) (getY() + upgrade.getIcon().getHeight() / 2 + height / 2), width, height); // get bounds for the upgrade button
        boolean isMaxLevel = upgrade.getMaxLevel() == upgrade.getCurrentLevel();
        boolean enoughCash = upgrade.getCost() <= Game.INSTANCE.getCash();
        if (isMaxLevel || !enoughCash)
            g.setColor(Color.RED);
        else
            g.setColor(Color.GREEN);

        g.drawString(!isMaxLevel ? "^" : "X", upgradeBounds.x, upgradeBounds.y); // draw upgrade button
        g.setColor(Color.WHITE);
        g.setFont(oldFont); // set font back to default
        g.setComposite(oldComposite); // set composite back to default
    }

    @Override
    public void update(double dt) {
        switch (state) { // increase fade or decrease fade on state
            case INCREASING:
                alpha += dt * 4;
                break;
            case DECREASING:
                alpha -=  dt * 4;
                break;
            case NONE:
                return;
        }

        if (state == AnimationState.INCREASING && alpha >= MAX_ALPHA) { // make sure fade doesn't go over or under max
            alpha = MAX_ALPHA;
            state = AnimationState.NONE;
        } else if (state == AnimationState.DECREASING && alpha <= 0) {
            alpha = 0;
            state = AnimationState.NONE;
        }
    }

    @Override
    public void click(MouseEvent e) {
        if (state == AnimationState.DECREASING || alpha == 0) return; // ignore clicks if it is closing
        if (upgradeBounds.isBetweenBounds(e.getX(), e.getY())) {
            UpgradeManager.getUpgrades().get(selectedUpgrade).upgrade(); // try to upgrade if the button is clicked
        }
        if (nextBounds.isBetweenBounds(e.getX(), e.getY())) { // go to next if the button is clicked
            ResourceManager.SWOOSH_SOUND.play();
            selectedUpgrade = (selectedUpgrade + 1) % UpgradeManager.getUpgrades().size();
        }

    }

    @Override
    public void resize() {
        setX(0);
        setY(Game.HEIGHT- 256 - 20);
    }

    public void toggle() {
        open = !open;
        // change animation state so it fades in / out
        if (open) state = AnimationState.INCREASING;
        else state = AnimationState.DECREASING;
        ResourceManager.SWOOSH_SOUND.play(); // play closing / opening sound
    }

    private class Bound {
        public int x;
        public int y;
        public int height;
        public int width;

        public Bound(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
        }

        public boolean isBetweenBounds(int x, int y) {
            if (this.x <= x && x <= this.x + this.width) { // if the pos is in between x and x + width
                if (this.y - height <= y && y <= this.y) { // if the pos is in between y and y + height
                    return true;
                }
            }

            return false;
        }

    }
    
}
