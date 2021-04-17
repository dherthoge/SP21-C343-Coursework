public class Edge {
    private final Node source;
    private final Node destination;
    private int weight;

    Edge (Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    Edge (Node source, Node destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    int getWeight () { return weight; }

    Node getSource () { return source; }

    Node getDestination() { return destination; }

    Edge flip () { return new Edge(destination,source,weight); }

    public boolean equals (Object o) {
        if (o instanceof Edge) {
            Edge other = (Edge)o;
            return other.source.equals(source) && other.destination.equals(destination);
        }
        return false;
    }

    public int hashCode () {
        return source.hashCode() + 7 * destination.hashCode();
    }

    public String toString () {
        return String.format("%s --%d--> %s", source, weight, destination);
    }
}
