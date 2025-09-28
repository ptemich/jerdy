package pl.ptemich.jerdy.game;

import java.awt.*;

public class ActivityBar extends GameEntity {

    private final int posX;
    private final int posY;
    private final int width;
    private final int height;

    private final int leftBorder;
    private final int rightBorder;
    private final int topBorder;


    private final int targetWidth;
    private int targetX;
    private int targetVelocity;
    private int traveledInDirection;

    private static final int MIN_ONE_DIRECTION_MOVE = 60;


    public ActivityBar(int posX, int posY, int width, int height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;

        this.leftBorder = posX - width / 2;
        this.rightBorder = posX + width / 2;
        this.topBorder = posY - height / 2;

        this.targetWidth = width / 10;
        this.targetX = posX;

        this.targetVelocity = 1;
    }

    @Override
    public void update() {
        traveledInDirection += Math.abs(targetVelocity);
        targetX += targetVelocity;

        // randomly change direction
        var rng = Math.round(Math.random() * 1000) % 19;

        if (rng == 1 && traveledInDirection > MIN_ONE_DIRECTION_MOVE) {
            changeTargetBoxDirection();
        }

        if (targetX + targetWidth / 2 > rightBorder || targetX - targetWidth / 2 < leftBorder) {
            changeTargetBoxDirection();
        }

        System.out.println(traveledInDirection);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);


        g.fillRect(leftBorder, topBorder, width, height);

        g.setColor(Color.BLACK);
        g.fillRect(targetX - targetWidth / 2, topBorder, targetWidth, height);
    }

    private void changeTargetBoxDirection() {
        targetVelocity = targetVelocity * -1;
        traveledInDirection = 0;
    }
}
