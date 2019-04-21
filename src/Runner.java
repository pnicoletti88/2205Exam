import DataStructures.*;
import RandomQs.*;

import java.util.ArrayList;
import java.util.Random;

public class Runner {


    public static void main(String[] args) {
        AdjacencyListGraph<String,Integer> graph = new AdjacencyListGraph<>(false);
        Vertex<Integer>[] verts = (Vertex<Integer>[])new Vertex[10];
        for (int i = 0; i < 10; i++){
            verts[i] = graph.insertVertex(i);
        }
        graph.insertEdge(verts[0],verts[1],"0-1");

        Edge<String> e = graph.getEdge(verts[0],verts[1]);
        graph.removeVertex(verts[2]);

        graph.printGraph();
    }

}
