package RandomQs;

import DataStructures.SinglyLinkedList;

public class SinglyLinkedListAverage {
    float computeAvg(SinglyLinkedList<Integer>.Node node, int n){
        float average;
        if (node.next != null){
            node.next.element = Integer.sum(node.element, (int)node.next.element);
            average = computeAvg(node.next,n+1);
            node.next.element = (int)node.next.element - node.element;
        }else{
            average = node.element / (n+1);
        }
        return average;
    }

}
