package play.club.silkpen.hatcheries.point;


import java.util.Vector;

import play.club.silkpen.entiy.Point;

/**
 * Point generator
 *
 * @author fuzh2
 */
public interface PointGenerator {

    Vector<Point> generatePoints(int width, int height, int cellSize, int variance);

    void setBleedX(int bleedX);

    void setBleedY(int bleedY);
}
