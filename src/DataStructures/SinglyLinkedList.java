package DataStructures;

public class SinglyLinkedList<T> {
    public class Node implements Position<T> {
        public Node next = null;
        public T element = null;

        Node(T inData, Node nextNode){
            next = nextNode;
            element = inData;
        }
        public T getElement() {
            return element;
        }
    }
    public Node head = null;
    public Node tail = null;
    public int size = 0;

    public void addFront(T data){
        if (head == null){
            head = tail = new Node(data,null);
        }else{
            head = new Node(data,head);
        }
        size++;
    }
    public void addBack(T data){
        if (head == null){
            head = tail = new Node(data,null);
        }else{
            Node temp = new Node(data,null);
            tail.next = temp;
            tail = temp;
        }
        size++;
    }
    public T removeFront(){
        if(head == null){
            return null;
        }
        T data = head.element;
        if(head == tail){
            head = tail = null;
        }else{
            head = head.next;
        }
        size--;
        return data;
    }

    public void printLinkedList(){
        Node tempHead = head;
        while (tempHead != null){
            System.out.print(tempHead.element + " -> ");
            tempHead = tempHead.next;
        }
    }
}
