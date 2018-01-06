import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
  public static void main(String[] args) {
    int number = Integer.parseInt(args[0]);
    RandomizedQueue<String> queue = new RandomizedQueue<>();
    while (number > 0) {
      queue.enqueue(StdIn.readString());
      number--;
    }
    while (!queue.isEmpty()) {
      StdOut.println(queue.dequeue());
    }
  }
}
