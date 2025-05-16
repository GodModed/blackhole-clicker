package game.entities;

import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.AlphaComposite;

import game.Entity;
import game.Game;
import game.NumberFormatter;
import game.ResourceManager;
import game.Upgrade;
import game.UpgradeManager;

public class ShopEntity extends Entity {

    public static ShopEntity INSTANCE;

    private boolean open = false;

    private Bound upgradeBounds;
    private Bound nextBounds;
    private AnimationState state = AnimationState.NONE;

    
    private int selectedUpgrade = 0;
    private float alpha = 0f;
    private static float MAX_ALPHA = 1f;
    
    public ShopEntity() {
        super(0, Game.HEIGHT - 256 - 20);
        if (INSTANCE != null) throw new RuntimeException("Shop already exists.");
        INSTANCE = this;
    }


    @Override
    public void render(Graphics2D g) {
        if (alpha == 0) return;

        Composite oldComposite = g.getComposite();
        AlphaComposite newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
        g.setComposite(newComposite);
        
        Upgrade upgrade = UpgradeManager.getUpgrades().get(selectedUpgrade);
        g.drawImage(upgrade.getIcon(), (int) getX(), (int) getY(), Game.INSTANCE);
        g.setColor(Color.WHITE);

        String upgradeName = upgrade.getName();
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(upgradeName, g);

        g.drawString(upgrade.getName(), (int) (getX() + upgrade.getIcon().getWidth()), (int) (getY() + upgrade.getIcon().getHeight() / 2 - bounds.getHeight()));
        g.drawString("Cost:  " + NumberFormatter.format(upgrade.getCost()), (int) (getX() + upgrade.getIcon().getWidth()), (int) (getY() + upgrade.getIcon().getHeight() / 2));
        g.drawString("Level: " + upgrade.getCurrentLevel(), (int) (getX() + upgrade.getIcon().getWidth()), (int) (getY() + upgrade.getIcon().getHeight() / 2 + bounds.getHeight()));
        Font oldFont = g.getFont();
        g.setFont(oldFont.deriveFont(50f));

        int xOffset = (int) (getX() + upgrade.getIcon().getWidth() + bounds.getWidth() + 50);
        int height = g.getFontMetrics().getHeight();
        int width = g.getFontMetrics().stringWidth("A");

        nextBounds = new Bound(xOffset, (int) (getY() + upgrade.getIcon().getHeight() / 2 - height / 2), width, height);
        g.drawString("→", nextBounds.x, nextBounds.y);

        upgradeBounds = new Bound(xOffset, (int) (getY() + upgrade.getIcon().getHeight() / 2 + height / 2), width, height);
        boolean isMaxLevel = upgrade.getMaxLevel() == upgrade.getCurrentLevel();
        boolean enoughCash = upgrade.getCost() <= Game.INSTANCE.getCash();
        if (isMaxLevel || !enoughCash)
            g.setColor(Color.RED);
        else
            g.setColor(Color.GREEN);

        g.drawString(!isMaxLevel ? "↑" : "X", upgradeBounds.x, upgradeBounds.y);
        g.setColor(Color.WHITE);
        g.setFont(oldFont);
        g.setComposite(oldComposite);
    }

    @Override
    public void update(double dt) {

        setX(0);
        setY(Game.HEIGHT- 256 - 20);

        switch (state) {
            case INCREASING:
                alpha += dt * 4;
                break;
            case DECREASING:
                alpha -=  dt * 4;
                break;
            case NONE:
                return;
        }

        if (state == AnimationState.INCREASING && alpha >= MAX_ALPHA) {
            alpha = MAX_ALPHA;
            state = AnimationState.NONE;
        } else if (state == AnimationState.DECREASING && alpha <= 0) {
            alpha = 0;
            state = AnimationState.NONE;
        }
    }

    public void click(MouseEvent e) {
        if (state == AnimationState.DECREASING || alpha == 0) return;
        if (upgradeBounds.isBetweenBounds(e.getX(), e.getY())) {
            ResourceManager.UPGRADE_SOUND.play();
            UpgradeManager.getUpgrades().get(selectedUpgrade).upgrade();
        }
        if (nextBounds.isBetweenBounds(e.getX(), e.getY())) {
            ResourceManager.SWOOSH_SOUND.play();
            selectedUpgrade = (selectedUpgrade + 1) % UpgradeManager.getUpgrades().size();
        }

    }

    public void toggle() {
        open = !open;
        if (open) state = AnimationState.INCREASING;
        else state = AnimationState.DECREASING;
        ResourceManager.SWOOSH_SOUND.play();
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
            if (this.x <= x && x <= this.x + this.width) {
                if (this.y - height <= y && y <= this.y) {
                    return true;
                }
            }

            return false;
        }

    }

    private enum AnimationState {
        NONE,
        INCREASING,
        DECREASING;
    }
    
}
