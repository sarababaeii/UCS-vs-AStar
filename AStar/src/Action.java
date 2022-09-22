public enum Action {
    RIGHT(1),
    LEFT(1),
    UP(2),
    BOTTOM(3);

    private int cost;

    Action(int cost) {
        this. cost = cost;
    }

    public int getCost() {
        return cost;
    }
}