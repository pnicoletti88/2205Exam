package DataStructures;

public class BalancedTrees<K,V> extends LinkedBinaryTree<Entry<K,V>>{
    public class BalanceNode<E> extends Node<E>{
        BalanceNode(E data, Node left, Node right, Node parent){
            super(data,left,right,parent);
        }

        private int aux = 0;
        public int getAux() {
            return aux;
        }
        public void setAux(int aux) {
            this.aux = aux;
        }
    }

    public void setAux(Position<Entry<K,V>> p, int aux){
        ((BalanceNode<Entry<K,V>>)p).setAux(aux);
    }
    public int getAux(Position<Entry<K,V>> p){
        return ((BalanceNode<Entry<K,V>>)p).getAux();
    }

    @Override
    public Node<Entry<K,V>> createNode(Entry<K,V> e, Node<Entry<K,V>> p, Node<Entry<K,V>> l, Node<Entry<K,V>> r) {
        return new BalanceNode<>(e, l, r, p);
    }

    private void relink(Node<Entry<K,V>> p, Node<Entry<K,V>> c, boolean leftChild){
        if (c==null){
            System.out.println("fuck");
        }
        c.setParent(p);
        if(leftChild){
            p.setLeft(c);
        }else{
            p.setRight(c);
        }
    }

    //passing in the lowest child in the rotation operation
    public void rotate(Position<Entry<K,V>> p){
        Node<Entry<K,V>> child = validate(p);
        Node<Entry<K,V>> parent = child.getParent();//assume will exist
        Node<Entry<K,V>> grandParent = parent.getParent();//could be null
        if (grandParent ==  null){
            root = child;
            child.setParent(null);
        }else{
            //child becomes a direct child of grandparent, taking parents place
            //parent is still going to have link that grandparent is its parent
            relink(grandParent,child,parent == grandParent.getLeft());
        }
        if (child == parent.getLeft()){
            relink(parent,child.getRight(),true); //parent get right child as its child
            relink(child,parent,false); //parent becomes child of child
        }else{
            relink(parent,child.getLeft(),false); //parent get left child as it child
            relink(child,parent,true); //parent becomes child of child
        }
    }

    public Position<Entry<K,V>> restructure(Position<Entry<K,V>> child){
        Position<Entry<K,V>> parent = parent(child);
        Position<Entry<K,V>> grandParent = parent(parent);
        if(grandParent==null){
            rotate(child);
            return child;
        }
        if ((child == right(parent)) == (parent == right(grandParent))){
            rotate(parent);
            return parent;
        }else{ //double rotation
            rotate(child); //gets it into straight line alginment
            rotate(child);//child is now second in line its the normal rotation
            return child;
        }

    }







}
