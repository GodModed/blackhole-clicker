package game.entities;

import java.awt.Graphics2D;

import game.Entity;
import java.awt.image.BufferedImage;

public class DestoryedFragmentEntity extends Entity {

    private static final double DEFAULT_VELOCITY = 15;

    public enum Part {
        NW,
        NE,
        SE,
        SW;
    }

    private BufferedImage image;

    public DestoryedFragmentEntity(int x, int y, BufferedImage image, Part type) {
        super(x, y);
        switch (type) {
            case NW:
                setDeltaY(-DEFAULT_VELOCITY);
                setDeltaX(-DEFAULT_VELOCITY);
                this.image = image.getSubimage(0, 0, image.getWidth() / 2, image.getHeight() / 2);
                break;
            case NE:
                setDeltaY(-DEFAULT_VELOCITY);
                setDeltaX(DEFAULT_VELOCITY);
                this.image = image.getSubimage(image.getWidth() / 2, 0, image.getWidth() / 2, image.getHeight() / 2);
                break;
            case SE:
                setDeltaX(DEFAULT_VELOCITY);
                setDeltaY(DEFAULT_VELOCITY);
                this.image = image.getSubimage(image.getWidth() / 2, image.getHeight() / 2, image.getWidth() / 2, image.getHeight() / 2);
                break;
            case SW:
                setDeltaX(-DEFAULT_VELOCITY);
                setDeltaY(DEFAULT_VELOCITY);
                this.image = image.getSubimage(0, image.getHeight() / 2, image.getWidth() / 2, image.getHeight() / 2);
                break;
        }
    }

    @Override
    public void render(Graphics2D g) {
        // TODO Auto-generated method stub
        
    }
    
}
