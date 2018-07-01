import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {

    private final Stack<LineSegment> lines;
    private int numberIndex = 0;

    public BruteCollinearPoints(Point[] ppp) { // finds all line segments containing 4 points

        if (ppp == null)
            throw new IllegalArgumentException();
        Point[] points = new Point[ppp.length];
        
        for (int i = 0; i < ppp.length; i++) {
            if (ppp[i] == null)
                throw new IllegalArgumentException();
            points[i] = ppp[i];
        }
        Quick3way.sort(points);
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        lines = new Stack<LineSegment>();
        int n = points.length;
        if (n < 4)
            return;

        for (int first = 0; first < n - 3; first++) {
            for (int second = first + 1; second < n - 2; second++) {
                for (int third = second + 1; third < n - 1; third++) {
                    for (int fourth = third + 1; fourth < n; fourth++) {

                        if (isCollinearPoints(points[first], points[second], points[third], points[fourth])) {
                            lines.push(new LineSegment(points[first], points[fourth]));
                            numberIndex++;
                        }
                    }
                }
            }
        }

        // recursion(0, 1, 2, 3, points);
    }

    private boolean isCollinearPoints(Point p0, Point p1, Point p2, Point p3) {

        if (p0.slopeTo(p1) == p1.slopeTo(p2) && p1.slopeTo(p2) == p2.slopeTo(p3))
            return true;

        return false;
    }

    public int numberOfSegments() { // the number of line segments
        return numberIndex;
    }

    public LineSegment[] segments() { // the line segments

        LineSegment[] tmp = new LineSegment[numberIndex];
        int i = 0;
        for (LineSegment li : lines) {
            tmp[i++] = li;
        }

        return tmp;
    }

    public static void main(String[] args) {

        In in = new In("B:/InterimFiles/collinear-testing/collinear/input8.txt"); // 
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            // StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}