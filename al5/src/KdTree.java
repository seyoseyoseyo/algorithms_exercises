import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KdTree {

  private Node root;

  public KdTree() {

  }

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return this.size(this.root);
  }

  private int size(Node node) {
    return node == null ? 0 : node.size;
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    if (!contains(p)) {
      this.root = this.put(this.root, p, true, new RectHV(0, 0, 1, 1));
    }
  }

  private Node put(Node node, Point2D p, boolean b, RectHV rect) {
    if (node == null) {
      return new Node(p, 1, rect);
    }
    Point2D current = node.p;
    double key;
    if (b) {
      key = p.x();
      if (key >= current.x()) {
        node.rt = this.put(node.rt, p, false, new RectHV(current.x(), rect.ymin(), rect.xmax(), rect.ymax()));
      } else {
        node.lb = this.put(node.lb, p, false, new RectHV(rect.xmin(), rect.ymin(), current.x(), rect.ymax()));
      }
    } else {
      key = p.y();
      if (key >= current.y()) {
        node.rt = this.put(node.rt, p, true, new RectHV(rect.xmin(), current.y(), rect.xmax(), rect.ymax()));
      } else {
        node.lb = this.put(node.lb, p, true, new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), current.y()));
      }
    }
    node.size = 1 + size(node.rt) + size(node.lb);
    return node;
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return this.contains(root, p, true);
  }

  private boolean contains(Node node, Point2D p, boolean b) {
    if (node == null) {
      return false;
    }
    if (node.p.equals(p)) {
      return true;
    }
    Point2D current = node.p;
    double key;
    if (b) {
      key = p.x();
      if (key >= current.x()) {
        return contains(node.rt, p, false);
      } else {
        return contains(node.lb, p, false);
      }
    } else {
      key = p.y();
      if (key >= current.y()) {
        return contains(node.rt, p, true);
      } else {
        return contains(node.lb, p, true);
      }
    }
  }

  public void draw() {
    draw(root, true);
  }

  private void draw(Node node, boolean b) {
    if (node != null) {
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(0.01);
      node.p.draw();
      if (b) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
        draw(node.lb, false);
        draw(node.rt, false);
      } else {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();
        StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
        draw(node.lb, true);
        draw(node.rt, true);
      }
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }
    List<Point2D> result = new ArrayList<>();
    range(result, root, rect);
    return result;
  }

  private void range(List<Point2D> result, Node node, RectHV rect) {
    if (node != null) {
      if (rect.contains(node.p)) {
        result.add(node.p);
      }
      if (node.rect.intersects(rect)) {
        range(result, node.rt, rect);
        range(result, node.lb, rect);
      }
    }
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    if (root == null) return null;
    return nearest(root, p, root.p, true);
  }

  private Point2D nearest(Node node, Point2D p, Point2D c, boolean cmp) {
    Point2D closest = c;
    if (node == null) return closest;
    if (node.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
      closest = node.p;
    }
    if (node.rect.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
      Node near;
      Node far;
      if ((cmp && (p.x() < node.p.x())) || (!cmp && (p.y() < node.p.y()))) {
        near = node.lb;
        far = node.rt;
      } else {
        near = node.rt;
        far = node.lb;
      }
      closest = nearest(near, p, closest, !cmp);
      closest = nearest(far, p, closest, !cmp);
    }
    return closest;
  }


  public static void main(String[] args) {
    KdTree tree = new KdTree();
    Point2D p1 = new Point2D(0.7, 0.2);
    Point2D p2 = new Point2D(0.5, 0.4);
    Point2D p3 = new Point2D(0.2, 0.3);
    Point2D p4 = new Point2D(0.4, 0.7);
    Point2D p5 = new Point2D(0.9, 0.6);
    tree.insert(p1);
    tree.insert(p2);
    tree.insert(p3);
    tree.insert(p4);
    tree.insert(p5);
    RectHV rect = new RectHV(0.5, 0.5, 1, 1);
    for (Point2D p : tree.range(rect)) {
      System.out.println(p.toString());
    }
    tree.draw();
  }

  private static class Node {
    private final Point2D p;
    private final RectHV rect;
    private Node lb;
    private Node rt;
    private int size;

    public Node(Point2D p, int size, RectHV rect) {
      this.p = p;
      this.size = size;
      this.rect = rect;
    }

  }
}
