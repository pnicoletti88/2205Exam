package DataStructures;

//with continuity of locations making this with a array list is ideal
//to get parent from an element index i -> (i-1)/2
//to get left 2*i+1
//to get right 2*i+2
//every entry into a heap needs to have a key and a value (key determines the order)

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<K,V> {

    private Comparator<K> comparator;

    public class HeapEntry implements Entry<K,V>{
        K key;
        V value;

        HeapEntry(K key, V value){
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
    }

    protected int compare(Entry<K, V> a, Entry<K, V> b) {
        return comparator.compare(a.getKey(), b.getKey());
    }

    ArrayList<Entry<K,V>> base = new ArrayList<>();

    protected int left(int i){
        return (2*i+1);
    }
    protected int right(int i){
        return (2*i+2);
    }
    protected int parent(int i){
        return (i-1)/2; //integer rounding
    }
    protected boolean hasRight(int i){
        return right(i) < base.size();
    }
    protected boolean hasLeft(int i) {
        return left(i) < base.size();
    }

    public int size(){
        return base.size();
    }

    public Heap(){
        comparator = new DefaultComparator<>();
    }


    public Entry<K,V> insert(K key, V val){
        Entry<K,V> ent = new HeapEntry(key,val);
        base.add(ent);
        upHeap(base.size()-1);
        return ent;
    }

    protected void upHeap(int i){
        while (i > 0){
            Entry<K,V> parent = base.get(parent(i));
            Entry<K,V> current = base.get(i);

            if (compare(current,parent) >= 0){
                break;
            }
            swap(parent(i),i);
            i = parent(i);

        }
    }

    public void downHeap(int i){
        //need to swap with smallest child
        while(hasLeft(i)){

            Entry<K,V> left = base.get(left(i));
            Entry<K,V> right;
            int min= left(i);
            if(hasRight(i)){
                right = base.get(right(i));
                if(compare(left,right) > 0){
                    min = right(i);
                }
            }

            if (compare(base.get(min), base.get(i)) > 0) {
                break;
            }

            swap(min,i);
            i = min;
        }
    }

    protected void swap(int i, int j){
        Entry<K,V> temp = base.get(i);
        base.set(i,base.get(j));
        base.set(j,temp);
    }

    public Entry<K,V> removeMin(){
        if(base.size()==0){
            return null;
        }
        Entry<K,V> temp = base.get(0);
        swap(0,base.size()-1);
        base.remove(base.size()-1);
        downHeap(0);
        return temp;
    }

    public void heapify(ArrayList<Entry<K,V>> newArr) {
        base = newArr;
        int startIndex = parent(size() - 1);        // start at PARENT of last entry
        for (int j = startIndex; j >= 0; j--) {     // loop until processing the root
            downHeap(j);
        }
    }


}
