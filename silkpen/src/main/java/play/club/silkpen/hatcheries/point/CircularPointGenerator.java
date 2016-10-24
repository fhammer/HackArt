package play.club.silkpen.hatcheries.point;


import java.util.Random;
import java.util.Vector;

import play.club.silkpen.entiy.Point;


/**
 * Circular point generator
 * point生成器
 *
 * @author fuzh2
 */
public class CircularPointGenerator implements PointGenerator {

    private final static int POINTS_PER_CIRCLE = 8;
    private final Random random = new Random();

    private int bleedX = 0;
    private int bleedY = 0;

    public CircularPointGenerator() {

    }

    @Override
    public Vector<Point> generatePoints(int width, int height, int cellSize, int variance) {
        Vector<Point> points = new Vector<>();

        Point center = new Point(width / 2, height / 2);
        points.add(new Point(center.x, center.y));

        int limit = Math.max(width + bleedX, height + bleedY);

        for (int radius = cellSize; radius < limit; radius += cellSize) {
            double slice = 2 * Math.PI / POINTS_PER_CIRCLE;
            for (int i = 0; i < POINTS_PER_CIRCLE; i++) {
                double angle = slice * i;
                int x = (int) (center.x + radius * Math.cos(angle)) + random.nextInt(variance);
                int y = (int) (center.y + radius * Math.sin(angle)) + random.nextInt(variance);
                points.add(new Point(x, y));
            }
        }

        return points;
    }

    @Override
    public void setBleedX(int bleedX) {
        this.bleedX = bleedX;
    }

    @Override
    public void setBleedY(int bleedY) {
        this.bleedY = bleedY;
    }
}
