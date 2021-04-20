public class Edge {
    private final Node source, destination;

    Edge (Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    Node getDestination() { return destination; }

    public String toString () {
        return String.format("%s --> %s", source, destination);
    }

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
}
