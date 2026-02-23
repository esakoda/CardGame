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
        g.setColor(Color.RED);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.WHITE);
        Font titleFont = new Font("Serif", Font.BOLD, 150);
        g.setFont(titleFont);
        g.drawString("Hearts", 200, 300);
    }

    public void drawInstructions(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
        g.setColor(Color.RED);
        // Split the instructions into lines so they fit into the window
        String instructions = backend.getInstructions();
        String[] lines = instructions.split("\n");
        for (int i = 0; i < lines.length; i++){
            g.drawString(lines[i], 50, (50 + 50 * i));
        }
    }

    public void drawGame(Graphics g){
        g.setColor(Color.GREEN);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
        g.drawImage(new ImageIcon("src/main/resources/1.png").getImage(), 50, 50, 75, 100, this);
    }

    public void drawEnd(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
