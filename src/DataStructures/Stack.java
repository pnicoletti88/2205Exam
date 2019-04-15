package DataStructures;

public class Stack<T> {
    SinglyLinkedList<T> base = new SinglyLinkedList<>();

    public void push(T data){
        base.addFront(data);
    }

    public T pop(){
        return base.removeFront();
    }

    public T peek(){
        return base.head.data;
    }

    public boolean isEmpty(){
        return base.size == 0;
    }

}
