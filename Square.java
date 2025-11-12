import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Square extends Shape {
    public Square(int length, int i, int j) {
        super(length, Type.SQUARE, i, j);
    }

    @Override
    public void draw(Graphics g) {
        if (g == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.color);

            int x = (this.j + 1) * this.length;
            int y = (this.i + 1) * this.length;
            g2.fillRect(x, y, this.length, this.length);
            g2.drawRect(x, y, this.length, this.length);
            g2.setStroke(new BasicStroke(2));

            g2.setColor(this.color);
            for (int side = 0; side < 4; side++) {
                if (!isConnected[side]) continue;
                switch (side) {
                    case 0 -> g2.drawLine(x, y + length, x, y);                     // Left
                    case 1 -> g2.drawLine(x, y, x + length, y);                     // Top
                    case 2 -> g2.drawLine(x + length, y, x + length, y + length);   // Right
                    case 3 -> g2.drawLine(x + length, y + length, x, y + length);   // Bottom
                }
            }
            g2.setColor(Color.BLACK);
            for (int side = 0; side < 4; side++) {
                if (isConnected[side]) continue;
                switch (side) {
                    case 0 -> g2.drawLine(x, y + length, x, y);                     // Left
                    case 1 -> g2.drawLine(x, y, x + length, y);                     // Top
                    case 2 -> g2.drawLine(x + length, y, x + length, y + length);   // Right
                    case 3 -> g2.drawLine(x + length, y + length, x, y + length);   // Bottom
                }
            }
        } finally {
            g2.dispose();
        }
    }
}