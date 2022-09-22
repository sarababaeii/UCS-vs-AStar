import java.util.Objects;

public class Node {
    private State state;
    private Node parent;
    private int pathCost = 0;
    private int function = 0;

    public Node(int x, int y, Node parent) {
        this.state = new State(x, y);
        this.parent = parent;
    }

    public State getState() {
        return state;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getPathCost() {
        return pathCost;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public int getFunction() {
        return function;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return state.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(state, node.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }

    public int compareTo(Node node) {
        return Integer.compare(function, node.function);
    }
}
