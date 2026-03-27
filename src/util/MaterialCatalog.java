package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import materials.Material;

public class MaterialCatalog<T extends Material> {

    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        if (item != null) {
            items.add(item);
        }
    }

    public boolean remove(T item) {
        return items.remove(item);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public T get(int index) {
        return items.get(index);
    }

    public List<T> getAll() {
        return Collections.unmodifiableList(items);
    }

    public void clear() {
        items.clear();
    }
}
