import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

  private Item[] s;
  private int top;
  private int bottom;

  public Deque() {
    s = (Item[]) new Object[1];
    top = -1;
    bottom = 0;
  }

  public boolean isEmpty() {
    return (top == -1);
  }

  public int size() {
    if (isEmpty()) {
      return 0;
    }
    if (bottom >= top) {
      return bottom - top + 1;
    } else {
      return s.length - (top - bottom) + 1;
    }
  }

  private boolean isFull() {
    return size() == s.length;
  }

  public void addFirst(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (isFull()) {
      resize(s.length * 2);
    }
    if (top == -1) {
      top = 0;
      bottom = 0;
    } else if (top == 0) {
      top = s.length - 1;
    } else {
      top = top - 1;
    }
    s[top] = item;
  }

  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException();
    }
    if (isFull()) {
      resize(s.length * 2);
    }

    if (top == -1) {
      top = 0;
      bottom = 0;
    } else if (bottom == s.length - 1) {
      bottom = 0;
    } else {
      bottom = bottom + 1;
    }
    s[bottom] = item;
  }

  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item value = s[top];
    if (top == bottom) {
      top = -1;
      bottom = -1;
    } else {
      if (top == s.length - 1) {
        top = 0;
      } else {
        top = top + 1;
      }
    }
    return value;
  }

  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    Item value = s[bottom];
    if (top == bottom) {
      top = -1;
      bottom = -1;
    } else if (bottom == 0) {
      bottom = s.length - 1;
    } else {
      bottom = bottom - 1;
    }
    return value;
  }

  private void resize(int capacity) {
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i <= bottom; i++) {
      copy[i] = s[i];
    }
    for (int i = bottom + 1; i < s.length; i++) {
      copy[i + s.length] = s[i];
    }
    if (top > bottom) {
      top = top + s.length;
    }
    s = copy;
  }


  @Override
  public Iterator<Item> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<Item> {

    private int current = top;

    @Override
    public boolean hasNext() {
      return current != -1;
    }

    @Override
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item item = s[current];
      if (current == bottom) {
        current = -1;
      } else {
        if (current == s.length - 1) {
          current = 0;
        } else {
          current = current + 1;
        }
      }
      return item;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }
  }

  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<Integer>();
    deque.addLast(1);
    Iterator<Integer> iterator = deque.iterator();
    System.out.println(iterator.next());
  }
}
