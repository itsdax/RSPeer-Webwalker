package com.dax.api.collections;

import java.util.HashMap;
import java.util.Map;


/**
 *
 * Queue with constant time removal and retrieval.
 *
 * This collection will only hold unique objects.
 *
 */
public class FastRemovalQueue<T> {

    private Node<T> leftMost, rightMost;
    private Map<T, Node<T>> map;

    public FastRemovalQueue() {
        map = new HashMap<>();
    }

    /**
     *
     * Remove the object if it already exists in list.
     * Appends object to back of the queue.
     *
     * @param object adds to back of queue
     */
    public void add(T object) {
        if (object == null) throw new NullPointerException();

        Node<T> node = new Node<>(object);
        remove(object);


        if (rightMost == null ^ leftMost == null) throw new IllegalStateException("Conflicting state");

        if (rightMost == null) {
            // Queue is empty
            leftMost = node;
            rightMost = node;
        } else {
            remove(object);

            // Add to front of queue
            rightMost.right = node;
            node.left = rightMost;
            rightMost = node;
        }
        map.put(object, node);
    }

    /**
     *
     * @return returns first item in queue
     */
    public T peek() {
        return !isEmpty() ? leftMost.v : null;
    }

    public T poll() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty");
        }
        return remove(leftMost);
    }

    public boolean isEmpty() {
        return leftMost == null;
    }

    public boolean remove(T t) {
        Node<T> node = map.get(t);
        if (node == null) {
            return false;
        }
        remove(node);
        return true;
    }

    public int getSize() {
        return map.size();
    }

    private T remove(Node<T> node) {
        if (node == null) throw new NullPointerException();

        Node<T> left = node.left, right = node.right;

        if (left != null) {
            left.right = right;
        }

        if (right != null) {
            right.left = left;
        }

        if (node == leftMost) {
            leftMost = node.right;
        }

        if (node == rightMost) {
            rightMost = node.left;
        }

        map.remove(node.v);
        return node.v;
    }

    private static class Node<V> {
        Node<V> left, right;
        V v;

        Node(V v) {
            this.v = v;
        }
    }


}
