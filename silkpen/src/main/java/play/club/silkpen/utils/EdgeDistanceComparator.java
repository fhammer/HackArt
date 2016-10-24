package play.club.silkpen.utils;

import android.support.annotation.NonNull;

import play.club.silkpen.entiy.Edge;


/**
 * Edge distance comparator
 *
 * @author fuzh2
 */
public class EdgeDistanceComparator implements Comparable<EdgeDistanceComparator> {

    public Edge edge;
    public double distance;

    public EdgeDistanceComparator(Edge edge, double distance) {
        this.edge = edge;
        this.distance = distance;
    }

    @Override
    public int compareTo(@NonNull EdgeDistanceComparator o) {
        if (o.distance == distance) {
            return 0;
        } else if (o.distance < distance) {
            return 1;
        } else {
            return -1;
        }
    }
}
