
import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Quick3way;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private final Stack<LineSegment> lines;
    private int numberIndex = 0;

    public FastCollinearPoints(Point[] ppp) { // finds all line segments containing 4 or more points

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
      //18-33行是检查输入格式是否有误
        
        lines = new Stack<LineSegment>();
        Point[] duplicate = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            duplicate[i] = points[i];
        }
        for (int i = 0; i < points.length; i++) {

            Comparator<Point> orignalPoint = points[i].slopeOrder();
            Arrays.sort(duplicate, orignalPoint);
            for (int j = 1; j < points.length - 1;) {

                int k = j + 1;
                while (k < points.length && orignalPoint.compare(duplicate[j], duplicate[k]) == 0)
                    k++;

                if (k - j > 2) {

                    Insertion.sort(duplicate, j, k); // sort duplicate[j] -- duplicate[k-1]
                    Point min = duplicate[j];
                    Point max = duplicate[k - 1];
                    if (points[i].compareTo(min) < 0) {
                        lines.push(new LineSegment(points[i], max));
                        numberIndex++;
                    }

                }
                j = k;
            }
        }
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

        // // read the n points from a file
        In in = new In("B:/InterimFiles/collinear-testing/collinear/input6.txt"); // 
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

        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            // StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}