package DataStructures;

import java.util.Comparator;

public class DefaultComparator<E> implements Comparator<E> {

    @Override
    public int compare(E a, E b) throws ClassCastException {
        /*

            returns 0 when they are equal
         */
        return ((Comparable<E>) a).compareTo(b);
    }
}
