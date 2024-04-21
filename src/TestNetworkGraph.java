import com.mxgraph.layout.mxFastOrganicLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.Graph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultWeightedEdge;

import javax.swing.*;
import java.util.Random;

public class TestNetworkGraph {
    private final GenerateNetworkGraph generator;
    private Graph<String, DefaultWeightedEdge> graph;
    private static final double p = 0.95; //prawdopodobienstwo nieuszkodzenia krawedzi
    public TestNetworkGraph() {
        generator = new GenerateNetworkGraph();
    }

    public void test(int capacityStartingValue, int capacityDelta, int concentrationStartingValue, int concentrationDelta) {

        graph = generator.generate(capacityStartingValue, capacityDelta, concentrationStartingValue, concentrationDelta);

        Random rand = new Random();
        for(DefaultWeightedEdge edge : graph.edgeSet()) {
            if(rand.nextDouble() > p) {
                graph.removeEdge(edge);
            }
        }

        System.out.println(calcT());
        showGraph();
    }

    private double calcT() {
        int[][] concentration = generator.getConcentrationMatrix();
        int[][] capacity = generator.getCapacityMatrix();

        int sum = 0, source, target;
        for(DefaultWeightedEdge edge : graph.edgeSet()) {
            source = Integer.parseInt(graph.getEdgeSource(edge));
            target = Integer.parseInt(graph.getEdgeTarget(edge));

            sum += concentration[source][target] / (capacity[source][target] - concentration[source][target]);
        }

        int G = 0;
        for(int i = 0; i < GenerateNetworkGraph.size; i++) {
            for(int j = 0; j < GenerateNetworkGraph.size; j++) {
                G += concentration[i][j];
            }
        }

        return 1.0/G + sum;
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
        System.out.println("Krawędzie wraz z wagami:");
        for (DefaultWeightedEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);
            double weight = graph.getEdgeWeight(edge);
            System.out.println(source + " <-> " + target + ", Weight: " + weight);
        }
    }
}
