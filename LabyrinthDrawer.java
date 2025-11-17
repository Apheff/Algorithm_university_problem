import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class LabyrinthDrawer extends JPanel {

    private BufferedImage buffer;

    // creates the frame and the panel where to paint the buffered image
    public LabyrinthDrawer(BufferedImage buffer) {
        this.buffer = buffer;
        
        JFrame frame = new JFrame("Shape Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setVisible(true);
        frame.add(this);
    }

    // draws the Bufferedimage on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(buffer, 0, 0, null);
    }
}
