import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TestNetworkGraph {
    private GenerateNetworkGraph generator;
    private Graph<String, DefaultWeightedEdge> graph;
    private static final double p = 0.98; //probability of preserving the edge
    private static final double t = 0.4;
    public TestNetworkGraph() {}

    public int test(int startValue, int endValue, int delta) {

        generator = new GenerateNetworkGraph();
        graph = generator.generate(startValue, endValue, delta);

        removeEdges();

        double T = calcT();

        if(isConnected(graph) && T < t) {
            System.out.println("T= " + T);
            return 1;
        } else {
            return 0;
        }

        //
        ///showGraph();
    }

    private void removeEdges() {
        Random rand = new Random();
        Set<DefaultWeightedEdge> edges = new HashSet<>(graph.edgeSet());
        String source, target;
        for (DefaultWeightedEdge edge : edges) {
            if (rand.nextDouble() > p) {
                source = graph.getEdgeSource(edge);
                target = graph.getEdgeTarget(edge);
                graph.removeEdge(source, target);
                graph.removeEdge(target, source);
            }
        }
    }

    private double calcT() {
        int[][] concentration = generator.getConcentrationMatrix();
        int[][] capacity = generator.getCapacityMatrix();

        double sum = 0;
        int source, target;
        for(DefaultWeightedEdge edge : graph.edgeSet()) {
            source = Integer.parseInt(graph.getEdgeSource(edge));
            target = Integer.parseInt(graph.getEdgeTarget(edge));

            //TODO: fix equation
            sum += ((double) concentration[source][target] / (capacity[source][target] - concentration[source][target]));
        }

        int G = 0;
        for(int i = 0; i < GenerateNetworkGraph.size; i++) {
            for(int j = 0; j < GenerateNetworkGraph.size; j++) {
                G += concentration[i][j];
            }
        }

        return 1.0/G * sum;
    }

    private boolean isConnected(Graph<String, DefaultWeightedEdge> graph) {
        ConnectivityInspector<String, DefaultWeightedEdge> inspector = new ConnectivityInspector<>(graph);
        return inspector.isConnected();
    }

    private void showGraph() {

        JGraphXAdapter<String, DefaultWeightedEdge> graphAdapter = new JGraphXAdapter<>(graph);

        graph.edgeSet().forEach(edge -> {
            String label = String.valueOf((int) graph.getEdgeWeight(edge));
            graphAdapter.getEdgeToCellMap().get(edge).setValue(label);
        });

        mxIGraphLayout layout = new mxFastOrganicLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        mxGraphComponent graphComponent = new mxGraphComponent(graphAdapter);

        JFrame frame = new JFrame();
        frame.getContentPane().add(graphComponent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void printGraph() {
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            double weight = graph.getEdgeWeight(edge);
            System.out.println(source + " <-> " + target + ", Weight: " + weight);
        }
    }
}
