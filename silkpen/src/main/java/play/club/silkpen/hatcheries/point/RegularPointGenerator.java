package play.club.silkpen.hatcheries.point;


import java.util.Random;
import java.util.Vector;

import play.club.silkpen.entiy.Point;


/**
 * Regular point generator
 *
 * @author fuzh2
 */
public class RegularPointGenerator implements PointGenerator {

    private final Random random = new Random();

    private int bleedX = 0;
    private int bleedY = 0;

    public RegularPointGenerator() {

    }

    @Override
    public void setBleedX(int bleedX) {
        this.bleedX = bleedX;
    }

    @Override
    public void setBleedY(int bleedY) {
        this.bleedY = bleedY;
    }

    @Override
    public Vector<Point> generatePoints(int width, int height, int cellSize, int variance) {
        Vector<Point> points = new Vector<>();

        for (int j = -bleedY; j < height + bleedY; j += cellSize) {
            for (int i = -bleedX; i < width + bleedX; i += cellSize) {
                int x = i + random.nextInt(variance);
                int y = j + random.nextInt(variance);
                points.add(new Point(x, y));
            }
        }

        return points;
    }
}
