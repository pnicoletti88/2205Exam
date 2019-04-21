package DataStructures;

public class AVL_Tree<K,V> extends BalancedTrees<K,V>{
    @Override
    public int height(Position<Entry<K,V>> p){
        if(p==null){
            return 0;
        }
        return getAux(p);
    }

    protected void recalcHeight(Position<Entry<K,V>> p){
        setAux(p, 1+ Math.max(height(left(p)),height(right(p))));
    }

    protected boolean isBalanced(Position<Entry<K,V>> p){
        int left = ((left(p) == null) ? 0:height(left(p)));
        int right = ((right(p) == null) ? 0:height(right(p)));
        return Math.abs(left - right) <= 1;
    }

    protected Position<Entry<K,V>> tallerChild(Position<Entry<K,V>> p){
        if (height(left(p)) > height(right(p))){return left(p);}
        if (height(left(p)) < height(right(p))){return right(p);}
        if (root() == p){return left(p);}//choice doesn't matter
        if (p == left(parent(p))){return left(p);} //return aligned child
        else{return right(p);}
    }

    //call on item that gets inserted
    protected void rebalance(Position<Entry<K,V>> p){
        int oldHeight, newHeight;
        do{
            oldHeight = height(p);
            if(!isBalanced(p)){
                Position<Entry<K,V>> temp = tallerChild(tallerChild(p));
                p=restructure(temp);//needs to have two taller children for an imbalance
                //restructure returns what is now the parent root so it works as it must have a left and a right
                recalcHeight(left(p));
                recalcHeight(right(p));
            }
            recalcHeight(p);
            newHeight = height(p);
            p = parent(p);
        }while(oldHeight != newHeight && p != null);
    }
}
