package pl.ptemich.jerdy.game.swing;

import pl.ptemich.jerdy.game.core.Game;
import pl.ptemich.jerdy.game.inputs.KeyboardInputs;
import pl.ptemich.jerdy.game.inputs.MouseInputs;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static pl.ptemich.jerdy.game.core.Game.GAME_HEIGHT;
import static pl.ptemich.jerdy.game.core.Game.GAME_WIDTH;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
    private KeyboardInputs keyboardInputs;
	private Game game;

	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(game);
        keyboardInputs = new KeyboardInputs();

		this.game = game;
		setPanelSize();

		addKeyListener(keyboardInputs);
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(GAME_WIDTH, GAME_HEIGHT);
		setPreferredSize(size);
	}

	public void updateGame() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}

	public Game getGame() {
		return game;
	}

}