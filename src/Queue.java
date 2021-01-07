import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    private Node<Item> first, last;
    private int N;

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return N;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last; // Queue为空时，添加元素需要同时修改first和last
        else oldLast.next = last;
        N++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = first;
        N--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator(first);
    }

    private class ListIterator implements Iterator<Item> {
        private Node<Item> first;

        public ListIterator(Node<Item> node) {
            first = node;
        }

        public boolean hasNext() {
            return first != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = first.item;
            first = first.next;
            return item;

        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
