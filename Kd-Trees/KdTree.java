import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root = null;
    private int number = 0;

    private class Node {
        double x;
        double y;
        Node left = null;
        Node right = null;
        Point2D point = null;

        public Node(double x, double y) {
            this.x = x;
            this.y = y;
            point = new Point2D(x, y);
        }

        public Node(Point2D point) {
            this.x = point.x();
            this.y = point.y();
            this.point = point;
        }
    }

    public KdTree() { // construct an empty set of points
    }

    public boolean isEmpty() { // is the set empty?
        return number == 0;
    }

    public int size() {// number of points in the set
        return number;
    }

    public void insert(Point2D p) {
        // add the point to the set (if it is not already in the set)
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null) {
            root = new Node(p);
            number = 1;
            return;
        }
        insert(-1, p, root);
    }

    private void insert(int flip, Point2D p, Node present) {
        if (p.equals(present.point))
            return;
        if (flip < 0) {
            if (p.x() < present.x) {
                if (present.left == null) {
                    present.left = new Node(p);
                    number++;
                    return;
                }
                insert(1, p, present.left);
            } else {
                if (present.right == null) {
                    present.right = new Node(p);
                    number++;
                    return;
                }
                insert(1, p, present.right);
            }
        } else {
            if (p.y() < present.y) {
                if (present.left == null) {
                    present.left = new Node(p);
                    number++;
                    return;
                }
                insert(-1, p, present.left);
            } else {
                if (present.right == null) {
                    present.right = new Node(p);
                    number++;
                    return;
                }
                insert(-1, p, present.right);
            }
        }

    }

    public boolean contains(Point2D p) { // does the set contain point p?
        if (p == null)
            throw new IllegalArgumentException();
        return contains(-1, p, root);

    }

    private boolean contains(int flip, Point2D p, Node present) {

        // System.out.println(present.point);
        if (present == null)
            return false;
        // System.out.println("present!=null");
        if (p.equals(present.point))
            return true;
        // System.out.println("!equals");
        if (flip < 0)
            if (p.x() < present.x)
                return contains(1, p, present.left);
            else
                return contains(1, p, present.right);
        else if (p.y() < present.y)
            return contains(-1, p, present.left);
        else
            return contains(-1, p, present.right);

    }

    public void draw() {// draw all points to standard draw

        StdDraw.setScale(-0.1, 1.1);
        draw(-1, 0, 0, 1, 1, root);
    }

    private void draw(int flip, double lx, double ly, double rx, double hy, Node present) {
        if (present == null)
            return;

        StdDraw.setPenColor(0, 0, 0);
        StdDraw.setPenRadius(0.008);
        new Point2D(present.x, present.y).draw();
        if (flip < 0) {
            StdDraw.setPenColor(255, 0, 0);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(present.x, ly, present.x, hy);
            draw(1, lx, ly, present.x, hy, present.left);
            draw(1, present.x, ly, rx, hy, present.right);
        } else {
            StdDraw.setPenColor(0, 0, 255);
            StdDraw.setPenRadius(0.001);
            StdDraw.line(lx, present.y, rx, present.y);
            draw(-1, lx, ly, rx, present.y, present.left);
            draw(-1, lx, present.y, rx, hy, present.right);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        // all points that are inside the rectangle (or on the boundary)
        if (rect == null)
            throw new IllegalArgumentException();
        Queue<Point2D> queue = new Queue<>();
        range(root, rect, queue, -1, 0, 0, 1, 1);
        return queue;
    }

    private void range(Node present, RectHV rect, Queue<Point2D> queue, int flip, double lx, double ly, double rx,
            double hy) {
        if (present == null)
            return;
        if (!rect.intersects(new RectHV(lx, ly, rx, hy)))
            return;
        if (rect.contains(present.point))
            queue.enqueue(present.point);
        if (flip < 0) {
            range(present.left, rect, queue, 1, lx, ly, present.x, hy);
            range(present.right, rect, queue, 1, present.x, ly, rx, hy);
        } else {
            range(present.left, rect, queue, -1, lx, ly, rx, present.y);
            range(present.right, rect, queue, -1, lx, present.y, rx, hy);
        }
    }

    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to point p; null if the set is empty
        if (p == null)
            throw new IllegalArgumentException();
        if (root == null)
            return null;
        Node nearest = new Node(1.1, 1.1);
        nearest.point = root.point;
        nearest(-1, p, nearest, root,0,0,1,1);
        return nearest.point;
    }

    private void nearest(int flip, Point2D p, Node nearest, Node present,double lx,double ly,double rx,double hy) {
        if (present == null)
            return;
        if(p.equals(present.point)) {
            nearest.point=present.point;
            return;
        }
        if (p.distanceSquaredTo(present.point) < p.distanceSquaredTo(nearest.point))
            nearest.point = present.point;
        if (flip < 0) {// vertical
            if (p.x() < present.x) { // query point on the left of present point
                nearest(1, p, nearest, present.left,lx,ly,present.x,hy);
                if (p.distanceSquaredTo(nearest.point) > new RectHV(present.x,ly,rx,hy).distanceSquaredTo(p)) {
                    nearest(1, p, nearest, present.right,present.x,ly,rx,hy);
                }
            } else { // query point on the right of present point
                nearest(1, p, nearest, present.right,present.x,ly,rx,hy);
                if (p.distanceSquaredTo(nearest.point) >new RectHV(lx,ly,present.x,hy).distanceSquaredTo(p)) {

                    nearest(1, p, nearest, present.left,lx,ly,present.x,hy);
                }
            }
        } else { // horizontal
            if (p.y() < present.y) { // query point under present point
                nearest(-1, p, nearest, present.left,lx,ly,rx,present.y);
                if (p.distanceSquaredTo(nearest.point) > new RectHV(lx,present.y,rx,hy).distanceSquaredTo(p)) {
                    nearest(-1, p, nearest, present.right,lx,present.y,rx,hy);
                }
            } else { // query point upon present point
                nearest(-1, p, nearest, present.right,lx,present.y,rx,hy);
                if (p.distanceSquaredTo(nearest.point) > new RectHV(lx,ly,rx,present.y).distanceSquaredTo(p)) {

                    nearest(-1, p, nearest, present.left,lx,ly,rx,present.y);
                }
            }
        }
    }

    public static void main(String[] args) { // unit testing of the methods (optional)

        KdTree k = new KdTree();
//        In in = new In("B:/InterimFiles/kdtree/input10.txt");
//        while (!in.isEmpty()) {
//            double x = in.readDouble();
//            double y = in.readDouble();
//            Point2D p = new Point2D(x, y);
//            k.insert(p);
//        }
        k.insert(new Point2D(0.372,0.497));
        k.insert(new Point2D(0.564,0.413));
        k.insert(new Point2D(0.226,0.577));
        k.insert(new Point2D(0.144,0.179));
        k.insert(new Point2D(0.084,0.51));
        k.insert(new Point2D(0.32,0.708));
        k.insert(new Point2D(0.417,0.362));
        k.insert(new Point2D(0.862,0.825));
        k.insert(new Point2D(0.785,0.725));
        k.insert(new Point2D(0.499,0.208));


        k.draw();
        System.out.println(k.nearest(new Point2D(0.29,0.47)));
        // StdDraw.setPenColor(0, 255, 0);
        // StdDraw.setPenRadius(0.01);
        // RectHV r = new RectHV(0, 0, 0.9, 1);
        // r.draw();
        // for (Point2D p : k.range(r)) {
        // p.draw();
        // }
        // System.out.println(k.contains(new Point2D(0.97528 ,0.654508)));

    }

}
