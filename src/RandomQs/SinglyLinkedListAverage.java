package RandomQs;

import DataStructures.SinglyLinkedList;

public class SinglyLinkedListAverage {
    float computeAvg(SinglyLinkedList<Integer>.Node node, int n){
        float average;
        if (node.next != null){
            node.next.data = Integer.sum(node.data , (int)node.next.data);
            average = computeAvg(node.next,n+1);
            node.next.data = (int)node.next.data - node.data;
        }else{
            average = node.data / (n+1);
        }
        return average;
    }

}
