import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Square extends Shape {

    // class for creating the square shape, this class extends the abstract class Shape
    public Square(int length, int i, int j) {
        super(length, Type.SQUARE, i, j);
        isConnected = new boolean[this.type.getValue()];
        for (int ii = 0; ii < this.type.getValue(); ii++)
            neighbors.add(null);
    }

    /*
        drawing method:
        checks the routes/edges connecting the different squares and
        paint them with the corresponding visted color 
    */ 
    @Override
    public void draw(Graphics g) {
        if (g == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.explored.getColor());
            
            double side = this.length * 3 / 2.0;
            int x = (int) ((this.j + 1) * side);
            int y = (int) ((this.i + 1) * side);
            g2.fillRect(x, y, (int) side, (int) side);
            g2.drawRect(x, y, (int) side, (int) side);
            g2.setStroke(new BasicStroke(2));

            for (int edge = 0; edge < 4; edge++) {
                if (!isConnected[edge]) continue;
                switch (edge) {
                    case 0 -> g2.drawLine(x, (int) (y + side), x, y);                     // Left
                    case 1 -> g2.drawLine(x, y, (int) (x + side), y);                     // Top
                    case 2 -> g2.drawLine((int) (x + side), y, (int) (x + side), (int) (y + side));   // Right
                    case 3 -> g2.drawLine((int) (x + side), (int) (y + side), x, (int) (y + side));   // Bottom
                }
            }
            g2.setColor(Color.BLACK);
            for (int edge = 0; edge < 4; edge++) {
                if (isConnected[edge]) continue;
                switch (edge) {
                    case 0 -> g2.drawLine(x, (int) (y + side), x, y);                     // Left
                    case 1 -> g2.drawLine(x, y, (int) (x + side), y);                     // Top
                    case 2 -> g2.drawLine((int) (x + side), y, (int) (x + side), (int) (y + side));   // Right
                    case 3 -> g2.drawLine((int) (x + side), (int) (y + side), x, (int) (y + side));   // Bottom
                }
            }
        } finally {
            g2.dispose();
        }
    }
}