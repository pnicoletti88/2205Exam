package DataStructures;

import java.util.ArrayList;

public class DoublyLinkedList<T> {
    private class Node<T> implements Position<T>{
        public Node next = null;
        public Node prev = null;
        public T element = null;

        Node(T inData, Node nextNode, Node prevNode){
            next = nextNode;
            prev = prevNode;
            element = inData;
        }
        public T getElement() {
            return element;
        }
    }
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public int getSize() {
        return size;
    }

    public Position<T> addFront(T data){
        Node temp;
        if (head == null){
            temp = head = tail = new Node(data,null, null);
        }else{
            temp = head = new Node(data,head, null);
        }
        size++;
        return temp;
    }
    public Position<T> addBack(T data){
        Node temp;
        if (head == null){
            temp = head = tail = new Node(data,null,null);
        }else{
            temp = new Node(data,null, tail);
            tail.next = temp;
            tail = temp;
        }
        size++;
        return temp;
    }
    public void removeFront(){
        if(head == null){
            return;
        }
        if(head == tail){
            head = tail = null;
        }else{
            head = head.next;
            head.prev = null;
        }
        size--;
    }
    public void removeBack(){
        if(head == null){
            return;
        }
        if(head == tail){
            head = tail = null;
        }else{
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }
    public Iterable<T> getIterable(){
        ArrayList<T> out = new ArrayList<>();
        Node<T> temp = head;
        while(temp != null){
            out.add(temp.getElement());
            temp = temp.next;
        }
        return out;
    }

    public T remove(Position<T> p){
        Node<T> node = validate(p);
        if(node.prev == null){
            removeFront();
            return node.getElement();
        }
        if(node.next == null){
            removeBack();
            return node.getElement();
        }
        Node<T> back = node.prev;
        Node<T> front = node.next;
        back.next = front;
        front.prev = back;
        size--;
        return node.getElement();
    }

    private Node<T> validate(Position<T> p){
        if (p instanceof Node){
            return (Node<T>)p;
        }
        throw new IllegalArgumentException("Not a node");
    }

}
