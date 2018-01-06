import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {

  private final SET<Point2D> storage;

  public PointSET() {
    storage = new SET<>();
  }

  public boolean isEmpty() {
    return storage.isEmpty();
  }

  public int size() {
    return storage.size();
  }

  public void insert(Point2D p) {
    validate(p);
    storage.add(p);
  }

  public boolean contains(Point2D p) {
    validate(p);
    return storage.contains(p);
  }

  public void draw() {
    for (Point2D p : storage) {
      p.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    validate(rect);
    List<Point2D> result = new ArrayList<>();
    for (Point2D p : storage) {
      if (rect.contains(p)) {
        result.add(p);
      }
    }
    return result;
  }

  public Point2D nearest(Point2D p) {
    validate(p);
    double distance = 2;
    Point2D result = null;
    for (Point2D point : storage) {
      double curr = p.distanceSquaredTo(point);
      if (curr < distance) {
        distance = curr;
        result = point;
      }
    }
    return result;
  }

  private void validate(Object object) {
    if (object == null) {
      throw new IllegalArgumentException();
    }
  }

}
