public class Problem {
    private State initialState;
    private State goalState;
    private Action[] actions;

    public Problem(State initialState, State goalState, Action[] actions) {
        this.initialState = initialState;
        this.goalState = goalState;
        this.actions = actions;
    }

    public State getInitialState() {
        return initialState;
    }

    public Action[] getActions() {
        return actions;
    }

    public boolean goalTest(Node node) {
        return node.getState().equals(goalState);
    }
}