package com.example.signalflowgraphs.backend;

import java.util.*;

// To get Masons
// Works Fine!
public class Mason {

    private final List<Path> forwardPaths;
    private final List<Path> feedbackLoops;
    private final List<List<Path>> nonTouchingLoops;
    private final Graph graph;
    private double deltaVal;
    private double[] deltaForForwardPath;
    private final StringBuilder gainFP;
    private final StringBuilder gainFBL;
    private final StringBuilder gainNTL;

    public Mason(Graph graph) {
        this.forwardPaths = new LinkedList<>();
        this.feedbackLoops = new LinkedList<>();
        this.nonTouchingLoops = new LinkedList<>();

        // GRAPH
        this.graph = graph;
        this.deltaVal = 1;

        // Output
        this.gainFP = new StringBuilder();
        this.gainFBL = new StringBuilder();
        this.gainNTL = new StringBuilder();

        // Play
        initialize();
    }

    private void initialize() {
        getForwardPaths();
        findFeedbackLoops();
        getNonTouchingLoops();
        getDelta();
        getDeltaForForwardPaths();
    }

    static class Path {
        public List<Integer> paths;
        public double cost;

        public Path(List<Integer> paths, double cost) {
            this.paths = paths;
            this.cost = cost;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder("Path\n");
            s.append("The Cost: ").append(cost).append("\n");
            for (int n : paths)
                s.append(n + 1).append("  ");
            s.append("\n");
            return s.toString();
        }
    }

    // Forward Paths _____
    private void dfsFB(int src, int des, double cost, List<Integer> res, boolean[] visited) {
        if (src == des) {
            res.add(des);
            forwardPaths.add(new Path(new LinkedList<>(res), cost));
            return;
        }

        visited[src] = true;
        res.add(src);
        for (N n : graph.getGraph().get(src).getNodes()) {
            if (!visited[n.id]) {
                dfsFB(n.id, des, cost * n.gain, res, visited);
                res.remove(res.size() - 1);
            }
        }
        visited[src] = false;
    }

    private void getForwardPaths() {
        // DFS Stuff
        dfsFB(0, graph.size() - 1, 1.0, new LinkedList<>(), new boolean[graph.size()]);
    }

    // Feedback Loops ______
    private void findFeedbackLoops() {
        Stack<Integer> visited;
        for (Node node : graph.getGraph()) {
            for (N n : node.getNodes()) {
                visited = new Stack<>();
                visited.add(node.getId());
                // DFS Stuff
                dfsFBL(node.getId(), n, visited, 1.0);
            }
        }
    }

    private void dfsFBL(int src, N des, Stack<Integer> visited, double cost) {
        if (src == des.id) {
            if (isValid(visited))
                feedbackLoops.add(new Path(new LinkedList<>(visited), cost * des.gain));
            return;
        }
        // already Visited
        if (visited.contains(des.id)) return;

        visited.add(des.id);
        cost *= des.gain;
        for (N n : graph.getGraph().get(des.id).getNodes()) {
            dfsFBL(src, n, visited, cost);
        }
        visited.pop();
    }

    private boolean isValid(Stack<Integer> newLoop) {
        for (Path loop : feedbackLoops) {
            if (loop.paths.size() == newLoop.size()) {
                List<Integer> path = new LinkedList<>(loop.paths);
                for (int val : newLoop) {
                    path.remove(Integer.valueOf(val));
                }
                if (path.isEmpty()) return false;
            }
        }
        return true;
    }

    // Non_touching Loops ______
    private void getNonTouchingLoops() {
        findNonTouchingLoops(feedbackLoops, 0);
    }

    private void findNonTouchingLoops(List<Path> feedBack, int n) {
        List<Path> nextLoop = new LinkedList<>();
        Set<List<Integer>> visited = new HashSet<>();
        boolean moveForward = false;
        for (Path path : feedBack) {
            for (Path feedbackLoop : feedbackLoops) {
                List<Integer> temp = new LinkedList<>();
                temp.addAll(path.paths);
                temp.addAll(feedbackLoop.paths);

                if (isNonTouchingLoop(temp)) {
                    Collections.sort(temp);
                    System.out.println(temp);
                    if (!visited.contains(temp)) {
                        visited.add(temp);
                        moveForward = true;
                        double gain = path.cost * feedbackLoop.cost;
                        nextLoop.add(new Path(new LinkedList<>(temp), gain));
                        try {
                            nonTouchingLoops.get(n).add(new Path(new LinkedList<>(temp), gain));
                        } catch (IndexOutOfBoundsException e) {
                            nonTouchingLoops.add(n, new LinkedList<>());
                            nonTouchingLoops.get(n).add(new Path(new LinkedList<>(temp), gain));
                        }
                    }
                }
            }
        }
        if (moveForward) findNonTouchingLoops(nextLoop, ++n);
    }

    private boolean isNonTouchingLoop(List<Integer> temp) {
        boolean flag = false;
        for (int i = 0; i < temp.size() - 1; i++) {
            for (int j = i + 1; j < temp.size(); j++) {
                if (temp.get(i).equals(temp.get(j))) {
                    flag = true;
                    break;
                }
            }
            if (flag) return false;
        }
        return true;
    }

    // Delta ______
    public void getDelta() {
        StringBuilder s = new StringBuilder("1 - ");
        deltaVal -= getSum(feedbackLoops, s);

        for (int i = 0; i < nonTouchingLoops.size(); i++) {
            if (i % 2 == 0) s.append(" + ");
            else s.append(" - ");
            double val = getSum(nonTouchingLoops.get(i), s);
            if (i % 2 == 0) deltaVal += val;
            else deltaVal -= val;

        }
    }

    private double getSum(List<Path> loops, StringBuilder s) {
        s.append("[");
        double gain = 0;
        for (int i = 0; i < loops.size(); i++) {
            double value = loops.get(i).cost;
            if (i < loops.size() - 1)
                s.append(value).append(" + ");
            else s.append(value);
            gain += value;
        }
        s.append("]");
        return gain;
    }

    // Delta 1, 2, ..
    public void getDeltaForForwardPaths() {
        this.deltaForForwardPath = new double[forwardPaths.size()];
        Arrays.fill(deltaForForwardPath, 1.0);
        // Start!
        for (int i = 0; i < forwardPaths.size(); i++) {
            // StringBuilder s = new StringBuilder("1 - ");
            double val = 0;

            // Feed Back Loops __ One Loop
            for (Path oneLoop : feedbackLoops) {
                List<Integer> temp = new LinkedList<>();
                temp.addAll(forwardPaths.get(i).paths);
                temp.addAll(oneLoop.paths);

                if (isNonTouchingLoop(temp)) {
                    // s.append(oneLoop.cost).append(" + ");
                    val += oneLoop.cost;
                }
            }
            deltaForForwardPath[i] -= val;

            // Feed Back Loops  __ Non-Touching Loops
            for (int j = 0; j < nonTouchingLoops.size(); j++) {
                val = 0;
                // String hereString = null;
                for (Path path : nonTouchingLoops.get(j)) {
                    List<Integer> temp = new LinkedList<>();
                    temp.addAll(forwardPaths.get(i).paths);
                    temp.addAll(path.paths);

                    if (isNonTouchingLoop(temp)) {
                        val += path.cost;
                    }
                }

                if (j % 2 == 0) {
                    deltaForForwardPath[i] += val;
                } else {
                    deltaForForwardPath[i] -= val;
                }
            }
        }
    }

    // OUTPUT ____
    public String getFP() {
        StringBuilder FP = new StringBuilder();
        for (int i = 0; i < forwardPaths.size(); i++) {
            FP.append(i + 1).append(") ");
            gainFP.append(i + 1).append(") ").append(forwardPaths.get(i).cost);
            for (int n : forwardPaths.get(i).paths) {
                FP.append(n + 1).append(" ");
            }
            FP.append("\n");
            gainFP.append("\n");
        }
        return FP.toString();
    }

    public String getGainFP() {
        //
        return gainFP.toString();
    }

    public String getFBL() {
        StringBuilder FBL = new StringBuilder();
        for (int i = 0; i < feedbackLoops.size(); i++) {
            FBL.append(i + 1).append(") ");
            gainFBL.append(i + 1).append(") ").append(feedbackLoops.get(i).cost);

            for (int n : feedbackLoops.get(i).paths) {
                FBL.append(n + 1).append(" ");
            }
            FBL.append("\n");
            gainFBL.append("\n");
        }
        return FBL.toString();
    }

    public String getGainFBL() {
        //
        return gainFBL.toString();
    }

    public String getNTL() {
        nonTouchingLoops.forEach(n -> {
            System.out.println("Start");
            n.forEach(System.out::println);
        });
        StringBuilder NTL = new StringBuilder();
        for (int i = 0; i < nonTouchingLoops.size(); i++) {
            NTL.append(i + 2).append(" Non Touching Loops").append("\n");
            gainNTL.append(i + 2).append(" Loops").append("\n");
            for (int j = 0; j < nonTouchingLoops.get(i).size(); j++) {
                NTL.append(j + 1).append(") ");
                gainNTL.append(j + 1).append(") ").append(nonTouchingLoops.get(i).get(j).cost);
                for (int p : nonTouchingLoops.get(i).get(j).paths) {
                    NTL.append(p + 1).append(" ");
                }
                NTL.append("\n");
                gainNTL.append("\n");
            }
            NTL.append("\n");
            gainNTL.append("\n");
        }
        return NTL.toString();
    }

    public String getGainNTL() {
        //
        return gainNTL.toString();
    }

    public String output() {
        StringBuilder output = new StringBuilder();
        output.append("C(s)/R(s) = ").append("\u03A3P\u0394/\u0394 = ");
        double finalVal = 0;
        for (int i = 0; i < forwardPaths.size(); i++) {
            finalVal += (forwardPaths.get(i).cost * deltaForForwardPath[i]);
        }
        finalVal /= deltaVal;
        output.append(String.format("%.4f", finalVal));
        return output.toString();
    }

    public String getValues() {
        StringBuilder s = new StringBuilder();
        s.append("\u0394 \u2192 ").append(deltaVal).append("\n");

        s.append("\nFor Forward Paths:\n");
        for (int i = 0; i < deltaForForwardPath.length; i++) {
            s.append("\u0394").append(i + 1).append(" \u2192 ").append(deltaForForwardPath[i]).append("\n");
        }
        return s.toString();
    }
}
