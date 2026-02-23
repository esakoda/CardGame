import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {
    private Game backend;
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;

    public GameView(Game backend){
        this.backend = backend;

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Hearts");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint(Graphics g){
        g.drawImage(new ImageIcon("src/main/resources/1.png").getImage(), 50, 50, 75, 100, this);
        if (backend.getState() == Game.STATE_TITLE){
            drawTitle(g);
        }
        else if (backend.getState() == Game.STATE_INSTR){
            drawInstructions(g);
        }
        else if (backend.getState() == Game.STATE_GAME){
            drawGame(g);
        }
        else if (backend.getState() == Game.STATE_END){
            drawEnd(g);
        }
    }

    public void drawTitle(Graphics g){

    }

    public void drawInstructions(Graphics g){

    }

    public void drawGame(Graphics g){

    }

    public void drawEnd(Graphics g){

    }
}
