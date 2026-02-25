import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
        Font titleFont = new Font("Serif", Font.ITALIC, 150);
        g.setFont(titleFont);
        g.drawString("Hearts", 200, 350);
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
        // Green background
        g.setColor(new Color(34,139,34));
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);

        Image backImage = new ImageIcon("src/main/resources/back.png").getImage();
        Player[] players = backend.getPlayers();

        // Figure out who is the current player and assign the rest of the players positions
        int currentIndex = backend.getCurrent().getNum();
        int leftIndex = (currentIndex + 1) % 4;
        int topIndex = (currentIndex + 2) % 4;
        int rightIndex = (currentIndex + 3) % 4;

        // Draw current player's hand face up at the bottom of the screen
        ArrayList<Card> currentHand = players[currentIndex].getHand();
        for (int i = 0; i < currentHand.size(); i++){
            g.drawImage(currentHand.get(i).getImage(), 50 + i * 55, 460, 55, 80, this);
        }

        // Draw the next players cards face down
        for (int i = 0; i < players[leftIndex].getHand().size(); i++){
            g.drawImage(backImage, 20, 50 + i * 25, 55, 80, this);
        }

        for (int i = 0; i < players[topIndex].getHand().size(); i++){
            g.drawImage(backImage,125 + i * 40,30,55,80,this);
        }

        for (int i = 0; i < players[rightIndex].getHand().size(); i++){
            g.drawImage(backImage, 720, 50 + i * 25, 55, 80, this);
        }

        g.setColor(Color.WHITE);
        g.drawString("Trick size: " + backend.getTrick().size(), 300,200);
        // Draw the trick in the center
        ArrayList<Card> trick = backend.getTrick();
        for (int i = 0; i < trick.size(); i++){
            g.drawImage(trick.get(i).getImage(), 290 + i * 65, 240, 55, 80, this);
        }

        // Draw player names and scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(players[currentIndex].getName() + ": " + players[currentIndex].getPoints() + " pts", 350, 555);
        g.drawString(players[topIndex].getName() + ": " + players[topIndex].getPoints() + " pts", 350, 125);
        g.drawString(players[leftIndex].getName() + ": " + players[leftIndex].getPoints() + " pts", 20, 450);
        g.drawString(players[rightIndex].getName() + ": " + players[rightIndex].getPoints() + " pts", 680, 450);

        // Draw whose turn it is
        g.setFont(new Font("Arial", Font.ITALIC, 18));
        g.drawString(players[currentIndex].getName() + "'s turn", 330, 225);
    }

    public void drawEnd(Graphics g){
        g.setColor(Color.WHITE);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
    }
}
