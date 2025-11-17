import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Triangle extends Shape {

    // class for creating the triangle shape, this class extends the abstract class Shape
    public Triangle(int length, int i, int j) {
        super(length, Type.TRIANGLE, i, j);
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
            
            double side = this.length * 3 / 4.0;
            double height = Math.sqrt(3) * side;
            int orientation = (this.i + this.j) % 2;
            int cy = (this.i + 1);
            int cx = (this.j + 2);

            int[] xs = { (int) ((cx - orientation) * side), (int) ((cx - 1 + orientation) * side), (int) ((cx + 1) * side)};
            int[] ys = { (int) ((cy + 1) * height), (int) (cy * height), (int) ((cy + orientation) * height) };

            Polygon p = new Polygon(xs, ys, 3);
            g2.fillPolygon(p);
            g2.drawPolygon(p);
            g2.setStroke(new BasicStroke(2));
            
            for (int edge = 0; edge < 3; edge++) {
                if (!isConnected[edge]) continue;
                int next = (edge + 1) % 3;
                g2.drawLine(xs[edge], ys[edge], xs[next], ys[next]);
            }
            g2.setColor(Color.BLACK);
            for (int edge = 0; edge < 3; edge++) {
                if (isConnected[edge]) continue;
                int next = (edge + 1) % 3;
                g2.drawLine(xs[edge], ys[edge], xs[next], ys[next]);
            }
        } finally {
            g2.dispose();
        }
    }
}