package DataStructures;


import java.util.ArrayList;
import java.util.Random;

public class LinearProbe<K,V> {
    MapEntry<K,V>[] table;
    private final MapEntry<K,V> DEFUNCT = new MapEntry<>(null,null);
    protected int size = 0;
    protected int capacity;
    protected int prime; //prime factor?
    private long scale,shift;

    public LinearProbe(){
        this(17);
    }

    public LinearProbe(int cap){
        this(cap,109345121);
    }

    public LinearProbe(int cap, int prime){
        capacity = cap;
        this.prime = prime;
        Random rand = new Random();
        scale = rand.nextInt(prime-1) + 1;
        shift = rand.nextInt(prime);
        createTable();
    }

    private boolean available(int j){
        return (table[j] == null||table[j] == DEFUNCT);
    }

    private void createTable(){
        table = (MapEntry<K,V>[])new MapEntry[capacity];
    }

    private int findSlot(int h){
        int count = 0;
        while(!available(h)){
            System.out.println("Collision Occured");
            h = ++h%capacity;
            count++;
            if (count == capacity){
                return -1;
            }
        }
        return h;
    }

    public V put(K key, V value){
        int hash = hashValue(key);
        int bucket = findSlot(hash);
        table[bucket] = new MapEntry<>(key,value);
        size++;
        if(((double)size)/(double)capacity > 0.5){
            System.out.println("Resize Called");
            reSize(capacity*2);
        }
        return value;
    }

    public V remove(K key){
        int hash = hashValue(key);
        int count = 0;
        while (count < capacity){
            if (table[hash] == null){
                return null;
            }
            if (table[hash] != DEFUNCT && table[hash].getKey() == key){
                V temp = table[hash].getValue();
                table[hash] = DEFUNCT;
                size--;
                return temp;
            }
            count++;
            hash = ++hash%capacity;
        }
        return null;
    }

    public int size(){
        return size;
    }

    private int hashValue(K key){
        return (int)Math.abs((((key.hashCode()*scale + shift)%prime)%capacity));
    }

    private void reSize(int newCap){
        Iterable<MapEntry<K,V>> entries = entrySet();
        capacity = newCap;
        createTable();
        size=0; //it will be changed when all the entries are put back in
        for (MapEntry<K,V> e:entries){
            put(e.getKey(),e.getValue());
        }
    }

    public Iterable<MapEntry<K,V>> entrySet(){
        ArrayList<MapEntry<K,V>> set = new ArrayList<>();
        for (int i=0; i < table.length;i++){
            if (!available(i)){
                set.add(table[i]);
            }
        }
        return set;
    }

    public V get(K key){
        int hash = hashValue(key);
        int count = 0;
        while (count < capacity){
            if (table[hash] == null){
                return null;
            }
            if (table[hash] != DEFUNCT && table[hash].getKey() == key){
                return table[hash].getValue();
            }
            count++;
            hash = ++hash%capacity;
        }
        return null;
    }
}
