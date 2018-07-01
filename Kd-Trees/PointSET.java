import java.util.Iterator;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private final TreeSet<Point2D> set = new TreeSet<>();

    public PointSET() { // construct an empty set of points

    }

    public boolean isEmpty() { // is the set empty?
        return set.isEmpty();
    }

    public int size() { // number of points in the set
        return set.size();
    }

    public void insert(Point2D p) {// add the point to the set (if it is not already in the set)
        set.add(p);
    }

    public boolean contains(Point2D p) {// does the set contain point p?
        return set.contains(p);
    }

    public void draw() { // draw all points to standard draw

        Iterator<Point2D> iterator = set.iterator();
        while (iterator.hasNext()) {
            iterator.next().draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        Queue<Point2D> queue = new Queue<>();
        Iterator<Point2D> iterator = set.iterator();
        while (iterator.hasNext()) {
            Point2D point = iterator.next();
            if (rect.contains(point))
                queue.enqueue(point);
        }
        return queue;

    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty

        Iterator<Point2D> iterator = set.iterator();
        Point2D nearestPoint = null;
        if (iterator.hasNext())
            nearestPoint = iterator.next();
        while (iterator.hasNext()) {
            Point2D point = iterator.next();

            if (point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p))
                nearestPoint = point;
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        // unit testing of the methods (optional)
        PointSET set = new PointSET();
        In in = new In("B:/InterimFiles/kdtree/input10.txt");
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            set.insert(p);
        }
        set.nearest(new Point2D(0, 0));
        // set.draw();
        // System.out.println(set.nearest(new Point2D(0.793892 ,0.095492)));

    }
}
