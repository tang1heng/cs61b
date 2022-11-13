public interface MyDeque<T> {

    /**
     * 在双端队列的前面添加一个类型为T的元素。
     *
     * @param item
     */
    public void addFirst(T item);

    /**
     * 在双端队列的后面添加一个类型为T的元素。
     *
     * @param item
     */
    public void addLast(T item);

    /**
     * 如果 deque 为空，则返回 true，否则返回 false。
     *
     * @return
     */
    public boolean isEmpty();

    /**
     * 返回双端队列中的元素数量。
     *
     * @return
     */
    public int size();

    /**
     * 从头到尾打印双端队列中的元素，用空格分隔
     */
    public void printDeque();

    /**
     * 删除并返回双端队列前面的元素。如果不存在这样的元素，则返回 null。
     *
     * @return
     */
    public T removeFirst();

    /**
     * 删除并返回双端队列后面的元素。如果不存在这样的元素，则返回 null。
     *
     * @return
     */
    public T removeLast();

    /**
     * 获取给定索引处的元素，其中 0 是 first，1 是下一个元素，依此类推。如果不存在这样的元素，则返回 null。
     *
     * @param index
     * @return
     */
    public T get(int index);
}
