import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue = (Item[]) new Object[1];
    private int last = 0;
    private int size = 0;

    public RandomizedQueue() {
        // construct an empty randomized queue
    }

    private void resizing(int capacity) {
        Item[] oldQueue;
        oldQueue = (Item[]) new Object[capacity];
        int j = 0;
        for (int i = 0; i < last; i++) {
            if (queue[i] != null) {
                oldQueue[j++] = queue[i];
            }
        }
        last = size;
        queue = oldQueue;
    }

    public boolean isEmpty() {
        // is the randomized queue empty?
        return size == 0;
    }

    public int size() {
        // return the number of items on the randomized queue
        return size;
    }

    public void enqueue(Item item) { // add the item
        if (item == null)
            throw new IllegalArgumentException();
        if (last == queue.length)
            resizing(2 * size + 1);
        queue[last++] = item;
        size++;
    }

    public Item dequeue() {
        // remove and return a random item
        if (isEmpty())
            throw new NoSuchElementException();
        if (size < queue.length / 4)
            resizing(queue.length / 2);
        int i = StdRandom.uniform(last);
        while (queue[i] == null) {
            i = StdRandom.uniform(last);
        }

        Item tmp = queue[i];
        queue[i] = null;
        size--;
        return tmp;

    }

    public Item sample() {
        // return a random item (but do not remove it)
        if (isEmpty())
            throw new NoSuchElementException();
        int i = StdRandom.uniform(last);
        while (queue[i] == null) {
            i = StdRandom.uniform(last);
        }
        return queue[i];
    }

    public Iterator<Item> iterator() {
        // return an independent iterator over items in random order
        return new MyIterator();

    }

    private class MyIterator implements Iterator<Item> {
        
        int innerSize=size;
        Item[] iter=(Item[]) new Object[size];
        
        public MyIterator() {                       
            int j=0;
            for (int i = 0; i < last; i++) {
                if (queue[i] != null) {
                    iter[j++] = queue[i];
                }
            }
            
        }
        @Override
        public boolean hasNext() {
            // TODO Auto-generated method stub           
            return innerSize!=0;
        }

        @Override
        public Item next() {
            // TODO Auto-generated method stub
            if (!hasNext())
                throw new NoSuchElementException();            
            int i = StdRandom.uniform(last);
            while (iter[i] == null) {
                i = StdRandom.uniform(last);
            }
            Item tmp = iter[i];
            iter[i] = null;
            innerSize--;
            return tmp;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

    public static void main(String[] args) {
        // unit testing (optional)
    }
}