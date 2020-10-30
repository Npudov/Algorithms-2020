package lesson5;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class OpenAddressingSet<T> extends AbstractSet<T> {

    private final int bits;

    private final int capacity;

    private final Object[] storage;

    private int size = 0;

    private int startingIndex(Object element) {
        return element.hashCode() & (0x7FFFFFFF >> (31 - bits));
    }

    public OpenAddressingSet(int bits) {
        if (bits < 2 || bits > 31) {
            throw new IllegalArgumentException();
        }
        this.bits = bits;
        capacity = 1 << bits;
        storage = new Object[capacity];
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Проверка, входит ли данный элемент в таблицу
     */
    @Override
    public boolean contains(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o)) {
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }

        for (int i = 0; i < storage.length; i++) { /* Добавлен данный кусок кода в метод, так как
                                                      не всегда верно вычисляется индекс*/
            current = storage[i];
            if (current != null) {
                if (current.equals(o)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Добавление элемента в таблицу.
     * <p>
     * Не делает ничего и возвращает false, если такой же элемент уже есть в таблице.
     * В противном случае вставляет элемент в таблицу и возвращает true.
     * <p>
     * Бросает исключение (IllegalStateException) в случае переполнения таблицы.
     * Обычно Set не предполагает ограничения на размер и подобных контрактов,
     * но в данном случае это было введено для упрощения кода.
     */
    @Override
    public boolean add(T t) {
        int startingIndex = startingIndex(t);
        int index = startingIndex;
        Object current = storage[index];
        while (current != null) {
            if (current.equals(t)) {
                return false;
            }
            index = (index + 1) % capacity;
            if (index == startingIndex) {
                throw new IllegalStateException("Table is full");
            }
            current = storage[index];
        }
        storage[index] = t;
        size++;
        return true;
    }

    /**
     * Удаление элемента из таблицы
     * <p>
     * Если элемент есть в таблица, функция удаляет его из дерева и возвращает true.
     * В ином случае функция оставляет множество нетронутым и возвращает false.
     * Высота дерева не должна увеличиться в результате удаления.
     * <p>
     * Спецификация: {@link Set#remove(Object)} (Ctrl+Click по remove)
     * <p>
     * Средняя
     */
    @Override
    public boolean remove(Object o) {
        int index = startingIndex(o);
        Object current = storage[index];
        while (current != null) {
            if (current.equals(o)) {
                storage[index] = null;
                size--;
                return true;
            }
            index = (index + 1) % capacity;
            current = storage[index];
        }
        return false;
        //return super.remove(o);
    }

    /**
     * Создание итератора для обхода таблицы
     * <p>
     * Не забываем, что итератор должен поддерживать функции next(), hasNext(),
     * и опционально функцию remove()
     * <p>
     * Спецификация: {@link Iterator} (Ctrl+Click по Iterator)
     * <p>
     * Средняя (сложная, если поддержан и remove тоже)
     */
    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new OpenAddressingSetIterator();
    }

    public class OpenAddressingSetIterator implements Iterator<T> {

        Integer curIndex = null;
        Map<Integer, Object> objects = new HashMap<>();
        List<Integer> indexes = new ArrayList<Integer>();
        Object removeObject = null;

        private OpenAddressingSetIterator() {
            int index = 0;
            if (storage == null) return;
            for (int i = 0; i < capacity; i++) {
                Object current = storage[i];
                if (current != null) {
                    objects.put(i, current);
                    indexes.add(i);
                }
            }
            curIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return curIndex < indexes.size();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new IllegalStateException();
            removeObject = objects.get(indexes.get(curIndex));
            curIndex++;
            return (T) removeObject;
        }

        @Override
        public void remove() {
            if (removeObject == null) throw new IllegalStateException();
            OpenAddressingSet.this.remove(removeObject);
            removeObject = null;
        }
    }
}
