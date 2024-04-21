import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateNetworkGraph {
    public final static int size = 20;
    private int[][] capacityMatrix; //more important
    private int[][] concentrationMatrix; //data flow values
    public GenerateNetworkGraph() {}

    public int[][] getConcentrationMatrix() {
        return concentrationMatrix;
    }

    public int[][] getCapacityMatrix() {
        return capacityMatrix;
    }

    public void setConcentrationMatrix(int[][] concentrationMatrix) {

    }

    public void setCapacityMatrix(int[][] capacityMatrix) {

    }

    public Graph<String, DefaultWeightedEdge> generate(int startValue, int endValue, int delta) {

        Graph<String, DefaultWeightedEdge> directedGraph = new DirectedWeightedMultigraph<>(DefaultWeightedEdge.class);

        for(int i = 0; i < size; i++) {
            directedGraph.addVertex(Integer.toString(i));
        }

        List<Point> vertexes = createVertexes();

        List<int[][]> res = createMatrix(vertexes, startValue, endValue, delta);
        capacityMatrix = res.get(0);
        concentrationMatrix = res.get(1);

        int value;
        for(Point v : vertexes) {
            value = capacityMatrix[v.x][v.y];
            directedGraph.setEdgeWeight(directedGraph.addEdge(Integer.toString(v.x), Integer.toString(v.y)), value);
            directedGraph.setEdgeWeight(directedGraph.addEdge(Integer.toString(v.y), Integer.toString(v.x)), value);

        }

        return directedGraph;
    }

    private List<int[][]> createMatrix(List<Point> vertexes, int startValue, int endValue, int delta) {
        int[][] capacityMatrix = new int[size][size];
        int[][] concentrationMatrix = new int[size][size];
        Random random = new Random();

        int i, j, value, diff;
        for (Point v : vertexes) {

            value = random.nextInt(endValue + startValue) + startValue;
            diff = random.nextInt(delta) + 1;

            capacityMatrix[v.x][v.y] = value + diff;
            capacityMatrix[v.y][v.x] = value + diff;

            concentrationMatrix[v.x][v.y] = value;
            concentrationMatrix[v.y][v.x] = value;
        }

        List<int[][]> res = new ArrayList<>();
        res.add(capacityMatrix);
        res.add(concentrationMatrix);
        return res;
    }

    private List<Point> createVertexes() {
        List<Point> vertexes = new ArrayList<>();

        vertexes.add(new Point(0, 2));
        vertexes.add(new Point(1, 2));
        vertexes.add(new Point(2, 3));
        vertexes.add(new Point(3, 4));
        vertexes.add(new Point(4, 5));

        vertexes.add(new Point(1, 5));

        vertexes.add(new Point(5, 7));
        vertexes.add(new Point(7, 6));

        vertexes.add(new Point(6, 8));

        vertexes.add(new Point(4, 9));
        vertexes.add(new Point(3, 9));

        vertexes.add(new Point(9, 10));
        vertexes.add(new Point(9, 11));
        vertexes.add(new Point(9, 12));

        vertexes.add(new Point(11, 12));
        vertexes.add(new Point(12, 13));
        vertexes.add(new Point(13, 14));
        vertexes.add(new Point(14, 15));
        vertexes.add(new Point(15, 9));
        vertexes.add(new Point(14, 16));
        vertexes.add(new Point(16, 17));
        vertexes.add(new Point(17, 18));
        vertexes.add(new Point(17, 19));
        vertexes.add(new Point(16, 18));
        vertexes.add(new Point(19, 10));

        return vertexes;
    }
}
