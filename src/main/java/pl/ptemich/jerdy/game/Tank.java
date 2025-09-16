package pl.ptemich.jerdy.game;

import pl.ptemich.jerdy.game.core.Game;

import java.awt.*;

public class Tank extends GameEntity {

    private static double SPEED = 1.5;
    private Game game;

    private double x = 100;
    private double y = 100;

    private double vx = 0;
    private double vy = 0;

    private int width = 10;
    private int height = 10;

    private int targetX;
    private int targetY;

    public Tank(Game game) {
        this.game = game;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
        if (targetX != x) {
            vx = targetX > x ? SPEED : -SPEED;
        } else {
            vx = 0;
        }

        if (targetY != y) {
            vy = targetY > y ? SPEED : -SPEED;
        } else {
            vy = 0;
        }

        x += vx;
        y += vy;

        if (x + width / 2 >= Game.GAME_WIDTH || x - width / 2 <= 0) {
            vx = 0;
        }

        if (y + width / 2 >= Game.GAME_HEIGHT || y - width / 2 <= 0) {
            vy = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        if (x > 0 && y > 0) {
            g.fillOval((int) x - width / 2, (int) y - height / 2, width, height);
        }
    }

    public void setTargetPosition(int x, int y) {
        targetX = x;
        targetY = y;
    }
}
