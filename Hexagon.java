import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Hexagon extends Shape {

    // class for creating the Hexagon shape, this class extends the abstract class Shape
    public Hexagon(int length, int i, int j) {
        super(length, Type.HEXAGON, i, j);
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

            int side = this.length;
            double xOffset = (Math.sqrt(3) / 2.0) * (this.i % 2);
            double cx = (this.j + 1) * Math.sqrt(3) + xOffset;
            double cy = (this.i + 1) * 3 / 2.0;

            int[] xs = new int[6];
            int[] ys = new int[6];
            for (int ii = 0; ii < 6; ii++) {
                double angle = Math.toRadians(60 * ii + 150);
                xs[ii] = (int) ((cx + Math.cos(angle)) * side);
                ys[ii] = (int) ((cy + Math.sin(angle)) * side);
            }

            Polygon hex = new java.awt.Polygon(xs, ys, 6);
            g2.fillPolygon(hex);
            g2.setStroke(new BasicStroke(2));

            for (int edge = 0; edge < 6; edge++) {
                if (!isConnected[edge]) continue;
                int next = (edge + 1) % 6;
                g2.drawLine(xs[edge], ys[edge], xs[next], ys[next]);
            }
            g2.setColor(Color.BLACK);
            for (int edge = 0; edge < 6; edge++) {
                if (isConnected[edge]) continue;
                int next = (edge + 1) % 6;
                g2.drawLine(xs[edge], ys[edge], xs[next], ys[next]);
            }
        } finally {
            g2.dispose();
        }
    }
}