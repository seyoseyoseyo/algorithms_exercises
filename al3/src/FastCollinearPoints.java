import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {

  private List<LineSegment> segments;

  public FastCollinearPoints(Point[] points) {
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
    int usedCounter = -1;
    List<Point> usedPoints = new ArrayList<>();
    List<Double> usedSlopes = new ArrayList<>();
    for (int i = 0; i < points.length; i++) {
      Point[] sorted = new Point[points.length - 1];
      int counter = 0;
      for (int j = 0; j < points.length; j++) {
        if (j != i) {
          sorted[counter] = points[j];
          counter++;
        }
      }
      Arrays.sort(sorted, points[i].slopeOrder());
      System.out.println(Arrays.toString(sorted));
      for (int k = 0; k < sorted.length - 2; k++) {
        Double slope = points[i].slopeTo(sorted[k]);
        List<Point> list = new ArrayList<>();
        list.add(sorted[k]);
        while (slope == points[i].slopeTo(sorted[k+1]) && k < sorted.length - 2) {
          k++;
          list.add(sorted[k]);
        }
        if (list.size() >= 3) {
          list.add(points[i]);
          if (!used(usedCounter, usedPoints, usedSlopes, list, slope)) {
            Point min = find_min(list);
            Point max = find_max(list);
            segments.add(new LineSegment(min, max));
            usedCounter++;
            usedPoints.add(min);
            usedSlopes.add(slope);
          }
        }
      }
    }
  }

  private boolean used(int counter, List<Point> usedPoints, List<Double> usedSlopes, List<Point> list, Double slope) {
    if (counter == -1) {
      return false;
    }
    if (slope.equals(usedSlopes.get(counter))) {
      for (Point point : list) {
        if (point.compareTo(usedPoints.get(counter)) == 0) {
          return true;
        }
      }
    }
    return false;
  }

  private Point find_min(List<Point> points) {
    Collections.sort(points);
    return points.get(0);
  }

  private Point find_max(List<Point> points) {
    Collections.sort(points);
    return points.get(points.size() - 1);
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
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
