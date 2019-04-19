package DataStructures;

import java.util.Comparator;

public class BST <K,V>{

    public class BSTEntry implements Entry<K,V>{
        K key;
        V value;

        BSTEntry(K key, V value){
            this.key = key;
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public String toString(){
            return value.toString();
        }
    }

    private LinkedBinaryTree<Entry<K,V>> tree = new LinkedBinaryTree<>();
    private Comparator<K> comparator = new DefaultComparator<>();

    public BST(){
        tree.addRoot(null);
    }


    public Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key){
        if(isExternal(p)){
            return p;
        }
        if (compare(key,p.getElement().getKey()) == 0){
            return p;
        }
        if (compare(key,p.getElement().getKey()) > 0){return treeSearch(tree.right(p),key);}
        else{return treeSearch(tree.left(p),key);}
    }

    public V put(K key, V value){
        BSTEntry newEntry = new BSTEntry(key,value);
        Position<Entry<K,V>> p = treeSearch(tree.root(), key);
        V tempVal;
        if (isExternal(p)){
            tree.set(p,newEntry);
            tree.addLeft(p,null);
            tree.addRight(p,null);
            tempVal = null;
        }else{
            tree.set(p,newEntry);
            tempVal = p.getElement().getValue();
        }
        return tempVal;
    }

    public V remove(K key){
        Position<Entry<K,V>> p = treeSearch(tree.root(),key);
        if(isExternal(p)){return null;}
        V old = p.getElement().getValue();
        if (isInternal(tree.left(p)) && isInternal(tree.right(p))){
            Position<Entry<K,V>> replacement = treeMax(tree.left(p));
            tree.set(p,replacement.getElement());
            p = replacement;
        }else if(isInternal(tree.left(p))){
            Position<Entry<K,V>> replacement = treeMax(tree.left(p));
            tree.set(p,replacement.getElement());
            p = replacement;
        }else if(isInternal(tree.right(p))){
            Position<Entry<K,V>> replacement = treeMin(tree.right(p));
            tree.set(p,replacement.getElement());
            p = replacement;
        }
        Position<Entry<K,V>> leaf = isExternal(tree.left(p))?tree.left(p):tree.right(p);
        tree.remove(leaf);
        tree.remove(p);
        return old;
    }

    private Position<Entry<K,V>> treeMin(Position<Entry<K,V>> p){
        while (tree.left(p).getElement() != null){
            p = tree.left(p);
        }
        return p;
    }

    private Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p){
        while (tree.right(p).getElement() != null){
            p = tree.right(p);
        }
        return p;
    }

    private boolean isExternal(Position<Entry<K,V>> p){
        return tree.isExternal(p);
    }

    private boolean isInternal(Position<Entry<K,V>> p){
        return !isExternal(p);
    }

    private int compare(Entry<K,V> a, Entry<K,V> b){
        return comparator.compare(a.getKey(),b.getKey());
    }
    private int compare(K a, K b){
        return comparator.compare(a,b);
    }

    public void printTree(){
        tree.printTree();
    }


}
