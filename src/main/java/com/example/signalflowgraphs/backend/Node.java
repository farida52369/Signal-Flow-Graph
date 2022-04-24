package com.example.signalflowgraphs.backend;

import java.util.LinkedList;
import java.util.List;

class N {
    public int id;
    public double gain;

    public N(int id, double gain) {
        this.id = id;
        this.gain = gain;
    }

    @Override
    public String toString() {
        return "N: " + (id + 1) + " *** gain: " + gain + "\n";
    }
}

// as HashMap<id, nodes>
public class Node {

    private final int id;
    private final List<N> nodes;

    public Node(int id) {
        this.id = id;
        this.nodes = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public List<N> getNodes() {
        return nodes;
    }

    public N getNode(int nodeId) {
        for (N n : nodes)
            if (n.id == nodeId)
                return n;
        return null;
    }

    public void addNewNode(N n) {
        nodes.add(n);
    }

    public boolean contains(int nodeId) {
        for (N n : nodes)
            if (n.id == nodeId)
                return true;

        return false;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Node ");
        s.append(id + 1).append("\n");
        for (N n : nodes)
            s.append(n);
        s.append("\n");
        return s.toString();
    }
}
