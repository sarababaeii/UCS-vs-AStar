import java.util.ArrayList;

public class Path {
    private ArrayList<Node> nodes;

    public Path() {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void print() {
        for (int i = nodes.size() - 1; i >= 0 ; i--) {
            System.out.print(nodes.get(i).toString());
            if (i != 0) {
                System.out.print(" -> ");
            }
        }
    }
}
