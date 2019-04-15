package DataStructures;

public class DoublyLinkedList<T> {
    private class Node<T>{
        public Node next = null;
        public Node prev = null;
        public T data = null;


        Node(T inData, Node nextNode, Node prevNode){
            next = nextNode;
            prev = prevNode;
            data = inData;
        }
    }
    private Node head = null;
    private Node tail = null;
    private int size = 0;

    public void addFront(T data){
        if (head == null){
            head = tail = new Node(data,null, null);
        }else{
            head = new Node(data,head, null);
        }
        size++;
    }
    public void addBack(T data){
        if (head == null){
            head = tail = new Node(data,null,null);
        }else{
            Node temp = new Node(data,null, tail);
            tail.next = temp;
            tail = temp;
        }
        size++;
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
}
