package DataStructures;

import java.util.HashMap;

public class AdjacencyListGraph<E,V> {
    public class InnerEdge<E> implements Edge<E>{
        private E element;
        private Position<InnerEdge<E>> pos;
        private InnerVertex[] vertex;

        public InnerEdge(InnerVertex v1, InnerVertex v2, E e){
            vertex = new InnerVertex[]{v1,v2};
            element = e;
        }
        public Position<InnerEdge<E>> getPos() {
            return pos;
        }
        public E getElement() {
            return element;
        }
        public InnerVertex<V>[] getEndpoints() {
            return vertex;
        }
        public void setPos(Position<InnerEdge<E>> pos) {
            this.pos = pos;
        }
    }
    public class InnerVertex<V> implements Vertex<V>{
        private V element;
        private Position<InnerVertex<V>> pos;
        private HashMap<InnerVertex<V>, InnerEdge<E>> outgoing,incoming;

        InnerVertex(V element){
            this.element = element;
            outgoing = new HashMap<>();
            incoming = isDirected?new HashMap<>():outgoing;
        }

        public void setPos(Position<InnerVertex<V>> pos) {
            this.pos = pos;
        }
        public HashMap<InnerVertex<V>, InnerEdge<E>> getIncoming() {
            return incoming;
        }
        public HashMap<InnerVertex<V>, InnerEdge<E>> getOutgoing() {
            return outgoing;
        }
        public V getElement() {
            return element;
        }
        public Position<InnerVertex<V>> getPos() {
            return pos;
        }
    }
    private boolean isDirected;
    private DoublyLinkedList<InnerVertex<V>> vertexList = new DoublyLinkedList<>();
    private DoublyLinkedList<InnerEdge<E>> edgeList = new DoublyLinkedList<>();

    public AdjacencyListGraph(boolean isDirected){
        this.isDirected = isDirected;
    }

    public int numVertex(){
        return vertexList.getSize();
    }
    public int numEdges(){
        return edgeList.getSize();
    }

    public Iterable<InnerVertex<V>> verticies(){return vertexList.getIterable();}
    public Iterable<InnerEdge<E>> edges(){return edgeList.getIterable();}

    public int outDegree(Vertex<V> v){
        InnerVertex<V> vertex = validate(v);
        return vertex.getOutgoing().size();
    }
    public int inDegree(Vertex<V> v){
        InnerVertex<V> vertex = validate(v);
        return vertex.getIncoming().size();
    }

    private InnerVertex<V> validate(Vertex<V> v) throws IllegalStateException{
        if(!(v instanceof InnerVertex)){
            throw new IllegalStateException("Not a valid vertex");
        }
        return (InnerVertex<V>)v;
    }
    private InnerEdge<E> validate(Edge<E> e) throws IllegalStateException{
        if(!(e instanceof InnerEdge)){
            throw new IllegalStateException("Not a valid edge");
        }
        return (InnerEdge<E>)e;
    }

    public Iterable<InnerEdge<E>> getIncomingEdges(Vertex<V> v){
        InnerVertex<V> vertex = validate(v);
        return vertex.getIncoming().values();
    }
    public Iterable<InnerEdge<E>> getOutgoingEdges(Vertex<V> v){
        InnerVertex<V> vertex = validate(v);
        return vertex.getOutgoing().values();
    }
    public InnerEdge<E> getEdge(Vertex<V> u, Vertex<V> v){
        InnerVertex<V> vertex = validate(u);
        return vertex.getOutgoing().get(v);
    }
    public Vertex<V>[] getEndVerticies(Edge<E> e){
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }
    public Vertex<V> getOpposite(Vertex<V> v, Edge<E> e) throws IllegalStateException{
        InnerVertex<V> vert = validate(v);
        InnerEdge<E> edge = validate(e);
        InnerVertex<V>[] verts = edge.getEndpoints();
        if(verts[0] == v){
            return verts[1];
        }
        if(verts[1] == v){
            return verts[0];
        }
        throw new IllegalArgumentException("vertex is not attached to edge");
    }
    public Vertex<V> insertVertex(V data){
        InnerVertex<V> vert = new InnerVertex<>(data);
        vert.setPos(vertexList.addBack(vert));
        return vert;
    }
    public Edge<E> insertEdge(Vertex<V> v, Vertex<V> u, E data){
        InnerVertex<V> vInnerVertex = validate(v);
        InnerVertex<V> uInnerVertex = validate(u);
        if(getEdge(u,v) == null){
            InnerEdge<E> edge = new InnerEdge<E>(vInnerVertex,uInnerVertex,data);
            vInnerVertex.getOutgoing().put(uInnerVertex,edge);
            uInnerVertex.getIncoming().put(vInnerVertex,edge);
            edge.setPos(edgeList.addBack(edge));
            return edge;
        }else{
            throw new IllegalArgumentException("Edge from V to U exists!");
        }
    }

    public Vertex<V> removeVertex(Vertex<V> v){
        InnerVertex<V> vert = validate(v);
        for (InnerVertex<V> vertex:vert.getOutgoing().keySet()){
            vertex.getIncoming().remove(vert);
        }
        if (isDirected) {
            for (InnerVertex<V> vertex : vert.getIncoming().keySet()) {
                vertex.getOutgoing().remove(vert);
            }
        }
        vertexList.remove(vert.getPos());
        return vert;
    }

    public Edge<E> removeEdge(Edge<E> e){
        InnerEdge<E> edge = validate(e);
        InnerVertex<V>[] verts = edge.getEndpoints();
        if(isDirected){
            if(verts[0].getOutgoing().get(verts[1]) == edge){
                verts[0].getOutgoing().remove(verts[1]);
                verts[1].getIncoming().remove(verts[0]);
            }else if(verts[1].getOutgoing().get(verts[0]) == edge){
                verts[0].getIncoming().remove(verts[1]);
                verts[1].getOutgoing().remove(verts[0]);
            }
        }else{
            verts[0].getOutgoing().remove(verts[1]);
            verts[1].getOutgoing().remove(verts[0]);
        }
        edgeList.remove(edge.getPos());
        return edge;
    }

    public void printGraph(){
        if (isDirected) {
            for (InnerVertex<V> vert : vertexList.getIterable()) {
                System.out.print("Vert: " + vert.getElement());
                System.out.print(" ||Outgoing: ");
                for (InnerEdge<E> edge : vert.getOutgoing().values()) {
                    System.out.print("E: " + edge.getElement() + " V: " + getOpposite(vert, edge).getElement() + " ");
                }
                System.out.print(" ||Incoming: ");
                for (InnerEdge<E> edge : vert.getIncoming().values()) {
                    System.out.print("E: " + edge.getElement() + " V: " + getOpposite(vert, edge).getElement() + " ");
                }
                System.out.println();
            }
        }else{
            for (InnerVertex<V> vert : vertexList.getIterable()) {
                System.out.print("Vert: " + vert.getElement());
                System.out.print(" ||Connections: ");
                for (InnerEdge<E> edge : vert.getOutgoing().values()) {
                    System.out.print("E: " + edge.getElement() + " V: " + getOpposite(vert, edge).getElement() + " ");
                }
                System.out.println();
            }
        }

    }


}
