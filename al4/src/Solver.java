import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solver {

  private Node finalNode;
  private boolean solvable;

  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }
    Board mirror = initial.twin();
    Node mirrorNode = new Node(mirror, 0);
    MinPQ<Node> pqMirror = new MinPQ<>();
    pqMirror.insert(mirrorNode);
    Node node = new Node(initial, 0);
    MinPQ<Node> pq = new MinPQ<>();
    pq.insert(node);
    while (true) {
      Node currentNode = pq.delMin();
      Board currentBoard = currentNode.current;
      Node mirrorCurrentNode = pqMirror.delMin();
      Board mirrorCurrentBoard = mirrorCurrentNode.current;
      if (currentBoard.isGoal()) {
        finalNode = currentNode;
        solvable = true;
        break;
      } else if (mirrorCurrentBoard.isGoal()) {
        solvable = false;
        break;
      } else {
        for (Board neighborBoard : currentBoard.neighbors()) {
          if (currentNode.prev == null) {
            Node temp = new Node(neighborBoard, currentNode.moves + 1);
            temp.addPrev(currentNode);
            pq.insert(temp);
          } else if (!(neighborBoard.equals(currentNode.prev.current))) {
            Node temp = new Node(neighborBoard, currentNode.moves + 1);
            temp.addPrev(currentNode);
            pq.insert(temp);
          }
        }
        for (Board neighborBoard : mirrorCurrentBoard.neighbors()) {
          if (mirrorCurrentNode.prev == null) {
            Node temp = new Node(neighborBoard, mirrorCurrentNode.moves + 1);
            temp.addPrev(mirrorCurrentNode);
            pqMirror.insert(temp);
          } else if (!(neighborBoard.equals(mirrorCurrentNode.prev.current))) {
            Node temp = new Node(neighborBoard, mirrorCurrentNode.moves + 1);
            temp.addPrev(mirrorCurrentNode);
            pqMirror.insert(temp);
          }
        }
      }
    }
  }

  public boolean isSolvable() {
    return solvable;
  }

  public int moves() {
    if (!solvable) {
      return -1;
    }
    return finalNode.moves;
  }

  public Iterable<Board> solution() {
    if (!solvable) {
      return null;
    }
    Stack<Board> boards = new Stack<>();
    while (finalNode.prev != null) {
      boards.push(finalNode.current);
      finalNode = finalNode.prev;
    }
    boards.push(finalNode.current);
    List<Board> result = new ArrayList<>();
    while (!boards.empty()) {
      result.add(boards.pop());
    }
    return result;
  }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

  private class Node implements Comparable<Node> {
    Board current;
    int moves;
    Node prev;

    public Node(Board current, int moves) {
      this.current = current;
      this.moves = moves;
    }

    public void addPrev(Node prevNode) {
      this.prev = prevNode;
    }

    @Override
    public int compareTo(Node node) {
      int val1 = this.current.manhattan() + moves;
      int val2 = node.current.manhattan() + node.moves;
      if (val1 > val2) {
        return 1;
      } else if (val1 == val2) {
        return 0;
      } else {
        return -1;
      }
    }
  }
}
