import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Comparator;

public class Main {
    public static Double[][] RECTANGLE;
    public static int LENGTH, WIDTH;

    public static void main(String[] args) throws FileNotFoundException {
        input();
        Problem problem = getProblemInfo();
        Path solution = AStarSearch(problem);
        if (solution == null) {
            System.out.println("There is no path from the initial square to the goal square.");
        } else {
            solution.print();
        }
    }

    public static void input() throws FileNotFoundException {
        File file = new File("Environment.txt");
        Scanner scanner = new Scanner(file);
        ArrayList<String> rows = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            rows.add(s);
        }
        LENGTH = rows.size();
        WIDTH = rows.get(0).split(",").length;
        RECTANGLE = new Double[LENGTH][WIDTH];
        for (int i = 0; i < LENGTH; i++) {
            String[] row = rows.get(i).split(",");
            for (int j = 0; j < WIDTH; j++) {
                RECTANGLE[i][j] = Double.parseDouble(row[j]);
            }
        }
    }

    public static Problem getProblemInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Coordinates of beginning:");
        int startX = scanner.nextInt(), startY = scanner.nextInt();
        System.out.println("Coordinates of destination:");
        int endX = scanner.nextInt(), endY = scanner.nextInt();
        return new Problem(
                new State(startX, startY),
                new State(endX, endY),
                new Action[]{Action.RIGHT, Action.LEFT, Action.UP, Action.BOTTOM});
    }

    public static Node createNode(int x, int y, Node parent) {
        if (x < 0 || y < 0 || x >= LENGTH || y >= WIDTH || RECTANGLE[x][y] != 0) {
            return null;
        }
        return new Node(x, y, parent);
    }

    public static Node getChild(Node node, Action action) {
        int x = node.getState().getX(), y = node.getState().getY();
        switch (action){
            case UP:
                return createNode( x - 1, y, node);
            case BOTTOM:
                return createNode(x + 1, y, node);
            case RIGHT:
                return createNode(x, y - 1, node);
            case LEFT:
                return createNode(x, y + 1, node);
        }
        return null;
    }

    public static Path AStarSearch(Problem problem) {
        PriorityQueue<Node> frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return o1.compareTo(o2);
            }
        });
        Node node = createNode(problem.getInitialState().getX(), problem.getInitialState().getY(), null);
        if (node == null) {
            return null;
        }
        frontier.add(node);
        Set<State> explored = new HashSet<>();
        while (true) {
            if (frontier.isEmpty()) {
                return null;
            }
            node = frontier.peek();
            frontier.remove(node);
            explored.add(node.getState());
            if (problem.goalTest(node)) {
                return trace(problem, node);
            }
            for (Action action: problem.getActions()) {
                expand(problem, frontier, explored, node, action);
            }
        }
    }

    public static void expand(Problem problem, PriorityQueue<Node> frontier, Set<State> explored, Node node, Action action) {
        Node child = getChild(node, action);
        if (child == null || explored.contains(child.getState())) {
            return;
        }
        Node addedChild = alreadyAdded(frontier, child);
        int pathCost = node.getPathCost() + action.getCost();
        if (addedChild == null) {
            updatePathCost(problem, frontier, child, pathCost);
        } else if (addedChild.getPathCost() > pathCost) {
            frontier.remove(addedChild);
            addedChild.setParent(node);
            updatePathCost(problem, frontier, addedChild, pathCost);
        }
    }

    public static Node alreadyAdded(PriorityQueue<Node> queue, Node node) {
        for (Node n: queue) {
            if (n.equals(node)) {
                return n;
            }
        }
        return null;
    }

    public static void updatePathCost(Problem problem, PriorityQueue<Node> frontier, Node node, int pathCost) {
        node.setPathCost(pathCost);
        node.setFunction(pathCost + problem.heuristic(node));
        frontier.add(node);
    }

    public static Path trace(Problem problem, Node node){
        Path path = new Path();
        while (!node.getState().equals(problem.getInitialState())) {
            path.addNode(node);
            node = node.getParent();
        }
        path.addNode(node);
        return path;
    }
}