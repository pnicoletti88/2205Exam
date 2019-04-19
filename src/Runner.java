import DataStructures.*;
import RandomQs.*;

import java.util.ArrayList;

public class Runner {


    public static void main(String[] args){
        BST<Integer, Integer> b = new BST<>();
        b.put(0,0);
        b.put(2,2);
        b.put(1,1);
        b.put(3,3);
        b.put(-1,-1);
        b.put(7,7);
        b.put(4,4);
        b.printTree();
        b.remove(0);
        b.printTree();
        b.remove(-1);
        b.printTree();
        b.remove(4);
        b.printTree();
    }

}
