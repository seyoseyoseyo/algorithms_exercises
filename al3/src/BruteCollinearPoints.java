import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {

  private List<LineSegment> segments;

  public BruteCollinearPoints(Point[] points){
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (Point point : points) {
      if (point == null) {
        throw new IllegalArgumentException();
      }
    }
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }

    segments = new ArrayList<>();
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        for (int k = j + 1; k < points.length; k++) {
          for (int l = k + 1; l < points.length; l++) {
            Double s1 = points[i].slopeTo(points[j]);
            Double s2 = points[i].slopeTo(points[k]);
            Double s3 = points[i].slopeTo(points[l]);
            if (s1.equals(s2) && s1.equals(s3)) {
              Point min = find_min(points[i], points[j], points[k], points[l]);
              Point max = find_max(points[i], points[j], points[k], points[l]);
              LineSegment line = new LineSegment(min, max);
              segments.add(line);
            }
          }
        }
      }
    }
  }

  private Point find_min(Point p1, Point p2, Point p3, Point p4) {
    Point min = p1;
    if (min.compareTo(p2) > 0) {
      min = p2;
    }
    if (min.compareTo(p3) > 0) {
      min = p3;
    }
    if (min.compareTo(p4) > 0) {
      min = p4;
    }
    return min;
  }

  private Point find_max(Point p1, Point p2, Point p3, Point p4) {
    Point max = p1;
    if (max.compareTo(p2) < 0) {
      max = p2;
    }
    if (max.compareTo(p3) < 0) {
      max = p3;
    }
    if (max.compareTo(p4) < 0) {
      max = p4;
    }
    return max;
  }

  public int numberOfSegments() {
    return segments.size();
  }
  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[segments.size()]);
  }

  public static void main(String[] args) {

    // read the n points from a file
    In in = new In(args[0]);
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
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
