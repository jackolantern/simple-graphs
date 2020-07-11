package space.earlygrey.simplegraphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Node<V> {

    //================================================================================
    // Graph structure related members
    //================================================================================

    final Graph<V> graph;
    final int idHash;
    final V object;

    Map<Node<V>, Connection<V>> neighbours = new LinkedHashMap<>();
    ArrayList<Connection<V>> outEdges = new ArrayList<>(); // List for fast iteration

    //================================================================================
    // Node map fields
    //================================================================================

    final int objectHash;
    int mapHash;
    Node<V> nextInOrder = null, prevInOrder = null;
    Node<V> nextInBucket = null;

    //================================================================================
    // Constructor
    //================================================================================

    Node(V v, Graph<V> graph, int objectHash) {
        this.object = v;
        this.graph = graph;
        this.objectHash = objectHash;
        idHash = System.identityHashCode(this);
    }

    //================================================================================
    // Internal methods
    //================================================================================

    Connection<V> getEdge(Node<V> v) {
        return neighbours.get(v);
    }

    Connection<V> addEdge(Node<V> v, float weight) {
        Connection<V> edge = neighbours.get(v);
        if (edge == null) {
            edge = graph.edgeSupplier.get();
            edge.set(this, v, weight);
            neighbours.put(v, edge);
            outEdges.add(edge);
            return edge;
        } else {
            edge.setWeight(weight);
        }
        return edge;
    }
    Connection<V> removeEdge(Node<V> v) {
        Connection<V> edge = neighbours.remove(v);
        if (edge == null) return null;
        // loop backwards to make Graph#removeNode faster
        for (int j = outEdges.size()-1; j >= 0; j--) {
            Connection<V> connection = outEdges.get(j);
            if (connection.equals(edge)) {
                outEdges.remove(j);
                break;
            }
        }
        return edge;
    }

    void disconnect() {
        neighbours.clear();
        outEdges.clear();
    }

    //================================================================================
    // Public Methods
    //================================================================================

    public Collection<Connection<V>> getConnections() {
        return outEdges;
    }

    public V getObject() {
        return object;
    }

    //================================================================================
    // Algorithm fields and methods
    //================================================================================

    //util fields for algorithms, don't store data in them
    boolean visited, seen;
    float distance;
    float estimate;
    Node<V> prev;
    int i, lastRunID;

    boolean resetAlgorithmAttribs(int runID) {
        if (runID == this.lastRunID) return false;
        visited = false;
        prev = null;
        distance = Float.MAX_VALUE;
        estimate = 0;
        i = 0;
        seen = false;
        this.lastRunID = runID;
        return true;
    }


    //================================================================================
    // Heap fields
    //================================================================================

    int heapIndex;
    float heapValue;

    //================================================================================
    // Misc
    //================================================================================


    @Override
    public boolean equals(Object o) {
        return o == this;
    }


    @Override
    public int hashCode() {
        return idHash;
    }

    @Override
    public String toString() {
        return "["+object+"]";
    }
}
