package DataStructures;

public class Queue<T> {
    SinglyLinkedList<T> base = new SinglyLinkedList<>();

    public void enqueue(T data){
        base.addBack(data);
    }

    public T dequeue(){
        return base.removeFront();
    }

    public T peek(){
        return base.tail.data;
    }

    public boolean isEmpty(){
        return base.size == 0;
    }

}