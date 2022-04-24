package com.example.signalflowgraphs.backend;

import java.util.LinkedList;
import java.util.List;

// as HashMap<indexOfNode, Node>
public class Graph {

    private final List<Node> graph;

    public Graph() {
        //
        this.graph = new LinkedList<>();
    }

    public List<Node> getGraph() {
        //
        return graph;
    }

    public Node getNode(int id) {
        //
        return graph.get(id);
    }

    public void addNewNode(int id) {
        //
        graph.add(new Node(id));
    }

    public boolean addNewBranch(int node_1, int node_2, double gain) {
        Node node = graph.get(node_1);
        if (node.contains(node_2))
            return false;
        node.addNewNode(new N(node_2, gain));
        return true;
    }

    public int size() {
        return graph.size();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Graph\n");
        for (Node n : graph) {
            s.append(n).append("\n");
        }
        return s.toString();
    }
}
