package pl.ptemich.jerdy.game.core;

import pl.ptemich.jerdy.game.Tank;
import pl.ptemich.jerdy.game.inputs.MouseGameCallback;
import pl.ptemich.jerdy.game.swing.GamePanel;
import pl.ptemich.jerdy.game.swing.GameWindow;

import java.awt.Graphics;

public class Game implements Runnable, MouseGameCallback {

	private GamePanel gamePanel;
	private Thread gameThread;
    private final double SECOND_AS_NANOSECONDS = 1000000000.0;
	private final int FPS_SET = 120;
	private final int UPS_SET = 200;

    private Tank tank;

	public final static int TILES_DEFAULT_SIZE = 32;
	public final static float SCALE = 1f;
	public final static int TILES_IN_WIDTH = 26;
	public final static int TILES_IN_HEIGHT = 14;
	public final static int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

	private final boolean SHOW_FPS_UPS = true;

	public Game() {
		System.out.println("size: " + GAME_WIDTH + " : " + GAME_HEIGHT);
		initClasses();
		gamePanel = new GamePanel(this);
		new GameWindow(gamePanel);
		gamePanel.requestFocusInWindow();
		startGameLoop();
	}

	private void initClasses() {
        tank = new Tank(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update() {
        tank.update();
	}

	public void render(Graphics g) {
        tank.render(g);
	}

	@Override
	public void run() {
		double timePerFrame =  SECOND_AS_NANOSECONDS / FPS_SET;
		double timePerUpdate = SECOND_AS_NANOSECONDS / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (SHOW_FPS_UPS)
				if (System.currentTimeMillis() - lastCheck >= 1000) {
					lastCheck = System.currentTimeMillis();
					System.out.println("FPS: " + frames + " | UPS: " + updates);
					frames = 0;
					updates = 0;
				}
		}
	}

	public void windowFocusLost() {
//		if (Gamestate.state == Gamestate.PLAYING)
//			playing.getPlayer().resetDirBooleans();
	}

    public Tank getTank() {
        return tank;
    }

    @Override
    public void moveMouse(int x, int y) {
        tank.setTargetPosition(x, y);
    }
}