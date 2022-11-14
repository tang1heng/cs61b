/**
 * 双端队列是具有动态大小的序列容器，可以在两端（前端或后端）扩展或收缩。
 * <p>
 * 使用带有一个哨兵节点的循环双向链表来实现双端队列。
 */
public class LinkedListDeque<T> {

    // 哨兵
    private Node<T> sentinel;

    // 元素个数
    private int size = 0;

    /**
     * 创建一个空的链表双端队列。
     */
    public LinkedListDeque() {
        sentinel = new Node<T>(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * 在双端队列的前面添加一个类型为T的元素。(add和remove操作不得涉及任何循环或递归。)
     * (单个这样的操作必须花费“恒定时间”，即执行时间不应取决于双端队列的大小。)
     *
     * @param item
     */
    public void addFirst(T item) {
        Node<T> newNode = new Node<>(sentinel, item, sentinel.next);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size++;
    }

    /**
     * 在双端队列的后面添加一个类型为T的元素。
     *
     * @param item
     */
    public void addLast(T item) {
        Node<T> newNode = new Node<>(sentinel.prev, item, sentinel);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size++;
    }

    /**
     * 如果 deque 为空，则返回 true，否则返回 false。
     *
     * @return
     */
    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    /**
     * 返回双端队列中的元素数量。(size必须花费恒定的时间。)
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 从头到尾打印双端队列中的元素，用空格分隔
     */
    public void printDeque() {
        Node<T> temp = sentinel.next;
        while (temp != sentinel) {
            System.out.print(temp.item + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    /**
     * 删除并返回双端队列前面的元素。如果不存在这样的元素，则返回 null。
     *
     * @return
     */
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node<T> first = sentinel.next;
        first.next.prev = sentinel;
        sentinel.next = first.next;

        first.prev = null;
        first.next = null;
        T result = first.item;
        first.item = null;
        size--;

        return result;
    }

    /**
     * 删除并返回双端队列后面的元素。如果不存在这样的元素，则返回 null。
     *
     * @return
     */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node<T> last = sentinel.prev;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;

        last.prev = null;
        last.next = null;
        T result = last.item;
        last.item = null;
        size--;

        return result;
    }

    /**
     * 获取给定索引处的元素，其中 0 是 first，1 是下一个元素，依此类推。
     * 如果不存在这样的元素，则返回 null。(get必须使用迭代，而不是递归。)
     *
     * @param index
     * @return
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        if (index < (size >> 1)) {
            Node<T> temp = sentinel.next;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            return temp.item;
        } else {
            Node<T> temp = sentinel.prev;
            for (int i = size - 1; i > index; i--) {
                temp = temp.prev;
            }
            return temp.item;
        }
    }

    /**
     * 与 get 相同，但使用递归。
     *
     * @param index
     * @return
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }

        Node<T> temp = sentinel.next;
        return getRecursiveHelp(temp, index);
    }

    private T getRecursiveHelp(Node<T> node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelp(node.next, --index);
    }

    private static class Node<T> {

        Node<T> prev;

        T item;

        Node<T> next;

        public Node(Node<T> prev, T item, Node<T> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
