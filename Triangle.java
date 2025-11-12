import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Polygon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Triangle extends Shape {
    public Triangle(int length, int i, int j) {
        super(length, Type.TRIANGLE, i, j);
    }

    @Override
    public void draw(Graphics g) {
        if (g == null) return;
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.color);

            double height = Math.sqrt(3) * this.length;
            int orientation = (this.i + this.j) % 2;
            int cy = (this.i + 1);
            double cx = (this.j + 2);

            int[] xs = { (int) ((cx - orientation) * this.length), (int) ((cx - 1 + orientation) * this.length), (int) ((cx + 1) * this.length)};
            int[] ys = { (int) ((cy + 1) * height), (int) (cy * height), (int) ((cy + orientation) * height) };

            Polygon p = new Polygon(xs, ys, 3);
            g2.fillPolygon(p);
            g2.drawPolygon(p);
            g2.setStroke(new BasicStroke(2));
            
            g2.setColor(this.color);
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