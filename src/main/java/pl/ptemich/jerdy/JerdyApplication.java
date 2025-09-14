package pl.ptemich.jerdy;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.ptemich.jerdy.game.core.Game;

@SpringBootApplication
public class JerdyApplication {

	public static void main(String[] args) {
//		SpringApplication.run(JerdyApplication.class, args);

//        SwingUtilities.invokeLater(() -> {
//            GameEngine game = new GameEngine();
//            game.start();
//        });

        new Game();
	}

}
