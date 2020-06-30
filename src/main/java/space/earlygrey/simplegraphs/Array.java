package space.earlygrey.simplegraphs;

import java.util.Arrays;

class Array<T> {

    Object[] items;
    int size;

    Array() {
        this(8);
    }

    Array(int capacity) {
        items = new Object[capacity];
        this.size = 0;
    }

    int add(T item) {
        checkSize();
        items[size] = item;
        return size++;
    }

    int getIndex(T item) {
        for (int i = size-1; i >= 0; i--) {
            if (item == items[i]) {
                return i;
            }
        }
        return -1;
    }

    void remove(int index) {
        if (index >= 0) {
            size--;
            for (int i = index; i < size; i++) {
                items[i] = items[i+1];
            }
            checkSize();
        }
    }

    void remove(T item) {
        remove(getIndex(item));
    }

    void checkSize() {
        if (items.length <= size) {
            items = Arrays.copyOf(items, 2*items.length);
        }
    }

    void set(int index, T item) {
        items[index] = item;
    }

    void clear() {
        Arrays.fill(items, null);
        size = 0;
    }

    @SuppressWarnings("unchecked")
    T get(int i) {
        return (T) items[i];
    }

    int size() {
        return size;
    }

    boolean isEmpty() {
        return size==0;
    }


}
