import java.util.Arrays;

/**
 * 双端队列是具有动态大小的序列容器，可以在两端（前端或后端）扩展或收缩。
 *
 * 使用带有一个自动扩容的动态数组来实现双端队列。
 */
public class ArrayDeque<T> {

    // 要分配的数组的最大大小。
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    // 存储队列元素的底层数组
    private Object[] elements;

    // 队列头部（指向队列头部元素）
    // deque头元素的索引
    private int head;

    // 队列尾部（指向队列尾部元素的下一个）
    // 将下一个元素添加到deque尾部的索引
    private int tail;

    /**
     * 创建一个空数组双端队列。
     *
     * (数组的起始大小应为 8。)
     */
    public ArrayDeque() {
        elements = new Object[8];
    }

    /**
     * 在双端队列的前面添加一个类型为T的元素。
     *
     * (add并且remove必须花费恒定的时间，除非在调整大小操作期间。)
     *
     * @param item
     */
    public void addFirst(T item) {
        final Object[] es = elements;

        // 移动队列头部下标
        head = dec(head, es.length);

        // 从新的队列头部入队
        es[head] = item;

        // 队列满之后扩容
        if (head == tail) {
            grow(1);
        }
    }

    /**
     * Circularly decrements i, mod modulus.
     * Precondition and postcondition: 0 <= i < modulus.
     */
    private final int dec(int i, int modulus) {
        if (--i < 0) {
            i = modulus - 1;
        }
        return i;
    }

    /**
     * 扩容函数
     *
     * Increases the capacity of this deque by at least the given amount.
     *
     * @param needed the required minimum extra capacity; must be positive
     */
    private void grow(int needed) {
        final int oldCapacity = elements.length;

        // Double capacity if small; else grow by 50%
        int jump = (oldCapacity < 64) ? (oldCapacity + 2) : (oldCapacity >> 1);

        int newCapacity;

        if (jump < needed || (newCapacity = (oldCapacity + jump)) - MAX_ARRAY_SIZE > 0) {
            newCapacity = newCapacity(needed, jump);
        }

        final Object[] es = elements = Arrays.copyOf(elements, newCapacity);

        // tail == head needs to be disambiguated
        if (tail < head || (tail == head && es[head] != null)) {
            int newSpace = newCapacity - oldCapacity;
            // 复制数组元素（搬家）
            System.arraycopy(es, head, es, head + newSpace, oldCapacity - head);
            // 清除过时数组元素
            for (int i = head, to = (head += newSpace); i < to; i++) {
                es[i] = null;
            }
        }
    }

    /** Capacity calculation for edge conditions, especially overflow. */
    private int newCapacity(int needed, int jump) {
        final int oldCapacity = elements.length;
        final int minCapacity;

        if ((minCapacity = oldCapacity + needed) - MAX_ARRAY_SIZE > 0) {
            assert minCapacity > 0;
            return Integer.MAX_VALUE;
        }

        if (needed > jump) {
            return minCapacity;
        }

        return (oldCapacity + jump - MAX_ARRAY_SIZE < 0) ?
                oldCapacity + jump :  MAX_ARRAY_SIZE;
    }

    /**
     * 在双端队列的后面添加一个类型为T的元素。
     *
     * @param item
     */
    public void addLast(T item) {
        final Object[] es = elements;

        // 从新的队列尾部入队
        es[tail] = item;

        // 移动队列头部下标
        tail = inc(tail, es.length);

        // 队列满之后扩容
        if (head == tail) {
            grow(1);
        }
    }

    /**
     * Circularly increments i, mod modulus.
     * Precondition and postcondition: 0 <= i < modulus.
     */
    private int inc(int i, int modulus) {
        if (++i >= modulus) {
            i = 0;
        }
        return i;
    }

    /**
     * 如果 deque 为空，则返回 true，否则返回 false。
     *
     * @return
     */
    public boolean isEmpty() {
//        return size() == 0;
        return head == tail;
    }

    /**
     * 返回双端队列中的元素数量。
     *
     * @return
     */
    public int size() {
        return sub(tail, head, elements.length);
    }

    /**
     * Subtracts j from i, mod modulus.
     * Index i must be logically ahead of index j.
     * Precondition: 0 <= i < modulus, 0 <= j < modulus.
     *
     * @return the "circular distance" from j to i; corner case i == j
     * is disambiguated to "empty", returning 0.
     */
    private int sub(int i, int j, int modulus) {
        i -= j;

        if (i < 0) {
            i += modulus;
        }

        return i;
    }

    /**
     * 从头到尾打印双端队列中的元素，用空格分隔
     */
    public void printDeque() {
        final Object[] es = elements;

        int temp = head;
        while (temp != tail) {
            System.out.print(elementAt(es, temp) + " ");
            temp = inc(temp, es.length);
        }
        System.out.println();
    }

    /**
     * 删除并返回双端队列前面的元素。如果不存在这样的元素，则返回 null。
     *
     * @return
     */
    public T removeFirst() {
        final Object[] es = elements;
        final int h = head;

        // 取出队头元素
        T item = elementAt(es, h);

        if (item != null) {
            es[h] = null;
            // 更新队头
            head = inc(h, es.length);
        }

        return item;
    }

    private final <T> T elementAt(Object[] es, int i) {
        return (T) es[i];
    }

    /**
     * 删除并返回双端队列后面的元素。如果不存在这样的元素，则返回 null。
     *
     * @return
     */
    public T removeLast() {
        final Object[] es = elements;

        // 更新队尾
        final int t = dec(tail, es.length);

        // 取出队尾元素
        T item = elementAt(es, t);

        if (item != null) {
            tail = t;
            es[tail] = null;
        }

        return item;
    }

    /**
     * 获取给定索引处的元素，其中 0 是 first，1 是下一个元素，依此类推。如果不存在这样的元素，则返回 null。
     *
     * (get并且size必须花费恒定的时间。)
     *
     * @param index
     * @return
     */
    public T get(int index) {
        if (index < 0 | index >= size()) {
            return null;
        }

        final Object[] es = elements;

        int actualIndex = head;
        actualIndex += index;
        if (actualIndex >= elements.length) {
            actualIndex -= elements.length;
        }

        return elementAt(es, actualIndex);
    }
}
