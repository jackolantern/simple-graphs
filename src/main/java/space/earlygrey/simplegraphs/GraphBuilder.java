package space.earlygrey.simplegraphs;

import java.util.Map.Entry;
import java.util.function.Supplier;

import space.earlygrey.simplegraphs.VertexTypes.SpatiallyEmbeddedVertex;
import space.earlygrey.simplegraphs.VertexTypes.SpatiallyEmbeddedVertex2D;

public class GraphBuilder{

    private GraphBuilder() {
    }

    public static <V, G extends Graph<V>> void buildCompleteGraph(G graph) {
        for (Entry<V, Node<V>> entry1 : graph.vertexMap.entrySet()) {
            for (Entry<V, Node<V>> entry2 : graph.vertexMap.entrySet()) {
                Node<V> a = entry1.getValue(), b = entry2.getValue();
                if (!a.equals(b)) {
                    Connection<V> e = a.getEdge(b);
                    if (e == null) {
                        graph.addEdge(a, b);
                    }
                    if (graph.isDirected()) {
                        e = b.getEdge(a);
                        if (e == null) {
                            graph.addEdge(b, a);
                        }
                    }
                }
            }
        }
    }


    public <V extends SpatiallyEmbeddedVertex2D, G extends Graph<V>> void buildGridGraph(G graph, Supplier<V> vertexSupplier) {
        int n = 10;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph.addVertex(vertexSupplier.get());
            }
        }

    }


}
