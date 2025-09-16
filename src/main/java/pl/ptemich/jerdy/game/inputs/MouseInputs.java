package pl.ptemich.jerdy.game.inputs;

import pl.ptemich.jerdy.game.core.Game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInputs implements MouseListener, MouseMotionListener {

    private MouseGameCallback gameCallback;

    public MouseInputs(MouseGameCallback gameCallback) {
        this.gameCallback = gameCallback;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("X: " + e.getX() + " Y:" + e.getY());
        //game.getTank().moveTo(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("X: " + e.getX() + " Y:" + e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gameCallback.moveMouse(e.getX(), e.getY());
    }
}
