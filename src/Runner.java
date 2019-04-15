import DataStructures.*;
import RandomQs.*;

import java.util.ArrayList;

public class Runner {


    public static void main(String[] args){
        LinkedBinaryTree<Integer> tree = new LinkedBinaryTree<>();
        tree.addRoot(1);
        Position<Integer> node = tree.addLeft(tree.root(),2);
        tree.addLeft(node,3);
        tree.addRight(node,4);
        node = tree.addRight(tree.root(),5);
        tree.addLeft(node,6);
        tree.addRight(node,7);

        Iterable<Position<Integer>> iter = tree.preOrder();

        for (Position<Integer> item : iter){
            System.out.print(item.getElement() + " ");
        }
        System.out.println(" h:" + tree.height(tree.root()));

    }

}
