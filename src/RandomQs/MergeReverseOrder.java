package RandomQs;

import DataStructures.SinglyLinkedList;

public class MergeReverseOrder {
    public SinglyLinkedList.Node head;

    public SinglyLinkedList.Node merger(SinglyLinkedList.Node L1, SinglyLinkedList.Node L2){
        if (L1 == null && L2 == null){
            System.out.println("Base Hit");
            return null;
        }
        if (L1 == null){
            System.out.println("L1 null");
            SinglyLinkedList.Node temp = merger(L1,L2.next);
            if (temp == null){
                temp = L2;
                head = L2;
            }else {
                temp.next = L2;
            }
            return L2;
        }
        if (L2 == null){
            System.out.println("L2 null");
            SinglyLinkedList.Node temp = merger(L1.next,L2);
            if (temp == null){
                temp = L1;
                head = L1;
            }else {
                temp.next = L1;
            }
            return L1;
        }
        if ((int)(L1.data) > (int)(L2.data)){
            System.out.println("L1: " + L1.data + " > L2: " + L2.data);
            SinglyLinkedList.Node temp = merger(L1.next, L2);
            temp.next = L1;
            return L1;
        }else{
            System.out.println("L1: " + L1.data + " <= L2: " + L2.data);
            SinglyLinkedList.Node temp = merger(L1, L2.next);
            temp.next = L2;
            return L2;
        }
    }
}
