import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.In;

public class Deque<Item> implements Iterable<Item> {
    private Node first = new Node();
    private Node last = first;
    private int size = 0;

    public Deque() {
        // construct an empty deque
        // first.next = first;
        // first.prev = first;
    }

    private class Node {
        Node prev;
        Node next;
        Item item;
    }

    public boolean isEmpty() {
        // is the deque empty?
        return first.item == null;
    }

    public int size() {
        // return the number of items on the deque
        return size;
    }

    public void addFirst(Item item) {
        // add the item to the front
        if (item == null)
            throw new IllegalArgumentException();
        if (isEmpty()) {
            first.item = item;
            size++;
            return;
        }
        first.prev = new Node();
        first.prev.item = item;
        first.prev.next = first;
        first = first.prev;
        size++;
    }

    public void addLast(Item item) {
        // add the item to the end
        if (item == null)
            throw new IllegalArgumentException();
        if (isEmpty()) {
            last.item = item;
            size++;
            return;
        }
        last.next = new Node();
        last.next.item = item;
        last.next.prev = last;
        last = last.next;
        size++;
    }

    public Item removeFirst() {
        // remove and return the item from the front
        if (isEmpty())
            throw new NoSuchElementException();
        Item tmp = first.item;
        first.item = null;
        if (size != 1) {
            first = first.next;
            first.prev = null;
        }
        size--;
        return tmp;

    }

    public Item removeLast() {
        // remove and return the item from the end
        if (isEmpty())
            throw new NoSuchElementException();

        Item tmp = last.item;
        last.item = null;
        if (size != 1) {
            last = last.prev;
            last.next = null;
        }
        size--;
        return tmp;

    }

    public Iterator<Item> iterator() {
        // return an iterator over items in order from front to end
        return new MyIterator();
    }

    private class MyIterator implements Iterator<Item> {

        Node current = first;

        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub
            if (current == first)
                return current.item != null;
            return current != null;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (current == null)
                throw new NoSuchElementException();
            Item tmp = current.item;
            current = current.next;
            return tmp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        // unit testing (optional)
        Deque<String> deque = new Deque<>();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            deque.addFirst(in.readString());
        }
        // Iterator<String> iter=deque.iterator();
        for (String element : deque) {
            System.out.println(element + "  ");
        }
        System.out.println(deque.size());

    }
}
