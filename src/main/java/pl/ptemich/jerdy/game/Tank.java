package pl.ptemich.jerdy.game;

import pl.ptemich.jerdy.game.core.Game;

import java.awt.*;

public class Tank extends GameEntity {

    private Game game;

    private double x = 100;
    private double y = 100;

    private double vx = -1;
    private double vy = -1;

    private int width = 10;
    private int height = 10;

    public Tank(Game game) {
        this.game = game;
    }

    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void update() {
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
            g.fillRect((int) x - width / 2, (int) y - height / 2, width, height);
        }
    }
}
