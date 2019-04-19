package DataStructures;

//Position is parent for position

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedBinaryTree<T> {
    private class Node<T> implements Position<T> {
        private Node left = null;
        private Node right = null;
        private Node parent = null;
        private T data;


        Node(T data, Node left, Node right, Node parent) {
            this.data = data;
            this.left = left;
            this.right = right;
            this.parent = parent;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        public T getElement() {
            return data;
        }

        public Node<T> getParent() {
            return parent;
        }

        public void setData(T data) {
            this.data = data;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    protected Node<T> root;
    private int size = 0;

    public Node<T> validate(Position nodeIn) throws IllegalArgumentException {
        if (!(nodeIn instanceof Node)) {//makes sure that this item is a node
            throw new IllegalArgumentException("Not of correct position type");
        }
        Node<T> outNode = (Node) nodeIn;
        if (outNode.getParent() == outNode) { //when nodes are no longer active the parent of the node is set to itself
            throw new IllegalArgumentException("Not part of the tree");
        }
        return outNode;
    }

    public Node<T> createNode(T e, Node<T> p, Node<T> l, Node<T> r) {
        return new Node<T>(e, l, r, p);
    }

    public Position<T> parent(Position<T> node) throws IllegalArgumentException {
        Node<T> nodeVal = validate(node);
        return nodeVal.getParent();
    }

    public Position<T> left(Position<T> node) throws IllegalArgumentException {
        Node<T> nodeVal = validate(node);
        return nodeVal.getLeft();
    }

    public Position<T> right(Position<T> node) throws IllegalArgumentException {
        Node<T> nodeVal = validate(node);
        return nodeVal.getRight();
    }

    public Position<T> addRoot(T e) throws IllegalStateException {
        if (!isEmpty()) {
            throw new IllegalStateException("Tree already has a root!");
        }
        root = createNode(e, null, null, null);
        size = 1;
        return root;
    }

    public Position<T> addLeft(Position<T> p, T e) throws IllegalArgumentException {
        Node<T> parent = validate(p);
        if (parent.getLeft() != null) {
            throw new IllegalArgumentException("Parent already has a left child");
        }
        Node<T> leftChild = createNode(e, parent, null, null);
        parent.setLeft(leftChild);
        size++;
        return leftChild;
    }

    public Position<T> addRight(Position<T> p, T e) throws IllegalArgumentException {
        Node<T> parent = validate(p);
        if (parent.getRight() != null) {
            throw new IllegalArgumentException("Parent already has a right child");
        }
        Node<T> rightChild = createNode(e, parent, null, null);
        parent.setRight(rightChild);
        size++;
        return rightChild;
    }

    public T remove(Position<T> p) throws IllegalArgumentException {
        Node<T> node = validate(p);
        if (numChildren(p) == 2) {
            throw new IllegalArgumentException("Can't remove: Two children");
        }
        Node<T> child = (node.getLeft() != null ? node.getLeft() : node.getRight());
        if (child != null) {
            child.setParent(node.getParent());
        }
        if (node == root) {
            root = node;
        } else {
            Node<T> parent = validate(node.getParent());
            if (parent.getLeft() == node) {
                parent.setLeft(child);
            } else {
                parent.setRight(child);
            }
        }
        T temp = node.getElement();
        node.setLeft(null);
        node.setParent(node); //setting parent reference to itself means it is out fo tree
        node.setRight(null);
        size--;
        return temp;
    }

    public int numChildren(Position<T> position) {
        int count = 0;

        if (left(position) != null) {
            count++;
        }

        if (right(position) != null) {
            count++;
        }

        return count;
    }

    @SuppressWarnings("Duplicates")
    public void attach(Position<T> p, LinkedBinaryTree<T> t1, LinkedBinaryTree<T> t2) throws IllegalArgumentException {
        Node<T> node = validate(p);
        if (numChildren(p) != 0) {
            throw new IllegalArgumentException("joining node has children!!");
        }
        size = t1.size() + t2.size();
        if(!t1.isEmpty()) {
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if(!t2.isEmpty()) {
            t2.root.setParent(node);
            node.setLeft(t2.root);
            t2.root = null;
            t2.size = 0;
        }

    }

    public void set(Position<T> p, T e) throws IllegalStateException{
        Node<T> node = validate(p);
        node.setData(e);
    }

    public boolean isExternal(Position<T> p) throws IllegalArgumentException {
        Node<T> node = validate(p);
        return numChildren(node) == 0;
    }

    public boolean isInternal(Position<T> p) throws IllegalArgumentException {
        Node<T> node = validate(p);
        return numChildren(node) != 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private class ElementIterator implements Iterator<T>{
        Iterator<Position<T>> postIter = positions().iterator();
        public boolean hasNext(){return postIter.hasNext();}
        public T next(){return postIter.next().getElement();}
        public void remove(){postIter.remove();}
    }

    public Iterator<T> iterator() {
        return new ElementIterator();
    }

    public Iterable<Position<T>> positions(){return preOrder();}

    //wrapper for recursion
    public Iterable<Position<T>>  preOrder(){
        List<Position<T>> snapshot = new ArrayList<Position<T>>();
        if(!isEmpty()){
            preOrderSubtreeNoRecursion(root, snapshot);
        }
        return snapshot;
    }

    public Iterable<Position<T>>  postOrder(){
        List<Position<T>> snapshot = new ArrayList<Position<T>>();
        if(!isEmpty()){
            postOrderSubtree(root, snapshot);
        }
        return snapshot;
    }

    public Iterable<Position<T>>  inOrder(){
        List<Position<T>> snapshot = new ArrayList<Position<T>>();
        if(!isEmpty()){
            inOrderSubtree(root, snapshot);
        }
        return snapshot;
    }

    public void preOrderSubtree(Position<T> p, List<Position<T>> snapshot){
        snapshot.add(p);
        for (Position<T> c : children(p)){
            preOrderSubtree(c,snapshot);
        }
    }

    public void preOrderSubtreeNoRecursion(Position<T> p, List<Position<T>> snapshot){
        Stack<Node<T>> s = new Stack<>();
        Node<T> node = validate(p);
        boolean newNode = true; //this indicates if it the first time visiting a node
        while (node != null){
            System.out.println(node.getElement());
            if (newNode) {
                snapshot.add(node);
            }
            if (newNode && node.getLeft() != null){
                s.push(node);
                node = node.getLeft();
            }
            else if (node.getRight() != null){
                node = node.getRight();
                newNode = true;
            }
            else if(!s.isEmpty()){
                node = s.pop();
                newNode = false;
            }
            else{
                node = null;
            }
        }
    }

    public void postOrderSubtree(Position<T> p, List<Position<T>> snapshot){
        for (Position<T> c : children(p)){
            postOrderSubtree(c,snapshot);
        }
        snapshot.add(p);
    }

    public void postOrderSubtreeNoRecursion(Position<T> p, List<Position<T>> snapshot){

    }

    public void inOrderSubtree(Position<T> p, List<Position<T>> snapshot){
        Iterator<Position<T>> child = children(p).iterator();
        if(child.hasNext()){
            inOrderSubtree(child.next(),snapshot);
        }
        snapshot.add(p);
        if(child.hasNext()){
            inOrderSubtree(child.next(),snapshot);
        }
    }

    public void inOrderSubtreeNoRecursion(Position<T> p, List<Position<T>> snapshot){

    }


    public Iterable<Position<T>> children(Position<T> p) throws IllegalStateException{
        Node<T> node = validate(p);
        List<Position<T>> children = new ArrayList<>();
        if (node.getLeft() != null){children.add(node.getLeft());}
        if (node.getRight() != null){children.add(node.getRight());}

        return children;
    }

    public Position<T> root(){
        return root;
    }

    public int height(Position<T> p){
        Node<T> node = validate(p);
        if (isExternal(node)){
            return 1;
        }
        int retVal = 0;
        for (Position<T> n : children(node)){
            retVal = Math.max(height(n),retVal);
        }
        return retVal+1;
    }

    public void printTree(){
        if(isEmpty()){
            return;
        }


        int height = height(root());
        int width = (int)Math.pow(2.0, ((double)height));
        int spaces = width;
        int depthCounter = 1;
        int widthCounter = 0;

        Queue<Position<T>> q = new Queue<>();
        q.enqueue(root());

        final Position<T> DEFUNCT = new Node(null,null,null,null);
        final Position<T> BLANK = new Node(null,null,null,null);
        q.enqueue(DEFUNCT);
        int spaceCurrent = spaces;

        for (int i = 0; i < spaces; i++){
            System.out.print("-");
        }
        System.out.println();

        while(depthCounter <= height){
            Position<T> p = q.dequeue();
            if (p == DEFUNCT){
                q.enqueue(p);
                spaceCurrent /= 2;
                depthCounter++;
                System.out.println(" ");
            }
            else{
                for (int i = 0; i < spaceCurrent/2-2; i++){
                    System.out.print(" ");
                }
                if (p != BLANK && p.getElement() != null){
                    String out = p.getElement().toString() + "  ";
                    out = out.substring(0,2);
                    System.out.print(out);
                }else{
                    System.out.print("  ");
                }
                for (int i = 0; i < spaceCurrent/2; i++){
                    System.out.print(" ");
                }
                if(left(p) != null){
                    q.enqueue(left(p));
                }else{
                    q.enqueue(BLANK);
                }
                if(right(p) != null){
                    q.enqueue(right(p));
                }else{
                    q.enqueue(BLANK);
                }
            }

        }
        for (int i = 0; i < spaces; i++){
            System.out.print("-");
        }
        System.out.println();


    }
}
