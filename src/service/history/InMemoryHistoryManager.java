package service.history;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager{
    private static class Node {
        Node prev;
        Task element;
        Node next;

        Node(Node prev, Task element) {
            this.prev = prev;
            this.element = element;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(prev, node.prev) && Objects.equals(element, node.element) && Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(prev, element, next);
        }
    }

    private final Map<Integer, Node> history;
    private Node head;
    private Node tail;

    public InMemoryHistoryManager() {
        this.history = new HashMap<>();
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node current = head;

        while (current != null) {
            history.add(current.element);
            current = current.next;
        }

        return history;
    }

    @Override
    public void addHistory(Task task) {
        Node node = history.get(task.getId());

        removeNode(node);
        addLast(task);
    }

    @Override
    public void removeHistory(int id) {
        Node node = history.remove(id);
        removeNode(node);
    }

    private void addLast(Task task) {
        final Node l = tail;
        final Node newNode = new Node(l, task);

        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }

        history.put(newNode.element.getId(), newNode);
    }

    private void removeNode(Node node) {
        if(node != null) {
            linkedNext(node);
            linkedPrev(node);
            history.remove(node.element.getId());
        }
    }

    private void linkedNext(Node node) {
        if (node.equals(head)) {
            head = node.next;
        } else {
            node.prev.next = node.next;
        }
    }

    private void linkedPrev(Node node) {
        if (node.equals(tail)) {
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }
}
