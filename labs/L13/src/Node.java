class Node {
    private final String name;
    private boolean visited;

    Node(String name) {
        this.name = name;
        this.visited = false;
    }

    String getName() {
        return name;
    }

    void reset() {this.visited = false;}

    void setVisited() { this.visited = true; }

    boolean isVisited() {
        return visited;
    }

    public String toString() {
        return name;
    }

    public boolean equals(Object o) {
        if (o instanceof Node) {
            Node that = (Node) o;
            return name.equals(that.getName());
        } else return false;
    }

    public int hashCode() {
        return name.hashCode();
    }
}